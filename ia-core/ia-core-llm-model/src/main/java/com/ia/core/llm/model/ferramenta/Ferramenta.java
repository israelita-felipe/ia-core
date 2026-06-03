package com.ia.core.llm.model.ferramenta;

import com.ia.core.llm.model.LLMModel;
import com.ia.core.llm.model.template.Template;
import com.ia.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Capacidade invocável por agentes de IA.
 * <p>
 * Representa qualquer capacidade que pode ser utilizada por agentes, incluindo
 * sub-agentes, {@code @Tool} Spring AI, recursos MCP e skills (capacidades
 * compostas com instruções e sub-ferramentas).
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Entity
@Table(name = Ferramenta.TABLE_NAME, schema = Ferramenta.SCHEMA_NAME)
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = false)
public class Ferramenta
  extends BaseEntity {

  private static final long serialVersionUID = 5644976387280082127L;

  public static final String TABLE_NAME = LLMModel.TABLE_PREFIX + "FERRAMENTA";
  public static final String SCHEMA_NAME = LLMModel.SCHEMA;
  public static final String SUB_FERRAMENTA_JOIN_TABLE = LLMModel.TABLE_PREFIX + "FERRAMENTA_SUB_FERRAMENTA";

  @Column(name = "titulo", unique = true, nullable = false)
  private String titulo;

  @Column(name = "descricao", length = 1000)
  private String descricao;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo", nullable = false)
  private TipoFerramentaEnum tipo;

  @Column(name = "identificador", unique = true, nullable = false)
  private String identificador;

  @Column(name = "modulo_origem")
  private String moduloOrigem;

  @Default
  @Column(name = "ativo", nullable = false, length = 1)
  private boolean ativo = true;

  @Default
  @Column(name = "descoberta_automatica", nullable = false, length = 1)
  private boolean descobertaAutomatica = false;

  @Lob
  @Column(name = "instrucoes")
  private String instrucoes;

  @ManyToOne(targetEntity = Template.class)
  @JoinColumn(name = "template_id", referencedColumnName = "id")
  private Template template;

  @Default
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = Ferramenta.SUB_FERRAMENTA_JOIN_TABLE,
      schema = SCHEMA_NAME,
      joinColumns = @JoinColumn(name = "ferramenta_id"),
      inverseJoinColumns = @JoinColumn(name = "sub_ferramenta_id"))
  private List<Ferramenta> subFerramentas = new ArrayList<>();
}
