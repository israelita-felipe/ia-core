package com.ia.core.llm.service.model.ferramenta;

import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe FerramentaActivationDTO.
 */
@DisplayName("Testes de FerramentaActivationDTO")
class FerramentaActivationDTOTestCore extends com.ia.test.dto.CoreDTOUnitTest<FerramentaActivationDTO> {

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
