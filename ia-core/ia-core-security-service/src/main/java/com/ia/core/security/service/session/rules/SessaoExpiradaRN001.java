package com.ia.core.security.service.session.rules;

import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.model.user.UserTranslator;
import com.ia.core.service.rules.BusinessRule;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.ValidationResult;
import com.ia.core.service.validators.ValidatorScope;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * Regra de negócio: Sessão expira após inatividade.
 * <p>
 * Código: SESSION_RN001
 * </p>
 * <p>
 * A sessão do usuário deve ser invalidada após 30 minutos de inatividade.
 * Esta regra é verificada durante a autenticação e operações críticas.
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@ValidatorScope
public class SessaoExpiradaRN001 implements BusinessRule<UserDTO> {

    private static final int SESSION_TIMEOUT_MINUTES = 30;

    @Getter
    private final Translator translator;

    /**
     * @param translator Translator para mensagens i18n
     */
    public SessaoExpiradaRN001(Translator translator) {
        this.translator = translator;
    }

    @Override
    public String getCode() {
        return "SESSION_RN001";
    }

    @Override
    public String getName() {
        return translator.getTranslation(UserTranslator.RULE.SESSAO_EXPIRADA + ".name");
    }

    @Override
    public String getDescription() {
        return translator.getTranslation(UserTranslator.RULE.SESSAO_EXPIRADA + ".description");
    }

    @Override
    public void validate(UserDTO dto, ValidationResult result) {
        // Esta regra é para runtime validation, não para DTO validation
        log.debug("Regra {} verificada para sessão do usuário: {}",
            getCode(), dto != null ? dto.getUserCode() : "null");
    }

    /**
     * Verifica se a sessão expirou com base no último acesso.
     *
     * @param lastAccessTime Último horário de acesso do usuário
     * @return true se sessão expirou, false caso contrário
     */
    public boolean isSessionExpired(LocalDateTime lastAccessTime) {
        if (lastAccessTime == null) {
            return true;
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryTime = lastAccessTime.plusMinutes(SESSION_TIMEOUT_MINUTES);
        return now.isAfter(expiryTime);
    }
}
