package com.ia.core.communication.service.model.mensagem;

import com.ia.core.communication.service.model.enviomensagem.dto.EnvioMensagemRequestDTO;
import com.ia.core.communication.service.model.enviomensagem.dto.EnvioMensagemResponseDTO;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.service.usecase.CrudUseCase;

/**
 * Interface de Use Case para Mensagem.
 * <p>
 * Define as operações específicas do domínio de mensagens conforme definido no
 * caso de uso Manter-Mensagem.
 *
 * @author Israel Araújo
 */
public interface MensagemUseCase
  extends CrudUseCase<MensagemDTO> {

  /**
   * Envia uma mensagem.
   *
   * @param dto dados da mensagem
   * @return mensagem enviada
   */
  MensagemDTO enviar(MensagemDTO dto);

  /**
   * Envia mensagens em massa.
   *
   * @param request requisição de envio em massa
   * @return resposta do envio
   */
  EnvioMensagemResponseDTO enviarEmMassa(EnvioMensagemRequestDTO request);

}
