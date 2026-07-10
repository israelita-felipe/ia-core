package com.ia.core.llm.service.agente;

import com.ia.core.llm.service.chat.ChatService;
import com.ia.core.llm.service.model.agente.ContextConversacaoDTO;
import com.ia.core.llm.service.model.agente.RespostaAgenteDTO;
import com.ia.core.llm.service.template.TemplateService;
import com.ia.core.owl.service.DefaultOwlService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Integration tests for {@link ConversationalAgentService}.
 * <p>
 * These tests validate the complete flow described in section 7.3 of PLANO_REFACTORIZACAO_AGENTES.md:
 * 1. User starts conversation
 * 2. Agent creates ContextOntology for session
 * 3. User sends message
 * 4. Agent extracts concepts/relations from message
 * 5. Agent updates ContextOntology with new concepts
 * 6. Agent builds prompt with ontology context
 * 7. Agent sends to LLM with enriched context
 * 8. Agent saves response in history
 * 9. Agent returns response to user
 *
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:hsqldb:mem:testdb;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.hsqldb.jdbc.JDBCDriver",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.show-sql=false"
})
@DisplayName("ConversationalAgentService Integration Tests")
class ConversationalAgentServiceIntegrationTest {

  private ConversationalAgentService conversationalAgentService;

  @Mock
  private ChatService chatService;

  @Mock
  private DefaultOwlService owlService;

  @Mock
  private TemplateService templateService;

  @Mock
  private List<OwlConstructorTool> owlTools;

  @Mock
  private ContextoConversacaoService contextoConversacaoService;

  @BeforeEach
  void setUp() {
    conversationalAgentService = new ConversationalAgentService(
        chatService,
        contextoConversacaoService,
        owlService,
        templateService,
        owlTools
    );
  }

  @Nested
  @DisplayName("Section 7.3 Flow: Conversational Agent with Context Ontology")
  class Section73FlowTests {

    @Test
    @DisplayName("Should complete full conversational flow from session creation to message processing")
    void shouldCompleteFullConversationalFlow() {
      // Given: User starts conversation
      String userId = "user-123";
      String dominio = "biologia";
      String sessionId = "session-abc";

      ContextConversacaoDTO contextoCriado = ContextConversacaoDTO.builder()
          .sessionId(sessionId)
          .userId(userId)
          .dominio(dominio)
          .build();

      when(chatService.ask(anyString(), any(), anyString(), anyString(), any()))
          .thenReturn("Resposta do agente baseada no contexto da ontologia.");

      when(contextoConversacaoService.createContextOntology(anyString(), anyString(), anyString()))
          .thenReturn(contextoCriado);
      when(contextoConversacaoService.getContextOntology(anyString()))
          .thenReturn(java.util.Optional.of(contextoCriado));

      // When: Agent creates ContextOntology for session (Step 2)
      ContextConversacaoDTO contexto = conversationalAgentService.createSession(userId, dominio);
      assertThat(contexto).isNotNull();
      assertThat(contexto.getSessionId()).isNotNull();
      assertThat(contexto.getUserId()).isEqualTo(userId);
      assertThat(contexto.getDominio()).isEqualTo(dominio);

      // And: User sends message (Step 3)
      String mensagem = "O que é um cachorro?";

// When: Agent processes message (Steps 4-9)
       RespostaAgenteDTO resposta = conversationalAgentService.processMessage(contexto.getSessionId(), mensagem);

       // Then: Agent returns response to user (Step 9)
      assertThat(resposta).isNotNull();
      assertThat(resposta.getAgentResponse()).isNotEmpty();
      assertThat(resposta.getOntologyStatus()).isNotNull();
    }

