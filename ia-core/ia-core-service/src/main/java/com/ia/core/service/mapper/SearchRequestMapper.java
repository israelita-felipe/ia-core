package com.ia.core.service.mapper;

import com.ia.core.model.filter.SearchRequest;
import com.ia.core.service.dto.request.SearchRequestDTO;
/**
 * Mapper para conversão de dados de search request.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a SearchRequestMapper
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public interface SearchRequestMapper
  extends Mapper<SearchRequest, SearchRequestDTO> {

}
