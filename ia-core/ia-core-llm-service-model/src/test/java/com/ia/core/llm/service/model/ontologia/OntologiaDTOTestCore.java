package com.ia.core.llm.service.model.ontologia;

import com.ia.core.service.dto.DTO;
import com.ia.test.dto.CoreDTOUnitTest;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe OntologiaDTO.
 */
@DisplayName("Testes de OntologiaDTO")
class OntologiaDTOTestCore extends CoreDTOUnitTest<OntologiaDTO> {

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
