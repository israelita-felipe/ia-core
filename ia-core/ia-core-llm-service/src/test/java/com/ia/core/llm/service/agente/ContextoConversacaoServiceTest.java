
package com.ia.core.llm.service.agente;

import com.ia.core.llm.model.agente.ContextoConversacao;
import com.ia.core.llm.model.ontologia.Ontologia;
import com.ia.core.llm.service.agente.mapper.ContextoConversacaoMapper;
import com.ia.core.llm.service.config.LlmModuleProperties;
import com.ia.core.llm.service.model.agente.ContextConversacaoDTO;
import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.llm.service.repository.ContextoConversacaoRepository;
import com.ia.core.owl.service.DefaultOwlService;
import com.ia.core.owl.service.OpenlletReasonerService;
import com.ia.core.owl.service.exception.OWLParserException;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.translator.Translator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Testes para {@link ContextoConversacaoService}.
 *
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ContextoConversacaoService")
class ContextoConversacaoServiceTest {

  @Mock
  private ContextoConversacaoRepository repository;

  @Mock
  private ContextoConversacaoMapper mapper;

  @Mock
  private SearchRequestMapper searchRequestMapper;

  @Mock
  private Translator translator;

  @Mock
  private ApplicationEventPublisher eventPublisher;

  @Mock
  private DefaultOwlService owlService;

  @Mock
  private OpenlletReasonerService reasonerService;

  @Mock
  private com.ia.core.llm.service.agente.mapper.OntologiaMapper ontologiaMapper;

  private ContextoConversacaoService service;

  @BeforeEach
  void setUp() {
    // Create config with all required dependencies
    ContextOntologyServiceConfig config = new ContextOntologyServiceConfig(
        repository,
        mapper,
        searchRequestMapper,
        translator,
        eventPublisher,
        ontologiaMapper
    );
    service = new ContextoConversacaoService(config, new LlmModuleProperties());
  }

  @Nested
  @DisplayName("createContextOntology")
  class TestesCreateContextOntology {

    @Test
    @DisplayName("Deve criar ontologia de contexto")
    void deveCriarOntologiaDeContexto() {
      // Dado
      String sessionId = "session-123";
      String userId = "user-456";
      String dominio = "biologia";

      ContextoConversacao entity = new ContextoConversacao();
      entity.setSessionId(sessionId);
      entity.setUserId(userId);
      entity.setDominio(dominio);

      ContextConversacaoDTO dto = new ContextConversacaoDTO();
      dto.setSessionId(sessionId);
      dto.setUserId(userId);
      dto.setDominio(dominio);

      when(repository.save(any(ContextoConversacao.class))).thenReturn(entity);
      when(mapper.toDTO(any(ContextoConversacao.class))).thenReturn(dto);

      // Quando
      ContextConversacaoDTO result = service.createContextOntology(sessionId, userId, dominio);

      // Então
      assertThat(result).isNotNull();
      assertThat(result.getSessionId()).isEqualTo(sessionId);
      assertThat(result.getUserId()).isEqualTo(userId);
      assertThat(result.getDominio()).isEqualTo(dominio);
      verify(repository).save(any(ContextoConversacao.class));
    }
  }

  @Nested
  @DisplayName("getContextOntology")
  class TestesGetContextOntology {

    @Test
    @DisplayName("Deve recuperar ontologia de contexto")
    void deveRecuperarOntologiaDeContexto() {
      // Dado
      String sessionId = "session-123";
      ContextoConversacao entity = new ContextoConversacao();
      entity.setSessionId(sessionId);

      ContextConversacaoDTO dto = new ContextConversacaoDTO();
      dto.setSessionId(sessionId);

      when(repository.findBySessionId(sessionId)).thenReturn(Optional.of(entity));
      when(mapper.toDTO(any(ContextoConversacao.class))).thenReturn(dto);

      // Quando
      Optional<ContextConversacaoDTO> result = service.getContextOntology(sessionId);

      // Então
      assertThat(result).isPresent();
      assertThat(result.get().getSessionId()).isEqualTo(sessionId);
      verify(repository).findBySessionId(sessionId);
    }

    @Test
    @DisplayName("Deve retornar vazio quando ontologia não existe")
    void deveRetornarVazioQuandoOntologiaNaoExiste() {
      // Dado
      String sessionId = "session-123";
      when(repository.findBySessionId(sessionId)).thenReturn(Optional.empty());

      // Quando
      Optional<ContextConversacaoDTO> result = service.getContextOntology(sessionId);

      // Então
      assertThat(result).isEmpty();
      verify(repository).findBySessionId(sessionId);
    }
  }

  @Nested
  @DisplayName("updateContextOntology")
  class TestesUpdateContextOntology {

