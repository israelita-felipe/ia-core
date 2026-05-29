package com.ia.core.communication.model.contato;

import com.ia.core.communication.model.CommunicationModel;
import com.ia.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um grupo de contatos para envio de mensagens em massa.
 * <p>
 * Esta entidade encapsula grupos de contatos que podem ser usados para enviar
 * mensagens a múltiplos destinatários simultaneamente. Cada grupo contém uma
 * lista de contatos com seus números de telefone e nomes opcionais.
 * <p>
 * Principais responsabilidades:
 * <ul>
 *   <li>Armazenar metadados do grupo (nome, descrição, status ativo)</li>
 *   <li>Manter relacionamento com entidades ContatoMensagem</li>
 *   <li>Suportar operações de envio em massa</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see ContatoMensagem
 */

@Entity
@Table(name = GrupoContato.TABLE_NAME, schema = GrupoContato.SCHEMA_NAME)
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class GrupoContato extends BaseEntity {
  /** UID de versão serial para serialização */
  private static final long serialVersionUID = 1L;
  /** Nome da tabela de banco de dados para esta entidade */
  public static final String TABLE_NAME = CommunicationModel.TABLE_PREFIX+"GRUPO_CONTATO";
  /** Nome do schema de banco de dados para esta entidade */
  public static final String SCHEMA_NAME = CommunicationModel.SCHEMA;

  /** Nome do grupo de contatos (obrigatório, máximo 100 caracteres) */
  @Column(name = "nome", length = 100, nullable = false)
  private String nome;

  /** Descrição do grupo de contatos (opcional, máximo 500 caracteres) */
  @Column(name = "descricao", length = 500)
  private String descricao;

  /** Indica se o grupo está ativo para envio de mensagens (padrão: true) */
  @Column(name = "ativo", nullable = false)
  private Boolean ativo = true;

  /** Lista de contatos pertencentes a este grupo */
  @OneToMany(mappedBy = "grupoContato", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ContatoMensagem> contatos = new ArrayList<>();
}
