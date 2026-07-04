package com.ia.core.llm.rest.web;

import com.ia.core.llm.service.agente.AgentOrchestratorService;
import com.ia.core.llm.service.model.session.AgentConfirmationDTO;
import com.ia.core.llm.service.model.session.AgentSessionRequestDTO;
import com.ia.core.llm.service.model.session.AgentSessionResponseDTO;
import com.ia.core.llm.service.model.ferramenta.FerramentaMetadataDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciamento de sessões de agente.
 * <p>
 * Expõe endpoints para execução e confirmação de sessões de orquestração multi-agente.
 * Usa AgentOrchestratorService diretamente (removido AgentSessionService wrapper).
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/${api.version}/llm/agente/sessao")
@Tag(name = "AgentSession", description = "Gerenciamento de sessões de orquestração multi-agente")
public class AgentSessionController {

  private final AgentOrchestratorService agentOrchestratorService;

  public AgentSessionController(AgentOrchestratorService agentOrchestratorService) {
    this.agentOrchestratorService = agentOrchestratorService;
  }

  /**
   * Executa uma requisição em uma sessão de agente.
   *
   * @param request requisição do usuário
   * @return resposta do agente
   */
  @PostMapping("/run")
  public AgentSessionResponseDTO run(@RequestBody AgentSessionRequestDTO request) {
    log.debug("Executando requisição em sessão de agente: sessionId={}", request.getSessionId());
    return agentOrchestratorService.run(request);
  }

  /**
   * Confirma uma ação pendente em uma sessão de agente.
   *
   * @param confirmation confirmação da ação
   * @return resposta do agente
   */
  @PostMapping("/confirm")
  public AgentSessionResponseDTO confirm(@RequestBody AgentConfirmationDTO confirmation) {
    log.debug("Confirmando ação em sessão de agente: sessionId={}", confirmation.getSessionId());
    return agentOrchestratorService.confirm(confirmation);
  }

  /**
   * Lista todas as ferramentas disponíveis.
   *
   * @return lista de metadados de ferramentas
   */
  @GetMapping("/ferramentas")
  public List<FerramentaMetadataDTO> listAvailableFerramentas() {
    log.debug("Listando ferramentas disponíveis");
    return agentOrchestratorService.listAvailableFerramentas();
  }
}
