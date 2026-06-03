package com.ia.core.llm.view.agenteconstrutor;

import com.ia.core.llm.service.model.agente.ResultadoConstrucaoOntologia;
import com.ia.core.view.manager.DefaultBaseManagerConfig;
import org.springframework.stereotype.Component;

@Component
public class AgenteConstrutorManagerConfig extends DefaultBaseManagerConfig<ResultadoConstrucaoOntologia> {

  public AgenteConstrutorManagerConfig(AgenteConstrutorClient client) {
    super(client);
  }
}
