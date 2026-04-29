package com.ia.core.communication.model.mensagem;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representando o status de uma mensagem enviada.
 *
 * @author Israel Araújo
 */
/**
 * Enumeração que representa a entidade de domínio status mensagem.
 * <p>
 * Define os valores possíveis para StatusMensagem no sistema.
 *
 * @author IA
 * @since 1.0
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
