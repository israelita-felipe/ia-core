package com.ia.core.llm.model.agente;

import com.ia.core.llm.model.LLMModel;
import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.llm.model.skill.Skill;
import com.ia.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * Agente para orquestração de ferramentas e skills.
 * <p>
 * Representa um agente que encapsula capacidades de processamento de linguagem natural
 * e orquestração de ferramentas. É a unidade fundamental de execução no sistema LLM.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Entity
@Table(name = Agente.TABLE_NAME, schema = Agente.SCHEMA_NAME)
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = false)
public class Agente
  extends BaseEntity {

  private static final long serialVersionUID = 1234567890123456789L;

  public static final String TABLE_NAME = LLMModel.TABLE_PREFIX + "AGENTE";
  public static final String SCHEMA_NAME = LLMModel.SCHEMA;

  /**
   * Identificador único do agente (ex: llm.core).
   */
  @Column(name = "identificador", unique = true, nullable = false, length = 100)
  private String identificador;

  /**
   * Nome apresentável do agente na UI.
   */
  @Column(name = "titulo", nullable = false, length = 200)
  private String titulo;

  /**
   * Descrição do propósito do agente.
   */
  @Column(name = "descricao", length = 1000)
  private String descricao;

  /**
   * Instruções do sistema (prompt system).
   * Contém as instruções que o agente deve seguir ao processar solicitações.
   */
  @Lob
  @Column(name = "instrucoes", columnDefinition = "CLOB")
  private String instrucoes;

  /**
   * Modelo LLM preferido para este agente.
   */
  @Column(name = "modelo", length = 100)
  private String modelo;

  /**
   * Indica se o agente está disponível para uso.
   */
  @Default
  @Column(name = "ativo")
  private Boolean ativo = true;

  /**
   * Módulo ou pacote fonte.
   * Usado para identificar a origem do agente.
   */
  @Column(name = "modulo_origem", length = 200)
  private String moduloOrigem;

  /**
   * Temperatura para geração de texto.
   */
  @Default
  @Column(name = "temperature")
  private Double temperature = 0.7;

  /**
   * Número máximo de tokens para geração.
   */
  @Default
  @Column(name = "max_tokens")
  private Integer maxTokens = 2048;

  /**
   * Conjunto de ferramentas que o agente pode usar.
   */
  @Default
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = Agente.FERRAMENTA_JOIN_TABLE,
      schema = SCHEMA_NAME,
      joinColumns = @JoinColumn(name = "agente_id"),
      inverseJoinColumns = @JoinColumn(name = "ferramenta_id"))
  private Set<Ferramenta> ferramentas = new HashSet<>();

  /**
   * Conjunto de habilidades especializadas que o agente pode ter.
   */
  @Default
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = Agente.SKILL_JOIN_TABLE,
      schema = SCHEMA_NAME,
      joinColumns = @JoinColumn(name = "agente_id"),
      inverseJoinColumns = @JoinColumn(name = "skill_id"))
  private Set<Skill> skills = new HashSet<>();

  public static final String FERRAMENTA_JOIN_TABLE = LLMModel.TABLE_PREFIX + "AGENTE_FERRAMENTA";
  public static final String SKILL_JOIN_TABLE = LLMModel.TABLE_PREFIX + "AGENTE_SKILL";

  @PrePersist
  protected void onCreate() {
    super.generateIdIfAbsent();
  }

  /**
   * Adiciona uma ferramenta ao conjunto de ferramentas autorizadas.
   *
   * @param ferramenta ferramenta a ser adicionada
   */
  public void adicionarFerramenta(Ferramenta ferramenta) {
    log.debug("Adicionando ferramenta {} ao agente {}", ferramenta.getIdentificador(), this.identificador);
    this.ferramentas.add(ferramenta);
  }

  /**
   * Remove uma ferramenta do conjunto de ferramentas autorizadas.
   *
   * @param ferramenta ferramenta a ser removida
   */
  public void removerFerramenta(Ferramenta ferramenta) {
    log.debug("Removendo ferramenta {} do agente {}", ferramenta.getIdentificador(), this.identificador);
    this.ferramentas.remove(ferramenta);
  }

  /**
   * Adiciona uma skill ao conjunto de habilidades.
   *
   * @param skill skill a ser adicionada
   */
  public void adicionarSkill(Skill skill) {
    log.debug("Adicionando skill {} ao agente {}", skill.getIdentificador(), this.identificador);
    this.skills.add(skill);
  }

  /**
   * Remove uma skill do conjunto de habilidades.
   *
   * @param skill skill a ser removida
   */
  public void removerSkill(Skill skill) {
    log.debug("Removendo skill {} do agente {}", skill.getIdentificador(), this.identificador);
    this.skills.remove(skill);
  }
}
