package com.ia.core.llm.service.agente;

import com.ia.core.llm.service.chat.ChatService;
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
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Testes para {@link OntologyBuilderService}.
 *
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("OntologyBuilderService")
class OntologyBuilderServiceTest {

  @Mock
  private ChatService chatService;

  @Mock
  private DefaultOwlService owlService;

  @Mock
  private TemplateService templateService;

  @Mock
  private List<OwlConstructorTool> owlTools;

  private OntologyBuilderService service;

  @BeforeEach
  void setUp() {
    service = new OntologyBuilderService(chatService, owlService, templateService, owlTools);
  }

  @Nested
  @DisplayName("buildOntology")
  class TestesBuildOntology {

    @Test
    @DisplayName("Deve construir ontologia a partir de corpus")
    void deveConstruirOntologiaAPartirDeCorpus() {
      // Dado
      String sessionId = "session-123";
      String corpus = "Animais têm partes do corpo. Cães são animais.";
      String domain = "biologia";

      String systemPrompt = "Construa uma ontologia OWL 2 DL para o domínio de biologia.";
      String response = "SubClassOf(Animal ObjectSomeValuesFrom(hasPart Body))\nSubClassOf(Cachorro Animal)";
      TemplateDTO template = TemplateDTO.builder()
          .conteudo(systemPrompt)
          .build();

      when(templateService.loadById("ontology-builder-system")).thenReturn(Optional.of(template));
      when(templateService.processTemplate(eq(template), any(Map.class))).thenReturn(systemPrompt);
      when(chatService.ask(anyString(), anyMap(), anyString(), anyString(), any())).thenReturn(response);

      // Quando
      String result = service.buildOntology(sessionId, corpus, domain);

      // Então
      assertThat(result).isNotNull();
      assertThat(result).contains("SubClassOf");
      verify(templateService).loadById("ontology-builder-system");
      verify(chatService).ask(anyString(), anyMap(), anyString(), anyString(), any());
    }

    @Test
    @DisplayName("Deve usar prompt padrão quando template não disponível")
    void deveUsarPromptPadraoQuandoTemplateNaoDisponivel() {
      // Dado
      String sessionId = "session-123";
      String corpus = "Animais têm partes do corpo.";
      String domain = "biologia";

      String response = "SubClassOf(Animal ObjectSomeValuesFrom(hasPart Body))";

      when(chatService.ask(anyString(), anyMap(), anyString(), anyString(), any())).thenReturn(response);

      // Quando
      String result = service.buildOntology(sessionId, corpus, domain);

      // Então
      assertThat(result).isNotNull();
      assertThat(result).contains("SubClassOf");
      verify(chatService).ask(anyString(), anyMap(), anyString(), anyString(), any());
    }
  }

  @Nested
  @DisplayName("refineOntology")
  class TestesRefineOntology {

    @Test
    @DisplayName("Deve refinar ontologia existente")
    void deveRefinarOntologiaExistente() {
      // Dado
      String sessionId = "session-123";
      String feedback = "Adicionar classe Mamífero";

      String response = "SubClassOf(Mamifero Animal)\nSubClassOf(Cachorro Mamifero)";

      when(chatService.ask(anyString(), anyMap(), anyString(), anyString(), any())).thenReturn(response);

      // Quando
      String result = service.refineOntology(sessionId, feedback);

      // Então
      assertThat(result).isNotNull();
      assertThat(result).contains("SubClassOf");
      assertThat(result).contains("Mamifero");
      verify(chatService).ask(anyString(), anyMap(), anyString(), anyString(), any());
    }

    @Test
    @DisplayName("Deve usar prompt padrão quando template não disponível")
    void deveUsarPromptPadraoQuandoTemplateNaoDisponivelRefine() {
      // Dado
      String sessionId = "session-123";
      String feedback = "Adicionar classe Mamífero";

      String response = "SubClassOf(Mamifero Animal)";

      when(chatService.ask(anyString(), anyMap(), anyString(), anyString(), any())).thenReturn(response);

      // Quando
      String result = service.refineOntology(sessionId, feedback);

      // Então
      assertThat(result).isNotNull();
      assertThat(result).contains("SubClassOf");
      verify(chatService).ask(anyString(), anyMap(), anyString(), anyString(), any());
    }
  }
}
