package com.ia.core.owl.service;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import com.ia.core.owl.service.exception.OWLParserException;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import com.ia.core.owl.service.model.axioma.HasAxiomas;

/**
 * Interface para serviços de parsing OWL.
 * <p>
 * Define operações para parsing de expressões Manchester, carga de axiomas
 * e conversão entre formatos OWL e DTOs.
 * </p>
 *
 * @author Israel Araújo
 */
public interface OWLParsingService {

  /**
   * Carrega uma ontologia a partir de axiomas.
   *
   * @param axiomas conjunto de axiomas OWL
   * @return a ontologia carregada
   * @throws OWLOntologyCreationException se ocorrer erro na criação
   */
  OWLOntology loadOntologyFromAxioms(Set<OWLAxiom> axiomas)
    throws OWLOntologyCreationException;

  /**
   * Converte um axioma OWL para formato Manchester.
   *
   * @param axiom o axioma OWL a ser convertido
   * @return o axioma convertido para DTO
   * @throws OWLParserException se ocorrer erro na conversão
   */
  AxiomaDTO convertOWLAxiomToDTO(OWLAxiom axiom)
    throws OWLParserException;

  /**
   * Extrai axiomas inferidos que não estavam presentes na ontologia original.
   *
   * @param axioms conjunto de axiomas da ontologia inferida
   * @return lista de novos axiomas inferidos
   * @throws OWLParserException se ocorrer erro no parsing
   */
  java.util.List<AxiomaDTO> extractInferredAxioms(Set<OWLAxiom> axioms)
    throws OWLParserException;
}
