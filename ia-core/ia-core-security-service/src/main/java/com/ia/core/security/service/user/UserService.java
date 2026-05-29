package com.ia.core.security.service.user;

import com.ia.core.model.filter.FieldType;
import com.ia.core.security.model.user.User;
import com.ia.core.security.service.CrudSecuredBaseService;
import com.ia.core.security.service.exception.InvalidPasswordException;
import com.ia.core.security.service.exception.UserNotFountException;
import com.ia.core.security.service.model.user.*;
import com.ia.core.service.annotations.TransactionalWrite;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

/**
 * @author Israel Araújo
 */
@Slf4j
@Service
public class UserService
  extends CrudSecuredBaseService<User, UserDTO>
  implements UserUseCase {

  /**
   * @param repository
   * @param mapper
   * @param searchRequestMapper
   * @param translator
   * @param authorizationManager
   * @param logOperationService
   */
  public UserService(UserServiceConfig config) {
    super(config);
  }

  @Override
  public UserServiceConfig getConfig() {
    return (UserServiceConfig) super.getConfig();
  }

  @Override
  public User synchronize(User model)
    throws ServiceException {
    User user = super.synchronize(model);
    user.getPrivileges().forEach(privilege -> {
      privilege.setUser(user);
      privilege.getOperations().forEach(operation -> {
        operation.getContext().forEach(context -> {
          context.setPrivilegeOperation(operation);
        });
      });
    });
    return user;
  }

  /**
   * Altera a senha do usuário
   *
   * @param change {@link UserPasswordChangeDTO}
   * @throws ServiceException caso ocorra alguma exceção
   */
  @TransactionalWrite
  @Tool(description = "Altera a senha de um usuário existente no sistema após validar a senha atual. " +
                     "O processo inclui: busca do usuário pelo código, verificação da senha antiga " +
                     "(decriptografia e comparação), atualização para a nova senha (decriptografia e encriptografia) " +
                     "e persistência no banco de dados. A senha antiga deve corresponder exatamente à senha atual. " +
                     "Lança exceção se o usuário não for encontrado ou se a senha antiga estiver incorreta. " +
                     "Útil para usuários alterarem suas próprias senhas de forma segura.")
  public void changePassword(
          @ToolParam(description = "DTO contendo dados para alteração de senha: userCode (String, obrigatório, código do usuário), " +
                                   "oldPassword (String, obrigatório, senha atual do usuário), " +
                                   "newPassword (String, obrigatório, nova senha desejada). " +
                                   "A senha antiga será validada antes da alteração. " +
                                   "A nova senha deve seguir as políticas de segurança do sistema.",
                      required = true) UserPasswordChangeDTO change)
    throws ServiceException {
    ServiceException ex = new ServiceException();
    try {
      SearchRequestDTO searchRequest = UserDTO.getSearchRequest();
      searchRequest.getFilters()
          .add(FilterRequestDTO.builder().key("userCode")
              .operator(OperatorDTO.EQUAL).fieldType(FieldType.STRING)
              .value(change.getUserCode()).build());
      UserDTO user = findAll(searchRequest)
          .stream()
          .findFirst()
          .orElseThrow(() -> new UserNotFountException(change.getUserCode()));

      String decryptedOldPassword = UserPasswordEncoder
          .decrypt(change.getOldPassword(), change.getUserCode());
      if (getConfig().getPasswordEncoder().matches(decryptedOldPassword,
                                                   user.getPassword())) {
        user.setPassword(UserPasswordEncoder
            .decrypt(change.getNewPassword(), change.getUserCode()));
        save(user);
      } else {
        throw new InvalidPasswordException(change.getUserCode());
      }
    } catch (UserNotFountException | InvalidPasswordException e) {
      ex.add(e);
    }
    throwIfHasErrors(ex);
  }

  @Override
  public String getFunctionalityTypeName() {
    return UserTranslator.USER;
  }

  /**
   * Reseta a senha do usuário
   *
   * @param reset {@link UserPasswordResetDTO}
   * @throws ServiceException caso ocorra alguma exceção
   */
  @TransactionalWrite
  @Tool(description = "Reseta a senha de um usuário gerando uma nova senha segura aleatória. " +
                     "O processo inclui: busca do usuário pelo código, geração de uma nova senha " +
                     "criptograficamente segura (aleatória e forte), encriptografia da nova senha " +
                     "e persistência no banco de dados. A nova senha substitui a senha anterior " +
                     "sem necessidade de validação da senha antiga. Útil para recuperação de senha " +
                     "por administradores ou quando o usuário esqueceu sua senha. " +
                     "A nova senha gerada é registrada no log para fins de suporte.")
  public void resetPassword(
          @ToolParam(description = "DTO contendo dados para reset de senha: userCode (String, obrigatório, código do usuário). " +
                                   "A nova senha será gerada automaticamente pelo sistema de forma segura e aleatória. " +
                                   "Não é necessário fornecer a nova senha.",
                      required = true) UserPasswordResetDTO reset)
    throws ServiceException {
    ServiceException ex = new ServiceException();
    try {
      log.info("Reset de password: {}", reset);
      String newPassword = UserPasswordEncoder
          .generateDefaultSecureRandomPassword();
      log.info("New password: {}", newPassword);
      SearchRequestDTO searchRequest = UserDTO.getSearchRequest();
      searchRequest.getFilters()
          .add(FilterRequestDTO.builder().key("userCode")
              .operator(OperatorDTO.EQUAL).fieldType(FieldType.STRING)
              .value(reset.getUserCode()).build());
      UserDTO user = findAll(searchRequest)
          .stream()
          .findFirst()
          .orElseThrow(() -> new UserNotFountException(reset.getUserCode()));
      user.setPassword(getConfig().getPasswordEncoder()
          .encode(newPassword));
      save(user);
    } catch (UserNotFountException | InvalidPasswordException e) {
      ex.add(e);
    }
    throwIfHasErrors(ex);
  }
}
