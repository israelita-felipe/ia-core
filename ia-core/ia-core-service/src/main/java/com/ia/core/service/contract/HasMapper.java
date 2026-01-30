package com.ia.core.service.contract;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.mapper.Mapper;

/**
 * Interface segregada para acesso ao mapper.
 * 
 * Princípio: ISP (Interface Segregation Principle)
 * Responsabilidade Única: Fornecer acesso ao mapper.
 *
 * @author Israel Araújo
 * @param <T> Tipo da entidade, deve estender {@link BaseEntity}
 * @param <D> Tipo do DTO, deve estender {@link DTO}
 */
public interface HasMapper<T extends BaseEntity, D extends DTO<?>> {

  /**
   * Obtém o mapper de transformação de entidade para DTO.
   *
   * @param <M> Tipo do mapper, deve estender {@link Mapper}
   * @return Mapper da entidade
   */
  <M extends Mapper<T, D>> M getMapper();
}
