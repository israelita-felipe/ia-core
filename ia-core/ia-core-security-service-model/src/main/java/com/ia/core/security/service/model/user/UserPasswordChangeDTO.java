package com.ia.core.security.service.model.user;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Israel Araújo
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordChangeDTO
  implements Serializable {
  /** Serial UID */
  private static final long serialVersionUID = -19560738760061623L;

  @NotNull(message = "{validation.user.passwordchange.usercode.required}")
  private String userCode;
  @NotNull(message = "{validation.user.passwordchange.oldpassword.required}")
  private String oldPassword;
  @NotNull(message = "{validation.user.passwordchange.newpassword.required}")
  private String newPassword;

  @Override
  public String toString() {
    return String.format("Change Password (%s)", userCode);
  }

}
