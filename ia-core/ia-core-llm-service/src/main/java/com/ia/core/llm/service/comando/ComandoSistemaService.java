package com.ia.core.llm.service.comando;

import com.ia.core.llm.model.comando.ComandoSistema;
import com.ia.core.llm.service.model.comando.ComandoSistemaDTO;
import com.ia.core.service.DefaultBaseService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Israel Araújo
 */
@Slf4j
public class ComandoSistemaService
  extends DefaultBaseService<ComandoSistema, ComandoSistemaDTO> {

  /**
   * @param repository
   * @param mapper
   * @param searchRequestMapper
   * @param translator
   */
  public ComandoSistemaService(ComandoSistemaServiceConfig config) {
    super(config);
  }

}
