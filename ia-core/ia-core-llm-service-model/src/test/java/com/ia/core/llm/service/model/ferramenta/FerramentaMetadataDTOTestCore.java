package com.ia.core.llm.service.model.ferramenta;

import com.ia.core.service.dto.CoreBaseDTOUnitTest;
import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe FerramentaMetadataDTO.
 */
@DisplayName("Testes de FerramentaMetadataDTO")
class FerramentaMetadataDTOTestCore extends CoreBaseDTOUnitTest<FerramentaMetadataDTO> {

  @Override
  public Class<FerramentaMetadataDTO> getDtoClass() {
    return FerramentaMetadataDTO.class;
  }

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
