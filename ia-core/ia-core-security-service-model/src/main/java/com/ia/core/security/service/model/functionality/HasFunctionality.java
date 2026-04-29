package com.ia.core.security.service.model.functionality;

import com.ia.core.security.model.functionality.Functionality;

import java.util.Set;
/**
 * Entidade de domínio que representa has functionality.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a HasFunctionality
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
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
