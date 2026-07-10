package com.ia.core.llm.service.agente;

import com.ia.core.llm.service.chat.ChatService;
import com.ia.core.llm.service.model.agente.ContextConversacaoDTO;
import com.ia.core.llm.service.model.agente.RespostaAgenteDTO;
import com.ia.core.llm.service.model.template.TemplateDTO;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Testes para {@link ConversationalAgentService}.
 *
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ConversationalAgentService")
class ConversationalAgentServiceTest {

  @Mock
  private ChatService chatService;

  @Mock
  private ContextoConversacaoService contextoConversacaoService;

  @Mock
  private DefaultOwlService owlService;

  @Mock
  private TemplateService templateService;

  @Mock
  private OwlConstructorTool owlTool1;

  @Mock
  private OwlConstructorTool owlTool2;

  private List<OwlConstructorTool> owlTools;

  private ConversationalAgentService service;

  @BeforeEach
  void setUp() {
    owlTools = List.of(owlTool1, owlTool2);
    service = new ConversationalAgentService(chatService, contextoConversacaoService, owlService, templateService, owlTools);
  }

  @Nested
  @DisplayName("createSession")
  class TestesCreateSession {

    @Test
    @DisplayName("Deve criar nova sessão conversacional")
    void deveCriarNovaSessaoConversacional() {
      // Dado
      String userId = "user-456";
      String dominio = "biologia";

      ContextConversacaoDTO contexto = ContextConversacaoDTO.builder()
          .sessionId("session-123")
          .userId(userId)
          .dominio(dominio)
          .build();

      when(contextoConversacaoService.createContextOntology(anyString(), eq(userId), eq(dominio)))
          .thenReturn(contexto);

      // Quando
      ContextConversacaoDTO result = service.createSession(userId, dominio);

      // Então
      assertThat(result).isNotNull();
      assertThat(result.getUserId()).isEqualTo(userId);
      assertThat(result.getDominio()).isEqualTo(dominio);
      verify(contextoConversacaoService).createContextOntology(anyString(), eq(userId), eq(dominio));
    }
  }

  @Nested
  @DisplayName("processMessage")
  class TestesProcessMessage {

    @Test
    @DisplayName("Deve processar mensagem do usuário")
    void deveProcessarMensagemDoUsuario() {
      // Dado
      String sessionId = "session-123";
      String mensagem = "O que é um cachorro?";
      String response = "Um cachorro é um mamífero domesticado.";

      ContextConversacaoDTO contexto = ContextConversacaoDTO.builder()
          .sessionId(sessionId)
          .dominio("biologia")
          .build();
      TemplateDTO template = TemplateDTO.builder()
          .conteudo("Prompt para {dominio}")
          .build();

      when(contextoConversacaoService.getContextOntology(sessionId)).thenReturn(Optional.of(contexto));
      when(chatService.ask(anyString(), anyMap(), anyString(), anyString(), any())).thenReturn(response);

// Quando
       RespostaAgenteDTO result = service.processMessage(sessionId, mensagem);

       // Então
       assertThat(result).isNotNull();
       assertThat(result.getAgentResponse()).isEqualTo(response);
       verify(contextoConversacaoService).getContextOntology(sessionId);
       verify(chatService).ask(anyString(), anyMap(), anyString(), anyString(), any());
     }

    @Test
    @DisplayName("Deve lançar exceção quando sessão não existe")
    void deveLancarExcecaoQuandoSessaoNaoExiste() {
      // Dado
      String sessionId = "session-123";
      String mensagem = "O que é um cachorro?";

      when(contextoConversacaoService.getContextOntology(sessionId)).thenReturn(Optional.empty());

      // Quando/Então
      assertThatThrownBy(() -> service.processMessage(sessionId, mensagem))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("Sessão não encontrada");
    }

    @Test
    @DisplayName("Deve usar prompt padrão quando template não disponível")
    void deveUsarPromptPadraoQuandoTemplateNaoDisponivel() {
      // Dado
      String sessionId = "session-123";
      String mensagem = "O que é um cachorro?";
      String response = "Um cachorro é um mamífero.";

      ContextConversacaoDTO contexto = ContextConversacaoDTO.builder()
          .sessionId(sessionId)
          .dominio("biologia")
          .build();
      TemplateDTO template = TemplateDTO.builder()
          .conteudo("Prompt para {dominio}")
          .build();

      when(contextoConversacaoService.getContextOntology(sessionId)).thenReturn(Optional.of(contexto));
      when(chatService.ask(anyString(), anyMap(), anyString(), anyString(), any())).thenReturn(response);

// Quando
       RespostaAgenteDTO result = service.processMessage(sessionId, mensagem);

       // Então
       assertThat(result).isNotNull();
       assertThat(result.getAgentResponse()).isEqualTo(response);
       verify(chatService).ask(anyString(), anyMap(), anyString(), anyString(), any());
     }
  }

  @Nested
  @DisplayName("endSession")
  class TestesEndSession {

    @Test
    @DisplayName("Deve encerrar sessão conversacional")
    void deveEncerrarSessaoConversacional() {
      // Dado
      String sessionId = "session-123";

      // Quando
      service.endSession(sessionId);

      // Então
      verify(contextoConversacaoService).deleteContextOntology(sessionId);
    }
  }
}
