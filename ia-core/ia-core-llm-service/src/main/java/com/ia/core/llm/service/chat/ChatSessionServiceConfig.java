package com.ia.core.llm.service.chat;

import com.ia.core.llm.model.chat.ChatSession;
import com.ia.core.llm.service.agente.AgenteRepository;
import com.ia.core.llm.service.model.chat.ChatSessionDTO;
import com.ia.core.service.CrudBaseService.CrudBaseServiceConfig;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Configuração de injeção de dependência para ChatSessionCrudService.
 * <p>
 * Fornece as dependências necessárias para o serviço de sessões de chat.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Component
public class ChatSessionServiceConfig
  extends CrudBaseServiceConfig<ChatSession, ChatSessionDTO> {

  @Getter
  private final AgenteRepository agenteRepository;

  public ChatSessionServiceConfig(BaseEntityRepository<ChatSession> repository,
                                  BaseEntityMapper<ChatSession, ChatSessionDTO> mapper,
                                  SearchRequestMapper searchRequestMapper,
                                  Translator translator,
                                  List<IServiceValidator<ChatSessionDTO>> validators,
                                  AgenteRepository agenteRepository) {
    super(repository, mapper, searchRequestMapper, translator, validators);
    this.agenteRepository = agenteRepository;
  }
}
