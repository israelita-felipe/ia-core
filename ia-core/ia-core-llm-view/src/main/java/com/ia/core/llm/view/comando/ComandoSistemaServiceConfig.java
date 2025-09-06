package com.ia.core.llm.view.comando;

import com.ia.core.llm.service.model.comando.ComandoSistemaDTO;
import com.ia.core.view.service.DefaultBaseServiceConfig;

/**
 *
 */
public class ComandoSistemaServiceConfig
  extends DefaultBaseServiceConfig<ComandoSistemaDTO> {

  /**
   * @param client cliente do serviço
   */
  public ComandoSistemaServiceConfig(ComandoSistemaClient client) {
    super(client);
  }

}
