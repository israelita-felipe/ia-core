package com.ia.core.llm.service.model.ferramenta;

/**
 * Interface comum para ferramentas descobertas automaticamente.
 * <p>
 * Esta interface deve ser implementada por:
 * - Beans Spring AI anotados com @Tool para descoberta automática
 * - FerramentaDTO para representação de ferramentas do banco de dados
 * <p>
 * Permite que FerramentaDiscoveryService recupere tanto ferramentas registradas
 * no contexto Spring quanto ferramentas persistidas no banco de dados de forma unificada.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface FerramentaDiscoverable {

  /**
   * Retorna o identificador único da ferramenta.
   *
   * @return identificador da ferramenta
   */
  String getIdentificador();

  /**
   * Retorna o título da ferramenta.
   *
   * @return título da ferramenta
   */
  String getTitulo();

  /**
   * Retorna a descrição da ferramenta.
   *
   * @return descrição da ferramenta
   */
  String getDescricao();

  /**
   * Retorna o módulo de origem da ferramenta.
   *
   * @return módulo de origem
   */
  String getModuloOrigem();
}
