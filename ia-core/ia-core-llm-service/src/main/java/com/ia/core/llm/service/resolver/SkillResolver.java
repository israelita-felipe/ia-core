package com.ia.core.llm.service.resolver;

import com.ia.core.llm.model.skill.Skill;
import com.ia.core.llm.service.skill.SkillRepository;
import com.ia.core.llm.service.model.skill.SkillDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utilitário compartilhado para resolver entidades {@link Skill} a partir de DTOs.
 * <p>
 * Centraliza a lógica de resolução que antes estava em {@code AgenteService}.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Component
public class SkillResolver {

  private final SkillRepository skillRepository;

  public SkillResolver(SkillRepository skillRepository) {
    this.skillRepository = skillRepository;
  }

  /**
   * Resolve entidades {@link Skill} a partir de uma lista de DTOs,
   * buscando por {@code id}.
   *
   * @param skillDtos lista de DTOs de skill (pode ser {@code null})
   * @return lista de entidades resolvidas (nunca {@code null})
   */
  public List<Skill> resolve(List<SkillDTO> skillDtos) {
    if (skillDtos == null || skillDtos.isEmpty()) {
      return Collections.emptyList();
    }
    List<Skill> result = new ArrayList<>();
    for (SkillDTO s : skillDtos) {
      if (s.getId() != null) {
        skillRepository.findById(s.getId()).ifPresent(result::add);
      }
    }
    return result;
  }
}
