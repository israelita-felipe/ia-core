package com.ia.core.llm.service.model.template;

import com.ia.core.service.dto.DTO;
import com.ia.test.dto.CoreDTOUnitTest;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe TemplateDTO.
 */
@DisplayName("Testes de TemplateDTO")
class TemplateDTOTestCore extends CoreDTOUnitTest<TemplateDTO> {

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
