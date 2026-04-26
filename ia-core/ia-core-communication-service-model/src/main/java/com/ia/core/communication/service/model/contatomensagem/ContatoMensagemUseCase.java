package com.ia.core.communication.service.model.contatomensagem;

import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.service.usecase.CrudUseCase;

/**
 * Interface de Use Case para GrupoContato.
 * <p>
 * Define as operações específicas do domínio de grupos de contatos conforme
 * definido no caso de uso Manter-GrupoContato.
 *
 * @author Israel Araújo
 */
public interface ContatoMensagemUseCase
  extends CrudUseCase<ContatoMensagemDTO> {

}
