package com.ia.core.llm.model.template;

import com.ia.core.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Parâmetros nominais do template para associação com legislação
 *
 * @author Israel Araújo
 */
@Entity
@Table(name = TemplateParameter.TABLE_NAME,
       schema = TemplateParameter.SCHEMA_NAME)
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TemplateParameter
  extends BaseEntity {
  /** Serial UID */
  private static final long serialVersionUID = 6823579073228741182L;
  /**
   * Nome da tabela onde esta entidade será persistida.
   */

  /** NOME DA TABELA */
  public static final String TABLE_NAME = "LLM_TEMPLATE_PARAMETER";
  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = "LARGE_LANGUAGE_MODEL";
  /**
   * /** Nome do parâmetro. Deve corresponder a um parâmetro textual
   * parametrizado no template. <br/>
   * Ex.: {documento} corresponde ao parâmetro documento.<br/>
   * O parâmetro não precisa apresentar chaves, basta informar o nome.
   */
  @Column(name = "nome", nullable = false)
  private String nome;

  /**
   * Template proprietário desta relação
   */
  @ManyToOne(optional = false, targetEntity = Template.class)
  @JoinColumn(name = "template", referencedColumnName = "id")
  private Template template;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("TemplateParameter [");
    if (nome != null) {
      builder.append("nome=");
      builder.append(nome);
    }
    builder.append("]");
    return builder.toString();
  }

}
