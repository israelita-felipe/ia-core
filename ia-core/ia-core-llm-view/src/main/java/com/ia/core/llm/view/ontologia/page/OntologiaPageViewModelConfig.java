package com.ia.core.llm.view.ontologia.page;

import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.llm.view.ontologia.OntologiaManager;
import com.ia.core.security.view.log.operation.LogOperationManager;
import com.ia.core.security.view.log.operation.page.EntityPageViewModelConfig;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@UIScope
@Component
public class OntologiaPageViewModelConfig extends EntityPageViewModelConfig<OntologiaDTO> {

  public OntologiaPageViewModelConfig(OntologiaManager service, LogOperationManager logOperationService) {
    super(service, logOperationService);
  }
}
