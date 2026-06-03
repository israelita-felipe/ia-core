package com.ia.core.llm.service.agente;

import com.ia.core.llm.service.model.agente.actions.AgentConfirmationDTO;
import com.ia.core.llm.service.model.agente.session.AgentSessionRequestDTO;
import com.ia.core.llm.service.model.agente.session.AgentSessionResponseDTO;
import com.ia.core.llm.service.model.agente.session.AgentSessionUseCase;
import com.ia.core.llm.service.model.skill.SkillMetadataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço para gerenciamento de sessões de agentes.
 * <p>
 * Implementa o caso de uso para execução de sessões de agentes, incluindo
 * confirmações e listagem de skills disponíveis.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class AgentSessionService
  implements AgentSessionUseCase {

  private final AgentOrchestratorService agentOrchestratorService;

  @Override
  public AgentSessionResponseDTO run(AgentSessionRequestDTO request) {
    return agentOrchestratorService.run(request);
  }

  @Override
  public AgentSessionResponseDTO confirm(AgentConfirmationDTO confirmation) {
    return agentOrchestratorService.confirm(confirmation);
  }

  @Override
  public List<SkillMetadataDTO> listAvailableSkills() {
    return agentOrchestratorService.listAvailableSkills();
  }
}
