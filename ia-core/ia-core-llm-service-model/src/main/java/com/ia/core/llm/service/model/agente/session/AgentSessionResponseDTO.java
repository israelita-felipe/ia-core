package com.ia.core.llm.service.model.agente.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para resposta de sessão de agente.
 * <p>
 * Contém o identificador da sessão, mensagem e flag de confirmação pendente.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentSessionResponseDTO {

  private String sessionId;
  private String message;
  private boolean pendingConfirmation;
}
