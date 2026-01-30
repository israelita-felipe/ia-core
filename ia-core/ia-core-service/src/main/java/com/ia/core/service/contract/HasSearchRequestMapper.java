package com.ia.core.service.contract;

import com.ia.core.service.mapper.SearchRequestMapper;

/**
 * Interface segregada para acesso ao mapper de requisição de busca.
 * 
 * Princípio: ISP (Interface Segregation Principle)
 * Responsabilidade Única: Fornecer acesso ao search request mapper.
 *
 * @author Israel Araújo
 */
public interface HasSearchRequestMapper {

  /**
   * Obtém o mapper de requisição de busca.
   *
   * @return Search request mapper
   */
  SearchRequestMapper getSearchRequestMapper();
}
