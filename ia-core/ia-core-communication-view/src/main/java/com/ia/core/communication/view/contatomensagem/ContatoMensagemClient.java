package com.ia.core.communication.view.contatomensagem;

import com.ia.core.communication.service.model.contatomensagem.dto.ContatoMensagemDTO;
import com.ia.core.view.client.DefaultBaseClient;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Cliente Feign para operações de ContatoMensagem.
 * <p>
 * Define os endpoints disponíveis para o caso de uso de Contato de Mensagem.
 * Fornece operações CRUD padrão herdadas de DefaultBaseClient.
 *
 * @author Israel Araújo
 * @see DefaultBaseClient
 */

@FeignClient(name = ContatoMensagemClient.NOME, url = ContatoMensagemClient.URL)
public interface ContatoMensagemClient extends DefaultBaseClient<ContatoMensagemDTO> {

  /**
   * Nome do cliente.
   */
  public static final String NOME = "contato-mensagem";

  /**
   * URL do cliente.
   */
  public static final String URL = "${feign.host}/api/${api.version}/${feign.url.contato-mensagem}";
}
