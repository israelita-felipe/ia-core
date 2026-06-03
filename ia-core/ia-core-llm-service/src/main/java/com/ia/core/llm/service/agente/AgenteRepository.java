package com.ia.core.llm.service.agente;

import com.ia.core.llm.model.agente.Agente;
import com.ia.core.service.repository.BaseEntityRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para acesso a dados de Agente.
 * <p>
 * Fornece métodos específicos para buscar e manipular agentes no banco de dados.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface AgenteRepository
  extends BaseEntityRepository<Agente> {

  /**
   * Busca um agente pelo identificador único.
   *
   * @param identificador identificador do agente (ex: llm.core)
   * @return Optional com o Agente encontrado
   */
  Optional<Agente> findByIdentificador(String identificador);

  /**
   * Lista todos os agentes ativos.
   *
   * @return lista de agentes ativos
   */
  List<Agente> findByAtivoTrue();
}
