package com.ia.core.llm.view.agenteconversacional;

import com.ia.core.llm.service.model.agente.ContextConversacaoDTO;
import com.ia.core.view.client.DefaultBaseClient;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Feign Client para comunicação REST com agente conversacional.
 * <p>
 * Segue o padrão ADR-004 para comunicação via Feign.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@FeignClient(name = AgenteConversacionalClient.NOME, url = AgenteConversacionalClient.URL)
public interface AgenteConversacionalClient extends DefaultBaseClient<ContextConversacaoDTO> {

  String NOME = "agente-conversacional";
  String URL = "${feign.host}/api/${api.version}/${feign.url.agente-conversacional}";
}
