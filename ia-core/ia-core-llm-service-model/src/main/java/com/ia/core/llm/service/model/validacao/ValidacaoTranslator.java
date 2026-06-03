package com.ia.core.llm.service.model.validacao;

/**
 * Translator para internacionalização de validação de ontologias.
 * <p>
 * Segue o padrão ADR-003 para gerenciamento de labels e mensagens de validação.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class ValidacaoTranslator {

  public static final String CONSISTENTE = "validacao.consistente";
  public static final String CLASSES_INSATISFATIVEIS = "validacao.classesInsatisfativeis";
  public static final String AXIOMAS_CONFLITANTES = "validacao.axiomasConflitantes";
  public static final String EXPLICACAO = "validacao.explicacao";
  public static final String SUGESTOES = "validacao.sugestoes";
  public static final String ITERACOES_USADAS = "validacao.iteracoesUsadas";
  public static final String TEMPO_PROCESSAMENTO = "validacao.tempoProcessamento";

  public static final class ERROR {
    public static final String VALIDATION_FAILED = "validacao.error.validation.failed";
    public static final String INCONSISTENCY_DETECTED = "validacao.error.inconsistency.detected";
    public static final String SYNTAX_ERROR = "validacao.error.syntax.error";
  }

  public static final class MESSAGE {
    public static final String VALIDATION_SUCCESS = "validacao.message.validation.success";
    public static final String INCONSISTENCY_CORRECTED = "validacao.message.inconsistency.corrected";
    public static final String AXIOM_VALID = "validacao.message.axiom.valid";
    public static final String AXIOM_INVALID = "validacao.message.axiom.invalid";
  }
}
