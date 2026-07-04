package com.ia.core.llm.service.model.ferramenta;

/**
 * Constantes de tradução para Ferramenta.
 * <p>
 * Define chaves i18n para validação e campos de ferramenta.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public final class FerramentaTranslator {

  private FerramentaTranslator() {}

  public static final String FERRAMENTA = "ferramenta";
  public static final String TITULO = "ferramenta.titulo";
  public static final String DESCRICAO = "ferramenta.descricao";
  public static final String TIPO = "ferramenta.tipo";
  public static final String IDENTIFICADOR = "ferramenta.identificador";
  public static final String INSTRUCOES = "ferramenta.instrucoes";
  public static final String SUB_FERRAMENTAS = "ferramenta.subFerramentas";
  public static final String ATIVO = "ferramenta.ativo";

  public static final class HELP {
    public static final String TITULO = "ferramenta.help.titulo";
    public static final String DESCRICAO = "ferramenta.help.descricao";
    public static final String TIPO = "ferramenta.help.tipo";
    public static final String IDENTIFICADOR = "ferramenta.help.identificador";
    public static final String INSTRUCOES = "ferramenta.help.instrucoes";
    public static final String MODULO_ORIGEM = "ferramenta.help.modulo_origem";
  }

  public static final class VALIDATION {
    public static final String TITULO_REQUIRED = "validation.ferramenta.titulo.required";
    public static final String TITULO_SIZE = "validation.ferramenta.titulo.size";
    public static final String IDENTIFICADOR_REQUIRED = "validation.ferramenta.identificador.required";
    public static final String IDENTIFICADOR_SIZE = "validation.ferramenta.identificador.size";
  }

  public static final class ERROR {
    public static final String NOT_FOUND = "ferramenta.error.notfound";
    public static final String DUPLICATE = "ferramenta.error.duplicate";
  }

  public static final class MESSAGE {
    public static final String CREATED = "ferramenta.message.created";
    public static final String UPDATED = "ferramenta.message.updated";
    public static final String DELETED = "ferramenta.message.deleted";
  }
}
