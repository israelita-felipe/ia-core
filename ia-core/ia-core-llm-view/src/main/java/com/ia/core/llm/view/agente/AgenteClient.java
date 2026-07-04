package com.ia.core.llm.view.agente;

import com.ia.core.llm.service.model.agente.AgenteDTO;
import com.ia.core.view.client.DefaultBaseClient;

/**
 * Client Feign para acesso remoto ao serviço de agentes.
 * <p>
 * Permite que a camada View acesse o serviço de agentes através de REST,
 * seguindo o padrão dual hosting do ia-core.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface AgenteClient
  extends DefaultBaseClient<AgenteDTO> {

  String NOME = "agente";
  String URL = "${feign.host}/api/${api.version}/${feign.url.agente}";
}
