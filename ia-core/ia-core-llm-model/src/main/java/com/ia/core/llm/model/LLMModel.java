package com.ia.core.llm.model;

/**
 * Classe utilitária que contém constantes de configuração para o modelo LLM.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a LLMModel
 * dentro do sistema.
 *
 * @author Israel Araújo
 * @since 1.0
 */
public final class LLMModel {

  /** Construtor privado para evitar instanciação */
  private LLMModel() {
    throw new UnsupportedOperationException("Classe utilitária");
  }

  /** Prefixo das tabelas do quartz */
  public static final String TABLE_PREFIX = "LLM_";

  /** Schema do quartz */
  public static final String SCHEMA = "LARGE_LANGUAGE_MODEL";

}
