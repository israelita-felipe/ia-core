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
    public static final String IDENTIFICADOR_SIZE = "validation.agente.identificador.size";
    public static final String TITULO_REQUIRED = "validation.agente.titulo.required";
    public static final String TITULO_SIZE = "validation.agente.titulo.size";
    public static final String DESCRICAO_SIZE = "validation.agente.descricao.size";
    public static final String MODELO_SIZE = "validation.agente.modelo.size";
    public static final String MODULO_ORIGEM_SIZE = "validation.agente.modulo_origem.size";
  }

  public static final class HELP {
    public static final String IDENTIFICADOR = "agente.help.identificador";
    public static final String TITULO = "agente.help.titulo";
    public static final String DESCRICAO = "agente.help.descricao";
    public static final String INSTRUCOES = "agente.help.instrucoes";
    public static final String MODELO = "agente.help.modelo";
    public static final String MODULO_ORIGEM = "agente.help.modulo_origem";
  }

  public static final class ERROR {
    public static final String NOT_FOUND = "agente.error.notfound";
    public static final String DUPLICATE = "agente.error.duplicate";
  }

  public static final class MESSAGE {
    public static final String CREATED = "agente.message.created";
    public static final String UPDATED = "agente.message.updated";
    public static final String DELETED = "agente.message.deleted";
  }
}
