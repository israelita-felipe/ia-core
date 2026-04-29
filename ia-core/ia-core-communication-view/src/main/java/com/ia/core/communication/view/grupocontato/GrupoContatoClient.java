package com.ia.core.communication.view.grupocontato;

import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.view.client.DefaultBaseClient;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Cliente Feign para operações de GrupoContato.
 * <p>
 * Define os endpoints disponíveis para o caso de uso de Grupo de Contato.
 * Fornece operações CRUD padrão herdadas de DefaultBaseClient.
 *
 * @author Israel Araújo
 * @see DefaultBaseClient
 */

@FeignClient(name = GrupoContatoClient.NOME, url = GrupoContatoClient.URL)
public interface GrupoContatoClient extends DefaultBaseClient<GrupoContatoDTO> {

  /**
   * Nome do cliente.
   */
  public static final String NOME = "grupo-contato";

  /**
   * URL do cliente.
   */
  public static final String URL = "${feign.host}/api/${api.version}/${feign.url.grupo-contato}";
}
