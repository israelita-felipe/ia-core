package com.ia.core.llm.service.model.ferramenta;

import com.ia.core.service.dto.DTO;
import com.ia.test.dto.CoreDTOUnitTest;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe FerramentaMetadataDTO.
 */
@DisplayName("Testes de FerramentaMetadataDTO")
class FerramentaMetadataDTOTestCore extends com.ia.test.dto.CoreDTOUnitTest<FerramentaMetadataDTO> {

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
