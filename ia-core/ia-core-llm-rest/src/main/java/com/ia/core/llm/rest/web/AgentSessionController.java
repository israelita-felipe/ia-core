package com.ia.core.llm.rest.web;

import com.ia.core.llm.service.agente.AgentSessionService;
import com.ia.core.llm.service.model.agente.actions.AgentConfirmationDTO;
import com.ia.core.llm.service.model.agente.session.AgentSessionRequestDTO;
import com.ia.core.llm.service.model.agente.session.AgentSessionResponseDTO;
import com.ia.core.llm.service.model.skill.SkillMetadataDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciamento de sessões de agente.
 * <p>
 * Expõe endpoints para execução e confirmação de sessões de orquestração multi-agente.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/llm/agente/sessao")
@Tag(name = "AgentSession", description = "Gerenciamento de sessões de orquestração multi-agente")
public class AgentSessionController {

  private final AgentSessionService agentSessionService;

  public AgentSessionController(AgentSessionService agentSessionService) {
    this.agentSessionService = agentSessionService;
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
    return agentSessionService.run(request);
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
    return agentSessionService.confirm(confirmation);
  }

  /**
   * Lista todas as skills disponíveis para ativação.
   *
   * @return lista de metadados de skills
   */
  @GetMapping("/skills")
  public List<SkillMetadataDTO> listAvailableSkills() {
    log.debug("Listando skills disponíveis para ativação");
    return agentSessionService.listAvailableSkills();
  }
}
