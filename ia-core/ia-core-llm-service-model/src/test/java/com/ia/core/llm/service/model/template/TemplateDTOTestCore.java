package com.ia.core.llm.service.model.template;

import com.ia.core.service.dto.CoreBaseDTOUnitTest;
import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe TemplateDTO.
 */
@DisplayName("Testes de TemplateDTO")
class TemplateDTOTestCore extends CoreBaseDTOUnitTest<TemplateDTO> {

  @Override
  public Class<TemplateDTO> getDtoClass() {
    return TemplateDTO.class;
  }

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
