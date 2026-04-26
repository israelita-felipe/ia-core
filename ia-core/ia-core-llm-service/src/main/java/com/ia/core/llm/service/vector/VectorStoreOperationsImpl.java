package com.ia.core.llm.service.vector;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;

/**
 * Implementação de VectorStoreOperations.
 * Wraps {@link VectorStoreService} para abstração de acesso ao vector store.
 *
 * @author Israel Araújo
 */
@RequiredArgsConstructor
public class VectorStoreOperationsImpl implements VectorStoreOperations {

  private final VectorStoreService vectorStoreService;

  @Override
  public QuestionAnswerAdvisor getQuestionAnswerAdvisor() {
    return vectorStoreService.getQuestionAnswerAdvisor();
  }
}
