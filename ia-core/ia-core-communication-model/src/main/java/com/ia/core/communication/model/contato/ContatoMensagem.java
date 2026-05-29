package com.ia.core.communication.model.contato;

import com.ia.core.communication.model.CommunicationModel;
import com.ia.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Representa a associação entre um grupo de contatos e um número de telefone.
 * <p>
 * Esta entidade vincula contatos individuais a grupos de contatos, permitindo
 * envio de mensagens em massa para múltiplos destinatários. Cada contato possui
 * um número de telefone e um nome opcional para identificação.
 * <p>
 * Principais responsabilidades:
 * <ul>
 *   <li>Associar contatos a grupos de contatos</li>
 *   <li>Armazenar números de telefone e nomes de contatos</li>
 *   <li>Suportar operações em cascata com o grupo pai</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see GrupoContato
 */

@Entity
@Table(name = ContatoMensagem.TABLE_NAME, schema = ContatoMensagem.SCHEMA_NAME)
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ContatoMensagem extends BaseEntity {
  /** UID de versão serial para serialização */
  private static final long serialVersionUID = 1L;
  /** Nome da tabela de banco de dados para esta entidade */
  public static final String TABLE_NAME = CommunicationModel.TABLE_PREFIX+"CONTATO_MENSAGEM";
  /** Nome do schema de banco de dados para esta entidade */
  public static final String SCHEMA_NAME = CommunicationModel.SCHEMA;

  /** O grupo de contatos a que este contato pertence (obrigatório) */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "grupo_contato_id", nullable = false)
  private GrupoContato grupoContato;

  /** Número de telefone do contato (obrigatório, máximo 20 caracteres) */
  @Column(name = "telefone", length = 20, nullable = false)
  private String telefone;

  /** Nome do contato (opcional, máximo 100 caracteres) */
  @Column(name = "nome", length = 100)
  private String nome;
}
