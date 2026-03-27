package com.ia.core.communication.service.mensagem.validators;

import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.ServiceValidator;

/**
 * Validador para MensagemDTO.
 * <p>
 * Implementa as regras de validação específicas para o domínio de mensagens,
 * incluindo validações de negócio via BusinessRules.
 *
 * @author Israel Araújo
 */
public class MensagemValidator extends ServiceValidator<MensagemDTO> {

  /**
   * Construtor com tradutor.
   *
   * @param translator tradutor para mensagens de validação
   */
  public MensagemValidator(Translator translator) {
    super(translator);
    // Adicionar regras de negócio específicas
  }

  /**
   * Construtor com tradutor e regras de negócio.
   *
   * @param translator   tradutor para mensagens de validação
   * @param businessRules lista de regras de negócio
   */
  public MensagemValidator(Translator translator,
      java.util.List<com.ia.core.service.rules.BusinessRule<MensagemDTO>> businessRules) {
    super(translator, businessRules);
  }
}
