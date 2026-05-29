package com.ia.core.llm.view.agent.dialog;

import com.ia.core.llm.service.model.agent.AgentConfirmationDTO;
import com.ia.core.llm.service.model.agent.AgentSessionResponseDTO;
import com.ia.core.llm.service.model.agent.AgentSessionRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModel;

public class AgentDialogViewModel
  extends FormViewModel<AgentSessionRequestDTO> {

  private String lastSessionId;
  private boolean pendingConfirmation;

  public AgentDialogViewModel(AgentDialogViewModelConfig config) {
    super(config);
  }

  public AgentSessionResponseDTO run() {
    AgentSessionResponseDTO response = getConfig().getAgentSessionService().run(getModel());
    lastSessionId = response.getSessionId();
    pendingConfirmation = response.isPendingConfirmation();
    return response;
  }

  public AgentSessionResponseDTO confirm(boolean confirmed, String message) {
    AgentSessionResponseDTO response = getConfig().getAgentSessionService().confirm(
        AgentConfirmationDTO.builder()
            .sessionId(lastSessionId)
            .confirmed(confirmed)
            .userMessage(message)
            .build());
    pendingConfirmation = response.isPendingConfirmation();
    return response;
  }

  public boolean isPendingConfirmation() {
    return pendingConfirmation;
  }

  @Override
  public AgentDialogViewModelConfig getConfig() {
    return (AgentDialogViewModelConfig) super.getConfig();
  }
}
