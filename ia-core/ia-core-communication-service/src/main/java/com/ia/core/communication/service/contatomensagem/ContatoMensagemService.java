package com.ia.core.communication.service.contatomensagem;

import com.ia.core.communication.model.contato.ContatoMensagem;
import com.ia.core.communication.service.model.contatomensagem.ContatoMensagemUseCase;
import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemTranslator;
import com.ia.core.security.service.CrudSecuredBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Serviço para gerenciamento de contatos de mensagens.
 * <p>
 * Implementa as operações de caso de uso para o domínio de contatos de mensagem,
 * incluindo CRUD completo e validações específicas.
 * <p>
 * Principais responsabilidades:
 * <ul>
 *   <li>Gerenciamento completo de contatos de mensagem via CRUD</li>
 *   <li>Validação de dados de contatos</li>
 *   <li>Integração com grupos de contatos</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
public class ContatoMensagemService
  extends CrudSecuredBaseService<ContatoMensagem, ContatoMensagemDTO>
  implements ContatoMensagemUseCase {

  public ContatoMensagemService(ContatoMensagemServiceConfig config) {
    super(config);
  }

  @Override
  public String getFunctionalityTypeName() {
    return ContatoMensagemTranslator.CONTATO_MENSAGEM;
  }
}
