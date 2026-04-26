package com.ia.core.communication.service.modelomensagem;

import com.ia.core.communication.model.mensagem.ModeloMensagem;
import com.ia.core.communication.service.contatomensagem.ContatoMensagemMapper;
import com.ia.core.communication.service.contatomensagem.ContatoMensagemRepository;
import com.ia.core.communication.service.mensagem.MensagemService;
import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.communication.service.model.modelomensagem.dto.ProcessadorVariaveis;
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
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Configuração de injeção de dependência para ModeloMensagemService.
 *
 * @author Israel Araújo
 */
@Getter
@Component
public class ModeloMensagemServiceConfig
  extends
  DefaultSecuredBaseServiceConfig<ModeloMensagem, ModeloMensagemDTO> {

  private final MensagemService mensagemService;
  private final ContatoMensagemRepository contatoMensagemRepository;
  private final ProcessadorVariaveis processadorVariaveis;
  private final ContatoMensagemMapper contatoMensagemMapper;

  public ModeloMensagemServiceConfig(BaseEntityRepository<ModeloMensagem> repository,
                                     BaseEntityMapper<ModeloMensagem, ModeloMensagemDTO> mapper,
                                     SearchRequestMapper searchRequestMapper,
                                     Translator translator,
                                     CoreSecurityAuthorizationManager authorizationManager,
                                     SecurityContextService securityContextService,
                                     LogOperationService logOperationService,
                                     List<IServiceValidator<ModeloMensagemDTO>> validators,
                                     MensagemService mensagemService,
                                     ContatoMensagemRepository contatoMensagemRepository,
                                     ProcessadorVariaveis processadorVariaveis,
                                     ContatoMensagemMapper contatoMensagemMapper) {
    super(repository, mapper, searchRequestMapper, translator,
          authorizationManager, securityContextService, logOperationService,
          validators);
    this.mensagemService = mensagemService;
    this.contatoMensagemRepository = contatoMensagemRepository;
    this.processadorVariaveis = processadorVariaveis;
    this.contatoMensagemMapper = contatoMensagemMapper;
  }
}
