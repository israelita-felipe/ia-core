package com.ia.core.owl.service;

import java.util.Collection;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import lombok.Getter;

/**
 *
 */
public class CoreBidirectionalShortFormProvider
  extends BidirectionalShortFormProviderAdapter {
  @Getter
  private DefaultPrefixManager prefixManager;

  /**
   * @param man
   * @param ontologies
   * @param shortFormProvider
   */
  public CoreBidirectionalShortFormProvider(OWLOntologyManager man,
                                            Collection<OWLOntology> ontologies,
                                            String prefix, String uri) {
    super(man, ontologies,
          this.prefixManager = createPrefixManager(prefix, uri));
  }

  /**
   * @param uri
   * @param prefix
   * @return
   */
  public static DefaultPrefixManager createPrefixManager(String prefix,
                                                         String uri) {
    DefaultPrefixManager prefixManager = new DefaultPrefixManager(uri
        + "#");
    prefixManager.setPrefix(prefix + ":", uri + "#");
    prefixManager.setDefaultPrefix(prefix + ":");
    return prefixManager;
  }

  @Override
  public Stream<OWLEntity> entities(String shortForm) {
    return super.entities(String.format("<%s>", shortForm));
  }

  @Override
  public OWLEntity getEntity(String shortForm) {
    return super.getEntity(String.format("<%s>", shortForm));
  }
}
