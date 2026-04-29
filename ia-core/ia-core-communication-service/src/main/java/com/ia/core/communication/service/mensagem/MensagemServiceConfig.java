package com.ia.core.communication.service.mensagem;

import com.ia.core.communication.model.mensagem.Mensagem;
import com.ia.core.communication.service.contatomensagem.ContatoMensagemMapper;
import com.ia.core.communication.service.contatomensagem.ContatoMensagemRepository;
import com.ia.core.communication.service.estrategia.EstrategiaEnvioFactory;
import com.ia.core.communication.service.grupocontato.GrupoContatoRepository;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.communication.service.model.modelomensagem.dto.ProcessadorVariaveis;
import com.ia.core.communication.service.modelomensagem.ModeloMensagemRepository;
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
 * Configuração de injeção de dependência para MensagemService.
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa as configurações para mensagem service.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a MensagemServiceConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Getter
@Component
public class MensagemServiceConfig
  extends DefaultSecuredBaseServiceConfig<Mensagem, MensagemDTO> {
  private final EstrategiaEnvioFactory estrategiaEnvioFactory;
  private final GrupoContatoRepository grupoContatoRepository;
  private final ContatoMensagemRepository contatoMensagemRepository;
  private final ModeloMensagemRepository modeloMensagemRepository;
  private final ProcessadorVariaveis processadorVariaveis;
  private final ContatoMensagemMapper contatoMensagemMapper;

  public MensagemServiceConfig(EstrategiaEnvioFactory estrategiaEnvioFactory,
                               GrupoContatoRepository grupoContatoRepository,
                               ContatoMensagemRepository contatoMensagemRepository,
                               ModeloMensagemRepository modeloMensagemRepository,
                               ProcessadorVariaveis processadorVariaveis,
                               ContatoMensagemMapper contatoMensagemMapper,
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
    this.grupoContatoRepository = grupoContatoRepository;
    this.contatoMensagemRepository = contatoMensagemRepository;
    this.modeloMensagemRepository = modeloMensagemRepository;
    this.processadorVariaveis = processadorVariaveis;
    this.contatoMensagemMapper = contatoMensagemMapper;
  }
}
