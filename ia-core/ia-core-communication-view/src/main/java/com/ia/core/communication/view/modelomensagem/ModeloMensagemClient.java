package com.ia.core.communication.view.modelomensagem;

import com.ia.core.communication.service.model.modelomensagem.dto.ModeloMensagemDTO;
import com.ia.core.view.client.DefaultBaseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * Cliente Feign para operações de ModeloMensagem.
 * <p>
 * Define os endpoints disponíveis para o caso de uso de Modelo de Mensagem.
 *
 * @author Israel Araújo
 */
@FeignClient(name = ModeloMensagemClient.NOME,
             url = ModeloMensagemClient.URL)
public interface ModeloMensagemClient
  extends DefaultBaseClient<ModeloMensagemDTO> {

  /**
   * Nome do cliente.
   */
  public static final String NOME = "modelo-mensagem";

  /**
   * URL do cliente.
   */
  public static final String URL = "${feign.host}/api/${api.version}/${feign.url.modelo-mensagem}";

  /**
   * Aplica template com parâmetros.
   *
   * @param id          ID do modelo
   * @param parametros  parâmetros do template
   * @return corpo da mensagem formatado
   */
  @PostMapping("/{id}/aplicar-template")
  String aplicarTemplate(@PathVariable("id") Long id,
                         @RequestBody Map<String, String> parametros);

  /**
   * Envia um modelo de mensagem para um grupo de contatos.
   *
   * @param modeloId ID do modelo de mensagem
   * @param grupoId  ID do grupo de contatos
   */
  @PostMapping("/{modeloId}/enviar-para-grupo/{grupoId}")
  void enviarParaGrupo(@PathVariable("modeloId") Long modeloId,
                       @PathVariable("grupoId") Long grupoId);

  /**
   * Envia um modelo de mensagem para um contato individual.
   *
   * @param modeloId  ID do modelo de mensagem
   * @param contatoId ID do contato
   */
  @PostMapping("/{modeloId}/enviar-para-contato/{contatoId}")
  void enviarParaContato(@PathVariable("modeloId") Long modeloId,
                         @PathVariable("contatoId") Long contatoId);

}
