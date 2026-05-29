package com.ia.core.llm.model.skill;

import com.ia.core.llm.model.LLMModel;
import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.llm.model.template.Template;
import com.ia.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Capacidade agentic com instruções e ferramentas autorizadas.
 * <p>
 * Representa uma habilidade ou competência que um agente de IA pode executar,
 * incluindo instruções específicas e lista de ferramentas autorizadas.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Entity
@Table(name = Skill.TABLE_NAME, schema = Skill.SCHEMA_NAME)
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = false)
public class Skill
  extends BaseEntity {

  private static final long serialVersionUID = 5644976387280082128L;

  public static final String TABLE_NAME = LLMModel.TABLE_PREFIX + "SKILL";
  public static final String SCHEMA_NAME = LLMModel.SCHEMA;

  @Column(name = "titulo", unique = true, nullable = false)
  private String titulo;

  @Column(name = "descricao", length = 1000)
  private String descricao;

  @Lob
  @Column(name = "instrucoes")
  private String instrucoes;

  @Default
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = Skill.FERRAMENTA_JOIN_TABLE,
      schema = SCHEMA_NAME,
      joinColumns = @JoinColumn(name = "skill_id"),
      inverseJoinColumns = @JoinColumn(name = "ferramenta_id"))
  private List<Ferramenta> ferramentas = new ArrayList<>();

  @ManyToOne(targetEntity = Template.class)
  @JoinColumn(name = "template_id", referencedColumnName = "id")
  private Template template;

  @Default
  @Column(name = "ativo", nullable = false, length = 1)
  private boolean ativo = true;

  public static final String FERRAMENTA_JOIN_TABLE = LLMModel.TABLE_PREFIX + "SKILL_FERRAMENTA";
}
