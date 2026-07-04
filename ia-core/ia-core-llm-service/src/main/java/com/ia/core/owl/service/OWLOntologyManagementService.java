package com.ia.core.owl.service;

import com.ia.core.owl.service.exception.OWLParserException;
import com.ia.core.owl.service.model.AnaliseInferenciaDTO;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import com.ia.core.owl.service.model.axioma.HasAxiomas;
import org.semanticweb.owlapi.model.*;

/**
 * Interface para serviços de gerenciamento de ontologias OWL.
 * <p>
 * Define operações para criação, manipulação, salvamento e análise de ontologias
 * OWL, incluindo integração com serviços de parsing e raciocínio.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface OWLOntologyManagementService {

  /**
   * Analisa um hasAxiomas completo: verifica consistência e realiza inferências.
   *
   * @param manager the ontology manager
   * @param ontology the ontology to analyze
   * @return resultado da análise de inferências
   */
  AnaliseInferenciaDTO checkInferrences(org.semanticweb.owlapi.model.OWLOntologyManager manager,
                                        org.semanticweb.owlapi.model.OWLOntology ontology);

  /**
   * Adiciona axiomas à ontologia atual.
   *
   * @param manager the ontology manager
   * @param ontology the ontology
   * @param hasAxiomas objeto contendo os axiomas
   * @throws OWLParserException se ocorrer erro no parsing
   * @throws OWLOntologyCreationException se ocorrer erro na criação
   */
  void addAxioms(org.semanticweb.owlapi.model.OWLOntologyManager manager,
                 org.semanticweb.owlapi.model.OWLOntology ontology,
                 HasAxiomas hasAxiomas)
    throws OWLParserException, OWLOntologyCreationException;

  /**
   * Adiciona um axioma à ontologia atual.
   *
   * @param manager the ontology manager
   * @param ontology the ontology
   * @param hasAxiomas objeto contendo os axiomas
   * @param axioma o axioma a ser adicionado
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
}
