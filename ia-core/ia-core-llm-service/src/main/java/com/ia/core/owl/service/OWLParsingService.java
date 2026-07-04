package com.ia.core.owl.service;

import com.ia.core.owl.service.exception.OWLParserException;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import java.util.Set;

/**
 * Interface para serviços de parsing OWL.
 * <p>
 * Define operações para parsing de expressões Manchester, carga de axiomas
 * e conversão entre formatos OWL e DTOs.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface OWLParsingService {

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

  /**
   * Parse ontology content from format and content string.
   *
   * @param format the ontology format (e.g., "MANCHESTER", "RDF/XML", "TURTLE")
   * @param content the ontology content as string
   * @param prefix the ontology prefix
   * @param uri the ontology URI
   * @return the parsed OWLOntology
   * @throws OWLParserException if parsing fails
   * @throws OWLOntologyCreationException if ontology creation fails
   */
  OWLOntology parseOntology(String format, String content, String prefix, String uri)
    throws OWLParserException, OWLOntologyCreationException;
}
