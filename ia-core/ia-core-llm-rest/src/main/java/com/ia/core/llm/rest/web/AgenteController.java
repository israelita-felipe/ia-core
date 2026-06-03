package com.ia.core.llm.rest.web;

import com.ia.core.llm.model.agente.Agente;
import com.ia.core.llm.service.agente.AgenteService;
import com.ia.core.llm.service.model.agente.AgenteDTO;
import com.ia.core.rest.control.DefaultBaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller REST para gerenciamento de agentes.
 * <p>
 * Expõe endpoints para operações CRUD em agentes especialistas.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/llm/agentes")
@Tag(name = "Agente", description = "Gerenciamento de agentes especialistas")
public class AgenteController
  extends DefaultBaseController<Agente,AgenteDTO> {

  private final AgenteService agenteService;

  public AgenteController(AgenteService agenteService) {
    super(agenteService);
    this.agenteService = agenteService;
  }

}
