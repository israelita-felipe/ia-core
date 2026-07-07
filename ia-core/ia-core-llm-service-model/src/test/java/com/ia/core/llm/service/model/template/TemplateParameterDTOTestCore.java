package com.ia.core.llm.service.model.template;

import com.ia.core.service.dto.DTO;
import com.ia.test.dto.CoreDTOUnitTest;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe TemplateParameterDTO.
 */
@DisplayName("Testes de TemplateParameterDTO")
class TemplateParameterDTOTestCore extends CoreDTOUnitTest<TemplateParameterDTO> {

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
