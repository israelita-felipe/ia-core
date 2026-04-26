package com.ia.core.communication.service.mensagem.rules;

import com.ia.core.communication.model.mensagem.TipoCanal;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.service.rules.BusinessRule;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.Severity;
import com.ia.core.service.validators.ValidationError;
import com.ia.core.service.validators.ValidationResult;
import com.ia.core.service.validators.ValidatorScope;

/**
 * Regra de negócio que verifica se o canal de comunicação é válido para envio.
 * <p>
 * Garante que apenas canais suportados sejam utilizados para envio de mensagens.
 *
 * @author Israel Araújo
 */
@ValidatorScope
public class MensagemCanalValidoRule implements BusinessRule<MensagemDTO> {

  /**
   * Canais válidos para envio de mensagens.
   */
  private static final TipoCanal[] CANAIS_VALIDOS = {
      TipoCanal.EMAIL,
      TipoCanal.WHATSAPP,
      TipoCanal.TELEGRAM,
      TipoCanal.SMS
  };

  private final Translator translator;

  /**
   * Construtor com tradutor.
   *
   * @param translator tradutor para mensagens de validação
   */
  public MensagemCanalValidoRule(Translator translator) {
    this.translator = translator;
  }

  @Override
  public String getCode() {
    return "MSG_001";
  }

  @Override
  public String getName() {
    return "MensagemCanalValidoRule";
  }

  @Override
  public String getDescription() {
    return "Verifica se o canal de comunicação é válido para envio";
  }

  @Override
  public Translator getTranslator() {
    return translator;
  }

  @Override
  public void validate(MensagemDTO mensagem, ValidationResult result) {
    if (mensagem == null) {
      result.addError(new ValidationError(
          "mensagem",
          "Mensagem não pode ser nula",
          Severity.ERROR,
          null));
      return;
    }

    if (mensagem.getTipoCanal() == null) {
      result.addError(new ValidationError(
          "tipoCanal",
          "Tipo do canal é obrigatório",
          Severity.ERROR,
          null));
      return;
    }

    boolean canalValido = false;
    for (TipoCanal canal : CANAIS_VALIDOS) {
      if (canal.equals(mensagem.getTipoCanal())) {
        canalValido = true;
        break;
      }
    }

    if (!canalValido) {
      result.addError(new ValidationError(
          "tipoCanal",
          "Canal não suportado: " + mensagem.getTipoCanal(),
          Severity.ERROR,
          null));
    }
  }
}
