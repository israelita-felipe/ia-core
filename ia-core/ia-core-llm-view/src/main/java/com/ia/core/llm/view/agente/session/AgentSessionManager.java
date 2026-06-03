package com.ia.core.llm.view.agente.session;

import com.ia.core.llm.service.model.agente.actions.AgentConfirmationDTO;
import com.ia.core.llm.service.model.agente.session.AgentSessionRequestDTO;
import com.ia.core.llm.service.model.agente.session.AgentSessionResponseDTO;
import com.ia.core.llm.service.model.agente.session.AgentSessionUseCase;
import com.ia.core.llm.service.model.skill.SkillMetadataDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Implementação remota de {@link AgentSessionUseCase} via Feign.
 */
@RequiredArgsConstructor
public class AgentSessionManager
  implements AgentSessionUseCase {

  private final AgentSessionClient client;

  @Override
  public AgentSessionResponseDTO run(AgentSessionRequestDTO request) {
    return client.run(request);
  }

  @Override
  public AgentSessionResponseDTO confirm(AgentConfirmationDTO confirmation) {
    return client.confirm(confirmation);
  }

  @Override
  public List<SkillMetadataDTO> listAvailableSkills() {
    return client.listAvailableSkills();
  }
}
