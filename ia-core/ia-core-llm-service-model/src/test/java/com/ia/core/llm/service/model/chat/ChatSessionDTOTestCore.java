package com.ia.core.llm.service.model.chat;

import com.ia.core.service.dto.CoreBaseDTOUnitTest;
import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe ChatSessionDTO.
 */
@DisplayName("Testes de ChatSessionDTO")
class ChatSessionDTOTestCore extends CoreBaseDTOUnitTest<ChatSessionDTO> {

  @Override
  public Class<ChatSessionDTO> getDtoClass() {
    return ChatSessionDTO.class;
  }

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
