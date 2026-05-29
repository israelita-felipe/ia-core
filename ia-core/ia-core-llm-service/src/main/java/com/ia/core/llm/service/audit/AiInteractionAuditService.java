package com.ia.core.llm.service.audit;

import com.ia.core.llm.model.audit.AiInteractionLog;
import com.ia.core.llm.service.config.LlmModuleProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço de auditoria para interações com IA (ADR-048).
 * <p>
 * Responsável por registrar todas as interações com modelos de linguagem,
 * incluindo prompts, chamadas de ferramentas, raciocínio do LLM e respostas finais.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AiInteractionAuditService {

  private final AiInteractionLogRepository repository;
  private final LlmModuleProperties properties;

  @Transactional
  public void record(String userPrompt, String toolCalls, String reasoning, String response, Long skillId) {
    if (!properties.getAudit().isEnabled()) {
      return;
    }
    AiInteractionLog entry = AiInteractionLog.builder()
        .correlationId(MDC.get("correlationId"))
        .userPrompt(userPrompt)
        .toolCalls(toolCalls)
        .llmReasoning(reasoning)
        .respostaFinal(response)
        .skillId(skillId)
        .build();
    repository.save(entry);
    log.debug("Interação AI auditada skillId={}", skillId);
  }
}
