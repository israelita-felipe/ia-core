package com.ia.core.llm.service.model.ontologia;

import com.ia.core.service.dto.CoreBaseDTOUnitTest;
import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe EstatisticasOntologiaDTO.
 */
@DisplayName("Testes de EstatisticasOntologiaDTO")
class EstatisticasOntologiaDTOTestCore extends CoreBaseDTOUnitTest<EstatisticasOntologiaDTO> {

  @Override
  public Class<EstatisticasOntologiaDTO> getDtoClass() {
    return EstatisticasOntologiaDTO.class;
  }

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
