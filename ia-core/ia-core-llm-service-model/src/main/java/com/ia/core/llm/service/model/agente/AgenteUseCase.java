package com.ia.core.llm.service.model.agente;

import com.ia.core.service.usecase.CrudUseCase;

import java.util.Optional;

/**
 * UseCase para operações de CRUD em Agente.
 * <p>
 * Define os métodos para gerenciar agentes especialistas no banco de dados,
 * seguindo o padrão ia-core para integração com spring-ai-agent-utils.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface AgenteUseCase
  extends CrudUseCase<AgenteDTO> {

  /**
   * Busca um agente pelo identificador único.
   *
   * @param identificador identificador do agente (ex: llm.core)
   * @return Optional com o AgenteDTO encontrado
   */
  Optional<AgenteDTO> findByIdentificador(String identificador);

  /**
   * Lista todos os agentes ativos.
   *
   * @return lista de agentes ativos
   */
  java.util.List<AgenteDTO> listAtivos();
}
