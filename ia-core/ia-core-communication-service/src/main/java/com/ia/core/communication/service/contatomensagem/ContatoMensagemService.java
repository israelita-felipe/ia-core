package com.ia.core.communication.service.contatomensagem;

import com.ia.core.communication.model.contato.ContatoMensagem;
import com.ia.core.communication.service.model.contatomensagem.ContatoMensagemUseCase;
import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemTranslator;
import com.ia.core.security.service.DefaultSecuredBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Serviço para gerenciamento de contatos de mensagens.
 *
 * @author Israel Araújo
 */
@Slf4j
@Service
public class ContatoMensagemService
  extends DefaultSecuredBaseService<ContatoMensagem, ContatoMensagemDTO>
  implements ContatoMensagemUseCase {

  public ContatoMensagemService(ContatoMensagemServiceConfig config) {
    super(config);
  }

  @Override
  public String getFunctionalityTypeName() {
    return ContatoMensagemTranslator.CONTATO_MENSAGEM;
  }
}
