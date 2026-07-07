package com.ia.core.llm.service.model.session;

import com.ia.core.service.dto.DTO;
import com.ia.test.dto.CoreDTOUnitTest;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe AgentSessionRequestDTO.
 */
@DisplayName("Testes de AgentSessionRequestDTO")
class AgentSessionRequestDTOTestCore extends com.ia.test.dto.CoreDTOUnitTest<AgentSessionRequestDTO> {

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
