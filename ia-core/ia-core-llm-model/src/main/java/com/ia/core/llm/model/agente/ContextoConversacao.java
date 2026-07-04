package com.ia.core.llm.model.agente;

import com.ia.core.llm.model.LLMModel;
import com.ia.core.llm.model.ontologia.Ontologia;
import com.ia.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Entidade para contexto de conversação com agente guiado por ontologia.
 * <p>
 * Mantém o estado da conversa, ontologia incremental e histórico de interações.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Entity
@Table(name = "CONTEXTO_CONVERSACAO", schema = LLMModel.SCHEMA)
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = false)
public class ContextoConversacao extends BaseEntity {

  private static final long serialVersionUID = 1L;

  /**
   * ID único da sessão de conversação.
   */
  @Column(name = "session_id", unique = true, nullable = false, length = 100)
  private String sessionId;

  /**
   * ID do usuário.
   */
  @Column(name = "user_id", length = 100)
  private String userId;

  /**
   * Domínio da conversação (ex: biologia, biblioteca, medicina).
   */
  @Column(name = "dominio", length = 200)
  private String dominio;

  /**
   * Ontologia construída incrementalmente durante a conversa.
   */
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "ontologia_id")
  private Ontologia ontologia;

  /**
   * Histórico de turnos da conversação (JSON).
   */
  @Lob
  @Column(name = "historico")
  private String historicoJson;

  /**
   * Data e hora de início da sessão.
   */
  @Column(name = "data_inicio", nullable = false)
  private LocalDateTime dataInicio;

  /**
   * Data e hora da última atividade.
   */
  @Column(name = "ultima_atividade")
  private LocalDateTime ultimaAtividade;

  /**
   * Número total de axiomas extraídos até o momento.
   */
  @Column(name = "total_axiomas_extraidos")
  @Default
  private Integer totalAxiomasExtraidos = 0;

  /**
   * Indica se a ontologia atual está consistente.
   */
  @Column(name = "ontologia_consistente")
  @Default
  private Boolean ontologiaConsistente = true;

  /**
   * Número de inconsistências corrigidas durante a conversa.
   */
  @Column(name = "inconsistencias_corrigidas")
  @Default
  private Integer inconsistenciasCorrigidas = 0;
}
