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
 * Representa um modelo de mensagem reutilizável.
 * <p>
 * Esta entidade armazena modelos de mensagem que podem ser usados para gerar
 * comunicações padronizadas. Os modelos suportam placeholders para conteúdo
 * dinâmico e são associados a canais de comunicação específicos.
 * <p>
 * Principais responsabilidades:
 * <ul>
 *   <li>Armazenar metadados do modelo (nome, descrição, status ativo)</li>
 *   <li>Manter corpo do modelo com placeholders</li>
 *   <li>Associar a canais de comunicação específicos</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see TipoCanal
 */

@Entity
@Table(name = ModeloMensagem.TABLE_NAME, schema = ModeloMensagem.SCHEMA_NAME)
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ModeloMensagem extends BaseEntity {
  /** UID de versão serial para serialização */
  private static final long serialVersionUID = 1L;
  /** Nome da tabela de banco de dados para esta entidade */
  public static final String TABLE_NAME = CommunicationModel.TABLE_PREFIX+"MODELO_MENSAGEM";
  /** Nome do schema de banco de dados para esta entidade */
  public static final String SCHEMA_NAME = CommunicationModel.SCHEMA;

  /** Nome do modelo de mensagem (obrigatório, máximo 100 caracteres) */
  @Column(name = "nome", length = 100, nullable = false)
  private String nome;

  /** Descrição do modelo de mensagem (opcional, máximo 500 caracteres) */
  @Column(name = "descricao", length = 500)
  private String descricao;

  /** Corpo do modelo com placeholders para conteúdo dinâmico (obrigatório) */
  @Lob
  @Column(name = "corpo_modelo", nullable = false)
  private String corpoModelo;

  /** Tipo de canal de comunicação para este modelo (obrigatório) */
  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_canal", nullable = false)
  private TipoCanal tipoCanal;

  /** Indica se o modelo está ativo (padrão: true) */
  @Column(name = "ativo", nullable = false)
  private Boolean ativo = true;
}
