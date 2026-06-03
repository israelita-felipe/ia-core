package com.ia.core.llm.service.model.agent;

import com.ia.core.llm.service.model.ferramenta.FerramentaMetadataDTO;

import java.util.List;

/**
 * Interface de Use Case para sessões de agente.
 * <p>
 * Define operações para execução, confirmação e listagem de ferramentas disponíveis.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface AgentSessionUseCase {

  AgentSessionResponseDTO run(AgentSessionRequestDTO request);

  AgentSessionResponseDTO confirm(AgentConfirmationDTO confirmation);

  List<FerramentaMetadataDTO> listAvailableFerramentas();
}
