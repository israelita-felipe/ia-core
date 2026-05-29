package com.ia.core.llm.model;

/**
 * Classe utilitária com constantes de configuração para o modelo LLM.
 * <p>
 * Contém constantes para prefixo de tabelas e nome do schema utilizado
 * pelo módulo de Large Language Model.
 *
 * @author Israel Araújo
 * @since 1.0.0
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
