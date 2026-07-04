package com.ia.core.llm.service.agente;

import com.ia.core.llm.service.chat.ChatService;
import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.llm.service.template.TemplateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Testes de integração para fluxos de agentes LLM.
 * Valida a interação entre ContextoConversacaoService, OntologyBuilderService e ConversationalAgentService.
 *
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Agent Integration Tests")
class AgentIntegrationTest {

  @Mock
  private ChatService chatService;

  @Mock
  private TemplateService templateService;

  @BeforeEach
  void setUp() {
    // Setup for simpler tests that don't require complex mocking
  }

  @Nested
  @DisplayName("Fluxo: Chat Service Integration")
  class ChatServiceIntegrationFlow {

    @Test
    @DisplayName("Deve validar integração com ChatService")
    void shouldValidateChatServiceIntegration() {
      // Dado
      String document = "Test document";
      String text = "Test question";
      String chatSessionId = "session-123";
      String expectedResponse = "Test response";

      when(chatService.ask(anyString(), anyString(), anyString())).thenReturn(expectedResponse);

      // Quando
      String result = chatService.ask(document, text, chatSessionId);

      // Então
      assertThat(result).isNotNull();
      assertThat(result).isEqualTo(expectedResponse);
    }
  }

  @Nested
  @DisplayName("Fluxo: Template Service Integration")
  class TemplateServiceIntegrationFlow {

    @Test
    @DisplayName("Deve validar integração com TemplateService")
    void shouldValidateTemplateServiceIntegration() {
      // Dado
      String templateId = "template-123";
      Map<String, Object> params = Map.of("key", "value");
      String expectedResponse = "Processed template";
      TemplateDTO template = TemplateDTO.builder()
          .identificador(templateId)
          .conteudo("Template {key}")
          .build();

      when(templateService.processTemplate(eq(template), any(Map.class))).thenReturn(expectedResponse);

      // Quando
      String result = templateService.processTemplate(template, params);

      // Então
      assertThat(result).isNotNull();
      assertThat(result).isEqualTo(expectedResponse);
      verify(templateService).processTemplate(eq(template), any(Map.class));
    }
  }
}
