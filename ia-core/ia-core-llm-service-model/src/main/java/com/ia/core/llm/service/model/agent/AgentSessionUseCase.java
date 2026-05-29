package com.ia.core.llm.service.model.agent;

import com.ia.core.llm.service.model.skill.SkillMetadataDTO;

import java.util.List;

/**
 * Interface de Use Case para sessões de agente.
 * <p>
 * Define operações para execução, confirmação e listagem de skills disponíveis.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface AgentSessionUseCase {

  AgentSessionResponseDTO run(AgentSessionRequestDTO request);

  AgentSessionResponseDTO confirm(AgentConfirmationDTO confirmation);

  List<SkillMetadataDTO> listAvailableSkills();
}
