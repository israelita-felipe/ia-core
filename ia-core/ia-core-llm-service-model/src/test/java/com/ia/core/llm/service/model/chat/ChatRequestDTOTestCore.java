package com.ia.core.llm.service.model.chat;

import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe ChatRequestDTO.
 */
@DisplayName("Testes de ChatRequestDTO")
class ChatRequestDTOTestCore extends com.ia.test.dto.CoreDTOUnitTest<ChatRequestDTO> {

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
