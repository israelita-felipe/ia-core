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
 * Entidade que representa a associação entre um grupo de contatos e um número de telefone.
 *
 * @author Israel Araújo
 */

@Entity
@Table(name = ContatoMensagem.TABLE_NAME, schema = ContatoMensagem.SCHEMA_NAME)
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ContatoMensagem extends BaseEntity {
  /** Serial UID */
  private static final long serialVersionUID = 1L;
  /** NOME DA TABELA */
  public static final String TABLE_NAME = CommunicationModel.TABLE_PREFIX+"CONTATO_MENSAGEM";
  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = CommunicationModel.SCHEMA;

  /** Grupo de contato */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "grupo_contato_id", nullable = false)
  private GrupoContato grupoContato;

  /** Número de telefone */
  @Column(name = "telefone", length = 20, nullable = false)
  private String telefone;

  /** Nome do contato */
  @Column(name = "nome", length = 100)
  private String nome;
}
