package com.ia.core.llm.model.template;

import com.ia.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Classe que representa a entidade de domínio template parameter.
 * <p>
 * Parâmetros nominais do template para associação com legislação.
 * Responsável por gerenciar as funcionalidades relacionadas a TemplateParameter
 * dentro do sistema.
 *
 * @author Israel Araújo
 * @since 1.0
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
@ToString(callSuper = false)
public class TemplateParameter
  extends BaseEntity {
  /** Serial UID */
  private static final long serialVersionUID = 6823579073228741182L;

  /** Nome da tabela onde esta entidade será persistida. */
  public static final String TABLE_NAME = "LLM_TEMPLATE_PARAMETER";

  /** Nome do schema */
  public static final String SCHEMA_NAME = "LARGE_LANGUAGE_MODEL";

  /**
   * Nome do parâmetro. Deve corresponder a um parâmetro textual
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

}
