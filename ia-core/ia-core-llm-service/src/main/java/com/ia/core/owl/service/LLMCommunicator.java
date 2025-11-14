package com.ia.core.owl.service;

import java.util.List;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Content;
import org.springframework.ai.content.Media;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LLMCommunicator {

  @Value("${spring.ai.ollama.base-url}")
  private String ollamaBaseUrl;
  @Value("${spring.ai.ollama.chat.options.model}")
  private String modelName;

  private Strategy strategy = Strategy.CHAT_CLIENT;

  public static enum Strategy {
    CHAT_CLIENT
  }

  public void setStrategy(Strategy strategy) {
    this.strategy = strategy;
    log.info("Alterando estratégia para: {}", strategy);
  }

  public String sendPrompt(ChatModel chatModel, String prompt) {
    if (strategy == Strategy.CHAT_CLIENT) {
      log.info("Usando estratégia ChatClient");
      String response = tryWithChatClient(chatModel, prompt);
      if (isValidResponse(response)) {
        return response;
      }
    }
    throw new IllegalStateException("Estratégia desconhecida: " + strategy);
  }

  public String sendPrompt(ChatModel chatModel, String prompt,
                           List<Media> media) {
    if (strategy == Strategy.CHAT_CLIENT) {
      log.info("Usando estratégia ChatClient");
      return tryWithChatClient(chatModel, prompt, media);
    }
    throw new IllegalStateException("Estratégia desconhecida: " + strategy);
  }

  private String tryWithChatClient(ChatModel chatModel, String prompt) {
    try {
      log.info("Tentando com ChatClient...");

      // Método alternativo: usando a API mais baixa
      ChatResponse chatResponse = chatModel
          .call(new Prompt(new UserMessage(prompt), OllamaChatOptions
              .builder().model(modelName).temperature(0.1).build()));

      if (chatResponse != null && chatResponse.getResult() != null
          && chatResponse.getResult().getOutput() != null) {

        Content content = chatResponse.getResult().getOutput();
        if (content.getText() != null) {
          return content.getText();
        }
      }

      return null;

    } catch (Exception e) {
      log.error("ChatClient falhou: {}", e.getMessage());
      return null;
    }
  }

  private String tryWithChatClient(ChatModel chatModel, String prompt,
                                   List<Media> media) {
    try {
      log.info("Tentando com ChatClient...");
      // Método alternativo: usando a API mais baixa
      Prompt promptObject = new Prompt(UserMessage.builder().text(prompt)
          .media(media).build(),
                                       OllamaChatOptions.builder()
                                           .model(modelName)
                                           .temperature(0.1).build());
      ChatResponse chatResponse = chatModel.call(promptObject);

      if (chatResponse != null && chatResponse.getResult() != null
          && chatResponse.getResult().getOutput() != null) {

        Content content = chatResponse.getResult().getOutput();
        if (content.getText() != null) {
          return content.getText();
        }
      }

      return null;

    } catch (Exception e) {
      log.error("ChatClient falhou: {}", e.getMessage());
      return null;
    }
  }

  private boolean isValidResponse(String response) {
    if (response == null || response.trim().isEmpty()) {
      return false;
    }

    // Filtra respostas completamente irrelevantes
    if (response.contains("candies") || response.contains("doces")
        || response.toLowerCase().contains("sorry")
        || response.length() < 10) {
      return false;
    }

    return true;
  }
}
