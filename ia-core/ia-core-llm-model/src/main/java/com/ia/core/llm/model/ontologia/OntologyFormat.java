package com.ia.core.llm.model.ontologia;

/**
 * Enum para representar os formatos de ontologia OWL suportados.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public enum OntologyFormat {
  /**
   * Manchester OWL Syntax.
   */
  MANCHESTER,

  /**
   * RDF/XML Syntax.
   */
  RDF_XML,

  /**
   * Turtle Syntax.
   */
  TURTLE,

  /**
   * OWL/XML Syntax.
   */
  OWL_XML,

  /**
   * Functional OWL Syntax.
   */
  FUNCTIONAL_OWL
}
