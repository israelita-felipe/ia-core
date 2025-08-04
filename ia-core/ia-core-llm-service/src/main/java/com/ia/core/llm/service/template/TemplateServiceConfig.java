package com.ia.core.llm.service.template;

import java.util.List;

import com.ia.core.llm.model.template.Template;
import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.service.DefaultBaseService.DefaultBaseServiceConfig;
import com.ia.core.service.mapper.BaseMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;

/**
 *
 */
public class TemplateServiceConfig
  extends DefaultBaseServiceConfig<Template, TemplateDTO> {

  /**
   * @param repository
   * @param mapper
   * @param searchRequestMapper
   * @param translator
   */
  public TemplateServiceConfig(BaseEntityRepository<Template> repository,
                               BaseMapper<Template, TemplateDTO> mapper,
                               SearchRequestMapper searchRequestMapper,
                               Translator translator,
                               List<IServiceValidator<TemplateDTO>> validators) {
    super(repository, mapper, searchRequestMapper, translator, validators);
  }

}
