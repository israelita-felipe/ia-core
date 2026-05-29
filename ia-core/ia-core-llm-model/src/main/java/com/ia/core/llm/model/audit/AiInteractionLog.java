package com.ia.core.llm.model.audit;

import com.ia.core.llm.model.LLMModel;
import com.ia.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Registro de auditoria de interações com IA (ADR-048).
 * <p>
 * Responsável por registrar todas as interações com modelos de linguagem,
 * incluindo prompts, chamadas de ferramentas, raciocínio do LLM e respostas finais.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Entity
@Table(name = AiInteractionLog.TABLE_NAME, schema = AiInteractionLog.SCHEMA_NAME)
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = false)
public class AiInteractionLog
  extends BaseEntity {

  private static final long serialVersionUID = 5644976387280082129L;

  public static final String TABLE_NAME = LLMModel.TABLE_PREFIX + "AI_INTERACTION_LOG";
  public static final String SCHEMA_NAME = LLMModel.SCHEMA;

  @Column(name = "correlation_id")
  private String correlationId;

  @Column(name = "usuario_id")
  private String usuarioId;

  @Lob
  @Column(name = "user_prompt")
  private String userPrompt;

  @Lob
  @Column(name = "tool_calls")
  private String toolCalls;

  @Lob
  @Column(name = "llm_reasoning")
  private String llmReasoning;

  @Lob
  @Column(name = "resposta_final")
  private String respostaFinal;

  @Column(name = "skill_id")
  private Long skillId;
}
