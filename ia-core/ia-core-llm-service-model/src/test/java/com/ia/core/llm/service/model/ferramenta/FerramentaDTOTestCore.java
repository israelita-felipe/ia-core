package com.ia.core.llm.service.model.ferramenta;

import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe FerramentaDTO.
 */
@DisplayName("Testes de FerramentaDTO")
class FerramentaDTOTestCore extends com.ia.test.dto.CoreDTOUnitTest<FerramentaDTO> {

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
