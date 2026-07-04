package com.ia.core.llm.service.model.session;

import com.ia.core.service.dto.CoreBaseDTOUnitTest;
import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe AgentSessionRequestDTO.
 */
@DisplayName("Testes de AgentSessionRequestDTO")
class AgentSessionRequestDTOTestCore extends CoreBaseDTOUnitTest<AgentSessionRequestDTO> {

  @Override
  public Class<AgentSessionRequestDTO> getDtoClass() {
    return AgentSessionRequestDTO.class;
  }

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
