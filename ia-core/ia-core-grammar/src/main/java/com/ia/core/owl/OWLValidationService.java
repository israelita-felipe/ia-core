package com.ia.core.owl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.AnnotationValueShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.mansyntax.ManchesterOWLSyntaxParser;

import com.ia.core.owl.OWLModel.HasName;
import com.ia.core.owl.OWLModel.HasType;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import openllet.owlapi.OWL;
import openllet.owlapi.OWLGenericTools;
import openllet.owlapi.OWLHelper;
import openllet.owlapi.OWLManagerGroup;
import openllet.owlapi.OpenlletReasoner;
import openllet.owlapi.explanation.PelletExplanation;
import openllet.owlapi.explanation.io.manchester.ManchesterSyntaxExplanationRenderer;

/**
 *
 */
@Slf4j
public class OWLValidationService {

  private final OWLOntologyManager m;
  private final OWLDataFactory owlDataFactory;
  private final OWLOntology ontology;
  private final ManchesterOWLSyntaxParser manchesterParser;
  private final OWLManagerGroup group;
  private final OWLHelper owl;
  private final OpenlletReasoner reasoner;

  /**
   * @throws OWLOntologyCreationException
   */
  public OWLValidationService()
    throws OWLOntologyCreationException {
    group = new OWLManagerGroup();
    m = OWLManager.createConcurrentOWLOntologyManager();
    manchesterParser = OWLManager.createManchesterParser();
    owlDataFactory = m.getOWLDataFactory();
    ontology = m.createOntology();
    owl = new OWLGenericTools(group, ontology.getOntologyID(), true);
    ShortFormProvider sfp = new AnnotationValueShortFormProvider(Arrays
        .asList(owlDataFactory.getRDFSLabel()),
                                                                 Collections
                                                                     .<OWLAnnotationProperty, List<String>> emptyMap(),
                                                                 m);
    BidirectionalShortFormProvider shortFormProvider = new BidirectionalShortFormProviderAdapter(m,
                                                                                                 m.getOntologies(),
                                                                                                 sfp);
    ShortFormEntityChecker owlEntityChecker = new ShortFormEntityChecker(shortFormProvider);
    manchesterParser.setOWLEntityChecker(owlEntityChecker);
    manchesterParser.setDefaultOntology(ontology);
    reasoner = (OpenlletReasoner) owl.getReasoner();
  }

  public <M extends HasName & HasType, T extends OWLModel<M>> void validate(OWLValidation<T> validation)
    throws UnsupportedOperationException, OWLException, IOException {
    m.addAxioms(ontology, validation.getAxioms());
    OWLClass modelClass = owlDataFactory
        .getOWLClass(validation.getModel().getType());
    m.addAxiom(ontology, owlDataFactory.getOWLDeclarationAxiom(modelClass));
    OWLNamedIndividual owlNamedIndividual = owlDataFactory
        .getOWLNamedIndividual(validation.getModel().toIndividual());
    m.addAxiom(ontology,
               owlDataFactory.getOWLDeclarationAxiom(owlNamedIndividual));
    m.addAxiom(ontology, owlDataFactory
        .getOWLClassAssertionAxiom(modelClass, owlNamedIndividual));
    printOntology(ontology);
    Collection<OWLClass> unsatisfiableClasses = unsatisfiableClasses();
    if (!isConsistent() || unsatisfiableClasses.size() > 0) {
      explain(unsatisfiableClasses);
    }
  }

