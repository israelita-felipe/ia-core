package com.ia.core.llm.service.skill;

import com.ia.core.llm.model.skill.Skill;
import com.ia.core.llm.service.model.skill.SkillDTO;
import com.ia.core.service.DefaultCrudBaseServiceConfig;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Configuração de injeção de dependência para SkillService.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Component
public class SkillServiceConfig
  extends DefaultCrudBaseServiceConfig<Skill, SkillDTO> {

  public SkillServiceConfig(BaseEntityRepository<Skill> repository,
                            BaseEntityMapper<Skill, SkillDTO> mapper,
                            SearchRequestMapper searchRequestMapper,
                            Translator translator,
                            List<IServiceValidator<SkillDTO>> validators) {
    super(repository, mapper, searchRequestMapper, translator, validators);
  }
}
