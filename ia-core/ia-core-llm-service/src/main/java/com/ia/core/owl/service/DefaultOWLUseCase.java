package com.ia.core.owl.service;

import com.ia.core.owl.service.exception.OWLParserException;
import com.ia.core.owl.service.model.AnaliseInferenciaDTO;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import com.ia.core.owl.service.model.axioma.HasAxiomas;
import org.semanticweb.owlapi.model.*;

import java.util.Set;

/**
 * Interface de Use Case para manipulação de ontologias OWL.
 * <p>
 * Combina as operações de CoreOWLService, OWLOntologyManagementService e
 * OWLParsingService para fornecer uma interface unificada para manipulação
 * de ontologias OWL.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface DefaultOWLUseCase {

  // ===== Métodos de CoreOWLService =====

  /**
   * Analisa um hasAxiomas completo: verifica consistência e realiza inferências.
   *
   * @param manager the ontology manager
   * @param ontology the ontology to analyze
   * @return {@link AnaliseInferenciaDTO}
   */
  AnaliseInferenciaDTO checkInferrences(org.semanticweb.owlapi.model.OWLOntologyManager manager,
                                        org.semanticweb.owlapi.model.OWLOntology ontology);

  /**
   * Adiciona um axioma Manchester ao hasAxiomas e verifica consistência.
   *
   * @param manager the ontology manager
   * @param ontology the ontology
   * @param hasAxiomas {@link HasAxiomas}
   * @param axioma     {@link AxiomaDTO}
   */
  void addAxiom(org.semanticweb.owlapi.model.OWLOntologyManager manager,
               org.semanticweb.owlapi.model.OWLOntology ontology,
               HasAxiomas hasAxiomas,
               AxiomaDTO axioma);

  /**
   * Cria um axioma DTO.
   *
   * @param expressao a expressão Manchester
   * @param uri the ontology URI
   * @param prefix the ontology prefix
   * @param version the ontology version
   * @return o AxiomaDTO criado
   */
  AxiomaDTO criarAxioma(String expressao, String uri, String prefix, String version);

  /**
   * Cria um formato de documento OWL.
   *
   * @return OWLDocumentFormat
   */
  OWLDocumentFormat createDocumentFormat();

  /**
   * Adiciona axiomas à ontologia atual.
   *
   * @param manager the ontology manager
   * @param ontology the ontology
   * @param hasAxiomas objeto contendo os axiomas
   * @throws OWLParserException           se ocorrer erro no parsing
   * @throws OWLOntologyCreationException se ocorrer erro na criação
   */
  void addAxioms(org.semanticweb.owlapi.model.OWLOntologyManager manager,
                 org.semanticweb.owlapi.model.OWLOntology ontology,
                 HasAxiomas hasAxiomas)
    throws OWLParserException, OWLOntologyCreationException;

  // ===== Métodos de OWLOntologyManagementService =====

  /**
   * Retorna o serviço de raciocínio OWL.
   *
   * @return OWLReasoningService
   */
  OWLReasoningService getReasoningService();

  /**
   * Define o serviço de raciocínio OWL.
   *
   * @param reasonerService the reasoning service
   */
  void setReasoningService(OWLReasoningService reasonerService);

  // ===== Métodos de OWLParsingService =====

  /**
   * Carrega uma ontologia a partir de axiomas.
   *
   * @param manager the ontology manager
   * @param axiomas conjunto de axiomas OWL
   * @return a ontologia carregada
   * @throws OWLOntologyCreationException se ocorrer erro na criação
   */
  OWLOntology loadOntologyFromAxioms(org.semanticweb.owlapi.model.OWLOntologyManager manager,
                                     Set<OWLAxiom> axiomas)
    throws OWLOntologyCreationException;

  /**
   * Converte um axioma OWL para formato Manchester.
   *
   * @param axiom o axioma OWL a ser convertido
   * @param uri the ontology URI
   * @param prefix the ontology prefix
   * @param version the ontology version
   * @return o axioma convertido para DTO
   * @throws OWLParserException se ocorrer erro na conversão
   */
  AxiomaDTO convertOWLAxiomToDTO(OWLAxiom axiom, String uri, String prefix, String version)
    throws OWLParserException;

  /**
   * Extrai axiomas inferidos que não estavam presentes na ontologia original.
   *
   * @param ontology the original ontology
   * @param axioms conjunto de axiomas da ontologia inferida
   * @return lista de novos axiomas inferidos
   * @throws OWLParserException se ocorrer erro no parsing
   */
  java.util.List<AxiomaDTO> extractInferredAxioms(org.semanticweb.owlapi.model.OWLOntology ontology,
                                                  Set<OWLAxiom> axioms)
    throws OWLParserException;
}
