package com.ia.core.security.view.user;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.model.user.UserPasswordChangeDTO;
import com.ia.core.security.service.model.user.UserPasswordEncoder;
import com.ia.core.security.service.model.user.UserPasswordResetDTO;
import com.ia.core.security.service.model.user.UserTranslator;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseManager;
import com.ia.core.service.dto.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Israel Araújo
 */
@Service
public class UserManager
  extends DefaultSecuredViewBaseManager<UserDTO> {

  /**
   * Construtor padrão
   *
   * @param client               cliente para o usuário
   * @param authorizationManager {@link CoreSecurityAuthorizationManager}
   * @param passwordEncoder      {@link UserPasswordEncoder}
   */
  public UserManager(UserManagerConfig config) {
    super(config);
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
    change.setNewPassword(UserPasswordEncoder.encrypt(getConfig()
        .getPasswordEncoder().encode(change.getNewPassword()),
                                                      user.getUserCode()));
    getClient().changePassword(change);
  }

  @Override
  public UserManagerConfig getConfig() {
    return (UserManagerConfig) super.getConfig();
  }

  @Override
  public UserClient getClient() {
    return getConfig().getClient();
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
