package com.ia.core.llm.view.comando;

import com.ia.core.llm.service.model.comando.ComandoSistemaDTO;
import com.ia.core.view.manager.DefaultBaseManagerConfig;

/**
 *
 */
/**
 * Classe de configuração para comando sistema manager.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a ComandoSistemaManagerConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class ComandoSistemaManagerConfig
  extends DefaultBaseManagerConfig<ComandoSistemaDTO> {

  /**
   * @param client cliente do serviço
   */
  public ComandoSistemaManagerConfig(ComandoSistemaClient client) {
    super(client);
  }

}