    @Test
    @Disabled("Service creates DefaultOwlService internally, cannot be mocked for unit testing")
    @DisplayName("Deve atualizar ontologia de contexto")
    void deveAtualizarOntologiaDeContexto() throws OWLParserException, OWLOntologyCreationException {
      // Dado
      String sessionId = "session-123";
      String manchesterAxiom = "SubClassOf(ctx:Animal ObjectSomeValuesFrom(ctx:hasPart ctx:Head))";

      Ontologia ontologia = Ontologia.builder()
          .iri("http://example.com/ontologia/" + sessionId)
          .nome("Ontologia de Contexto")
          .descricao("Test ontology")
          .versao("1.0")
          .prefixo("ctx")
          .namespace("http://example.com/ontologia/" + sessionId + "#")
          .formato(com.ia.core.llm.model.ontologia.OntologyFormat.MANCHESTER)
          .conteudo("")
          .consistente(true)
          .dataCriacao(LocalDateTime.now())
          .ultimaModificacao(LocalDateTime.now())
          .build();

      ContextoConversacao entity = new ContextoConversacao();
      entity.setSessionId(sessionId);
      entity.setTotalAxiomasExtraidos(0);
      entity.setOntologia(ontologia);

      OntologiaDTO ontologiaDTO = new OntologiaDTO();
      ontologiaDTO.setIri("http://example.com/ontologia/" + sessionId);
      ontologiaDTO.setNome("Ontologia de Contexto");
      ontologiaDTO.setDescricao("Test ontology");
      ontologiaDTO.setVersao("1.0");
      ontologiaDTO.setPrefixo("ctx");
      ontologiaDTO.setNamespace("http://example.com/ontologia/" + sessionId + "#");
      ontologiaDTO.setFormato(com.ia.core.llm.model.ontologia.OntologyFormat.MANCHESTER);
      ontologiaDTO.setConteudo("Prefix: ctx: <http://example.com/ontologia/" + sessionId + "#>\nOntology: <http://example.com/ontologia/" + sessionId + ">\n\n");
      ontologiaDTO.setConsistente(true);
      ontologiaDTO.setDataCriacao(LocalDateTime.now());
      ontologiaDTO.setUltimaModificacao(LocalDateTime.now());

      ContextConversacaoDTO dto = new ContextConversacaoDTO();
      dto.setSessionId(sessionId);
      dto.setOntologia(ontologiaDTO);

      when(repository.findBySessionId(sessionId)).thenReturn(Optional.of(entity));
      when(repository.save(any(ContextoConversacao.class))).thenReturn(entity);
      when(mapper.toDTO(any(ContextoConversacao.class))).thenReturn(dto);
      when(mapper.toModel(any(ContextConversacaoDTO.class))).thenReturn(entity);
      when(reasonerService.isConsistent()).thenReturn(true);

      // Quando
      ContextConversacaoDTO result = service.updateContextOntology(sessionId, manchesterAxiom);

      // Então
      assertThat(result).isNotNull();
      verify(repository).save(any(ContextoConversacao.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando ontologia não existe")
    void deveLancarExcecaoQuandoOntologiaNaoExiste() {
      // Dado
      String sessionId = "session-123";
      String manchesterAxiom = "SubClassOf(Animal ObjectSomeValuesFrom(hasPart Head))";

      when(repository.findBySessionId(sessionId)).thenReturn(Optional.empty());

      // Quando/Então
      assertThatThrownBy(() -> service.updateContextOntology(sessionId, manchesterAxiom))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("Contexto não encontrado");
    }
  }

  @Nested
  @DisplayName("deleteContextOntology")
  class TestesDeleteContextOntology {

    @Test
    @DisplayName("Deve remover ontologia de contexto")
    void deveRemoverOntologiaDeContexto() {
      // Dado
      String sessionId = "session-123";

      // Quando
      service.deleteContextOntology(sessionId);

      // Então
      verify(repository).deleteBySessionId(sessionId);
    }
  }

  @Nested
  @DisplayName("existsContextOntology")
  class TestesExistsContextOntology {

    @Test
    @DisplayName("Deve retornar true quando ontologia existe")
    void deveRetornarTrueQuandoOntologiaExiste() {
      // Dado
      String sessionId = "session-123";
      when(repository.existsBySessionId(sessionId)).thenReturn(true);

      // Quando
      boolean result = service.existsContextOntology(sessionId);

      // Então
      assertThat(result).isTrue();
      verify(repository).existsBySessionId(sessionId);
    }

    @Test
    @DisplayName("Deve retornar false quando ontologia não existe")
    void deveRetornarFalseQuandoOntologiaNaoExiste() {
      // Dado
      String sessionId = "session-123";
      when(repository.existsBySessionId(sessionId)).thenReturn(false);

      // Quando
      boolean result = service.existsContextOntology(sessionId);

      // Então
      assertThat(result).isFalse();
      verify(repository).existsBySessionId(sessionId);
    }
  }
}
