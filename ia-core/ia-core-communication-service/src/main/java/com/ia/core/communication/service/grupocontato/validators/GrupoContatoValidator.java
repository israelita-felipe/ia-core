package com.ia.core.communication.service.grupocontato.validators;

import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.ServiceValidator;
import com.ia.core.service.validators.ValidatorScope;

/**
 * Validador para GrupoContatoDTO.
 * <p>
 * Implementa as regras de validação específicas para o domínio de grupos de contatos.
 *
 * @author Israel Araújo
 */
@ValidatorScope
public class GrupoContatoValidator extends ServiceValidator<GrupoContatoDTO> {

  /**
   * Construtor com tradutor e regras de negócio.
   *
   * @param translator   tradutor para mensagens de validação
   * @param businessRules lista de regras de negócio
   */
  public GrupoContatoValidator(Translator translator,
      java.util.List<com.ia.core.service.rules.BusinessRule<GrupoContatoDTO>> businessRules) {
    super(translator, businessRules);
  }
}
