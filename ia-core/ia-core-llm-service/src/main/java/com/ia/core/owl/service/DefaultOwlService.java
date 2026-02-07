package com.ia.core.owl.service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import com.ia.core.owl.service.exception.OWLParserException;
import com.ia.core.owl.service.model.AnaliseInferenciaDTO;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import com.ia.core.owl.service.model.axioma.HasAxiomas;

import lombok.Getter;

/**
 * Serviço para manipulação de ontologias OWL
 *
 * @author Israel Araújo
 * @version 1.0.0
 */
public class DefaultOwlService
  implements CoreOWLService, OWLOntologyManagementService, OWLParsingService {

  @Getter
  private final CoreOWLReasoner reasonerService;
  @Getter
  private final OWLDataFactory dataFactory;
  @Getter
  private final OWLOntologyManager manager;
  @Getter
  private final OWLOntology ontology;

  @Getter
  private final String uri;
  @Getter
  private final String prefix;

  @Getter
  private final String version;

  private CoreBidirectionalShortFormProvider shortFormProvider;

  public DefaultOwlService(String prefix, String uri, String version)
    throws OWLOntologyCreationException {
    this.uri = uri;
    this.prefix = prefix;
    this.version = version;
    this.manager = createOntologyManager();
    this.dataFactory = createDataFactory(manager);
    this.ontology = createOntology(manager, uri, version);
    this.shortFormProvider = createBidirectionalShorFormProvider(manager,
                                                                 ontology,
                                                                 prefix,
                                                                 uri);
    this.reasonerService = createOWLReasoner(manager, ontology,
                                             getPrefixManager());
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

  public CoreOWLReasoner createOWLReasoner(OWLOntologyManager manager,
                                           OWLOntology ontology,
                                           PrefixManager prefixManager) {
    return new OpenlletReasonerService(manager, ontology, prefixManager);
  }

  public PrefixManager getPrefixManager() {
    return this.shortFormProvider.getPrefixManager();
  }

  @Override
  public OWLReasoningService getReasoningService() {
    return (OWLReasoningService) this.reasonerService;
  }

  /**
   * Carrega uma ontologia a partir de uma coleção de axiomas e inicializa o
   * reasoner.
   *
   * @param hasAxiomas objecto que possui coleção de axiomas para construir a
   *                   ontologia (não pode ser nula)
   * @throws OWLParserException           se ocorrer erro no parsing dos axiomas
   * @throws OWLOntologyCreationException se ocorrer erro na criação da
   *                                      ontologia
   * @throws IllegalArgumentException     se a coleção de axiomas for nula
   */
  @Override
  public void addAxioms(HasAxiomas hasAxiomas)
    throws OWLParserException, OWLOntologyCreationException {

    Objects.requireNonNull(hasAxiomas, "Axiomas collection cannot be null");

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

  }

  @Override
  public AnaliseInferenciaDTO checkInferrences() {
    try {
      // Carrega a ontologia no reasoner
      reasonerService.refreshReasoner();

      boolean isConsistent = reasonerService.isConsistent();

      if (isConsistent) {
        List<AxiomaDTO> axiomasInferidos = extractInferredAxioms(reasonerService
            .performInferences());
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
                                          .detectInconsistencies(),
                                      new ArrayList<>(), 0);

    } catch (Exception e) {
      return new AnaliseInferenciaDTO(false, Arrays.asList(String
          .format("Expressão: %s\nErro: %s", new ArrayList<>(),
                  e.getLocalizedMessage())), new ArrayList<>(), 0);
    }
  }

  /**
   * Cria um objeto AxiomaDTO com as propriedades padrão.
   *
   * @param expressao a expressão Manchester (não pode ser nula ou vazia)
   * @param ordem     a ordem do AxiomaDTO na sequência
   * @return o AxiomaDTO configurado
   * @throws IllegalArgumentException se expressao ou tipo forem nulos
   */
  @Override
  public AxiomaDTO criarAxioma(String expressao) {
    Objects.requireNonNull(expressao, "Expressão não pode ser nula");

    AxiomaDTO dto = new AxiomaDTO();
    dto.setExpressao(expressao);
    dto.setConsistente(true);
    dto.setInferido(false);
    dto.setAtivo(true);
    dto.setUri(getUri());
    dto.setPrefix(getPrefix());
    dto.setUriVersion(getVersion());
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
  public List<AxiomaDTO> extractInferredAxioms(Set<OWLAxiom> axioms)
    throws OWLParserException {
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
    AxiomaDTO inferredAxiom = convertOWLAxiomToDTO(axiom);
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
  public AxiomaDTO convertOWLAxiomToDTO(OWLAxiom axiom)
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
      return criarAxioma(baos.toString());
    } catch (Exception e) {
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
  public void addAxiom(HasAxiomas hasAxiomas, AxiomaDTO axioma) {
    try {
      addAxioms(() -> Arrays.asList(axioma));
      checkInferrences();
      hasAxiomas.getAxiomas().add(axioma);
    } catch (Exception e) {
      axioma.setConsistente(false);
      throw new IllegalArgumentException("AxiomaDTO inválido: "
          + e.getMessage(), e);
    }
  }

  @Override
  public OWLOntology loadOntologyFromAxioms(Set<OWLAxiom> axiomas)
    throws OWLOntologyCreationException {
    Objects.requireNonNull(axiomas, "Axiomas cannot be null");
    OWLOntology newOntology = manager.createOntology();
    manager.addAxioms(newOntology, axiomas);
    return newOntology;
  }

}
