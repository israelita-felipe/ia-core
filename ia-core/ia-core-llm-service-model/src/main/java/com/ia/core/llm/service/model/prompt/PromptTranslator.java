package com.ia.core.llm.service.model.prompt;

/**
 * Constantes de tradução para Prompt.
 * <p>
 * Define chaves i18n para validação e campos de prompt.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public final class PromptTranslator {

  private PromptTranslator() {}

  public static final class HELP {
    public static final String PROMPT = "prompt.help";
    public static final String TITULO = "prompt.help.titulo";
    public static final String ENTRADA = "prompt.help.entrada";
    /** @deprecated use {@link #ENTRADA} */
    @Deprecated
    public static final String COMANDO = ENTRADA;
    public static final String FINALIDADE = "prompt.help.finalidade";
  }

  public static final String PROMPT_CLASS = PromptDTO.class.getCanonicalName();
  public static final String PROMPT = "prompt";
  public static final String TITULO = "prompt.titulo";
  public static final String ENTRADA = "prompt.entrada";
  /** @deprecated use {@link #ENTRADA} */
  @Deprecated
  public static final String COMANDO = ENTRADA;
  public static final String FINALIDADE = "prompt.finalidade";

  public static final class VALIDATION {
    public static final String TITULO_REQUIRED = "validation.prompt.titulo.required";
    public static final String TITULO_SIZE = "validation.prompt.titulo.size";
    public static final String ENTRADA_SIZE = "validation.prompt.entrada.size";
    public static final String TEMPLATE_REQUIRED = "validation.prompt.template.required";
  }
}
