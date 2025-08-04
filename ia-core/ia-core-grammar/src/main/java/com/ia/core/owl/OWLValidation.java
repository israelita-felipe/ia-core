package com.ia.core.owl;

import java.util.Collection;
import java.util.HashSet;

import org.semanticweb.owlapi.model.OWLAxiom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 */
@RequiredArgsConstructor
public class OWLValidation<T extends OWLModel<?>> {
  @Getter
  private final T model;
  @Getter
  private Collection<OWLAxiom> axioms = new HashSet<>();
}
