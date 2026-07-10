package com.ia.core.llm.service.model.ferramenta;

/**
 * Translator para internacionalização de ferramentas OWL 2 DL.
 * <p>
 * Segue o padrão ADR-003 para gerenciamento de labels e mensagens de validação.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public final class FerramentaOWLTranslator {

  private FerramentaOWLTranslator() {}

/**
    * Validation message keys
    */
   public static final class VALIDATION {
     public static final String DESCRICAO_REQUIRED = "ferramentaOwl.validation.descricao.required";
     public static final String CONSTRUTOR_SIZE = "ferramentaOwl.validation.construtor.size";
   }

  /**
   * Help text i18n keys
   */
  public static final class HELP {
    public static final String DESCRICAO_NATUREZA = "ferramentaOwl.help.descricaoNatureza";
    public static final String CONTEXTO_ONTOLOGIA = "ferramentaOwl.help.contextoOntologia";
    public static final String CONSTRUTOR = "ferramentaOwl.help.construtor";
    public static final String PARAMETROS_ADICIONAIS = "ferramentaOwl.help.parametrosAdicionais";
  }

  /**
   * Error message keys
   */
  public static final class ERROR {
    public static final String TOOL_NOT_FOUND = "ferramentaOwl.error.tool.notfound";
    public static final String TOOL_EXECUTION_FAILED = "ferramentaOwl.error.tool.execution.failed";
    public static final String CONSTRUCTOR_NOT_SUPPORTED = "ferramentaOwl.error.constructor.not.supported";
  }

  /**
   * Success message keys
   */
  public static final class MESSAGE {
    public static final String TOOL_EXECUTED = "ferramentaOwl.message.tool.executed";
    public static final String AXIOMS_GENERATED = "ferramentaOwl.message.axioms.generated";
    public static final String VALIDATION_SUCCESS = "ferramentaOwl.message.validation.success";
  }

  /**
   * DTO class canonical name
   */
  public static final String REQUISICAO_CLASS = RequisicaoFerramentaDTO.class.getCanonicalName();

  /**
   * Field name constants
   */
  public static final String DESCRICAO_NATUREZA = "ferramentaOwl.descricaoNatureza";
  public static final String CONTEXTO_ONTOLOGIA = "ferramentaOwl.contextoOntologia";
  public static final String CONSTRUTOR = "ferramentaOwl.construtor";
  public static final String PARAMETROS_ADICIONAIS = "ferramentaOwl.parametrosAdicionais";

  /**
   * Constructor type constants
   */
  public static final class CONSTRUCTOR {
    public static final String SUBCLASS_OF = "ferramentaOwl.constructor.subclassof";
    public static final String EQUIVALENT_CLASSES = "ferramentaOwl.constructor.equivalentclasses";
    public static final String OBJECT_PROPERTY_DOMAIN = "ferramentaOwl.constructor.objectpropertydomain";
    public static final String OBJECT_PROPERTY_RANGE = "ferramentaOwl.constructor.objectpropertyrange";
  }
}