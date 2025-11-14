package com.ia.core.owl.model.axiom;

import com.ia.core.llm.model.LLMModel;
import com.ia.core.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Axioma.TABLE_NAME, schema = Axioma.SCHEMA_NAME)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Axioma
  extends BaseEntity {
  /** Serial UID */
  private static final long serialVersionUID = 5644976387280082125L;
  /** NOME DA TABELA */
  public static final String TABLE_NAME = LLMModel.TABLE_PREFIX + "AXIOMA";
  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = LLMModel.SCHEMA;

  @Column(name = "uri", length = 500)
  private String uri;

  @Column(name = "uri_version", length = 500)
  private String uri_version;

  @Column(name = "prefix", length = 500)
  private String prefix;

  @Lob
  @Column(name = "expressao", nullable = false)
  private String expressao;

  @Column(name = "consistente", nullable = false, length = 1)
  private Boolean consistente = true;

  @Column(name = "inferido", nullable = false, length = 1)
  private Boolean inferido = false;

  @Column(name = "ativo", nullable = false, length = 1)
  private Boolean ativo = true;

}
