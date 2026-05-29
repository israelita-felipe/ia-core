package com.ia.core.llm.service.model.skill;

import com.ia.core.service.usecase.CrudUseCase;

import java.util.List;

public interface SkillUseCase
  extends CrudUseCase<SkillDTO> {

  List<SkillMetadataDTO> listMetadata();

  SkillActivationDTO loadForActivation(Long id);
}
