package com.ia.core.model;

/**
 * Indica que uma entidade é versionável, considerando lock otimista.
 */
public interface HasVersion {
  /** Versão default */
  public static final Long DEFAULT_VERSION = 1l;

  /**
   * @return Long com a versão do objeto
   */
  Long getVersion();

  /**
   * @param version Versão do objeto
   */
  void setVersion(Long version);
}
