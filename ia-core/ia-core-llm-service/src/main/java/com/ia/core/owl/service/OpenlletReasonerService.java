package com.ia.core.owl.service;

import openllet.owlapi.OpenlletReasonerFactory;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Serviço para realização de inferências e verificação de consistência em
 * ontologias OWL utilizando o reasoner Openllet.
 * <p>
 * Esta classe fornece funcionalidades para:
 * <ul>
 * <li>Carregar ontologias a partir de axiomas Manchester</li>
 * <li>Verificar consistência da ontologia</li>
 * <li>Realizar inferências e extrair novos axiomas</li>
 * <li>Detectar inconsistências e classes insatisfatíveis</li>
 * <li>Classificar a hierarquia de classes</li>
 * <li>Recuperar subclasses e instâncias</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */

public class OpenlletReasonerService
  implements OWLReasoningService {
  /** Tipos de inferências padrão a serem pré-computadas */
  private static final InferenceType[] DEFAULT_INFERENCE_TYPES = {
      InferenceType.CLASS_HIERARCHY,
      InferenceType.OBJECT_PROPERTY_HIERARCHY,
      InferenceType.DATA_PROPERTY_HIERARCHY, InferenceType.CLASS_ASSERTIONS,
      InferenceType.DATA_PROPERTY_ASSERTIONS,
      InferenceType.DIFFERENT_INDIVIDUALS, InferenceType.DISJOINT_CLASSES,
      InferenceType.OBJECT_PROPERTY_ASSERTIONS,
      InferenceType.SAME_INDIVIDUAL };
  /** Geradores de axiomas inferidos padrão */
  private static final List<InferredAxiomGenerator<? extends OWLAxiom>> DEFAULT_AXIOM_GENERATORS = List
      .of(new InferredSubClassAxiomGenerator(),
          new InferredClassAssertionAxiomGenerator(),
          new InferredDataPropertyCharacteristicAxiomGenerator(),
          new InferredDisjointClassesAxiomGenerator(),
          new InferredEquivalentClassAxiomGenerator(),
          new InferredInverseObjectPropertiesAxiomGenerator(),
          new InferredObjectPropertyCharacteristicAxiomGenerator(),
          new InferredPropertyAssertionGenerator(),
          new InferredSubDataPropertyAxiomGenerator(),
          new InferredSubObjectPropertyAxiomGenerator(),
          new InferredEquivalentDataPropertiesAxiomGenerator());
  /** Reasoner Openllet */
  private OWLReasoner reasoner;
  /** OWL Service for parsing OntologiaDTO to OWLOntology */
  private final DefaultOwlService owlService;

  /**
   * Constrói um novo serviço de reasoner Openllet.
   *
   * @param owlService the OWL service for parsing OntologiaDTO
   */
  public OpenlletReasonerService(DefaultOwlService owlService) {
    this.owlService = Objects.requireNonNull(owlService, "owlService cannot be null");
  }

  /**
   * Inicializa o reasoner Openllet com a ontologia OWL carregada e pré-computa
   *
   * @param ontology the OWL ontology to load into the reasoner
   */
  @Override
  public void refreshReasoner(OWLOntology ontology) {
    Objects.requireNonNull(ontology, "ontology cannot be null");
    refreshReasonerWithOntology(ontology);
  }

  /**
   * Inicializa o reasoner Openllet com a ontologia OWL carregada e pré-computa
   *
   * @param ontology the OWL ontology to load into the reasoner
   */
  private void refreshReasonerWithOntology(OWLOntology ontology) {
    Objects.requireNonNull(ontology, "ontology cannot be null");
    OpenlletReasonerFactory reasonerFactory = OpenlletReasonerFactory
        .getInstance();
    // limpa a instância anterior
    dispose();
    this.reasoner = reasonerFactory.createReasoner(ontology);
    precomputeInferences();
  }


  /**
   * Pré-computa os tipos de inferências padrão para otimizar consultas futuras.
   */
  private void precomputeInferences() {
    reasoner.precomputeInferences(getInferenceTypes());
  }

  /**
   * @return
   */
  @Override
  public InferenceType[] getInferenceTypes() {
    return DEFAULT_INFERENCE_TYPES;
  }

  /**
   * Verifica se a ontologia carregada é consistente.
   *
   * @return {@code true} se a ontologia for consistente, {@code false} caso
   *         contrário
   * @throws IllegalStateException se nenhuma ontologia estiver carregada
   */
  @Override
  public boolean isConsistent() {
    validateReasonerLoaded();
    return reasoner.isConsistent();
  }

  /**
   * Retorna todas as classes insatisfatíveis (inconsistentes) na ontologia.
   *
   * @return conjunto de classes insatisfatíveis, excluindo a classe bottom
   *         (owl:Nothing)
   * @throws IllegalStateException se nenhuma ontologia estiver carregada
   */
  @Override
  public Set<OWLClass> getUnsatisfiableClasses() {
    validateReasonerLoaded();
    return reasoner.getUnsatisfiableClasses().getEntitiesMinusBottom();
  }

  /**
   * Realiza inferências sobre a ontologia e retorna novos axiomas inferidos.
   * <p>
   * Os axiomas retornados são aqueles que não estavam presentes na ontologia
   * original mas foram inferidos pelo reasoner.
   * </p>
   *
   * @param ontology the ontology to perform inferences on
   * @return lista de axiomas inferidos em formato Manchester
   * @throws IllegalStateException se a ontologia não estiver carregada ou for
   *                               inconsistente
   * @throws RuntimeException      se ocorrer erro durante a geração da
   *                               ontologia inferida
   */
  @Override
  public Set<OWLAxiom> performInferences(OWLOntology ontology) {
    try {
      Objects.requireNonNull(ontology, "ontology cannot be null");
      OWLOntologyManager manager = ontology.getOWLOntologyManager();
      validateConsistentOntology();
      OWLOntology inferredOntology = createInferredOntology(manager);
      return inferredOntology.getAxioms();
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage(), e);
    }
  }

  /**
   * Cria uma ontologia contendo os axiomas inferidos pelo reasoner.
   *
   * @param manager the ontology manager to use
   * @return ontologia com axiomas inferidos
   * @throws OWLOntologyCreationException se ocorrer erro na criação da
   *                                      ontologia
   */
  private OWLOntology createInferredOntology(OWLOntologyManager manager)
    throws OWLOntologyCreationException {
    OWLOntology inferredOntology = manager.createOntology();

    InferredOntologyGenerator generator = new InferredOntologyGenerator(reasoner,
                                                                        getAxiomGenerators());
    generator.fillOntology(manager.getOWLDataFactory(), inferredOntology);

    return inferredOntology;
  }

  /**
   * @return
   */
  @Override
  public List<InferredAxiomGenerator<? extends OWLAxiom>> getAxiomGenerators() {
    return DEFAULT_AXIOM_GENERATORS;
  }

  /**
   * Detecta e retorna uma lista de inconsistências na ontologia.
   * <p>
   * As inconsistências detectadas incluem:
   * <ul>
   * <li>Ontologia inconsistente</li>
   * <li>Classes insatisfatíveis</li>
   * <li>Indivíduos pertencendo a classes disjuntas</li>
   * </ul>
   * </p>
   *
   * @param ontology the ontology to check for inconsistencies
   * @return lista de mensagens descrevendo as inconsistências encontradas
   * @throws IllegalStateException se nenhuma ontologia estiver carregada
   */
  @Override
  public List<String> detectInconsistencies(OWLOntology ontology) {
    Objects.requireNonNull(ontology, "ontology cannot be null");
    validateReasonerLoaded();

    List<String> inconsistencies = new ArrayList<>();
    checkGlobalConsistency(inconsistencies);
    checkDisjointClassViolations(ontology, inconsistencies);

    return inconsistencies;
  }

  /**
   * Verifica a consistência global da ontologia e adiciona detalhes de
   * inconsistências à lista fornecida.
   *
   * @param inconsistencies lista onde as mensagens de inconsistência serão
   *                        adicionadas
   */
  private void checkGlobalConsistency(List<String> inconsistencies) {
    if (!isConsistent()) {
      inconsistencies
          .add("Ontologia inconsistente - existem contradições lógicas");
      addUnsatisfiableClassesDetails(inconsistencies);
    }
  }

  /**
   * Adiciona detalhes sobre classes insatisfatíveis à lista de inconsistências.
   *
   * @param inconsistencies lista onde as mensagens de inconsistência serão
   *                        adicionadas
   */
  private void addUnsatisfiableClassesDetails(List<String> inconsistencies) {
    Set<OWLClass> unsatisfiableClasses = getUnsatisfiableClasses();
    for (OWLClass cls : unsatisfiableClasses) {
      inconsistencies.add("Classe insatisfatível: " + cls.toString());
    }
  }

  /**
   * * Verifica violações de classes disjuntas na ontologia e adiciona detalhes
   * à lista fornecida.
   *
   * @param ontology the ontology to check
   * @param inconsistencies lista onde as mensagens de inconsistência serão
   *                        adicionadas
   */
  private void checkDisjointClassViolations(OWLOntology ontology, List<String> inconsistencies) {
    for (OWLClass cls : ontology.getClassesInSignature()) {
      checkDisjointViolationsForClass(cls, inconsistencies);
    }
  }

  /**
   * * Verifica violações de classes disjuntas para uma classe específica e
   * adiciona detalhes à lista fornecida.
   *
   * @param cls             a classe a ser verificada (não pode ser nula)
   * @param inconsistencies lista onde as mensagens de inconsistência serão
   *                        adicionadas
   */
  private void checkDisjointViolationsForClass(OWLClass cls,
                                               List<String> inconsistencies) {
    Set<OWLClass> disjoints = reasoner.getDisjointClasses(cls).entities()
        .collect(Collectors.toSet());

    for (OWLClass disjoint : disjoints) {
      if (!disjoint.isOWLNothing()) {
        findDisjointClassViolations(cls, disjoint, inconsistencies);
      }
    }
  }

  /**
   * * Encontra e registra violações de indivíduos pertencentes a classes
   * disjuntas.
   *
   * @param cls1            a primeira classe disjunta (não pode ser nula)
   * @param cls2            a segunda classe disjunta (não pode ser nula)
   * @param inconsistencies lista onde as mensagens de inconsistência serão
   *                        adicionadas
   */
  private void findDisjointClassViolations(OWLClass cls1, OWLClass cls2,
                                           List<String> inconsistencies) {
    Set<OWLNamedIndividual> intersection = findIndividualsInBothClasses(cls1,
                                                                        cls2);

    for (OWLNamedIndividual individual : intersection) {
      String violation = String
          .format("Indivíduo %s pertence a classes disjuntas: %s e %s",
                  individual.toString(), cls1.toString(), cls2.toString());
      inconsistencies.add(violation);
    }
  }

  /**
   * * Encontra indivíduos que são instâncias de ambas as classes especificadas.
   *
   * @param cls1 a primeira classe (não pode ser nula)
   * @param cls2 a segunda classe (não pode ser nula)
   * @return conjunto de indivíduos que são instâncias de ambas as classes
   */
  private Set<OWLNamedIndividual> findIndividualsInBothClasses(OWLClass cls1,
                                                               OWLClass cls2) {
    NodeSet<OWLNamedIndividual> instances1 = reasoner.getInstances(cls1,
                                                                   false);
    NodeSet<OWLNamedIndividual> instances2 = reasoner.getInstances(cls2,
                                                                   false);

    Set<OWLNamedIndividual> intersection = new HashSet<>();
    for (OWLNamedIndividual individual : instances1.getFlattened()) {
      if (instances2.containsEntity(individual)) {
        intersection.add(individual);
      }
    }

    return intersection;
  }

  /**
   * Classifica a ontologia, computando a hierarquia completa de classes.
   *
   * @throws IllegalStateException se nenhuma ontologia estiver carregada
   */
  @Override
  public void classify() {
    validateReasonerLoaded();
    reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
  }

  /**
   * Libera os recursos utilizados pelo reasoner.
   * <p>
   * Este método deve ser chamado quando o reasoner não for mais necessário para
   * garantir a liberação adequada de recursos.
   * </p>
   */
  @Override
  public void dispose() {
    if (reasoner != null) {
      reasoner.dispose();
      reasoner = null;
    }
  }

  /**
   * Valida se o reasoner está carregado.
   *
   * @throws IllegalStateException se nenhum reasoner estiver carregado
   */
  private void validateReasonerLoaded() {
    if (reasoner == null) {
      throw new IllegalStateException("Reasoner not loaded. Call refreshReasoner() first.");
    }
  }

  /**
   * Valida se a ontologia está carregada e é consistente.
   *
   * @throws IllegalStateException se nenhuma ontologia estiver carregada ou se
   *                               for inconsistente
   */
  private void validateConsistentOntology() {
    validateReasonerLoaded();
    if (!isConsistent()) {
      throw new IllegalStateException("Ontologia inconsistente - não é possível realizar inferências");
    }
  }

  /**
   * Retorna o reasoner Openllet em uso.
   *
   * @return o reasoner OWL, ou {@code null} se nenhum estiver inicializado
   */
  @Override
  public OWLReasoner getReasoner() {
    return reasoner;
  }
}
