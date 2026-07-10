package com.ia.core.security.service.model.user;

import com.ia.core.service.dto.DTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * DTO para redefinição de senha de usuário.
 * <p>
 * Responsável por transferir dados necessários para redefinição de senha,
 * incluindo código do usuário e código do solicitante.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see UserTranslator
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordResetDTO
    implements DTO<Serializable> {
    /**
     * Código de usuário anônimo
     */
    public static final String DEFAULT_USER_CODE_REQUESTER = "ANONYMOUS";
    /**
     * Serial UID
     */
    private static final long serialVersionUID = -19560738760061623L;
    @NotNull(message = UserTranslator.VALIDATION.PASSWORD_RESET_USER_CODE_REQUIRED)
    private String userCode;

    @Default
    private String userCodeRequester = DEFAULT_USER_CODE_REQUESTER;

    @Override
    public UserPasswordResetDTO cloneObject() {
        return toBuilder().build();
    }

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
