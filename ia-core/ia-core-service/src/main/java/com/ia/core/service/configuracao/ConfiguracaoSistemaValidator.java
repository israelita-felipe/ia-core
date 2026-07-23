package com.ia.core.service.configuracao;

import com.ia.core.service.configuracao.dto.ConfiguracaoSistemaDTO;
import com.ia.core.service.rules.BusinessRule;
import com.ia.core.service.translator.Translator;

import java.util.List;

/**
 * Validador para ConfiguracaoSistemaDTO.
 * <p>
 * Implementa as regras de validação para configurações do sistema.
 * Segue o padrão ADR-019 (Service Validator).
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class ConfiguracaoSistemaValidator<T extends ConfiguracaoSistemaDTO<?>>
    extends com.ia.core.service.validators.ServiceValidator<T> {

    /**
     * Construtor com tradutor.
     *
     * @param translator tradutor para mensagens de validação
     */
    public ConfiguracaoSistemaValidator(Translator translator) {
        super(translator);
    }

    public ConfiguracaoSistemaValidator(Translator translator, List<BusinessRule<T>> businessRules) {
        super(translator, businessRules);
    }
}
