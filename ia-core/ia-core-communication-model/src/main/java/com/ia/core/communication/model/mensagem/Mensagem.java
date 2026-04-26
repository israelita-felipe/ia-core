package com.ia.core.communication.model.mensagem;

import com.ia.core.communication.model.CommunicationModel;
import com.ia.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Entidade que representa uma mensagem de comunicação.
 *
 * @author Israel Araújo
 */
@Entity
@Table(name = Mensagem.TABLE_NAME, schema = Mensagem.SCHEMA_NAME)
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Mensagem extends BaseEntity {
  /** Serial UID */
  private static final long serialVersionUID = 1L;
  /** NOME DA TABELA */
  public static final String TABLE_NAME = CommunicationModel.TABLE_PREFIX+"MENSAGEM";
  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = CommunicationModel.SCHEMA;

  /** Telefone do destinatário */
  @Column(name = "telefone_destinatario", length = 20, nullable = false)
  private String telefoneDestinatario;

  /** Nome do destinatário */
  @Column(name = "nome_destinatario", length = 100)
  private String nomeDestinatario;

  /** Corpo da mensagem */
  @Lob
  @Column(name = "corpo_mensagem", nullable = false)
  private String corpoMensagem;

  /** Tipo do canal de comunicação */
  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_canal", nullable = false)
  private TipoCanal tipoCanal;

  /** Status da mensagem */
  @Enumerated(EnumType.STRING)
  @Column(name = "status_mensagem", nullable = false)
  private StatusMensagem statusMensagem;

  /** Identificador externo (ID da API do WhatsApp) */
  @Column(name = "id_externo", length = 100)
  private String idExterno;

  /** Data de envio */
  @Column(name = "data_envio")
  private LocalDateTime dataEnvio;

  /** Data de entrega */
  @Column(name = "data_entrega")
  private LocalDateTime dataEntrega;

  /** Data de leitura */
  @Column(name = "data_leitura")
  private LocalDateTime dataLeitura;

  /** Motivo da falha */
  @Column(name = "motivo_falha", length = 500)
  private String motivoFalha;
}
