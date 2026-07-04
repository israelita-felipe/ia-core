package com.ia.core.llm.view.agente.dialog;

import com.ia.core.llm.service.model.session.AgentSessionRequestDTO;
import com.ia.core.llm.view.agente.session.AgentSessionManager;
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
