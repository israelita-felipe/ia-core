package com.ia.core.owl.service;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.PrefixManager;

import com.ia.core.owl.service.exception.OWLParserException;
import com.ia.core.owl.service.model.AnaliseInferenciaDTO;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import com.ia.core.owl.service.model.axioma.HasAxiomas;

/**
 * Interface de Use Case para manipulação de ontologias OWL.
 * <p>
 * Combina as operações de CoreOWLService, OWLOntologyManagementService e
 * OWLParsingService para fornecer uma interface unificada para manipulação
 * de ontologias OWL.
 *
 * @author Israel Araújo
 */
public interface DefaultOWLUseCase {

  // ===== Métodos de CoreOWLService =====

  /**
   * Analisa um hasAxiomas completo: verifica consistência e realiza inferências.
   *
   * @return {@link AnaliseInferenciaDTO}
   */
  AnaliseInferenciaDTO checkInferrences();

  /**
   * Adiciona um axioma Manchester ao hasAxiomas e verifica consistência.
   *
   * @param hasAxiomas {@link HasAxiomas}
   * @param axioma     {@link AxiomaDTO}
   */
  void addAxiom(HasAxiomas hasAxiomas, AxiomaDTO axioma);

  /**
   * Cria um axioma DTO.
   *
   * @param expressao a expressão Manchester
   * @return o AxiomaDTO criado
   */
  AxiomaDTO criarAxioma(String expressao);

  /**
   * Cria um formato de documento OWL.
   *
   * @return OWLDocumentFormat
   */
  OWLDocumentFormat createDocumentFormat();

  /**
   * Retorna a URI da ontologia.
   *
   * @return URI da ontologia
   */
  String getUri();

  /**
   * Adiciona axiomas à ontologia atual.
   *
   * @param hasAxiomas objeto contendo os axiomas
   * @throws OWLParserException           se ocorrer erro no parsing
   * @throws OWLOntologyCreationException se ocorrer erro na criação
   */
  void addAxioms(HasAxiomas hasAxiomas)
    throws OWLParserException, OWLOntologyCreationException;

  // ===== Métodos de OWLOntologyManagementService =====

  /**
   * Retorna o prefixo da ontologia.
   *
   * @return prefixo da ontologia
   */
  String getPrefix();

  /**
   * Retorna a versão da ontologia.
   *
   * @return versão da ontologia
   */
  String getVersion();

  /**
   * Retorna o gerenciador de ontologia.
   *
   * @return OWLOntologyManager
   */
  OWLOntologyManager getManager();

  /**
   * Retorna a ontologia atual.
   *
   * @return a ontologia OWL
   */
  OWLOntology getOntology();

  /**
   * Retorna a fábrica de dados OWL.
   *
   * @return OWLDataFactory
   */
  OWLDataFactory getDataFactory();

  /**
   * Retorna o gerenciador de prefixos.
   *
   * @return PrefixManager
   */
  PrefixManager getPrefixManager();

  // ===== Métodos de OWLParsingService =====

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
