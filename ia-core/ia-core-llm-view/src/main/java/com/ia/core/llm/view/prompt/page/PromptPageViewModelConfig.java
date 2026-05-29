package com.ia.core.llm.view.prompt.page;

import com.ia.core.llm.service.model.prompt.PromptDTO;
import com.ia.core.llm.view.template.TemplateManager;
import com.ia.core.security.view.log.operation.LogOperationManager;
import com.ia.core.security.view.log.operation.page.EntityPageViewModelConfig;
import com.ia.core.view.manager.DefaultBaseManager;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * Classe que representa as configurações para comando sistema page view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PromptPageViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@UIScope
@Component
public class PromptPageViewModelConfig
  extends EntityPageViewModelConfig<PromptDTO> {

  /** Serviço de template */
  @Getter
  private final TemplateManager templateService;

  /**
   * @param service
   * @param logOperationService
   */

public PromptPageViewModelConfig(DefaultBaseManager<PromptDTO> service,
                                           LogOperationManager logOperationService,
                                           TemplateManager templateService) {
    super(service, logOperationService);
    this.templateService = templateService;
  }

}
