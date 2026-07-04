package com.ia.core.llm.view.ontologia;

import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.view.client.DefaultBaseClient;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Feign Client para comunicação REST com ontologias.
 * <p>
 * Segue o padrão ADR-004 para comunicação via Feign.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@FeignClient(name = OntologiaClient.NOME, url = OntologiaClient.URL)
public interface OntologiaClient extends DefaultBaseClient<OntologiaDTO> {

  String NOME = "ontologia";
  String URL = "${feign.host}/api/${api.version}/${feign.url.ontologia}";
}
