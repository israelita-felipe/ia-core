package com.ia.core.llm.view.prompt;

import com.ia.core.llm.service.model.prompt.PromptDTO;
import com.ia.core.llm.service.model.prompt.PromptUseCase;
import com.ia.core.view.manager.DefaultBaseManager;
/**
 * Gerenciador de prompts de catálogo.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PromptManager
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class PromptManager
  extends DefaultBaseManager<PromptDTO>
  implements PromptUseCase {

  /**
   * @param config
   */
  public PromptManager(PromptManagerConfig config) {
    super(config);
  }

}
