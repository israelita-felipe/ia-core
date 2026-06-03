package com.ia.core.llm.view.agenteconversacional;

import com.ia.core.llm.service.model.agente.ContextoConversacao;
import com.ia.core.llm.service.model.agente.RespostaAgente;
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
@FeignClient(name = "ia-core-llm-rest", url = "${ia-core.rest.url}")
public interface AgenteConversacionalClient extends DefaultBaseClient<ContextoConversacao> {
}
