package com.ia.core.llm.service.model.chat;

import com.ia.core.service.dto.CoreBaseDTOUnitTest;
import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe ChatRequestDTO.
 */
@DisplayName("Testes de ChatRequestDTO")
class ChatRequestDTOTestCore extends CoreBaseDTOUnitTest<ChatRequestDTO> {

  @Override
  public Class<ChatRequestDTO> getDtoClass() {
    return ChatRequestDTO.class;
  }

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
