package com.ia.core.llm.service.skill;

import com.ia.core.llm.model.skill.Skill;
import com.ia.core.service.repository.BaseEntityRepository;

import java.util.List;

/**
 * Repositório para acesso a dados de Skill.
 * <p>
 * Fornece métodos específicos para buscar e manipular skills no banco de dados.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface SkillRepository
  extends BaseEntityRepository<Skill> {

  List<Skill> findByAtivoTrue();
}
