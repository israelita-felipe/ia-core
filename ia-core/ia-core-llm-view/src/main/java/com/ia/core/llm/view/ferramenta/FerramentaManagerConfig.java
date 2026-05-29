package com.ia.core.llm.view.ferramenta;

import com.ia.core.llm.service.model.ferramenta.FerramentaDTO;
import com.ia.core.view.manager.DefaultBaseManagerConfig;
import org.springframework.stereotype.Component;

@Component
public class FerramentaManagerConfig
  extends DefaultBaseManagerConfig<FerramentaDTO> {

  public FerramentaManagerConfig(FerramentaClient client) {
    super(client);
  }
}
