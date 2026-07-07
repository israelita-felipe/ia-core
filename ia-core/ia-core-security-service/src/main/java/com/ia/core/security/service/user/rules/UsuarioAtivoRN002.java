package com.ia.core.security.service.user.rules;

import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.model.user.UserTranslator;
import com.ia.core.service.rules.BusinessRule;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.ValidationResult;
import com.ia.core.service.validators.ValidatorScope;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Regra de negócio RN002: Usuário ativo.
 * <p>
 * Código: USER_RN002
 * </p>
 * <p>
 * O usuário deve estar ativo (enabled = true) para realizar operações no sistema.
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@ValidatorScope
public class UsuarioAtivoRN002 implements BusinessRule<UserDTO> {

    @Getter
    private final Translator translator;

    /**
     * @param translator Translator para mensagens i18n
     */
    public UsuarioAtivoRN002(Translator translator) {
        this.translator = translator;
    }

    @Override
    public String getCode() {
        return "USER_RN002";
    }

    @Override
    public String getName() {
        return translator.getTranslation(UserTranslator.RULE.USUARIO_ATIVO + ".name");
    }

    @Override
    public String getDescription() {
        return translator.getTranslation(UserTranslator.RULE.USUARIO_ATIVO + ".description");
    }

    @Override
    public void validate(UserDTO dto, ValidationResult result) {
        if (dto == null) {
            return;
        }

        log.debug("Regra {} verificada para usuário: {}", getCode(), dto.getUserCode());
    }

    /**
     * Valida se o usuário está ativo.
     * Este método é chamado durante autenticação e operações críticas.
     *
     * @param dto DTO do usuário a ser validado
     * @return true se o usuário está ativo, false caso contrário
     */
    public boolean isUserActive(UserDTO dto) {
        if (dto == null) {
            return false;
        }
        return dto.isEnabled()
            && dto.isAccountNotExpired()
            && dto.isAccountNotLocked()
            && dto.isCredentialsNotExpired();
    }
}
