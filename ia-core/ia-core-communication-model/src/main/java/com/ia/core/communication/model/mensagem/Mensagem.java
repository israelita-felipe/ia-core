package com.ia.core.communication.model.mensagem;
import com.ia.core.communication.model.CommunicationModel;
import com.ia.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.ToString;

import java.time.LocalDateTime;


/**
 * Representa uma mensagem de comunicação.
 * <p>
 * Esta entidade encapsula todas as informações relacionadas a uma mensagem de
 * comunicação, incluindo detalhes do destinatário, conteúdo da mensagem, tipo de
 * canal e status de entrega. Rastreia o ciclo de vida completo de uma mensagem
 * desde a criação até a entrega ou falha.
 * <p>
 * Principais responsabilidades:
 * <ul>
 *   <li>Armazenar conteúdo da mensagem e informações do destinatário</li>
 *   <li>Rastrear status de entrega e timestamps</li>
 *   <li>Manter identificadores externos de API para rastreamento</li>
 *   <li>Registrar motivos de falha quando aplicável</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see TipoCanal
 * @see StatusMensagem
 */

@Entity
@Table(name = Mensagem.TABLE_NAME, schema = Mensagem.SCHEMA_NAME)
@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Mensagem extends BaseEntity {
  /** UID de versão serial para serialização */
  private static final long serialVersionUID = 1L;
  /** Nome da tabela de banco de dados para esta entidade */
  public static final String TABLE_NAME = CommunicationModel.TABLE_PREFIX+"MENSAGEM";
  /** Nome do schema de banco de dados para esta entidade */
  public static final String SCHEMA_NAME = CommunicationModel.SCHEMA;

  /** Número de telefone do destinatário da mensagem (obrigatório, máximo 20 caracteres) */
  @Column(name = "telefone_destinatario", length = 20, nullable = false)
  private String telefoneDestinatario;

  /** Nome do destinatário da mensagem (opcional, máximo 100 caracteres) */
  @Column(name = "nome_destinatario", length = 100)
  private String nomeDestinatario;

  /** Conteúdo do corpo da mensagem (obrigatório) */
  @Lob
  @Column(name = "corpo_mensagem", nullable = false)
  private String corpoMensagem;

  /** Tipo de canal de comunicação (obrigatório) */
  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_canal", nullable = false)
  private TipoCanal tipoCanal;

  /** Status atual da mensagem (obrigatório) */
  @Enumerated(EnumType.STRING)
  @Column(name = "status_mensagem", nullable = false)
  private StatusMensagem statusMensagem;

  /** Identificador externo da API de mensagens (ex: ID da API do WhatsApp, opcional) */
  @Column(name = "id_externo", length = 100)
  private String idExterno;

  /** Timestamp quando a mensagem foi enviada */
  @Column(name = "data_envio")
  private LocalDateTime dataEnvio;

  /** Timestamp quando a mensagem foi entregue ao destinatário */
  @Column(name = "data_entrega")
  private LocalDateTime dataEntrega;

  /** Timestamp quando a mensagem foi lida pelo destinatário */
  @Column(name = "data_leitura")
  private LocalDateTime dataLeitura;

  /** Motivo da falha da mensagem se aplicável (opcional, máximo 500 caracteres) */
  @Column(name = "motivo_falha", length = 500)
  private String motivoFalha;
}
