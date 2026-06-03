package com.ia.core.llm.model.agente.session;

import com.ia.core.llm.model.LLMModel;
import com.ia.core.llm.model.agente.Agente;
import com.ia.core.llm.model.skill.Skill;
import com.ia.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Sessão de orquestração multi-agente.
 * <p>
 * Representa uma interação complexa onde um agente principal pode orquestrar
 * sub-agentes especializados para executar tarefas específicas.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Entity
@Table(name = AgentSession.TABLE_NAME, schema = AgentSession.SCHEMA_NAME)
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = false)
public class AgentSession
  extends BaseEntity {

  private static final long serialVersionUID = 5644976387280082129L;

  public static final String TABLE_NAME = LLMModel.TABLE_PREFIX + "AGENT_SESSION";
  public static final String SCHEMA_NAME = LLMModel.SCHEMA;

  /**
   * Identificador único da sessão (UUID).
   */
  @Column(name = "session_id", unique = true, nullable = false, length = 100)
  private String sessionId;

  /**
   * Título da sessão.
   */
  @Column(name = "titulo", length = 200)
  private String titulo;

  /**
   * Contexto inicial da sessão.
   */
  @Lob
  @Column(name = "contexto_inicial")
  private String contextoInicial;

  /**
   * Data e hora de início da sessão.
   */
  @Column(name = "data_inicio", nullable = false)
  private LocalDateTime dataInicio;

  /**
   * Data e hora de fim da sessão.
   */
  @Column(name = "data_fim")
  private LocalDateTime dataFim;

  /**
   * Status da sessão.
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 20)
  @Default
  private AgentSessionStatus status = AgentSessionStatus.ATIVA;

  /**
   * Agente principal da sessão.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "agente_id", nullable = false)
  private Agente agente;

  /**
   * Skills ativadas durante a sessão.
   */
  @Default
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = AgentSession.SKILL_ACTIVATION_TABLE,
      schema = SCHEMA_NAME,
      joinColumns = @JoinColumn(name = "agent_session_id"),
      inverseJoinColumns = @JoinColumn(name = "skill_id"))
  private List<Skill> skillsAtivadas = new ArrayList<>();

  public static final String SKILL_ACTIVATION_TABLE = LLMModel.TABLE_PREFIX + "SKILL_ACTIVATION";

  /**
   * Status da sessão de agente.
   */
  public enum AgentSessionStatus {
    ATIVA,
    ENCERRADA,
    PAUSADA,
    PENDENTE
  }
}
