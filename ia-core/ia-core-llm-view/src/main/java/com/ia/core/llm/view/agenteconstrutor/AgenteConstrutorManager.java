package com.ia.core.llm.view.agenteconstrutor;

import com.ia.core.llm.service.model.agente.ResultadoConstrucaoOntologia;
import com.ia.core.view.manager.DefaultBaseManager;
import org.springframework.stereotype.Component;

@Component
public class AgenteConstrutorManager extends DefaultBaseManager<ResultadoConstrucaoOntologia> {

  public AgenteConstrutorManager(AgenteConstrutorManagerConfig config) {
    super(config);
  }
}
