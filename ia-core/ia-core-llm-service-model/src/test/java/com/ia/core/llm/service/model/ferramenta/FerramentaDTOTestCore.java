package com.ia.core.llm.service.model.ferramenta;

import com.ia.core.service.dto.CoreBaseDTOUnitTest;
import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe FerramentaDTO.
 */
@DisplayName("Testes de FerramentaDTO")
class FerramentaDTOTestCore extends CoreBaseDTOUnitTest<FerramentaDTO> {

  @Override
  public Class<FerramentaDTO> getDtoClass() {
    return FerramentaDTO.class;
  }

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
