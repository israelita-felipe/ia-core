package com.ia.core.llm.service.model.session;

import com.ia.core.service.dto.CoreBaseDTOUnitTest;
import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe AgentSessionResponseDTO.
 */
@DisplayName("Testes de AgentSessionResponseDTO")
class AgentSessionResponseDTOTestCore extends CoreBaseDTOUnitTest<AgentSessionResponseDTO> {

  @Override
  public Class<AgentSessionResponseDTO> getDtoClass() {
    return AgentSessionResponseDTO.class;
  }

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
