package com.ia.core.llm.model.prompt;

import com.ia.core.llm.model.LLMModel;
import com.ia.core.llm.model.template.Template;
import com.ia.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

/**
 * Entrada de catálogo para invocação de LLM.
 * <p>
 * Representa uma invocação de modelo de linguagem com {@link Template} e
 * finalidade definida. Define o tipo de resposta esperado (texto, objeto ou lista).
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Entity
@Table(name = Prompt.TABLE_NAME, schema = Prompt.SCHEMA_NAME)
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = false)
public class Prompt
  extends BaseEntity {

  private static final long serialVersionUID = 5644976387280082126L;

  public static final String TABLE_NAME = LLMModel.TABLE_PREFIX + "PROMPT";
  public static final String SCHEMA_NAME = LLMModel.SCHEMA;

  @Default
  @Column(name = "finalidade")
  @Enumerated(EnumType.STRING)
  private FinalidadePromptEnum finalidade = FinalidadePromptEnum.RESPOSTA_TEXTUAL;

  @Column(name = "titulo", unique = true, nullable = false)
  private String titulo;

  @Column(name = "entrada", length = 500)
  private String entrada;

  @ManyToOne(targetEntity = Template.class)
  @JoinColumn(name = "template_id", referencedColumnName = "id", nullable = false)
  private Template template;
}
