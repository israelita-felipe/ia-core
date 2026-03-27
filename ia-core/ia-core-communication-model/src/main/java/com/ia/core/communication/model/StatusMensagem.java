package com.ia.core.communication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representando o status de uma mensagem enviada.
 *
 * @author Israel Araújo
 */
@Getter
@AllArgsConstructor
public enum StatusMensagem {
  PENDENTE("Pendente"),
  ENVIADA("Enviada"),
  ENTREGUE("Entregue"),
  LIDA("Lida"),
  FALHA("Falha"),
  CANCELADA("Cancelada");

  private final String descricao;
}