  /**
   * @throws IOException
   * @throws OWLException
   * @throws UnsupportedOperationException
   */
  protected void explain(Collection<OWLClass> unsatisfiableClasses)
    throws UnsupportedOperationException, OWLException, IOException {
    PelletExplanation.setup();

    // The renderer is used to pretty print clashExplanation
    final ManchesterSyntaxExplanationRenderer renderer = new ManchesterSyntaxExplanationRenderer();
    // The writer used for the clashExplanation rendered
    final PrintWriter out = new PrintWriter(System.out);
    renderer.startRendering(out);
    // Create an clashExplanation generator
    final PelletExplanation expGen = new PelletExplanation(reasoner);
    // Explain why mad cow is an unsatisfiable concept
    Set<Set<OWLAxiom>> exp = unsatisfiableClasses.stream()
        .map(clz -> expGen.getUnsatisfiableExplanation(clz))
        .collect(Collectors.toSet());
    renderer.render(exp);
    renderer.endRendering();
  }

  /**
   * @return
   */
  public Collection<OWLClass> unsatisfiableClasses() {
    return reasoner.unsatisfiableClasses()
        .filter(clz -> !OWL.Nothing.equals(clz)).toList();
  }

  /**
   * @return
   */
  public boolean isConsistent() {
    return reasoner.isConsistent();
  }

  /**
   * @param ontology
   * @throws OWLOntologyStorageException
   */
  protected void printOntology(OWLOntology ontology)
    throws OWLOntologyStorageException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ontology.saveOntology(new ManchesterSyntaxDocumentFormat(),
                          outputStream);
    log.info(outputStream.toString());
  }

  public <M extends HasName & HasType, T extends OWLModel<M>> void addAxiom(OWLValidation<T> validation,
                                                                            String manchesterSyntaxAxiom)
    throws OWLOntologyStorageException {
    manchesterParser.setStringToParse(manchesterSyntaxAxiom);
    OWLAxiom axiom = manchesterParser.parseAxiom();
    m.addAxiom(ontology, axiom);
    printOntology(ontology);
  }

  public <M extends HasName & HasType, T extends OWLModel<M>> void addClassAxiom(OWLValidation<T> validation,
                                                                                 String className)
    throws OWLOntologyStorageException {
    OWLDeclarationAxiom axiom = owlDataFactory
        .getOWLDeclarationAxiom(owlDataFactory.getOWLClass(className));
    validation.getAxioms().add(axiom);
    m.addAxiom(ontology, axiom);
    printOntology(ontology);
  }

  public <M extends HasName & HasType, T extends OWLModel<M>> void addPropertyAxiom(OWLValidation<T> validation,
                                                                                    String propertyName)
    throws OWLOntologyStorageException {
    OWLDeclarationAxiom axiom = owlDataFactory
        .getOWLDeclarationAxiom(owlDataFactory
            .getOWLObjectProperty(propertyName));
    validation.getAxioms().add(axiom);
    m.addAxiom(ontology, axiom);
    printOntology(ontology);
  }

  public static void main(String[] args)
    throws UnsupportedOperationException, OWLException, IOException {
    OWLModel<Dog> dogModel = new OWLModel<OWLValidationService.Dog>(new Dog());
    dogModel.getObject().setName("Ted");
    OWLValidation<OWLModel<Dog>> validation = new OWLValidation<OWLModel<Dog>>(dogModel);
    OWLValidationService s = new OWLValidationService();
    s.addClassAxiom(validation, "dog");
    s.addClassAxiom(validation, "meat");
    s.addClassAxiom(validation, "animal");
    s.addPropertyAxiom(validation, "partOf");
    s.addPropertyAxiom(validation, "eats");
    s.addAxiom(validation, "dog SubClassOf: animal");
    // s.addAxiom(validation, "dog DisjointWith: animal");
    s.addAxiom(validation, "dog SubClassOf: eats only meat");
    s.addAxiom(validation, "dog SubClassOf: eats some meat");
    s.addAxiom(validation, "dog SubClassOf: eats only animal");
    s.addAxiom(validation, "dog SubClassOf: eats some animal");
    // s.addAxiom(validation, "meat DisjointWith: animal");
    s.addAxiom(validation, "meat SubClassOf: partOf some animal");
    s.validate(validation);
  }

  @Data
  private static class Dog
    implements HasName, HasType {
    private String name = "Ted";
    private String type = "dog";
  }
}
