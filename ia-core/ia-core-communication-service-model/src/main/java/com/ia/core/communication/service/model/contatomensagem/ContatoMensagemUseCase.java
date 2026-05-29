package com.ia.core.communication.service.model.contatomensagem;

import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.service.usecase.CrudUseCase;

/**
 * Interface de Use Case para ContatoMensagem.
 * <p>
 * Define as operações específicas do domínio de contatos de mensagens conforme
 * definido no caso de uso Manter-ContatoMensagem.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface ContatoMensagemUseCase
  extends CrudUseCase<ContatoMensagemDTO> {

}
