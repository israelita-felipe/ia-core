package com.ia.core.owl.service;

import lombok.Getter;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Provedor bidirecional de formas curtas para entidades OWL.
 * <p>
 * Estende o BidirectionalShortFormProviderAdapter para fornecer funcionalidade
 * de conversão entre formas curtas e IRIs completas, com suporte a prefixos.
 *
 * @author Israel Araújo
 * @since 1.0.0
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
                                            DefaultPrefixManager prefixManager) {
    super(man, ontologies, prefixManager);
    this.prefixManager = prefixManager;
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
