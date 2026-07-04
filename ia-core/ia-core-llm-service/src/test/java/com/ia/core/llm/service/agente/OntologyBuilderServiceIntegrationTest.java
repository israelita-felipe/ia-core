package com.ia.core.llm.service.agente;

import com.ia.core.llm.service.chat.ChatService;
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

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:hsqldb:mem:testdb;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.hsqldb.jdbc.JDBCDriver",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.show-sql=false"
})
@DisplayName("OntologyBuilderService Integration Tests")
class OntologyBuilderServiceIntegrationTest {

  private OntologyBuilderService ontologyBuilderService;

  @Mock
  private ChatService chatService;

  @Mock
  private DefaultOwlService owlService;

  @Mock
  private TemplateService templateService;

  @Mock
  private List<OwlConstructorTool> owlTools;

  @BeforeEach
  void setUp() {
    ontologyBuilderService = new OntologyBuilderService(
        chatService,
        owlService,
        templateService,
        owlTools
    );
  }

  @Nested
  @DisplayName("Section 6.5 Flow: Ontology Builder with OWL Tools")
  class Section65FlowTests {

    @Test
    @DisplayName("Should complete full ontology building flow")
    void shouldCompleteFullOntologyBuildingFlow() {
      String sessionId = "session-ont-123";
      String corpus = "Animais são seres vivos. Cães são animais.";
      String domain = "biologia";

      when(chatService.ask(anyString(), any(), anyString(), anyString(), any()))
          .thenReturn("Ontologia construída com classes: Animal, Cachorro");

      String result = ontologyBuilderService.buildOntology(sessionId, corpus, domain);

      assertThat(result).isNotNull();
      assertThat(result).contains("Ontologia");
    }

    @Test
    @DisplayName("Should use OWL tools for axiom construction")
    void shouldUseOwlToolsForAxiomConstruction() {
      String sessionId = "session-ont-456";
      String corpus = "Livros têm autores.";
      String domain = "biblioteca";

      when(chatService.ask(anyString(), any(), anyString(), anyString(), any()))
          .thenReturn("SubClassOf(Livro ObjectSomeValuesFrom(temAutor Autor))");

      String result = ontologyBuilderService.buildOntology(sessionId, corpus, domain);

      assertThat(result).contains("SubClassOf");
    }

    @Test
    @DisplayName("Should handle ontology refinement with feedback")
    void shouldHandleOntologyRefinementWithFeedback() {
      String sessionId = "session-ont-789";
      String feedback = "Adicionar classe Mamífero";

      when(chatService.ask(anyString(), any(), anyString(), anyString(), any()))
          .thenReturn("SubClassOf(Mamifero Animal)");

      String result = ontologyBuilderService.refineOntology(sessionId, feedback);

      assertThat(result).isNotNull();
      assertThat(result).contains("SubClassOf");
    }
  }
}
