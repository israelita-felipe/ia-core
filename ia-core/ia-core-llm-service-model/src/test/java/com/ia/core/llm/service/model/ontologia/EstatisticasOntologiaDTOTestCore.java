package com.ia.core.llm.service.model.ontologia;

import com.ia.core.service.dto.DTO;
import com.ia.test.dto.CoreDTOUnitTest;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe EstatisticasOntologiaDTO.
 */
@DisplayName("Testes de EstatisticasOntologiaDTO")
class EstatisticasOntologiaDTOTestCore extends com.ia.test.dto.CoreDTOUnitTest<EstatisticasOntologiaDTO> {

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
