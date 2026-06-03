package com.ia.core.llm.view.ontologia;

import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.view.manager.DefaultBaseManagerConfig;
import org.springframework.stereotype.Component;

@Component
public class OntologiaManagerConfig extends DefaultBaseManagerConfig<OntologiaDTO> {

  public OntologiaManagerConfig(OntologiaClient client) {
    super(client);
  }
}
