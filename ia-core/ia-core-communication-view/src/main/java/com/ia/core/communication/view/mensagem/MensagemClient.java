package com.ia.core.communication.view.mensagem;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ia.core.communication.service.model.enviomensagem.dto.EnvioMensagemRequestDTO;
import com.ia.core.communication.service.model.enviomensagem.dto.EnvioMensagemResponseDTO;
import com.ia.core.communication.service.model.mensagem.dto.MensagemDTO;
import com.ia.core.view.client.DefaultBaseClient;

/**
 * Cliente Feign para operações de Mensagem.
 * <p>
 * Define os endpoints disponíveis para o caso de uso de Mensagem.
 *
 * @author Israel Araújo
 */
@FeignClient(name = MensagemClient.NOME, url = MensagemClient.URL)
public interface MensagemClient extends DefaultBaseClient<MensagemDTO> {

  /**
   * Nome do cliente.
   */
  public static final String NOME = "mensagem";

  /**
   * URL do cliente.
   */
  public static final String URL = "${feign.host}/api/${api.version}/${feign.url.mensagem}";

  /**
   * Envia uma mensagem.
   *
   * @param dto mensagem a ser enviada
   * @return mensagem enviada
   */
  @PostMapping("/enviar")
  MensagemDTO enviar(@RequestBody MensagemDTO dto);

  /**
   * Envia mensagens em massa.
   *
   * @param request requisição de envio em massa
   * @return resposta do envio
   */
  @PostMapping("/enviar-massa")
  EnvioMensagemResponseDTO enviarEmMassa(@RequestBody EnvioMensagemRequestDTO request);
}
