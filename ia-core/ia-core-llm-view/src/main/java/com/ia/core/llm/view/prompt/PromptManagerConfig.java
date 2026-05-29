package com.ia.core.llm.view.prompt;

import com.ia.core.llm.service.model.prompt.PromptDTO;
import com.ia.core.view.manager.DefaultBaseManagerConfig;

/**
 *
 */
/**
 * Classe de configuração para comando sistema manager.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PromptManagerConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class PromptManagerConfig
  extends DefaultBaseManagerConfig<PromptDTO> {

  /**
   * @param client cliente do serviço
   */
  public PromptManagerConfig(PromptClient client) {
    super(client);
  }

}
