package com.ia.core.llm.service.model.ontologia;

import com.ia.core.service.dto.CoreBaseDTOUnitTest;
import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe OntologiaDTO.
 */
@DisplayName("Testes de OntologiaDTO")
class OntologiaDTOTestCore extends CoreBaseDTOUnitTest<OntologiaDTO> {

  @Override
  public Class<OntologiaDTO> getDtoClass() {
    return OntologiaDTO.class;
  }

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
