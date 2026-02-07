package com.ia.core.llm.service.chat;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.CallResponseSpec;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.ParameterizedTypeReference;

import com.google.gson.Gson;
import com.ia.core.llm.model.comando.FinalidadeComandoEnum;
import com.ia.core.llm.service.template.PromptTemplateService;
import com.ia.core.llm.service.template.PromptTemplateServiceImpl;
import com.ia.core.llm.service.vector.VectorStoreOperations;
import com.ia.core.llm.service.vector.VectorStoreOperationsImpl;
import com.ia.core.llm.service.vector.VectorStoreService;

import lombok.RequiredArgsConstructor;

/**
 * Serviço principal de Chat que orquestra as operações de chat.
 * Responsável pela coordenação entre os serviços especializados.
 *
 * @author Israel Araújo
 */
@RequiredArgsConstructor
public class ChatService {

  /** Modelo de chat a ser utilizado */
  private final ChatModel chatModel;

  /** Operações de Vector Store */
  private final VectorStoreOperations vectorStoreOperations;

  /** Serviço de Templates de Prompt */
  private final PromptTemplateService promptTemplateService;

  /** Serviço de Sessão de Chat */
  private final ChatSessionService chatSessionService;

  /**
   * Construtor para subclasses que passam VectorStoreService diretamente.
   * Mantém compatibilidade retroativa.
   *
   * @param chatModel modelo de chat
   * @param vectorStoreService serviço de vector store
   */
  protected ChatService(ChatModel chatModel, VectorStoreService vectorStoreService) {
    this(chatModel,
        new VectorStoreOperationsImpl(vectorStoreService),
        new PromptTemplateServiceImpl(),
        new ChatSessionServiceImpl());
  }

  /**
   * Realiza uma pergunta simples ao modelo.
   *
   * @param document o documento base
   * @param text a pergunta/texto
   * @return resposta do modelo
   */
  protected String ask(String document, String text) {
    Prompt prompt = promptTemplateService.createSimplePrompt(document, text);
    CallResponseSpec response = call(prompt);
    return response.content();
  }

  /**
   * Realiza uma pergunta ao modelo com template de sistema.
   *
   * @param document o documento base
   * @param text a pergunta/texto
   * @param systemTemplate o template do sistema
   * @param finalidade o tipo de retorno esperado
   * @param exigeContexto se deve usar contexto do vector store
   * @param params parâmetros adicionais
   * @return resposta do modelo
   */
  protected String ask(String document, String text, String systemTemplate,
      FinalidadeComandoEnum finalidade, boolean exigeContexto, Map<String, Object> params) {
    Prompt prompt = promptTemplateService.createSystemPrompt(
        document, text, systemTemplate, finalidade, params);
    CallResponseSpec response = call(prompt);
    ParameterizedTypeReference<?> returnType = finalidade.getReturnType();
    if (returnType == null) {
      return response.content();
    }
    Object entity = response.entity(returnType);
    return new Gson().toJson(entity);
  }

  /**
   * Executa uma chamada ao modelo com advisor de vector store.
   *
   * @param chatClient o cliente de chat
   * @param prompt o prompt a ser enviado
   * @return resposta da chamada
   */
  protected CallResponseSpec call(ChatClient chatClient, Prompt prompt) {
    return chatClient.prompt(prompt)
        .advisors(vectorStoreOperations.getQuestionAnswerAdvisor())
        .call();
  }

  /**
   * Executa uma chamada ao modelo com advisor de vector store.
   *
   * @param prompt o prompt a ser enviado
   * @return resposta da chamada
   */
  protected CallResponseSpec call(Prompt prompt) {
    return createClient().prompt(prompt)
        .advisors(vectorStoreOperations.getQuestionAnswerAdvisor())
        .call();
  }

  /**
   * Cria um novo cliente de chat.
   *
   * @return cliente de chat configurado
   */
  protected ChatClient createClient() {
    return ChatClient.builder(chatModel).build();
  }
}
