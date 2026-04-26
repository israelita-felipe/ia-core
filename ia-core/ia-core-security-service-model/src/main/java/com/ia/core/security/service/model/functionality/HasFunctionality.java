package com.ia.core.security.service.model.functionality;

import com.ia.core.security.model.functionality.Functionality;

import java.util.Set;

/**
 * @author Israel Araújo
 */
public interface HasFunctionality {

  String getFunctionalityTypeName();

  /**
   * Registra a funcionalidade
   *
   * @param manager {@link FunctionalityManager}
   */
  Set<Functionality> registryFunctionalities(FunctionalityManager manager);

}
