package com.ia.core.llm.view.agent;

import com.ia.core.llm.service.model.agent.AgentConfirmationDTO;
import com.ia.core.llm.service.model.agent.AgentSessionRequestDTO;
import com.ia.core.llm.service.model.agent.AgentSessionResponseDTO;
import com.ia.core.llm.service.model.agent.AgentSessionUseCase;
import com.ia.core.llm.service.model.ferramenta.FerramentaMetadataDTO;
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
  public List<FerramentaMetadataDTO> listAvailableFerramentas() {
    return client.listAvailableFerramentas();
  }
}
