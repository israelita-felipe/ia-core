package com.ia.core.llm.view.skill;

import com.ia.core.llm.service.model.skill.SkillDTO;
import com.ia.core.view.manager.DefaultBaseManagerConfig;
import org.springframework.stereotype.Component;

@Component
public class SkillManagerConfig
  extends DefaultBaseManagerConfig<SkillDTO> {

  public SkillManagerConfig(SkillClient client) {
    super(client);
  }
}
