package com.ia.core.llm.service.chat;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.CallResponseSpec;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.core.ParameterizedTypeReference;

import com.google.gson.Gson;
import com.ia.core.llm.model.comando.FinalidadeComandoEnum;
import com.ia.core.llm.model.template.TemplateParameterEnum;
import com.ia.core.llm.service.vector.VectorStoreService;

import lombok.RequiredArgsConstructor;

/**
 * @author Israel Araújo
 */
@RequiredArgsConstructor
public class ChatService {

  /** Modelo a ser utilizado */
  private final ChatModel chatModel;

  /** Vector Store */
  private final VectorStoreService vectorStoreService;

  protected String ask(String document, String text) {
    PromptTemplate pt = new PromptTemplate("{document}\n\n{text}");
    pt.add("document", document);
    pt.add("text", text);
    Prompt prompt = new Prompt(pt.createMessage());
    CallResponseSpec response = call(prompt);
    return response.content();
  }

  protected String ask(String document, String text, String systemTemplate,
                       FinalidadeComandoEnum finalidade,
                       boolean exigeContexto, Map<String, Object> params) {
    SystemPromptTemplate promptTemplate = new SystemPromptTemplate(systemTemplate);

    promptTemplate
        .add(TemplateParameterEnum.TEMPLATE_PARAM_DOCUMENTO.getNome(),
             document);

    Message message = promptTemplate.createMessage(params);

    PromptTemplate pt = new PromptTemplate("{document}\n\n{text}");
    pt.add("document", document);
    pt.add("text", text);

    Prompt prompt = new Prompt(List.of(message, pt.createMessage()));
    ParameterizedTypeReference<?> returnType = finalidade.getReturnType();
    CallResponseSpec response = call(prompt);
    if (returnType == null) {
      return response.content();
    }
    Object entity = response.entity(returnType);
    return new Gson().toJson(entity);
  }

  /**
   * @param chatClient
   * @param prompt
   * @return
   */
  protected CallResponseSpec call(ChatClient chatClient, Prompt prompt) {
    return chatClient.prompt(prompt)
        .advisors(vectorStoreService.getQuestionAnswerAdvisor()).call();
  }

  protected CallResponseSpec call(Prompt prompt) {
    return createClient().prompt(prompt)
        .advisors(vectorStoreService.getQuestionAnswerAdvisor()).call();
  }

  /**
   * @return
   */
  protected ChatClient createClient() {
    return ChatClient.builder(chatModel).build();
  }

}
