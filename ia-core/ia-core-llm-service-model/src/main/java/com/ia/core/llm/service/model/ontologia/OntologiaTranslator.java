package com.ia.core.llm.service.model.ontologia;

/**
 * Translator para internacionalização de OntologiaDTO.
 * <p>
 * Segue o padrão ADR-003 para gerenciamento de labels e mensagens de validação.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see OntologiaDTO
 */
public final class OntologiaTranslator {

  private OntologiaTranslator() {}

/**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String IRI_REQUIRED = "ontologia.validation.iri.required";
        public static final String NOME_REQUIRED = "ontologia.validation.nome.required";
        public static final String IRI_SIZE = "ontologia.validation.iri.size";
        public static final String NOME_SIZE = "ontologia.validation.nome.size";
        public static final String DESCRICAO_SIZE = "ontologia.validation.descricao.size";
        public static final String VERSAO_SIZE = "ontologia.validation.versao.size";
        public static final String PREFIXO_SIZE = "ontologia.validation.prefixo.size";
        public static final String NAMESPACE_SIZE = "ontologia.validation.namespace.size";
    }

  /**
   * Help text i18n keys
   */
  public static final class HELP {
    public static final String IRI = "ontologia.help.iri";
    public static final String NOME = "ontologia.help.nome";
    public static final String DESCRICAO = "ontologia.help.descricao";
    public static final String FORMATO = "ontologia.help.formato";
  }

  /**
   * Error message keys
   */
  public static final class ERROR {
    public static final String NOT_FOUND = "ontologia.error.notfound";
    public static final String INCONSISTENT = "ontologia.error.inconsistent";
    public static final String LOAD_FAILED = "ontologia.error.load.failed";
    public static final String SAVE_FAILED = "ontologia.error.save.failed";
  }

  /**
   * Success message keys
   */
  public static final class MESSAGE {
    public static final String CREATED = "ontologia.message.created";
    public static final String UPDATED = "ontologia.message.updated";
    public static final String DELETED = "ontologia.message.deleted";
    public static final String VALIDATED = "ontologia.message.validated";
    public static final String EXPORTED = "ontologia.message.exported";
  }

  /**
   * DTO class canonical name
   */
  public static final String ONTOLOGIA_CLASS = OntologiaDTO.class.getCanonicalName();

  /**
   * Field name constants
   */
  public static final String IRI = "ontologia.iri";
  public static final String NOME = "ontologia.nome";
  public static final String DESCRICAO = "ontologia.descricao";
  public static final String VERSAO = "ontologia.versao";
  public static final String PREFIXO = "ontologia.prefixo";
  public static final String NAMESPACE = "ontologia.namespace";
  public static final String FORMATO = "ontologia.formato";
  public static final String CONTEUDO = "ontologia.conteudo";
  public static final String CONSISTENTE = "ontologia.consistente";
  public static final String ULTIMA_MODIFICACAO = "ontologia.ultimaModificacao";
  public static final String DATA_CRIACAO = "ontologia.dataCriacao";
  public static final String ESTATISTICAS = "ontologia.estatisticas";
  public static final String AXIOMAS = "ontologia.axiomas";
}