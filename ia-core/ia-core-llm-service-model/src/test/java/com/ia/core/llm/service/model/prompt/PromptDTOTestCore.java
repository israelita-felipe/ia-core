package com.ia.core.llm.service.model.prompt;

import com.ia.core.service.dto.CoreBaseDTOUnitTest;
import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe PromptDTO.
 */
@DisplayName("Testes de PromptDTO")
class PromptDTOTestCore extends CoreBaseDTOUnitTest<PromptDTO> {

  @Override
  public Class<PromptDTO> getDtoClass() {
    return PromptDTO.class;
  }

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
