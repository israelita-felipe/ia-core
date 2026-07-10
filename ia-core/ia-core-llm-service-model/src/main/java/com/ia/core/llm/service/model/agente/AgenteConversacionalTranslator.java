package com.ia.core.llm.service.model.agente;

/**
 * Translator para internacionalização de agentes conversacionais guiados por ontologia.
 * <p>
 * Segue o padrão ADR-003 para gerenciamento de labels e mensagens de validação.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see ContextConversacaoDTO
 */
public final class AgenteConversacionalTranslator {

  private AgenteConversacionalTranslator() {}

  /**
   * Validation message keys
   */
  public static final class VALIDATION {
    public static final String SESSION_ID_REQUIRED = "agenteConversacional.validation.sessionId.required";
    public static final String USER_ID_REQUIRED = "agenteConversacional.validation.userId.required";
    public static final String REQUEST_REQUIRED = "agenteConversacional.validation.request.required";
  }

  /**
   * Help text i18n keys
   */
  public static final class HELP {
    public static final String SESSION_ID = "agenteConversacional.help.sessionId";
    public static final String USER_ID = "agenteConversacional.help.userId";
    public static final String DOMINIO = "agenteConversacional.help.dominio";
    public static final String ONTOLOGIA = "agenteConversacional.help.ontologia";
    public static final String HISTORICO = "agenteConversacional.help.historico";
  }

  /**
   * Error message keys
   */
  public static final class ERROR {
    public static final String SESSION_NOT_FOUND = "agenteConversacional.error.session.notfound";
    public static final String SESSION_EXPIRED = "agenteConversacional.error.session.expired";
    public static final String ONTOLOGY_LOAD_FAILED = "agenteConversacional.error.ontology.load.failed";
  }

  /**
   * Success message keys
   */
  public static final class MESSAGE {
    public static final String SESSION_CREATED = "agenteConversacional.message.session.created";
    public static final String AXIOM_ADDED = "agenteConversacional.message.axiom.added";
    public static final String RESPONSE_GENERATED = "agenteConversacional.message.response.generated";
    public static final String SESSION_CLOSED = "agenteConversacional.message.session.closed";
  }

  /**
   * DTO class canonical name
   */
  public static final String CONTEXT_CLASS = ContextConversacaoDTO.class.getCanonicalName();

  /**
   * Field name constants
   */
  public static final String SESSION_ID = "agenteConversacional.sessionId";
  public static final String USER_ID = "agenteConversacional.userId";
  public static final String DOMINIO = "agenteConversacional.dominio";
  public static final String ONTOLOGIA = "agenteConversacional.ontologia";
  public static final String HISTORICO = "agenteConversacional.historico";
  public static final String DATA_INICIO = "agenteConversacional.dataInicio";
  public static final String ULTIMA_ATIVIDADE = "agenteConversacional.ultimaAtividade";
  public static final String TOTAL_AXIOMAS = "agenteConversacional.totalAxiomas";
  public static final String CONSISTENTE = "agenteConversacional.consistente";
  public static final String INCONSISTENCIAS_CORRIGIDAS = "agenteConversacional.inconsistenciasCorrigidas";
}