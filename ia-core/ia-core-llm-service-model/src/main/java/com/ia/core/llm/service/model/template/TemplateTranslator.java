package com.ia.core.llm.service.model.template;

/**
 * @author Israel Araújo
 */
@SuppressWarnings("javadoc")
public class TemplateTranslator {
  public static final class HELP {
    public static final String TEMPLATE = "template.help";
    public static final String TITULO = "template.help.titulo";
    public static final String CONTEUDO = "template.help.conteudo";
    public static final String EXIGE_CONTEXTO = "template.help.exite.contexto";
    public static final String PARAMETROS = "template.help.parametros";
  }
  public static final String TEMPLATE_CLASS = TemplateDTO.class
      .getCanonicalName();
  public static final String TEMPLATE = "template";
  public static final String TITULO = "template.titulo";
  public static final String CONTEUDO = "template.conteudo";
  public static final String EXIGE_CONTEXTO = "template.exite.contexto";

  public static final String PARAMETROS = "template.parametros";
}
