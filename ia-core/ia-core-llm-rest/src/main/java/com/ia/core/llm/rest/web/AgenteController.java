package com.ia.core.llm.rest.web;

import com.ia.core.llm.model.agente.Agente;
import com.ia.core.llm.service.agente.AgenteService;
import com.ia.core.llm.service.model.agente.AgenteDTO;
import com.ia.core.rest.control.DefaultBaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller REST para gerenciamento de agentes.
 * <p>
 * Expõe endpoints para operações CRUD em agentes especialistas.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Tag(name = "Agente", description = "Gerenciamento de agentes especialistas")
public class AgenteController
  extends DefaultBaseController<Agente,AgenteDTO> {

  public AgenteController(AgenteService agenteService) {
    super(agenteService);
  }

}
