package com.ia.core.owl.service;

import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import com.ia.core.owl.service.exception.OWLParserException;
import com.ia.core.owl.service.model.AnaliseInferenciaDTO;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import com.ia.core.owl.service.model.axioma.HasAxiomas;

/**
 * Serviço para manipulação de ontologias
 *
 * @author Israel Araújo
 */
public interface CoreOWLService {

  /**
   * Analisa um hasAxiomas completo: verifica consistência e realiza inferências
   *
   * @return {@link AnaliseInferenciaDTO}
   */
  AnaliseInferenciaDTO checkInferrences();

  /**
   * Adiciona um axioma Manchester ao hasAxiomas e verifica consistência
   *
   * @param hasAxiomas {@link HasAxiomas}
   * @param axioma     {@link AxiomaDTO}
   */
  void addAxiom(HasAxiomas hasAxiomas, AxiomaDTO axioma);

  /**
   * @param expressao
   * @param tipo
   * @param ordem
   * @return
   */
  AxiomaDTO criarAxioma(String expressao);

  /**
   * @return
   */
  OWLDocumentFormat createDocumentFormat();

  String getUri();

  /**
   * @param hasAxiomas
   * @throws OWLParserException
   * @throws OWLOntologyCreationException
   */
  void addAxioms(HasAxiomas hasAxiomas)
    throws OWLParserException, OWLOntologyCreationException;
}
