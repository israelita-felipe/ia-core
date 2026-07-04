package com.ia.core.owl.service;

import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.owl.service.exception.OWLParserException;
import com.ia.core.owl.service.model.AnaliseInferenciaDTO;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import com.ia.core.owl.service.model.axioma.HasAxiomas;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

/**
 * Serviço para manipulação de ontologias OWL.
 * <p>
 * Define operações básicas para análise de inferências, adição de axiomas
 * e criação de formatos de documento OWL.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface CoreOWLService {

  /**
   * Analisa um hasAxiomas completo: verifica consistência e realiza inferências
   *
   * @param ontologiaDTO the ontology DTO to analyze
   * @return {@link AnaliseInferenciaDTO}
   */
  AnaliseInferenciaDTO checkInferrences(OntologiaDTO ontologiaDTO);

  /**
   * Adiciona um axioma Manchester ao hasAxiomas e verifica consistência
   *
   * @param ontologiaDTO the ontology DTO
   * @param hasAxiomas {@link HasAxiomas}
   * @param axioma     {@link AxiomaDTO}
   */
  void addAxiom(OntologiaDTO ontologiaDTO, HasAxiomas hasAxiomas, AxiomaDTO axioma);

  /**
   * Adiciona um axioma em Manchester syntax à ontologia
   *
   * @param ontologiaDTO the ontology DTO
   * @param manchesterAxiom the Manchester syntax axiom string
   * @return {@link AxiomaDTO} criado a partir do axioma Manchester
   * @throws OWLParserException se ocorrer erro no parsing
   * @throws OWLOntologyCreationException se ocorrer erro na criação
   */
  AxiomaDTO addAxiom(OntologiaDTO ontologiaDTO, String manchesterAxiom)
    throws OWLParserException, OWLOntologyCreationException;

  /**
   * @param expressao
   * @param uri the ontology URI
   * @param prefix the ontology prefix
   * @param version the ontology version
   * @return
   */
  AxiomaDTO criarAxioma(String expressao, String uri, String prefix, String version);

  /**
   * @return
   */
  OWLDocumentFormat createDocumentFormat();

  /**
   * @param ontologiaDTO the ontology DTO
   * @param hasAxiomas
   * @throws OWLParserException
   * @throws OWLOntologyCreationException
   */
  void addAxioms(OntologiaDTO ontologiaDTO, HasAxiomas hasAxiomas)
    throws OWLParserException, OWLOntologyCreationException;
}
