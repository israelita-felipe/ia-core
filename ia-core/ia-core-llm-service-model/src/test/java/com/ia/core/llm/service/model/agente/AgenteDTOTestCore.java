package com.ia.core.llm.service.model.agente;

import com.ia.core.service.dto.CoreBaseDTOUnitTest;
import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe AgenteDTO.
 */
@DisplayName("Testes de AgenteDTO")
class AgenteDTOTestCore extends CoreBaseDTOUnitTest<AgenteDTO> {

  @Override
  public Class<AgenteDTO> getDtoClass() {
    return AgenteDTO.class;
  }

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
