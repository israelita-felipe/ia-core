package com.ia.core.llm.service.comando;

import com.ia.core.llm.model.comando.ComandoSistema;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Repository para entidade ComandoSistema. Implementa EntityGraph para evitar
 * N+1 queries.
 *
 * @author Israel Araújo
 */
public interface ComandoSistemaRepository
  extends BaseEntityRepository<ComandoSistema> {

}
