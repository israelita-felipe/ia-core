package com.ia.core.communication.model.mensagem;

import com.ia.core.communication.model.CommunicationModel;
import com.ia.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Entidade que representa um modelo de mensagem reutilizável.
 *
 * @author Israel Araújo
 */

@Entity
@Table(name = ModeloMensagem.TABLE_NAME, schema = ModeloMensagem.SCHEMA_NAME)
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ModeloMensagem extends BaseEntity {
  /** Serial UID */
  private static final long serialVersionUID = 1L;
  /** NOME DA TABELA */
  public static final String TABLE_NAME = CommunicationModel.TABLE_PREFIX+"MODELO_MENSAGEM";
  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = CommunicationModel.SCHEMA;

  /** Nome do modelo */
  @Column(name = "nome", length = 100, nullable = false)
  private String nome;

  /** Descrição do modelo */
  @Column(name = "descricao", length = 500)
  private String descricao;

  /** Corpo do modelo com placeholders */
  @Lob
  @Column(name = "corpo_modelo", nullable = false)
  private String corpoModelo;

  /** Tipo do canal */
  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_canal", nullable = false)
  private TipoCanal tipoCanal;

  /** Indica se o modelo está ativo */
  @Column(name = "ativo", nullable = false)
  private Boolean ativo = true;
}
