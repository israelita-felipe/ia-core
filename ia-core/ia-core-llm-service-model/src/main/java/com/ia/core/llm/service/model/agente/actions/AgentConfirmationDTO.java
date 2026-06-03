package com.ia.core.llm.service.model.agente.actions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para confirmação de ação de agente.
 * <p>
 * Utilizado para confirmar ou cancelar ações que requerem aprovação do usuário.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentConfirmationDTO {

  private String sessionId;
  private boolean confirmed;
  private String userMessage;
}
