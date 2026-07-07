package com.ia.core.llm.service.model.prompt;

import com.ia.core.service.dto.DTO;
import com.ia.test.dto.CoreDTOUnitTest;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe PromptDTO.
 */
@DisplayName("Testes de PromptDTO")
class PromptDTOTestCore extends CoreDTOUnitTest<PromptDTO> {

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
