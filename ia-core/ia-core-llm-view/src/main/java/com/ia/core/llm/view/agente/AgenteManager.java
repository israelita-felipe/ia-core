package com.ia.core.llm.view.agente;

import com.ia.core.llm.service.model.agente.AgenteDTO;
import com.ia.core.view.manager.DefaultBaseManager;
import lombok.extern.slf4j.Slf4j;

/**
 * Manager Feign para CRUD de agentes.
 * <p>
 * Implementa o padrão dual hosting do ia-core, permitindo acesso remoto
 * ao serviço de agentes através de REST.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public class AgenteManager
  extends DefaultBaseManager<AgenteDTO> {

  public AgenteManager(AgenteManagerConfig config) {
    super(config);
  }
}
