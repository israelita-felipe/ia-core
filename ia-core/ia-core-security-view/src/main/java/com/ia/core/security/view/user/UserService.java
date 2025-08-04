package com.ia.core.security.view.user;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.model.user.UserPasswordChangeDTO;
import com.ia.core.security.service.model.user.UserPasswordEncoder;
import com.ia.core.security.service.model.user.UserPasswordResetDTO;
import com.ia.core.security.service.model.user.UserTranslator;
import com.ia.core.security.view.service.DefaultSecuredViewBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.view.client.BaseClient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Israel Araújo
 */
@Service
public class UserService
  extends DefaultSecuredViewBaseService<UserDTO> {

  /**
   * Codificador de senha do usuário
   */
  private final UserPasswordEncoder passwordEncoder;

  /**
   * Construtor padrão
   *
   * @param client               cliente para o usuário
   * @param authorizationManager {@link CoreSecurityAuthorizationManager}
   * @param passwordEncoder      {@link UserPasswordEncoder}
   */
  public UserService(BaseClient<UserDTO> client,
                     CoreSecurityAuthorizationManager authorizationManager,
                     UserPasswordEncoder passwordEncoder) {
    super(client, authorizationManager);
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Atualiza a senha do usuário
   *
   * @param user   {@link UserDTO} usuário
   * @param change {@link UserPasswordChangeSuportDTO} requisitada.
   */
  public void changePassword(UserDTO user,
                             UserPasswordChangeSuportDTO change) {
    if (!Objects.equals(change.getNewPassword(),
                        change.getConfirmPassword())) {
      throw new IllegalArgumentException("A nova senha não confere");
    }
    change.setUserCode(user.getUserCode());
    change.setOldPassword(UserPasswordEncoder
        .encrypt(change.getOldPassword(), user.getUserCode()));
    change.setNewPassword(UserPasswordEncoder
        .encrypt(passwordEncoder.encode(change.getNewPassword()),
                 user.getUserCode()));
    getClient().changePassword(change);
  }

  @Override
  public UserClient getClient() {
    return (UserClient) super.getClient();
  }

  @Override
  public String getFunctionalityTypeName() {
    return UserTranslator.USER;
  }

  /**
   * Reseta a senha do usuário
   *
   * @param user          {@link UserDTO} do usuário
   * @param userRequester {@link UserDTO} requisitante
   */
  public void resetPassword(UserDTO user, UserDTO userRequester) {
    getClient().resetPassword(UserPasswordResetDTO.builder()
        .userCode(user.getUserCode())
        .userCodeRequester(userRequester.getUserCode()).build());
  }

  /**
   * {@link DTO} para alteração de senha de usuário
   */
  @Data
  @EqualsAndHashCode(callSuper = true)
  @SuperBuilder(toBuilder = true)
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UserPasswordChangeSuportDTO
    extends UserPasswordChangeDTO {
    /** Serial UID */
    private static final long serialVersionUID = 1882961256388922611L;
    /** Confirmação de senha */
    private String confirmPassword;

    /**
     * Cópia do dto atual
     *
     * @return {@link UserPasswordChangeSuportDTO}
     */
    public UserPasswordChangeSuportDTO copy() {
      return toBuilder().build();
    }
  }
}
