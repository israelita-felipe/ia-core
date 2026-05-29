package com.ia.core.llm.view.skill;

import com.ia.core.llm.service.model.skill.SkillDTO;
import com.ia.core.view.manager.DefaultBaseManager;

/**
 * Manager Feign para CRUD de skills. Metadados/activação via {@link com.ia.core.llm.service.skill.SkillService}.
 */
public class SkillManager
  extends DefaultBaseManager<SkillDTO> {

  public SkillManager(SkillManagerConfig config) {
    super(config);
  }
}
