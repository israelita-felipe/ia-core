package com.ia.core.llm.service.model.agente;

/**
 * Translator para internacionalização de agentes conversacionais guiados por ontologia.
 * <p>
 * Segue o padrão ADR-003 para gerenciamento de labels e mensagens de validação.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class AgenteConversacionalTranslator {

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

  public static final class VALIDATION {
    public static final String DOMAIN_REQUIRED = "agenteConversacional.validation.domain.required";
    public static final String USER_ID_REQUIRED = "agenteConversacional.validation.userId.required";
    public static final String MESSAGE_REQUIRED = "agenteConversacional.validation.message.required";
  }

  public static final class ERROR {
    public static final String SESSION_NOT_FOUND = "agenteConversacional.error.session.notfound";
    public static final String SESSION_EXPIRED = "agenteConversacional.error.session.expired";
    public static final String ONTOLOGY_LOAD_FAILED = "agenteConversacional.error.ontology.load.failed";
  }

  public static final class MESSAGE {
    public static final String SESSION_CREATED = "agenteConversacional.message.session.created";
    public static final String AXIOM_ADDED = "agenteConversacional.message.axiom.added";
    public static final String RESPONSE_GENERATED = "agenteConversacional.message.response.generated";
    public static final String SESSION_CLOSED = "agenteConversacional.message.session.closed";
  }
}
