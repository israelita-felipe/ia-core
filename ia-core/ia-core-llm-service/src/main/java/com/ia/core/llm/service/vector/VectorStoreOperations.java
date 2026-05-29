package com.ia.core.llm.service.vector;

import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;

/**
 * Interface para operações de Vector Store.
 * <p>
 * Abstração para isolamento de acesso ao vector store.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface VectorStoreOperations {

  /**
   * Obtém o advisor para perguntas e respostas baseado no vector store.
   *
   * @return advisor configurado
   */
  QuestionAnswerAdvisor getQuestionAnswerAdvisor();
}
