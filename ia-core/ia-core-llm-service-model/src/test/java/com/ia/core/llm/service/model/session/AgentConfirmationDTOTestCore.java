package com.ia.core.llm.service.model.session;

import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe AgentConfirmationDTO.
 */
@DisplayName("Testes de AgentConfirmationDTO")
class AgentConfirmationDTOTestCore extends com.ia.test.dto.CoreDTOUnitTest<AgentConfirmationDTO> {

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
