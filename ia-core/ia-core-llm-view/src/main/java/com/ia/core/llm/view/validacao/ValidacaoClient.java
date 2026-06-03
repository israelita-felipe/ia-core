package com.ia.core.llm.view.validacao;

import com.ia.core.llm.service.model.validacao.ResultadoValidacao;
import com.ia.core.view.client.DefaultBaseClient;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Feign Client para comunicação REST com validação.
 * <p>
 * Segue o padrão ADR-004 para comunicação via Feign.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@FeignClient(name = "ia-core-llm-rest", url = "${ia-core.rest.url}")
public interface ValidacaoClient extends DefaultBaseClient<ResultadoValidacao> {
}
