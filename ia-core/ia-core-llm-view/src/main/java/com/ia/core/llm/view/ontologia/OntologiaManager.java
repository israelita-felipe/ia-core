package com.ia.core.llm.view.ontologia;

import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.view.manager.DefaultBaseManager;
import org.springframework.stereotype.Component;

@Component
public class OntologiaManager extends DefaultBaseManager<OntologiaDTO> {

  public OntologiaManager(OntologiaManagerConfig config) {
    super(config);
  }
}
