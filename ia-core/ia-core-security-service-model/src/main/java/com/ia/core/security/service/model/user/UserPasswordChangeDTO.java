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

  @NotNull
  private String userCode;
  @NotNull
  private String oldPassword;
  @NotNull
  private String newPassword;

  @Override
  public String toString() {
    return String.format("Change Password (%s)", userCode);
  }

}
