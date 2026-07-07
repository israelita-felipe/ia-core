package com.ia.core.security.service.model.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author Israel Araújo
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordResetDTO
  implements Serializable {
  /** Serial UID */
  private static final long serialVersionUID = -19560738760061623L;
  /**
   * Código de usuário anônimo
   */
  public static final String DEFAULT_USER_CODE_REQUESTER = "ANONYMOUS";

  @NotNull(message = "{validation.user.passwordreset.usercode.required}")
  private String userCode;

  @Default
  private String userCodeRequester = DEFAULT_USER_CODE_REQUESTER;

  @Override
  public String toString() {
    return String.format("Reset Password (%s), solicitado por %s", userCode,
                         userCodeRequester);
  }

  @SuppressWarnings("javadoc")
  public static class CAMPOS {
    public static final String USER_CODE = "userCode";
    public static final String USER_CODE_REQUESTER = "userCodeRequester";

    public static java.util.Set<String> values() {
      return java.util.Set.of(USER_CODE, USER_CODE_REQUESTER);
    }
  }

}
