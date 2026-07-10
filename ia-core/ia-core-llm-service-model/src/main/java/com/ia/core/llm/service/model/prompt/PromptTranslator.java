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

  /**
   * Validation message keys
   */
  public static final class VALIDATION {
    public static final String TITULO_REQUIRED = "prompt.validation.titulo.required";
    public static final String TITULO_SIZE = "prompt.validation.titulo.size";
    public static final String ENTRADA_SIZE = "prompt.validation.entrada.size";
    public static final String TEMPLATE_REQUIRED = "prompt.validation.template.required";
  }

  /**
   * Help text i18n keys
   */
  public static final class HELP {
    public static final String PROMPT = "prompt.help";
    public static final String TITULO = "prompt.help.titulo";
    public static final String ENTRADA = "prompt.help.entrada";
    /** @deprecated use {@link #ENTRADA} */
    @Deprecated
    public static final String COMANDO = ENTRADA;
    public static final String FINALIDADE = "prompt.help.finalidade";
  }

  /**
   * Error message keys
   */
  public static final class ERROR {
    public static final String NOT_FOUND = "prompt.error.notfound";
    public static final String DUPLICATE = "prompt.error.duplicate";
  }

  /**
   * Success message keys
   */
  public static final class MESSAGE {
    public static final String CREATED = "prompt.message.created";
    public static final String UPDATED = "prompt.message.updated";
    public static final String DELETED = "prompt.message.deleted";
  }

  /**
   * DTO class canonical name
   */
  public static final String PROMPT_CLASS = PromptDTO.class.getCanonicalName();

  /**
   * Field name constants
   */
  public static final String PROMPT = "prompt";
  public static final String TITULO = "prompt.titulo";
  public static final String ENTRADA = "prompt.entrada";
  /** @deprecated use {@link #ENTRADA} */
  @Deprecated
  public static final String COMANDO = ENTRADA;
  public static final String FINALIDADE = "prompt.finalidade";
}