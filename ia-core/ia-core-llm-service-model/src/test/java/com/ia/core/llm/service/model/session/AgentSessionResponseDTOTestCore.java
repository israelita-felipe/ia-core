package com.ia.core.llm.service.model.session;

import com.ia.core.service.dto.DTO;
import com.ia.test.dto.CoreDTOUnitTest;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe AgentSessionResponseDTO.
 */
@DisplayName("Testes de AgentSessionResponseDTO")
class AgentSessionResponseDTOTestCore extends com.ia.test.dto.CoreDTOUnitTest<AgentSessionResponseDTO> {

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
