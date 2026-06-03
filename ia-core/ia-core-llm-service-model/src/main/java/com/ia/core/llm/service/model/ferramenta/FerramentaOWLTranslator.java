package com.ia.core.llm.service.model.ferramenta;

/**
 * Translator para internacionalização de ferramentas OWL 2 DL.
 * <p>
 * Segue o padrão ADR-003 para gerenciamento de labels e mensagens de validação.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class FerramentaOWLTranslator {

  public static final String DESCRICAO_NATUREZA = "ferramentaOWL.descricaoNatureza";
  public static final String CONTEXTO_ONTOLOGIA = "ferramentaOWL.contextoOntologia";
  public static final String CONSTRUTOR = "ferramentaOWL.construtor";
  public static final String PARAMETROS_ADICIONAIS = "ferramentaOWL.parametrosAdicionais";

  public static final class VALIDATION {
    public static final String DESCRICAO_REQUIRED = "ferramentaOWL.validation.descricao.required";
  }

  public static final class ERROR {
    public static final String TOOL_NOT_FOUND = "ferramentaOWL.error.tool.notfound";
    public static final String TOOL_EXECUTION_FAILED = "ferramentaOWL.error.tool.execution.failed";
    public static final String CONSTRUCTOR_NOT_SUPPORTED = "ferramentaOWL.error.constructor.not.supported";
  }

  public static final class MESSAGE {
    public static final String TOOL_EXECUTED = "ferramentaOWL.message.tool.executed";
    public static final String AXIOMS_GENERATED = "ferramentaOWL.message.axioms.generated";
    public static final String VALIDATION_SUCCESS = "ferramentaOWL.message.validation.success";
  }

  public static final class CONSTRUCTOR {
    public static final String SUBCLASS_OF = "ferramentaOWL.constructor.subclassof";
    public static final String EQUIVALENT_CLASSES = "ferramentaOWL.constructor.equivalentclasses";
    public static final String OBJECT_PROPERTY_DOMAIN = "ferramentaOWL.constructor.objectpropertydomain";
    public static final String OBJECT_PROPERTY_RANGE = "ferramentaOWL.constructor.objectpropertyrange";
  }
}
