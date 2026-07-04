package com.ia.core.llm.view.agenteconversacional;

import com.ia.core.llm.service.model.agente.ContextConversacaoDTO;
import com.ia.core.view.manager.DefaultBaseManagerConfig;
import org.springframework.stereotype.Component;

@Component
public class AgenteConversacionalManagerConfig extends DefaultBaseManagerConfig<ContextConversacaoDTO> {

  public AgenteConversacionalManagerConfig(AgenteConversacionalClient client) {
    super(client);
  }
}
