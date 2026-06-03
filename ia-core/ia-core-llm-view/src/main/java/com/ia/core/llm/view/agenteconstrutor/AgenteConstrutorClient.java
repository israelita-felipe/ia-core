package com.ia.core.llm.view.agenteconstrutor;

import com.ia.core.llm.service.model.agente.RequisicaoConstrucaoOntologia;
import com.ia.core.llm.service.model.agente.ResultadoConstrucaoOntologia;
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
@FeignClient(name = "ia-core-llm-rest", url = "${ia-core.rest.url}")
public interface AgenteConstrutorClient extends DefaultBaseClient<ResultadoConstrucaoOntologia> {
}
