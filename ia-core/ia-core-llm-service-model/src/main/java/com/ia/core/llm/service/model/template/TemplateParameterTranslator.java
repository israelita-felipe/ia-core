package com.ia.core.llm.service.model.template;

/**
 * @author Israel Araújo
 */
@SuppressWarnings("javadoc")
public class TemplateParameterTranslator {
  public static final class HELP {
    public static final String TEMPLATE_PARAMETER = "template.parameter.help";
    public static final String NOME = "template.parameter.help.nome";
  }
  public static final String TEMPLATE_PARAMETER_CLASS = TemplateParameterDTO.class
      .getCanonicalName();
  public static final String TEMPLATE_PARAMETER = "template.parameter";

  public static final String NOME = "template.parameter.nome";

  public static final class VALIDATION {
    public static final String NOME_REQUIRED = "validation.template.parameter.nome.required";
    public static final String NOME_SIZE = "validation.template.parameter.nome.size";
  }
}
