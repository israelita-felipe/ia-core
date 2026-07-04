package com.ia.core.llm.service.model.skill;

import com.ia.core.service.dto.CoreBaseDTOUnitTest;
import com.ia.core.service.dto.DTO;
import org.junit.jupiter.api.DisplayName;

/**
 * Testes para a classe SkillDTO.
 */
@DisplayName("Testes de SkillDTO")
class SkillDTOTestCore extends CoreBaseDTOUnitTest<SkillDTO> {

  @Override
  public Class<SkillDTO> getDtoClass() {
    return SkillDTO.class;
  }

  @Override
  protected Class<?> getDtoInterface() {
    return DTO.class;
  }
}
