package com.ia.core.owl.model.axiom;

import com.ia.core.llm.model.LLMModel;
import com.ia.core.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Classe que representa a entidade de domínio axioma.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a Axioma
 * dentro do sistema.
 *
 * @author Israel Araújo
 * @since 1.0
 */
@Entity
@Table(name = Axioma.TABLE_NAME, schema = Axioma.SCHEMA_NAME)
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = false)
public class Axioma
  extends BaseEntity {
  /** Serial UID */
  private static final long serialVersionUID = 5644976387280082125L;

  /** NOME DA TABELA */
  public static final String TABLE_NAME = LLMModel.TABLE_PREFIX + "AXIOMA";

  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = LLMModel.SCHEMA;

  /** URI do axioma */
  @Column(name = "uri", length = 500)
  private String uri;

  /** URI com versão do axioma */
  @Column(name = "uri_version", length = 500)
  private String uri_version;

  /** Prefixo do axioma */
  @Column(name = "prefix", length = 500)
  private String prefix;

  /** Expressão do axioma */
  @Lob
  @Column(name = "expressao", nullable = false)
  private String expressao;

  /** Flag indicativa de consistência */
  @Column(name = "consistente", nullable = false, length = 1)
  private Boolean consistente = true;

  /** Flag indicativa de inferência */
  @Column(name = "inferido", nullable = false, length = 1)
  private Boolean inferido = false;

  /** Flag indicativa de atividade */
  @Column(name = "ativo", nullable = false, length = 1)
  private Boolean ativo = true;

}
