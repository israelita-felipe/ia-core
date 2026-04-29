package com.ia.core.llm.view.template;

import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.view.manager.DefaultBaseManagerConfig;

/**
 *
 */
/**
 * Classe de configuração para template manager.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a TemplateManagerConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class TemplateManagerConfig
  extends DefaultBaseManagerConfig<TemplateDTO> {

  /**
   * @param client cliente do serviço
   */
  public TemplateManagerConfig(TemplateClient client) {
    super(client);
  }

}
