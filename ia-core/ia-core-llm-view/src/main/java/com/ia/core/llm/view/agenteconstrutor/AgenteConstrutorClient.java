package com.ia.core.llm.view.agenteconstrutor;

import com.ia.core.llm.service.model.agente.RequisicaoConstrucaoOntologiaDTO;
import com.ia.core.llm.service.model.agente.ResultadoConstrucaoOntologiaDTO;
import com.ia.core.view.client.DefaultBaseClient;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Feign Client para comunicação REST com agente construtor.
 * <p>
 * Segue o padrão ADR-004 para comunicação via Feign.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@FeignClient(name = AgenteConstrutorClient.NOME, url = AgenteConstrutorClient.URL)
public interface AgenteConstrutorClient extends DefaultBaseClient<ResultadoConstrucaoOntologiaDTO> {

  String NOME = "agente-construtor";
  String URL = "${feign.host}/api/${api.version}/${feign.url.agente-construtor}";
}
