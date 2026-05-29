package com.ia.core.llm.service.model.agente;

/**
 * Chaves de tradução para Agente.
 * <p>
 * Centraliza todas as chaves de tradução usadas na UI e validações.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public final class AgenteTranslator {

  private AgenteTranslator() {}

  public static final String AGENTE = "agente";
  public static final String IDENTIFICADOR = "agente.identificador";
  public static final String TITULO = "agente.titulo";
  public static final String DESCRICAO = "agente.descricao";
  public static final String INSTRUCOES = "agente.instrucoes";
  public static final String MODELO = "agente.modelo";
  public static final String FERRAMENTAS = "agente.ferramentas";
  public static final String ATIVO = "agente.ativo";
  public static final String MODULO_ORIGEM = "agente.modulo_origem";
  public static final String SKILLS = "agente.skills";

  public static final class VALIDATION {
    public static final String IDENTIFICADOR_REQUIRED = "validation.agente.identificador.required";
    public static final String TITULO_REQUIRED = "validation.agente.titulo.required";
  }

  public static final class HELP {
    public static final String IDENTIFICADOR = "agente.help.identificador";
    public static final String TITULO = "agente.help.titulo";
    public static final String DESCRICAO = "agente.help.descricao";
    public static final String INSTRUCOES = "agente.help.instrucoes";
    public static final String MODELO = "agente.help.modelo";
    public static final String MODULO_ORIGEM = "agente.help.modulo_origem";
  }
}
