package com.ia.core.security.service.model.user;

import com.ia.core.service.dto.DTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * DTO para alteração de senha de usuário.
 * <p>
 * Responsável por transferir dados necessários para alteração de senha,
 * incluindo código do usuário, senha antiga e nova senha.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see UserTranslator
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordChangeDTO
  implements DTO<Serializable> {
  /** Serial UID */
  private static final long serialVersionUID = -19560738760061623L;

  @NotNull(message = UserTranslator.VALIDATION.PASSWORD_CHANGE_USER_CODE_REQUIRED)
  private String userCode;
  @NotNull(message = UserTranslator.VALIDATION.PASSWORD_CHANGE_OLD_PASSWORD_REQUIRED)
  private String oldPassword;
  @NotNull(message = UserTranslator.VALIDATION.PASSWORD_CHANGE_NEW_PASSWORD_REQUIRED)
  private String newPassword;

  @Override
  public UserPasswordChangeDTO cloneObject() {
    return toBuilder().build();
  }

  @Override
  public String toString() {
    return String.format("Change Password (%s)", userCode);
  }

  /**
   * Field name constants
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS {
    public static final String USER_CODE = "userCode";
    public static final String OLD_PASSWORD = "oldPassword";
    public static final String NEW_PASSWORD = "newPassword";
  }

}
