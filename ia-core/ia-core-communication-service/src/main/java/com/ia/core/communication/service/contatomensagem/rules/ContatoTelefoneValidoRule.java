package com.ia.core.communication.service.contatomensagem.rules;

import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.service.rules.BusinessRule;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.Severity;
import com.ia.core.service.validators.ValidationError;
import com.ia.core.service.validators.ValidationResult;
import com.ia.core.service.validators.ValidatorScope;

/**
 * Regra de negócio que verifica se o telefone do contato é válido.
 * <p>
 * Garante que o telefone tenha o formato correto e a quantidade de dígitos adequada.
 *
 * @author Israel Araújo
 */
@ValidatorScope
public class ContatoTelefoneValidoRule implements BusinessRule<ContatoMensagemDTO> {

  /**
   * Tamanho mínimo do telefone.
   */
  private static final int TELEFONE_MINIMO = 10;

  /**
   * Tamanho máximo do telefone.
   */
  private static final int TELEFONE_MAXIMO = 20;

  private final Translator translator;

  /**
   * Construtor com tradutor.
   *
   * @param translator tradutor para mensagens de validação
   */
  public ContatoTelefoneValidoRule(Translator translator) {
    this.translator = translator;
  }

  @Override
  public String getCode() {
    return "CTR_001";
  }

  @Override
  public String getName() {
    return "ContatoTelefoneValidoRule";
  }

  @Override
  public String getDescription() {
    return "Verifica se o telefone do contato é válido";
  }

  @Override
  public Translator getTranslator() {
    return translator;
  }

  @Override
  public void validate(ContatoMensagemDTO contato, ValidationResult result) {
    if (contato == null) {
      result.addError(new ValidationError(
          "contato",
          "Contato não pode ser nulo",
          Severity.ERROR,
          null));
      return;
    }

    if (contato.getTelefone() == null || contato.getTelefone().isEmpty()) {
      result.addError(new ValidationError(
          "telefone",
          "Telefone é obrigatório",
          Severity.ERROR,
          null));
      return;
    }

    String telefone = contato.getTelefone().replaceAll("[^0-9]", "");

    if (telefone.length() < TELEFONE_MINIMO || telefone.length() > TELEFONE_MAXIMO) {
      result.addError(new ValidationError(
          "telefone",
          "Telefone deve ter entre " + TELEFONE_MINIMO + " e " + TELEFONE_MAXIMO + " dígitos",
          Severity.ERROR,
          null));
    }
  }
}
