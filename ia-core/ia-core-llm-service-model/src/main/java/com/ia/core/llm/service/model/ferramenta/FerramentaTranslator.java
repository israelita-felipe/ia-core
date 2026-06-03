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

  public static final class VALIDATION {
    public static final String TITULO_REQUIRED = "validation.ferramenta.titulo.required";
    public static final String IDENTIFICADOR_REQUIRED = "validation.ferramenta.identificador.required";
  }
}
