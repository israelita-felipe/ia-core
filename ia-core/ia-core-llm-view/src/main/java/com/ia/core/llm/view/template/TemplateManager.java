package com.ia.core.llm.view.template;

import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.llm.service.model.template.TemplateUseCase;
import com.ia.core.view.manager.DefaultBaseManager;
/**
 * Gerenciador de template.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a TemplateManager
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class TemplateManager
  extends DefaultBaseManager<TemplateDTO>
  implements TemplateUseCase {

  /**
   * @param config
   */
  public TemplateManager(TemplateManagerConfig config) {
    super(config);
  }

}
