package com.ia.core.communication.view.contatomensagem;

import org.springframework.stereotype.Service;

import com.ia.core.communication.service.model.contatomensagem.ContatoMensagemUseCase;
import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemTranslator;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseManager;

/**
 * Manager para operações de ContatoMensagem.
 * <p>
 * Implementa o caso de uso para gerenciamento de contatos de mensagens na
 * camada de visualização. Atua como proxy para as operações do serviço,
 * delegando chamadas ao cliente Feign.
 *
 * @author Israel Araújo
 * @see ContatoMensagemUseCase
 */
@Service
public class ContatoMensagemManager
  extends DefaultSecuredViewBaseManager<ContatoMensagemDTO>
  implements ContatoMensagemUseCase {

  public ContatoMensagemManager(ContatoMensagemManagerConfig config) {
    super(config);
  }

  @Override
  public ContatoMensagemManagerConfig getConfig() {
    return (ContatoMensagemManagerConfig) super.getConfig();
  }

  @Override
  public ContatoMensagemClient getClient() {
    return getConfig().getClient();
  }

  @Override
  public String getFunctionalityTypeName() {
    return ContatoMensagemTranslator.CONTATO_MENSAGEM;
  }
}
