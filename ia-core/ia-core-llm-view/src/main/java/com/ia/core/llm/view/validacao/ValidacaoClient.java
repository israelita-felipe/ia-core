package com.ia.core.llm.view.validacao;

import com.ia.core.llm.service.model.ontologia.ResultadoValidacao;
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
@FeignClient(name = ValidacaoClient.NOME, url = ValidacaoClient.URL)
public interface ValidacaoClient extends DefaultBaseClient<ResultadoValidacao> {

  String NOME = "validacao";
  String URL = "${feign.host}/api/${api.version}/${feign.url.validacao}";
}
