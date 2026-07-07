package com.ia.core.security.service.user.rules;

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
 * Regra de negócio RN001: Código de usuário único.
 * <p>
 * Código: USER_RN001
 * </p>
 * <p>
 * O código de usuário (userCode) deve ser único no sistema.
 * Nota: A validação de unicidade é feita no repositório via constraint UNIQUE.
 * Esta regra serve como placeholder para o padrão de validação.
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@ValidatorScope
public class UserCodeUnicoRN001 implements BusinessRule<UserDTO> {

    @Getter
    private final Translator translator;

    /**
     * @param translator Translator para mensagens i18n
     */
    public UserCodeUnicoRN001(Translator translator) {
        this.translator = translator;
    }

    @Override
    public String getCode() {
        return "USER_RN001";
    }

    @Override
    public String getName() {
        return translator.getTranslation(UserTranslator.RULE.CODIGO_UNICO + ".name");
    }

    @Override
    public String getDescription() {
        return translator.getTranslation(UserTranslator.RULE.CODIGO_UNICO + ".description");
    }

    @Override
    public void validate(UserDTO dto, ValidationResult result) {
        if (dto == null || dto.getUserCode() == null) {
            return;
        }

        // Validation is handled by database constraint (UNIQUE)
        // This rule is for documentation and future extensibility
        log.debug("Regra {} verificada para userCode: {}",
            getCode(), dto.getUserCode());
    }
}