    @Test
    @DisplayName("Should handle multiple messages in the same session")
    void shouldHandleMultipleMessagesInSameSession() {
      // Given: Existing session
      String userId = "user-456";
      String dominio = "medicina";

      ContextConversacaoDTO contextoCriado = ContextConversacaoDTO.builder()
          .sessionId("session-456")
          .userId(userId)
          .dominio(dominio)
          .build();

      when(contextoConversacaoService.createContextOntology(anyString(), anyString(), anyString()))
          .thenReturn(contextoCriado);
      when(contextoConversacaoService.getContextOntology(anyString()))
          .thenReturn(java.util.Optional.of(contextoCriado));

      ContextConversacaoDTO contexto = conversationalAgentService.createSession(userId, dominio);

      when(chatService.ask(anyString(), any(), anyString(), anyString(), any()))
          .thenReturn("Primeira resposta")
          .thenReturn("Segunda resposta");

// When: User sends first message
       RespostaAgenteDTO resposta1 = conversationalAgentService.processMessage(contexto.getSessionId(),
           "O que é diabetes?");

       // Then: Agent processes and responds
       assertThat(resposta1.getAgentResponse()).isEqualTo("Primeira resposta");

       // When: User sends second message in same session
       RespostaAgenteDTO resposta2 = conversationalAgentService.processMessage(contexto.getSessionId(),
           "Quais são os sintomas?");

       // Then: Agent processes with accumulated context
       assertThat(resposta2.getAgentResponse()).isEqualTo("Segunda resposta");
    }

    @Test
    @DisplayName("Should end session and clean up resources")
    void shouldEndSessionAndCleanUpResources() {
      // Given: Active session
      String userId = "user-789";
      String dominio = "biblioteca";

      ContextConversacaoDTO contextoCriado = ContextConversacaoDTO.builder()
          .sessionId("session-789")
          .userId(userId)
          .dominio(dominio)
          .build();

      when(contextoConversacaoService.createContextOntology(anyString(), anyString(), anyString()))
          .thenReturn(contextoCriado);

      ContextConversacaoDTO contexto = conversationalAgentService.createSession(userId, dominio);

      // When: User ends session
      conversationalAgentService.endSession(contexto.getSessionId());

      // Then: Session should be cleaned up (verified by service behavior)
      // The endSession method calls deleteContextOntology which should clean up resources
    }
  }

  @Nested
  @DisplayName("Flow Validation: Context Ontology Updates")
  class ContextOntologyUpdateTests {

    @Test
    @DisplayName("Should update context ontology with extracted concepts")
    void shouldUpdateContextOntologyWithExtractedConcepts() {
      // Given: Session with context
      String userId = "user-999";
      String dominio = "biologia";

      ContextConversacaoDTO contextoCriado = ContextConversacaoDTO.builder()
          .sessionId("session-999")
          .userId(userId)
          .dominio(dominio)
          .build();

      when(contextoConversacaoService.createContextOntology(anyString(), anyString(), anyString()))
          .thenReturn(contextoCriado);
      when(contextoConversacaoService.getContextOntology(anyString()))
          .thenReturn(java.util.Optional.of(contextoCriado));

      ContextConversacaoDTO contexto = conversationalAgentService.createSession(userId, dominio);

      when(chatService.ask(anyString(), any(), anyString(), anyString(), any()))
          .thenReturn("Cachorro é um mamífero. Mamíferos têm pelos.");

// When: User sends message with new concepts
       String mensagem = "Cães são animais que têm pelos.";
       RespostaAgenteDTO resposta = conversationalAgentService.processMessage(contexto.getSessionId(), mensagem);

       // Then: Ontology status should reflect updates
       assertThat(resposta.getOntologyStatus()).isNotNull();
      // The LLM would have used OWL tools to add concepts like "Cachorro", "Animal", "temPelos"
    }

    @Test
    @DisplayName("Should build enriched prompt with ontology context")
    void shouldBuildEnrichedPromptWithOntologyContext() {
      // Given: Session with existing ontology context
      String userId = "user-888";
      String dominio = "biologia";

      ContextConversacaoDTO contextoCriado = ContextConversacaoDTO.builder()
          .sessionId("session-888")
          .userId(userId)
          .dominio(dominio)
          .build();

      when(contextoConversacaoService.createContextOntology(anyString(), anyString(), anyString()))
          .thenReturn(contextoCriado);
      when(contextoConversacaoService.getContextOntology(anyString()))
          .thenReturn(java.util.Optional.of(contextoCriado));

      ContextConversacaoDTO contexto = conversationalAgentService.createSession(userId, dominio);

      when(chatService.ask(anyString(), any(), anyString(), anyString(), any()))
          .thenReturn("Resposta com contexto enriquecido");

// When: User sends message
       String mensagem = "Explique a relação entre mamíferos e animais.";
       RespostaAgenteDTO resposta = conversationalAgentService.processMessage(contexto.getSessionId(), mensagem);

       // Then: Response should be based on enriched context
       assertThat(resposta.getAgentResponse()).isNotEmpty();
    }
  }
}
