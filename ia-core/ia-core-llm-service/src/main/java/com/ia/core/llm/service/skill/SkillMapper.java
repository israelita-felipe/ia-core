package com.ia.core.llm.service.skill;

import com.ia.core.llm.model.skill.Skill;
import com.ia.core.llm.service.ferramenta.FerramentaMapper;
import com.ia.core.llm.service.model.skill.SkillDTO;
import com.ia.core.llm.service.template.TemplateMapper;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper MapStruct para conversão entre {@link Skill} e {@link SkillDTO}.
 * <p>
 * Utiliza o MapStruct para geração automática de implementação de mapeamento.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Mapper(componentModel = "spring", uses = { TemplateMapper.class, FerramentaMapper.class })
public interface SkillMapper
  extends BaseEntityMapper<Skill, SkillDTO> {
}
