package com.ia.core.llm.model.template;

import java.util.ArrayList;
import java.util.List;

import com.ia.core.model.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Classe que representa um template para questionamento com o modelo de
 * linguagem
 *
 * @author Israel Araújo
 */
@Entity
@Table(name = Template.TABLE_NAME, schema = Template.SCHEMA_NAME)
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Template
  extends BaseEntity {
  /** Serial UID */
  private static final long serialVersionUID = 5644976387280082125L;
  /** NOME DA TABELA */
  public static final String TABLE_NAME = "LLM_TEMPLATE";
  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = "LARGE_LANGUAGE_MODEL";

  /**
   * Título do template
   */
  @Column(name = "titulo", unique = true, nullable = false)
  private String titulo;

  /**
   * Conteúdo do template
   */
  @Lob
  @Column(name = "conteudo")
  private String conteudo;

  /**
   * Flag indicativa de exigência de contexto
   */
  @Default
  @Column(name = "flg_exige_contexto", nullable = false, length = 1)
  private boolean exigeContexto = false;

  /**
   * Parâmetros do template
   */
  @Default
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
             mappedBy = "template", fetch = FetchType.LAZY)
  private List<TemplateParameter> parametros = new ArrayList<>();

}
