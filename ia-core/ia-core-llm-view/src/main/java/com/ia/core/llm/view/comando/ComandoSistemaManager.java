package com.ia.core.llm.view.comando;

import com.ia.core.llm.service.model.comando.ComandoSistemaDTO;
import com.ia.core.llm.service.model.comando.ComandoSistemaUseCase;
import com.ia.core.view.manager.DefaultBaseManager;
/**
 * Gerenciador de comando sistema.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a ComandoSistemaManager
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class ComandoSistemaManager
  extends DefaultBaseManager<ComandoSistemaDTO>
  implements ComandoSistemaUseCase {

  /**
   * @param config
   */
  public ComandoSistemaManager(ComandoSistemaManagerConfig config) {
    super(config);
  }

}
