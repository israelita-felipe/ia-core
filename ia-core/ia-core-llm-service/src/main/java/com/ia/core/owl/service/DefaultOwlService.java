package com.ia.core.owl.service;

import com.ia.core.llm.model.ontologia.OntologyFormat;
import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.llm.service.model.ontologia.ResultadoValidacaoDTO;
import com.ia.core.owl.service.exception.OWLParserException;
import com.ia.core.owl.service.model.AnaliseInferenciaDTO;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import com.ia.core.owl.service.model.axioma.HasAxiomas;
import lombok.extern.slf4j.Slf4j;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Serviço para manipulação de ontologias OWL.
 * <p>
 * Implementação unificada que combina as operações de CoreOWLService,
 * OWLOntologyManagementService e OWLParsingService para fornecer uma interface
 * completa para manipulação de ontologias OWL.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public class DefaultOwlService
  implements CoreOWLService, OWLOntologyManagementService, OWLParsingService,
  DefaultOWLUseCase {

  private OWLReasoningService reasonerService;

  public DefaultOwlService() {
    this.reasonerService = new OpenlletReasonerService(this);
  }

  /**
   * @param ontology
   * @param manager
   * @param prefix
   * @param uri
   * @return
   */
  public CoreBidirectionalShortFormProvider createBidirectionalShorFormProvider(OWLOntologyManager manager,
                                                                                OWLOntology ontology,
                                                                                String prefix,
                                                                                String uri) {
    return new CoreBidirectionalShortFormProvider(manager, ontology
        .getImportsClosure(), createPrefixManager(prefix, uri));
  }

  /**
   * Creates and initializes the OWL reasoning service.
   *
   * @param manager the ontology manager
   * @param ontology the ontology to reason over
   * @param prefixManager the prefix manager
   * @return the initialized reasoning service
   */
  public OWLReasoningService createReasoningService(OWLOntologyManager manager,
                                                     OWLOntology ontology,
                                                     PrefixManager prefixManager) {
    OWLReasoningService service = new OpenlletReasonerService(this);
    service.refreshReasoner(ontology);
    return service;
  }

  /**
   * @param uri
   * @param prefix
   * @return
   */
  public DefaultPrefixManager createPrefixManager(String prefix,
                                                  String uri) {
    DefaultPrefixManager prefixManager = new DefaultPrefixManager(uri
        + "#");
    prefixManager.setPrefix(prefix + ":", uri + "#");
    prefixManager.setDefaultPrefix(prefix + ":");
    return prefixManager;
  }

  public OWLOntologyManager createOntologyManager() {
    return OWLManager.createConcurrentOWLOntologyManager();
  }

  public OWLDataFactory createDataFactory(OWLOntologyManager manager) {
    return manager.getOWLDataFactory();
  }

  public OWLOntology createOntology(OWLOntologyManager manager, String uri,
                                    String version)
    throws OWLOntologyCreationException {
    return manager.createOntology(new OWLOntologyID(IRI.create(uri),
                                                    IRI.create(version)));
  }

  @Override
  public OWLReasoningService getReasoningService() {
    return this.reasonerService;
  }

  /**
   * Sets the reasoning service to use.
   *
   * @param reasonerService the reasoning service
   */
  public void setReasoningService(OWLReasoningService reasonerService) {
    this.reasonerService = reasonerService;
  }

  /**
   * Carrega uma ontologia a partir de uma coleção de axiomas e inicializa o
   * reasoner.
   *
   * @param ontologiaDTO the ontology DTO
   * @param hasAxiomas objecto que possui coleção de axiomas para construir a
   *                   ontologia (não pode ser nula)
   * @throws OWLParserException           se ocorrer erro no parsing dos axiomas
   * @throws OWLOntologyCreationException se ocorrer erro na criação da
   *                                      ontologia
   * @throws IllegalArgumentException     se a coleção de axiomas for nula
   */
  @Override
  public void addAxioms(OntologiaDTO ontologiaDTO, HasAxiomas hasAxiomas)
    throws OWLParserException, OWLOntologyCreationException {

    Objects.requireNonNull(ontologiaDTO, "ontologiaDTO cannot be null");
    Objects.requireNonNull(hasAxiomas, "Axiomas collection cannot be null");

    // Convert OntologiaDTO to OWLOntology
    OWLOntology ontology = parseOntology(
        ontologiaDTO.getFormato().name(),
        ontologiaDTO.getConteudo(),
        ontologiaDTO.getPrefixo(),
        ontologiaDTO.getIri()
    );
    OWLOntologyManager manager = ontology.getOWLOntologyManager();

    for (AxiomaDTO axiom : hasAxiomas.getAxiomas()) {
      StringDocumentSource source = new StringDocumentSource(axiom
          .getExpressao()) {
        @Override
        public Optional<OWLDocumentFormat> getFormat() {
          return Optional.of(createDocumentFormat());
        }
      };
      OWLOntology axiomsOntology = manager
          .loadOntologyFromOntologyDocument(source);
      manager.addAxioms(ontology, axiomsOntology.getAxioms());
    }

    // Update the OntologiaDTO content with the updated ontology
    String updatedContent = saveOntologyToString(ontology);
    ontologiaDTO.setConteudo(updatedContent);
    ontologiaDTO.setUltimaModificacao(LocalDateTime.now());

  }

  @Override
  public AnaliseInferenciaDTO checkInferrences(OntologiaDTO ontologiaDTO) {
    try {
      Objects.requireNonNull(ontologiaDTO, "ontologiaDTO cannot be null");
      Objects.requireNonNull(reasonerService, "reasonerService must be set before calling this method");

      // Convert OntologiaDTO to OWLOntology
      OWLOntology ontology = parseOntology(
          ontologiaDTO.getFormato().name(),
          ontologiaDTO.getConteudo(),
          ontologiaDTO.getPrefixo(),
          ontologiaDTO.getIri()
      );
      OWLOntologyManager manager = ontology.getOWLOntologyManager();

      // Carrega a ontologia no reasoner
      reasonerService.refreshReasoner(ontology);

      boolean isConsistent = reasonerService.isConsistent();

      if (isConsistent) {
        List<AxiomaDTO> axiomasInferidos = extractInferredAxioms(ontology, reasonerService
            .performInferences(ontology));
        AnaliseInferenciaDTO resultado = new AnaliseInferenciaDTO(isConsistent,
                                                                  new ArrayList<>(),
                                                                  axiomasInferidos,
                                                                  axiomasInferidos
                                                                      .size());
        reasonerService.classify();
        return resultado;
      }
      return new AnaliseInferenciaDTO(false,
                                      reasonerService
                                          .detectInconsistencies(ontology),
                                      new ArrayList<>(), 0);

    } catch (org.semanticweb.owlapi.reasoner.InconsistentOntologyException e) {
      return new AnaliseInferenciaDTO(false, Arrays.asList(String
          .format("Expressão: %s\nErro: %s", new ArrayList<>(),
                  e.getLocalizedMessage())), new ArrayList<>(), 0);
    } catch (org.semanticweb.owlapi.reasoner.ReasonerInterruptedException e) {
      return new AnaliseInferenciaDTO(false, Arrays.asList(String
          .format("Expressão: %s\nErro: %s", new ArrayList<>(),
                  e.getLocalizedMessage())), new ArrayList<>(), 0);
    } catch (OWLParserException | OWLOntologyCreationException e) {
        return new AnaliseInferenciaDTO(false, Arrays.asList(String
            .format("Expressão: %s\nErro: %s", new ArrayList<>(),
                e.getLocalizedMessage())), new ArrayList<>(), 0);
    }
  }

  /**
   * Cria um objeto AxiomaDTO com as propriedades padrão.
   *
   * @param expressao a expressão Manchester (não pode ser nula ou vazia)
   * @return o AxiomaDTO configurado
   * @throws IllegalArgumentException se expressao ou tipo forem nulos
   */
  @Override
  public AxiomaDTO criarAxioma(String expressao, String uri, String prefix, String version) {
    Objects.requireNonNull(expressao, "Expressão não pode ser nula");

    AxiomaDTO dto = new AxiomaDTO();
    dto.setExpressao(expressao);
    dto.setConsistente(true);
    dto.setInferido(false);
    dto.setAtivo(true);
    dto.setUri(uri);
    dto.setPrefix(prefix);
    dto.setUriVersion(version);
    return dto;
  }

  /**
   * Extrai axiomas inferidos que não estavam presentes na ontologia original.
   *
   * @param axioms conjunto de axiomas da ontologia inferida
   * @return lista de novos axiomas inferidos
   * @throws OWLParserException
   */
  @Override
  public List<AxiomaDTO> extractInferredAxioms(OWLOntology ontology, Set<OWLAxiom> axioms)
    throws OWLParserException {
    Objects.requireNonNull(ontology, "ontology cannot be null");
    List<AxiomaDTO> inferredAxioms = new ArrayList<>();
    for (OWLAxiom axiom : axioms) {
      if (!ontology.containsAxiom(axiom)) {
        convertAndAddInferredAxiom(axiom, inferredAxioms);
      }
    }

    return inferredAxioms;
  }

  /**
   * Converte um axioma OWL para formato Manchester e o adiciona à lista de
   * axiomas inferidos.
   *
   * @param axiom         o axioma OWL a ser convertido (não pode ser nulo)
   * @param currentAxioms lista onde o axioma convertido será adicionado
   * @throws OWLParserException
   */
  private AxiomaDTO convertAndAddInferredAxiom(OWLAxiom axiom,
                                               List<AxiomaDTO> currentAxioms)
    throws OWLParserException {
    AxiomaDTO inferredAxiom = convertOWLAxiomToDTO(axiom, "", "", "");
    if (inferredAxiom != null) {
      inferredAxiom.setInferido(true);
      currentAxioms.add(inferredAxiom);
    }
    return inferredAxiom;
  }

  /**
   * Converte um axioma OWL para o formato Manchester.
   *
   * @param axiom o axioma OWL a ser convertido (não pode ser nulo)
   * @return o axioma convertido para formato Manchester, ou {@code null} se o
   *         tipo não for suportado
   * @throws IllegalArgumentException se o axioma for nulo
   */
  @Override
  public AxiomaDTO convertOWLAxiomToDTO(OWLAxiom axiom, String uri, String prefix, String version)
    throws OWLParserException {
    try {
      Objects.requireNonNull(axiom, "Axiom cannot be null");
      OWLOntologyManager manager = OWLManager
          .createConcurrentOWLOntologyManager();
      OWLOntology tempOntology = createOntology(manager, uri, version);
      tempOntology.addAxiom(axiom);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      manager.saveOntology(tempOntology, createDocumentFormat(), baos);
      manager.clearOntologies();
      return criarAxioma(baos.toString(), uri, prefix, version);
    } catch (OWLOntologyStorageException e) {
      throw new OWLParserException(e.getLocalizedMessage(), e);
    } catch (org.semanticweb.owlapi.model.OWLOntologyCreationException e) {
      throw new OWLParserException(e.getLocalizedMessage(), e);
    }
  }

  /**
   * @return
   */
  @Override
  public OWLDocumentFormat createDocumentFormat() {
    return new ManchesterSyntaxDocumentFormat();
  }

  @Override
  public void addAxiom(OntologiaDTO ontologiaDTO, HasAxiomas hasAxiomas, AxiomaDTO axioma) {
    try {
      Objects.requireNonNull(ontologiaDTO, "ontologiaDTO cannot be null");
      Objects.requireNonNull(hasAxiomas, "hasAxiomas cannot be null");
      Objects.requireNonNull(axioma, "axioma cannot be null");

      // Convert OntologiaDTO to OWLOntology
      OWLOntology ontology = parseOntology(
          ontologiaDTO.getFormato().name(),
          ontologiaDTO.getConteudo(),
          ontologiaDTO.getPrefixo(),
          ontologiaDTO.getIri()
      );
      OWLOntologyManager manager = ontology.getOWLOntologyManager();

      addAxioms(ontologiaDTO, () -> Arrays.asList(axioma));
      checkInferrences(ontologiaDTO);
      hasAxiomas.getAxiomas().add(axioma);
    } catch (OWLParserException e) {
      axioma.setConsistente(false);
      throw new IllegalArgumentException("AxiomaDTO inválido: "
          + e.getMessage(), e);
    } catch (OWLOntologyCreationException e) {
      axioma.setConsistente(false);
      throw new IllegalArgumentException("AxiomaDTO inválido: "
          + e.getMessage(), e);
    }
  }

  @Override
  public AxiomaDTO addAxiom(OntologiaDTO ontologiaDTO, String manchesterAxiom)
    throws OWLParserException, OWLOntologyCreationException {
    Objects.requireNonNull(ontologiaDTO, "ontologiaDTO cannot be null");
    Objects.requireNonNull(manchesterAxiom, "manchesterAxiom cannot be null");

    try {
      // Create AxiomaDTO from Manchester syntax
      AxiomaDTO axiomaDTO = criarAxioma(
          manchesterAxiom,
          ontologiaDTO.getIri(),
          ontologiaDTO.getPrefixo(),
          ontologiaDTO.getVersao() != null ? ontologiaDTO.getVersao() : "1.0"
      );

      // Add the axiom to the ontology using the existing addAxiom method
      var hasAxiomas = new HasAxiomas() {
        @Override
        public List<AxiomaDTO> getAxiomas() {
          return new ArrayList<>();
        }
      };

      addAxiom(ontologiaDTO, hasAxiomas, axiomaDTO);

      return axiomaDTO;
    } catch (Exception e) {
      throw new OWLParserException("Failed to add Manchester axiom: " + e.getMessage(), e);
    }
  }

  @Override
  public void addAxiom(OWLOntologyManager manager, OWLOntology ontology, HasAxiomas hasAxiomas, AxiomaDTO axioma) {
    // Convert OWL API objects to DTO and call DTO-based method
    try {
      String content = saveOntologyToString(ontology);
      OntologiaDTO ontologiaDTO = OntologiaDTO.builder()
          .conteudo(content)
          .formato(OntologyFormat.MANCHESTER)
          .iri(ontology.getOntologyID().getOntologyIRI().toString())
          .prefixo("ex")
          .dataCriacao(LocalDateTime.now())
          .ultimaModificacao(LocalDateTime.now())
          .build();

      addAxiom(ontologiaDTO, hasAxiomas, axioma);
    } catch (Exception e) {
      throw new RuntimeException("Failed to add axiom using OWL API objects", e);
    }
  }

  @Override
  public void addAxioms(OWLOntologyManager manager, OWLOntology ontology, HasAxiomas hasAxiomas)
    throws OWLParserException, OWLOntologyCreationException {
    // Convert OWL API objects to DTO and call DTO-based method
    String content = saveOntologyToString(ontology);
    OntologiaDTO ontologiaDTO = OntologiaDTO.builder()
        .conteudo(content)
        .formato(OntologyFormat.MANCHESTER)
        .iri(ontology.getOntologyID().getOntologyIRI().toString())
        .prefixo("ex")
        .dataCriacao(LocalDateTime.now())
        .ultimaModificacao(LocalDateTime.now())
        .build();

    addAxioms(ontologiaDTO, hasAxiomas);
  }

  @Override
  public AnaliseInferenciaDTO checkInferrences(OWLOntologyManager manager, OWLOntology ontology) {
    // Convert OWL API objects to DTO and call DTO-based method
    String content = saveOntologyToString(ontology);
    OntologiaDTO ontologiaDTO = OntologiaDTO.builder()
        .conteudo(content)
        .formato(OntologyFormat.MANCHESTER)
        .iri(ontology.getOntologyID().getOntologyIRI().toString())
        .prefixo("ex")
        .dataCriacao(LocalDateTime.now())
        .ultimaModificacao(LocalDateTime.now())
        .build();

    return checkInferrences(ontologiaDTO);
  }

  @Override
  public OWLOntology loadOntologyFromAxioms(OWLOntologyManager manager, Set<OWLAxiom> axiomas)
    throws OWLOntologyCreationException {
    Objects.requireNonNull(manager, "manager cannot be null");
    Objects.requireNonNull(axiomas, "Axiomas cannot be null");
    OWLOntology newOntology = manager.createOntology();
    manager.addAxioms(newOntology, axiomas);
    return newOntology;
  }

  private String saveOntologyToString(OWLOntology ontology) {
    try {
      java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
      ontology.getOWLOntologyManager().saveOntology(ontology, createDocumentFormat(), baos);
      return baos.toString();
    } catch (OWLOntologyStorageException e) {
      throw new RuntimeException("Failed to save ontology to string", e);
    }
  }

  @Override
  public OWLOntology parseOntology(String format, String content, String prefix, String uri)
    throws OWLParserException, OWLOntologyCreationException {
    Objects.requireNonNull(format, "Format cannot be null");
    Objects.requireNonNull(content, "Content cannot be null");
    Objects.requireNonNull(prefix, "Prefix cannot be null");
    Objects.requireNonNull(uri, "URI cannot be null");

    OWLOntologyManager tempManager = OWLManager.createConcurrentOWLOntologyManager();
    OWLDocumentFormat documentFormat = determineDocumentFormat(format);

    StringDocumentSource source = new StringDocumentSource(content) {
      @Override
      public Optional<OWLDocumentFormat> getFormat() {
        return Optional.of(documentFormat);
      }
    };

    try {
      OWLOntology parsedOntology = tempManager.loadOntologyFromOntologyDocument(source);

      // Set up prefix manager for the parsed ontology
      DefaultPrefixManager prefixManager = createPrefixManager(prefix, uri);
      CoreBidirectionalShortFormProvider shortFormProvider =
        new CoreBidirectionalShortFormProvider(tempManager, parsedOntology.getImportsClosure(), prefixManager);

      return parsedOntology;
    } catch (OWLOntologyCreationException e) {
      throw new OWLParserException("Failed to parse ontology: " + e.getLocalizedMessage(), e);
    }
  }

  private OWLDocumentFormat determineDocumentFormat(String format) throws OWLParserException {
    String normalizedFormat = format.toUpperCase().trim();

    switch (normalizedFormat) {
      case "MANCHESTER":
      case "MANCHESTER_SYNTAX":
        return new ManchesterSyntaxDocumentFormat();
      case "RDF":
      case "RDF/XML":
      case "RDFXML":
        return new RDFXMLDocumentFormat();
      case "TURTLE":
      case "TTL":
        return new TurtleDocumentFormat();
      case "OWL":
      case "OWL/XML":
      case "OWLXML":
        return new OWLXMLDocumentFormat();
      default:
        throw new OWLParserException("Unsupported ontology format: " + format);
    }
  }

  /**
   * Valida a consistência de um axioma individual.
   *
   * @param axiom axioma a validar
   * @return resultado da validação
   */
  public ResultadoValidacaoDTO validarAxioma(AxiomaDTO axiom) {
    long startTime = System.currentTimeMillis();
    log.debug("Validando axioma: {}", axiom);

    try {
      // Create temporary OntologiaDTO for validation
      String conteudo = "Prefix: ex: <http://example.com/>\n" +
                       "Ontology: <http://example.com/>\n\n" +
                       axiom.getExpressao();

      OntologiaDTO ontologiaDTO = OntologiaDTO.builder()
          .conteudo(conteudo)
          .formato(OntologyFormat.MANCHESTER)
          .iri("http://example.com/")
          .prefixo("ex")
          .dataCriacao(LocalDateTime.now())
          .ultimaModificacao(LocalDateTime.now())
          .build();

      // Use checkInferrences to check consistency
      var analise = checkInferrences(ontologiaDTO);
      boolean consistente = analise.isConsistente();

      long processingTime = System.currentTimeMillis() - startTime;

      if (consistente) {
        log.debug("Axioma consistente: {}", axiom);
        return ResultadoValidacaoDTO.builder()
            .consistente(true)
            .explicacao("Axioma é consistente com a ontologia atual")
            .iteracoesUsadas(1)
            .tempoProcessamentoMs(processingTime)
            .build();
      } else {
        log.warn("Axioma inconsistente: {}, inconsistências: {}",
                 axiom, analise.getInconsistencias());

        return ResultadoValidacaoDTO.builder()
            .consistente(false)
            .classesInsatisfativeis(analise.getInconsistencias())
            .explicacao("Axioma causa inconsistência na ontologia")
            .iteracoesUsadas(1)
            .tempoProcessamentoMs(processingTime)
            .build();
      }
    } catch (Exception e) {
      long processingTime = System.currentTimeMillis() - startTime;
      log.error("Erro ao validar axioma: {}", axiom, e);

      return ResultadoValidacaoDTO.builder()
          .consistente(false)
          .explicacao("Erro na validação: " + e.getMessage())
          .iteracoesUsadas(1)
          .tempoProcessamentoMs(processingTime)
          .build();
    }
  }

  /**
   * Valida a consistência de uma lista de axiomas.
   *
   * @param axioms lista de axiomas a validar
   * @return resultado da validação
   */
  public ResultadoValidacaoDTO validarAxiomas(List<AxiomaDTO> axioms) {
    long startTime = System.currentTimeMillis();
    log.debug("Validando {} axiomas", axioms.size());

    try {
      // Create temporary OntologiaDTO for validation
      StringBuilder conteudo = new StringBuilder();
      conteudo.append("Prefix: ex: <http://example.com/>\n");
      conteudo.append("Ontology: <http://example.com/>\n\n");
      for (AxiomaDTO axiom : axioms) {
        conteudo.append(axiom.getExpressao()).append("\n");
      }

      OntologiaDTO ontologiaDTO = OntologiaDTO.builder()
          .conteudo(conteudo.toString())
          .formato(OntologyFormat.MANCHESTER)
          .iri("http://example.com/")
          .prefixo("ex")
          .dataCriacao(LocalDateTime.now())
          .ultimaModificacao(LocalDateTime.now())
          .build();

      // Use checkInferrences to check consistency
      var analise = checkInferrences(ontologiaDTO);
      boolean consistente = analise.isConsistente();

      long processingTime = System.currentTimeMillis() - startTime;

      if (consistente) {
        log.debug("Axiomas consistentes: {}", axioms.size());
        return ResultadoValidacaoDTO.builder()
            .consistente(true)
            .explicacao("Todos os axiomas são consistentes com a ontologia atual")
            .iteracoesUsadas(1)
            .tempoProcessamentoMs(processingTime)
            .build();
      } else {
        log.warn("Axiomas inconsistentes, inconsistências: {}", analise.getInconsistencias());

        return ResultadoValidacaoDTO.builder()
            .consistente(false)
            .classesInsatisfativeis(analise.getInconsistencias())
            .explicacao("Axiomas causam inconsistência na ontologia")
            .iteracoesUsadas(1)
            .tempoProcessamentoMs(processingTime)
            .build();
      }
    } catch (Exception e) {
      long processingTime = System.currentTimeMillis() - startTime;
      log.error("Erro ao validar axiomas", e);

      return ResultadoValidacaoDTO.builder()
          .consistente(false)
          .explicacao("Erro na validação: " + e.getMessage())
          .iteracoesUsadas(1)
          .tempoProcessamentoMs(processingTime)
          .build();
    }
  }

  /**
   * Valida a consistência da ontologia atual.
   * Nota: Este método requer uma ontologia para validar.
   *
   * @param ontologiaDTO ontologia DTO a ser validada
   * @return resultado da validação
   */
  public ResultadoValidacaoDTO validarOntologiaAtual(OntologiaDTO ontologiaDTO) {
    long startTime = System.currentTimeMillis();
    log.debug("Validando ontologia atual");

    try {
      var analise = checkInferrences(ontologiaDTO);
      boolean consistente = analise.isConsistente();
      long processingTime = System.currentTimeMillis() - startTime;

      if (consistente) {
        log.debug("Ontologia atual é consistente");
        return ResultadoValidacaoDTO.builder()
            .consistente(true)
            .explicacao("Ontologia é consistente")
            .iteracoesUsadas(1)
            .tempoProcessamentoMs(processingTime)
            .build();
      } else {
        log.warn("Ontologia inconsistente, inconsistências: {}", analise.getInconsistencias());

        return ResultadoValidacaoDTO.builder()
            .consistente(false)
            .classesInsatisfativeis(analise.getInconsistencias())
            .explicacao("Ontologia contém inconsistências")
            .iteracoesUsadas(1)
            .tempoProcessamentoMs(processingTime)
            .build();
      }
    } catch (Exception e) {
      long processingTime = System.currentTimeMillis() - startTime;
      log.error("Erro ao validar ontologia atual", e);

      return ResultadoValidacaoDTO.builder()
          .consistente(false)
          .explicacao("Erro na validação: " + e.getMessage())
          .iteracoesUsadas(1)
          .tempoProcessamentoMs(processingTime)
          .build();
    }
  }

}
