package com.ia.core.llm.service.model.ferramenta;

import com.ia.core.service.dto.CoreBaseDTOUnitTest;
import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe FerramentaActivationDTO.
 */
@DisplayName("Testes de FerramentaActivationDTO")
class FerramentaActivationDTOTestCore extends CoreBaseDTOUnitTest<FerramentaActivationDTO> {

  @Override
  public Class<FerramentaActivationDTO> getDtoClass() {
    return FerramentaActivationDTO.class;
  }

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
