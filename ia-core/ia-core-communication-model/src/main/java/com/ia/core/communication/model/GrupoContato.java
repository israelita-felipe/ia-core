package com.ia.core.communication.model;

import com.ia.core.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
  public static final String TABLE_NAME = "biblia_GRUPO_CONTATO";
  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = "biblia";

  /** Nome do grupo */
  @Column(name = "nome", length = 100, nullable = false)
  private String nome;

  /** Descrição do grupo */
  @Column(name = "descricao", length = 500)
  private String descricao;

  /** Indicates if the group is active for messaging */
  @Column(name = "ativo", nullable = false)
  private Boolean ativo = true;
}