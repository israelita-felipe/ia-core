package com.ia.core.communication.service.modelomensagem.validators;

import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.ServiceValidator;
import com.ia.core.service.validators.ValidatorScope;

/**
 * Validador para ModeloMensagemDTO.
 * <p>
 * Implementa as regras de validação específicas para o domínio de modelos de mensagens.
 *
 * @author Israel Araújo
 */
@ValidatorScope
public class ModeloMensagemValidator extends ServiceValidator<ModeloMensagemDTO> {

    /**
   * Construtor com tradutor e regras de negócio.
   *
   * @param translator   tradutor para mensagens de validação
   * @param businessRules lista de regras de negócio
   */
  public ModeloMensagemValidator(Translator translator,
      java.util.List<com.ia.core.service.rules.BusinessRule<ModeloMensagemDTO>> businessRules) {
    super(translator, businessRules);
  }
}
