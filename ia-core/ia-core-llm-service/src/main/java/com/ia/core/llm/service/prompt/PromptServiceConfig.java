package com.ia.core.llm.service.prompt;

import com.ia.core.llm.model.prompt.Prompt;
import com.ia.core.llm.service.model.prompt.PromptDTO;
import com.ia.core.service.DefaultCrudBaseServiceConfig;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Configuração de injeção de dependência para PromptService.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Component
public class PromptServiceConfig
  extends DefaultCrudBaseServiceConfig<Prompt, PromptDTO> {

  public PromptServiceConfig(BaseEntityRepository<Prompt> repository,
                             BaseEntityMapper<Prompt, PromptDTO> mapper,
                             SearchRequestMapper searchRequestMapper,
                             Translator translator,
                             List<IServiceValidator<PromptDTO>> validators) {
    super(repository, mapper, searchRequestMapper, translator, validators);
  }
}
