package com.ia.core.llm.view.ferramenta;

import com.ia.core.llm.service.model.ferramenta.FerramentaDTO;
import com.ia.core.view.client.DefaultBaseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = FerramentaClient.NOME, url = FerramentaClient.URL)
public interface FerramentaClient
  extends DefaultBaseClient<FerramentaDTO> {

  String NOME = "ferramenta";
  String URL = "${feign.host}/api/${api.version}/${feign.url.ferramenta}";

  @PostMapping("/sync-discovery")
  void syncFromDiscovery();
}
