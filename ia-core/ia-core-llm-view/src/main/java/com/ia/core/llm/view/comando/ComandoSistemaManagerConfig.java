package com.ia.core.llm.view.comando;

import com.ia.core.llm.service.model.comando.ComandoSistemaDTO;
import com.ia.core.view.manager.DefaultBaseManagerConfig;

/**
 *
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
