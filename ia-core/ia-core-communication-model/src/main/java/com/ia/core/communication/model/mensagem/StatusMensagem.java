package com.ia.core.communication.model.mensagem;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Representa o status de uma mensagem enviada.
 * <p>
 * Define os possíveis estados no ciclo de vida de uma mensagem de comunicação,
 * desde a criação inicial através da entrega, leitura ou falha. Cada status
 * representa um estágio específico no processo de entrega de mensagens.
 * <p>
 * Ciclo de vida do status:
 * <ul>
 *   <li>PENDENTE: Mensagem criada mas ainda não enviada</li>
 *   <li>ENVIADA: Mensagem enviada para o serviço de mensagens</li>
 *   <li>ENTREGUE: Mensagem entregue ao dispositivo do destinatário</li>
 *   <li>LIDA: Mensagem lida pelo destinatário</li>
 *   <li>FALHA: Falha na entrega da mensagem</li>
 *   <li>CANCELADA: Mensagem cancelada antes da entrega</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum StatusMensagem {
  /** Mensagem criada mas ainda não enviada */
  PENDENTE("Pendente"),
  /** Mensagem enviada para o serviço de mensagens */
  ENVIADA("Enviada"),
  /** Mensagem entregue ao dispositivo do destinatário */
  ENTREGUE("Entregue"),
  /** Mensagem lida pelo destinatário */
  LIDA("Lida"),
  /** Falha na entrega da mensagem */
  FALHA("Falha"),
  /** Mensagem cancelada antes da entrega */
  CANCELADA("Cancelada");

  /** Descrição legível por humanos do status */
  private final String descricao;
}
