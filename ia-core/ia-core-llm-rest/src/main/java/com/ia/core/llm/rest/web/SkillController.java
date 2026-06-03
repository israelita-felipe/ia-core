package com.ia.core.llm.rest.web;

import com.ia.core.llm.model.skill.Skill;
import com.ia.core.llm.service.model.skill.SkillActivationDTO;
import com.ia.core.llm.service.model.skill.SkillDTO;
import com.ia.core.llm.service.model.skill.SkillMetadataDTO;
import com.ia.core.llm.service.skill.SkillService;
import com.ia.core.rest.control.DefaultBaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller REST para gerenciamento de skills.
 * <p>
 * Expõe endpoints para operações CRUD em habilidades especializadas.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/llm/skills")
@Tag(name = "Skill", description = "Gerenciamento de habilidades especializadas")
public class SkillController
  extends DefaultBaseController<Skill, SkillDTO> {

  private final SkillService skillService;

  public SkillController(SkillService skillService) {
    super(skillService);
    this.skillService = skillService;
  }

  @Operation(summary = "Lista metadados de skills ativas (progressive disclosure)")
  @GetMapping("/metadata")
  public List<SkillMetadataDTO> listMetadata() {
    return skillService.listMetadata();
  }

  @Operation(summary = "Carrega skill com instruções e ferramentas para ativação")
  @GetMapping("/{id}/activation")
  public SkillActivationDTO loadForActivation(@PathVariable Long id) {
    return skillService.loadForActivation(id);
  }
}
