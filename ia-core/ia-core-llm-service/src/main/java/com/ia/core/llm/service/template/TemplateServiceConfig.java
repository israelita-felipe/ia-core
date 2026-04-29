package com.ia.core.llm.service.template;

import com.ia.core.llm.model.template.Template;
import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.service.DefaultBaseService.DefaultBaseServiceConfig;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Classe de configuração para template service.
 * <p>
 * Responsável por gerenciar as dependências e configurações do TemplateService.
 *
 * @author Israel Araújo
 * @since 1.0
 */
@Component
public class TemplateServiceConfig
  extends DefaultBaseServiceConfig<Template, TemplateDTO> {

  /**
   * Construtor da configuração do serviço de template.
   *
   * @param repository repositório de Template
   * @param mapper mapper de Template
   * @param searchRequestMapper mapper de requisição de busca
   * @param translator tradutor de mensagens
   * @param validators validadores de serviço
   */
  public TemplateServiceConfig(BaseEntityRepository<Template> repository,
                               BaseEntityMapper<Template, TemplateDTO> mapper,
                               SearchRequestMapper searchRequestMapper,
                               Translator translator,
                               List<IServiceValidator<TemplateDTO>> validators) {
    super(repository, mapper, searchRequestMapper, translator, validators);
  }

}
