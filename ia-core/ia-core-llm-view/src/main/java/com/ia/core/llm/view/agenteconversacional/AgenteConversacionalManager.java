package com.ia.core.llm.view.agenteconversacional;

import com.ia.core.llm.service.model.agente.ContextoConversacao;
import com.ia.core.view.manager.DefaultBaseManager;
import org.springframework.stereotype.Component;

@Component
public class AgenteConversacionalManager extends DefaultBaseManager<ContextoConversacao> {

  public AgenteConversacionalManager(AgenteConversacionalManagerConfig config) {
    super(config);
  }
}
