package com.ia.core.communication.view.mensagem;

import org.springframework.stereotype.Service;

import com.ia.core.communication.service.model.enviomensagem.dto.EnvioMensagemRequestDTO;
import com.ia.core.communication.service.model.enviomensagem.dto.EnvioMensagemResponseDTO;
import com.ia.core.communication.service.model.mensagem.MensagemUseCase;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.communication.service.model.mensagem.dto.MensagemTranslator;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseManager;

/**
 * Manager para operações de Mensagens.
 * <p>
 * Implementa o caso de uso para gerenciamento de mensagens na camada de
 * visualização. Atua como proxy para as operações do serviço, delegando
 * chamadas ao cliente Feign.
 *
 * @author Israel Araújo
 */
@Service
public class MensagemManager
  extends DefaultSecuredViewBaseManager<MensagemDTO>
  implements MensagemUseCase {

  /**
   * Construtor com configuração.
   *
   * @param config configuração do manager
   */
  public MensagemManager(MensagemManagerConfig config) {
    super(config);
  }

  @Override
  public MensagemManagerConfig getConfig() {
    return (MensagemManagerConfig) super.getConfig();
  }

  @Override
  public MensagemClient getClient() {
    return getConfig().getClient();
  }

  @Override
  public String getFunctionalityTypeName() {
    return MensagemTranslator.MENSAGEM;
  }

  @Override
  public MensagemDTO enviar(MensagemDTO dto) {
    return getClient().enviar(dto);
  }

  @Override
  public EnvioMensagemResponseDTO enviarEmMassa(EnvioMensagemRequestDTO request) {
    return getClient().enviarEmMassa(request);
  }

}
