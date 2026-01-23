package com.ia.core.service.contract;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Interface segregada para acesso ao repositório.
 * 
 * Princípio: ISP (Interface Segregation Principle)
 * Responsabilidade Única: Fornecer acesso ao repositório.
 *
 * @author Israel Araújo
 * @param <T> Tipo da entidade, deve estender {@link BaseEntity}
 */
public interface HasRepository<T extends BaseEntity> {

  /**
   * Obtém o repositório da entidade.
   *
   * @param <R> Tipo do repositório, deve estender {@link BaseEntityRepository}
   * @return Repositório da entidade
   */
  <R extends BaseEntityRepository<T>> R getRepository();
}
