package com.ia.core.llm.service.model.agente;

import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe AgenteDTO.
 */
@DisplayName("Testes de AgenteDTO")
class AgenteDTOTestCore extends com.ia.test.dto.CoreDTOUnitTest<AgenteDTO> {

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
