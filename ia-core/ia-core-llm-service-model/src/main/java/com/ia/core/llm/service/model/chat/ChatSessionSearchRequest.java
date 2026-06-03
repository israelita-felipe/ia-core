package com.ia.core.llm.service.model.chat;

import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Set;

/**
 * SearchRequest para ChatSession.
 * <p>
 * Define os filtros disponíveis para pesquisa de sessões de chat.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class ChatSessionSearchRequest extends SearchRequestDTO {

  @Override
  public Set<String> propertyFilters() {
    return Set.of("sessionId", "titulo", "status", "agenteId", "usuarioId");
  }
}
