package com.ia.core.llm.view.agenteconstrutor;

import com.ia.core.llm.service.model.agente.ResultadoConstrucaoOntologiaDTO;
import com.ia.core.view.manager.DefaultBaseManagerConfig;
import org.springframework.stereotype.Component;

@Component
public class AgenteConstrutorManagerConfig extends DefaultBaseManagerConfig<ResultadoConstrucaoOntologiaDTO> {

  public AgenteConstrutorManagerConfig(AgenteConstrutorClient client) {
    super(client);
  }
}
