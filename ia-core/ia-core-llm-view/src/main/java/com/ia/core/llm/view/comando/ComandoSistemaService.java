package com.ia.core.llm.view.comando;

import com.ia.core.llm.service.model.comando.ComandoSistemaDTO;
import com.ia.core.view.client.BaseClient;
import com.ia.core.view.service.DefaultBaseService;

/**
 * @author Israel Araújo
 */
public class ComandoSistemaService
  extends DefaultBaseService<ComandoSistemaDTO> {

  /**
   * @param client
   */
  public ComandoSistemaService(BaseClient<ComandoSistemaDTO> client) {
    super(client);
  }

}
