package com.ia.core.llm.service.model.session;

import com.ia.core.service.dto.CoreBaseDTOUnitTest;
import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe AgentConfirmationDTO.
 */
@DisplayName("Testes de AgentConfirmationDTO")
class AgentConfirmationDTOTestCore extends CoreBaseDTOUnitTest<AgentConfirmationDTO> {

  @Override
  public Class<AgentConfirmationDTO> getDtoClass() {
    return AgentConfirmationDTO.class;
  }

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
