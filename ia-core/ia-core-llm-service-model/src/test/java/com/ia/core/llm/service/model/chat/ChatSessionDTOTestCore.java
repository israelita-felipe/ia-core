package com.ia.core.llm.service.model.chat;

import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe ChatSessionDTO.
 */
@DisplayName("Testes de ChatSessionDTO")
class ChatSessionDTOTestCore extends com.ia.test.dto.CoreDTOUnitTest<ChatSessionDTO> {

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
