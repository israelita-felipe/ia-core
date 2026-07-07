package com.ia.core.llm.service.model.skill;

import com.ia.core.service.dto.DTO;
import com.ia.test.dto.CoreDTOUnitTest;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe SkillDTO.
 */
@DisplayName("Testes de SkillDTO")
class SkillDTOTestCore extends CoreDTOUnitTest<SkillDTO> {

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
