package com.ia.core.llm.service.template;

import com.ia.core.llm.model.template.Template;
import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.service.DefaultCrudBaseServiceConfig;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Configuração de injeção de dependência para TemplateService.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Component
public class TemplateServiceConfig
  extends DefaultCrudBaseServiceConfig<Template, TemplateDTO> {

  public TemplateServiceConfig(BaseEntityRepository<Template> repository,
                               BaseEntityMapper<Template, TemplateDTO> mapper,
                               SearchRequestMapper searchRequestMapper,
                               Translator translator,
                               List<IServiceValidator<TemplateDTO>> validators) {
    super(repository, mapper, searchRequestMapper, translator, validators);
  }
}
