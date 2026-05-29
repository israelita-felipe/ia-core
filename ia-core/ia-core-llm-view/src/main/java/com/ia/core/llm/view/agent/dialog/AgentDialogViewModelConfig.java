package com.ia.core.llm.view.agent.dialog;

import com.ia.core.llm.service.model.agent.AgentSessionRequestDTO;
import com.ia.core.llm.view.agent.AgentSessionManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import lombok.Getter;

public class AgentDialogViewModelConfig
  extends FormViewModelConfig<AgentSessionRequestDTO> {

  @Getter
  private final AgentSessionManager agentSessionService;

  public AgentDialogViewModelConfig(boolean readOnly, AgentSessionManager agentSessionService) {
    super(readOnly);
    this.agentSessionService = agentSessionService;
  }
}
