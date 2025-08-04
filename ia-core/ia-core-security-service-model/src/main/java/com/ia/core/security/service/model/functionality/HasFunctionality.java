package com.ia.core.security.service.model.functionality;

import java.util.Set;

import com.ia.core.security.model.functionality.Functionality;

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
