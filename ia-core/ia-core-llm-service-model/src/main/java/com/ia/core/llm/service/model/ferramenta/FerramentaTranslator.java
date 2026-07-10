package com.ia.core.llm.service.model.ferramenta;

/**
 * Translator para internacionalização de Ferramenta.
 * <p>
 * Define chaves i18n para validação e campos de ferramenta.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public final class FerramentaTranslator {

  private FerramentaTranslator() {}

  /**
   * Validation message keys
   */
  public static final class VALIDATION {
    public static final String TITULO_REQUIRED = "ferramenta.validation.titulo.required";
    public static final String TITULO_SIZE = "ferramenta.validation.titulo.size";
    public static final String IDENTIFICADOR_REQUIRED = "ferramenta.validation.identificador.required";
    public static final String IDENTIFICADOR_SIZE = "ferramenta.validation.identificador.size";
    public static final String DESCRICAO_SIZE = "ferramenta.validation.descricao.size";
    public static final String TIPO_REQUIRED = "ferramenta.validation.tipo.required";
    public static final String MODULO_ORIGEM_SIZE = "ferramenta.validation.modulo_origem.size";
  }

  /**
   * Help text i18n keys
   */
  public static final class HELP {
    public static final String TITULO = "ferramenta.help.titulo";
    public static final String DESCRICAO = "ferramenta.help.descricao";
    public static final String TIPO = "ferramenta.help.tipo";
    public static final String IDENTIFICADOR = "ferramenta.help.identificador";
    public static final String INSTRUCOES = "ferramenta.help.instrucoes";
    public static final String MODULO_ORIGEM = "ferramenta.help.modulo_origem";
  }

  /**
   * Error message keys
   */
  public static final class ERROR {
    public static final String NOT_FOUND = "ferramenta.error.notfound";
    public static final String DUPLICATE = "ferramenta.error.duplicate";
  }

  /**
   * Success message keys
   */
  public static final class MESSAGE {
    public static final String CREATED = "ferramenta.message.created";
    public static final String UPDATED = "ferramenta.message.updated";
    public static final String DELETED = "ferramenta.message.deleted";
  }

  /**
   * Business rule message keys
   */
  public static final class RULE {
    public static final String IDENTIFICADOR_DUPLICADO = "ferramenta.rule.identificador.duplicado";
  }

  /**
   * Domain event message keys
   */
  public static final class EVENT {
    public static final String FERRAMENTA_CRIADO = "ferramenta.event.criado";
    public static final String FERRAMENTA_ATUALIZADO = "ferramenta.event.atualizado";
  }

  /**
   * DTO class canonical name
   */
  public static final String FERRAMENTA_CLASS = FerramentaDTO.class.getCanonicalName();

  /**
   * Field name constants
   */
  public static final String FERRAMENTA = "ferramenta";
  public static final String TITULO = "ferramenta.titulo";
  public static final String DESCRICAO = "ferramenta.descricao";
  public static final String TIPO = "ferramenta.tipo";
  public static final String IDENTIFICADOR = "ferramenta.identificador";
  public static final String INSTRUCOES = "ferramenta.instrucoes";
  public static final String SUB_FERRAMENTAS = "ferramenta.subFerramentas";
  public static final String ATIVO = "ferramenta.ativo";
  public static final String MODULO_ORIGEM = "ferramenta.modulo_origem";
}