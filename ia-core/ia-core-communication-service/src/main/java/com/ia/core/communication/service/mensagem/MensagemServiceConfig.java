package com.ia.core.communication.service.mensagem;

import java.util.List;

import com.ia.core.communication.model.Mensagem;
import com.ia.core.communication.service.estrategia.EstrategiaEnvioFactory;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
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
 * Configuração de injeção de dependência para MensagemService.
 *
 * @author Israel Araújo
 */
@Getter
public class MensagemServiceConfig
  extends DefaultSecuredBaseServiceConfig<Mensagem, MensagemDTO> {
  private final EstrategiaEnvioFactory estrategiaEnvioFactory;

  public MensagemServiceConfig(EstrategiaEnvioFactory estrategiaEnvioFactory,
                               BaseEntityRepository<Mensagem> repository,
                               BaseEntityMapper<Mensagem, MensagemDTO> mapper,
                               SearchRequestMapper searchRequestMapper,
                               Translator translator,
                               CoreSecurityAuthorizationManager authorizationManager,
                               SecurityContextService securityContextService,
                               LogOperationService logOperationService,
                               List<IServiceValidator<MensagemDTO>> validators) {
    super(repository, mapper, searchRequestMapper, translator,
          authorizationManager, securityContextService, logOperationService,
          validators);
    this.estrategiaEnvioFactory = estrategiaEnvioFactory;
  }
}
