package com.ia.core.security.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ia.core.security.model.user.User;
import com.ia.core.security.service.DefaultSecuredBaseService;
import com.ia.core.security.service.exception.InvalidPasswordException;
import com.ia.core.security.service.exception.UserNotFountException;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.model.user.UserPasswordChangeDTO;
import com.ia.core.security.service.model.user.UserPasswordEncoder;
import com.ia.core.security.service.model.user.UserPasswordResetDTO;
import com.ia.core.security.service.model.user.UserTranslator;
import com.ia.core.service.dto.filter.FieldTypeDTO;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Israel Araújo
 */
@Slf4j
@Service
public class UserService
  extends DefaultSecuredBaseService<User, UserDTO> {

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

  /**
   * Altera a senha do usuário
   *
   * @param change {@link UserPasswordChangeDTO}
   * @throws ServiceException caso ocorra alguma exceção
   */
  @Transactional
  public void changePassword(UserPasswordChangeDTO change)
    throws ServiceException {
    SearchRequestDTO searchRequest = UserDTO.getSearchRequest();
    searchRequest.getFilters()
        .add(FilterRequestDTO.builder().key("userCode")
            .operator(OperatorDTO.EQUAL).fieldType(FieldTypeDTO.STRING)
            .value(change.getUserCode()).build());
    UserDTO user = findAll(searchRequest).get().findFirst()
        .orElseThrow(() -> new UserNotFountException(change.getUserCode()));

    String decryptedOldPassword = UserPasswordEncoder
        .decrypt(change.getOldPassword(), change.getUserCode());
    if (getConfig().getPasswordEncoder().matches(decryptedOldPassword,
                                                 user.getPassword())) {
      user.setPassword(UserPasswordEncoder.decrypt(change.getNewPassword(),
                                                   change.getUserCode()));
      save(user);
    } else {
      throw new InvalidPasswordException(change.getUserCode());
    }
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
  @Transactional
  public void resetPassword(UserPasswordResetDTO reset)
    throws ServiceException {
    log.info("Reset de password: {}", reset);
    String newPassword = UserPasswordEncoder
        .generateDefaultSecureRandomPassword();
    log.info("New password: {}", newPassword);
    SearchRequestDTO searchRequest = UserDTO.getSearchRequest();
    searchRequest.getFilters()
        .add(FilterRequestDTO.builder().key("userCode")
            .operator(OperatorDTO.EQUAL).fieldType(FieldTypeDTO.STRING)
            .value(reset.getUserCode()).build());
    UserDTO user = findAll(searchRequest).get().findFirst()
        .orElseThrow(() -> new UserNotFountException(reset.getUserCode()));
    user.setPassword(getConfig().getPasswordEncoder().encode(newPassword));
    save(user);
  }
}
