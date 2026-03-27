package com.ia.core.communication.service.modelomensagem;

import java.util.List;

import com.ia.core.communication.model.ModeloMensagem;
import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
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
 * Configuração de injeção de dependência para ModeloMensagemService.
 *
 * @author Israel Araújo
 */
@Getter
public class ModeloMensagemServiceConfig
  extends
  DefaultSecuredBaseServiceConfig<ModeloMensagem, ModeloMensagemDTO> {

  public ModeloMensagemServiceConfig(BaseEntityRepository<ModeloMensagem> repository,
                                     BaseEntityMapper<ModeloMensagem, ModeloMensagemDTO> mapper,
                                     SearchRequestMapper searchRequestMapper,
                                     Translator translator,
                                     CoreSecurityAuthorizationManager authorizationManager,
                                     SecurityContextService securityContextService,
                                     LogOperationService logOperationService,
                                     List<IServiceValidator<ModeloMensagemDTO>> validators) {
    super(repository, mapper, searchRequestMapper, translator,
          authorizationManager, securityContextService, logOperationService,
          validators);
  }
}
