package com.ia.core.communication.service.contatomensagem.validators;

import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.ServiceValidator;
import com.ia.core.service.validators.ValidatorScope;

/**
 * Validador para ContatoMensagemDTO.
 * <p>
 * Implementa as regras de validação específicas para o domínio de contatos de mensagens.
 *
 * @author Israel Araújo
 */
@ValidatorScope
public class ContatoMensagemValidator extends ServiceValidator<ContatoMensagemDTO> {


  /**
   * Construtor com tradutor e regras de negócio.
   *
   * @param translator   tradutor para mensagens de validação
   * @param businessRules lista de regras de negócio
   */
  public ContatoMensagemValidator(Translator translator,
      java.util.List<com.ia.core.service.rules.BusinessRule<ContatoMensagemDTO>> businessRules) {
    super(translator, businessRules);
  }
}
