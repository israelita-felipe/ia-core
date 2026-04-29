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
 * Entidade que representa um grupo de contatos para envio de mensagens em massa.
 *
 * @author Israel Araújo
 */

@Entity
@Table(name = GrupoContato.TABLE_NAME, schema = GrupoContato.SCHEMA_NAME)
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class GrupoContato extends BaseEntity {
  /** Serial UID */
  private static final long serialVersionUID = 1L;
  /** NOME DA TABELA */
  public static final String TABLE_NAME = CommunicationModel.TABLE_PREFIX+"GRUPO_CONTATO";
  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = CommunicationModel.SCHEMA;

  /** Nome do grupo */
  @Column(name = "nome", length = 100, nullable = false)
  private String nome;

  /** Descrição do grupo */
  @Column(name = "descricao", length = 500)
  private String descricao;

  /** Indicates if the group is active for messaging */
  @Column(name = "ativo", nullable = false)
  private Boolean ativo = true;

  /** Lista de contatos do grupo */
  @OneToMany(mappedBy = "grupoContato", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ContatoMensagem> contatos = new ArrayList<>();
}
