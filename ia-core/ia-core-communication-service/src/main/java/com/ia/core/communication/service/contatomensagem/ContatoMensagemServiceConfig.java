package com.ia.core.communication.service.contatomensagem;

import java.util.List;

import com.ia.core.communication.model.ContatoMensagem;
import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.security.service.DefaultSecuredBaseService.DefaultSecuredBaseServiceConfig;
import com.ia.core.security.service.SecurityContextService;
import com.ia.core.security.service.log.operation.LogOperationService;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;

import lombok.Getter;

/**
 * Configuração de injeção de dependência para ContatoMensagemService.
 *
 * @author Israel Araújo
 */
@Getter
public class ContatoMensagemServiceConfig
  extends
  DefaultSecuredBaseServiceConfig<ContatoMensagem, ContatoMensagemDTO> {

  public ContatoMensagemServiceConfig(BaseEntityRepository<ContatoMensagem> repository,
                                      BaseEntityMapper<ContatoMensagem, ContatoMensagemDTO> mapper,
                                      SearchRequestMapper searchRequestMapper,
                                      Translator translator,
                                      CoreSecurityAuthorizationManager authorizationManager,
                                      SecurityContextService securityContextService,
                                      LogOperationService logOperationService,
                                      List<IServiceValidator<ContatoMensagemDTO>> validators) {
    super(repository, mapper, searchRequestMapper, translator,
          authorizationManager, securityContextService, logOperationService,
          validators);
  }
}
