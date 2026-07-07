package com.ia.core.security.service.user.rules.validation;

import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.model.user.UserTranslator;
import com.ia.core.service.rules.BusinessRule;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.Severity;
import com.ia.core.service.validators.ValidationError;
import com.ia.core.service.validators.ValidationResult;
import com.ia.core.service.validators.ValidatorScope;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Regra de negócio RN001: Senha deve ter mínimo 8 caracteres.
 * <p>
 * Código: USER_RN003
 * </p>
 * <p>
 * A senha do usuário deve ter no mínimo 8 caracteres para garantir segurança.
 * Aplica-se no registro e na recuperação de senha.
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@ValidatorScope
public class SenhaMinimaCaracteresRN003 implements BusinessRule<UserDTO> {

    private static final int MIN_PASSWORD_LENGTH = 8;

    @Getter
    private final Translator translator;

    /**
     * @param translator Translator para mensagens i18n
     */
    public SenhaMinimaCaracteresRN003(Translator translator) {
        this.translator = translator;
    }

    @Override
    public String getCode() {
        return "USER_RN003";
    }

    @Override
    public String getName() {
        return translator.getTranslation(UserTranslator.RULE.SENHA_MINIMA + ".name");
    }

    @Override
    public String getDescription() {
        return translator.getTranslation(UserTranslator.RULE.SENHA_MINIMA + ".description");
    }

    @Override
    public void validate(UserDTO dto, ValidationResult result) {
        if (dto == null || dto.getPassword() == null) {
            return;
        }

        if (dto.getPassword().length() < MIN_PASSWORD_LENGTH) {
            result.addError(new ValidationError(
                "password",
                translator.getTranslation(UserTranslator.RULE.SENHA_MINIMA),
                Severity.ERROR));
            log.debug("Regra {} violada: senha com {} caracteres (mínimo: {})",
                getCode(), dto.getPassword().length(), MIN_PASSWORD_LENGTH);
            return;
        }

        log.debug("Regra {} validada com sucesso", getCode());
    }
}
