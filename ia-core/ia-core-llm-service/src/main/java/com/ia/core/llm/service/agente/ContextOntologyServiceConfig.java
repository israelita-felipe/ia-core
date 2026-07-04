package com.ia.core.llm.service.agente;

import com.ia.core.llm.model.agente.ContextoConversacao;
import com.ia.core.llm.service.agente.mapper.ContextoConversacaoMapper;
import com.ia.core.llm.service.agente.mapper.OntologiaMapper;
import com.ia.core.llm.service.model.agente.ContextConversacaoDTO;
import com.ia.core.llm.service.repository.ContextoConversacaoRepository;
import com.ia.core.service.DefaultCrudBaseServiceConfig;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.translator.Translator;
import lombok.Getter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Configuração para ContextoConversacaoService.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Component
public class ContextOntologyServiceConfig extends DefaultCrudBaseServiceConfig<ContextoConversacao, ContextConversacaoDTO> {

  @Getter
  private final ContextoConversacaoRepository repository;
  private final ContextoConversacaoMapper mapper;
  private final SearchRequestMapper searchRequestMapper;
  private final Translator translator;
  private final ApplicationEventPublisher eventPublisher;

  @Getter
  private final OntologiaMapper ontologiaMapper;

  public ContextOntologyServiceConfig(ContextoConversacaoRepository repository,
                                      ContextoConversacaoMapper mapper,
                                      SearchRequestMapper searchRequestMapper,
                                      Translator translator,
                                      ApplicationEventPublisher eventPublisher,
                                      OntologiaMapper ontologiaMapper) {
    super(repository, mapper, searchRequestMapper, translator, List.of());
    this.repository = repository;
    this.mapper = mapper;
    this.searchRequestMapper = searchRequestMapper;
    this.translator = translator;
    this.eventPublisher = eventPublisher;
    this.ontologiaMapper = ontologiaMapper;
  }

  @Override
  public ContextoConversacaoRepository getRepository() {
    return repository;
  }

  @Override
  public ContextoConversacaoMapper getMapper() {
    return mapper;
  }

  @Override
  public SearchRequestMapper getSearchRequestMapper() {
    return searchRequestMapper;
  }

  @Override
  public Translator getTranslator() {
    return translator;
  }

  @Override
  public ApplicationEventPublisher getEventPublisher() {
    return eventPublisher;
  }
}
