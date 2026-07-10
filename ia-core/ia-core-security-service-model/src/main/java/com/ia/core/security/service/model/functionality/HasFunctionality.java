package com.ia.core.security.service.model.functionality;

import com.ia.core.security.model.functionality.Functionality;

import java.util.Set;

/**
 * Interface que define um módulo com funcionalidades.
 * <p>
 * Implementada por serviços que possuem funcionalidades
 * que podem ser registradas no sistema.
 *
 * @author Israel Araújo
 * @see Functionality
 * @since 1.0.0
 */
public interface HasFunctionality {

  /**
   * Retorna o nome do tipo de funcionalidade.
   *
   * @return nome do tipo de funcionalidade
   */
  String getFunctionalityTypeName();

  /**
   * Registra as funcionalidades no gerenciador.
   *
   * @param manager o gerenciador de funcionalidades
   * @return conjunto de funcionalidades registradas
   */
  Set<Functionality> registryFunctionalities(FunctionalityManager manager);

}
