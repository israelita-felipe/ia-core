package com.ia.core.owl.service.model.axioma;

import java.util.Collection;

/**
 * Interface para entidades que possuem axiomas
 *
 * @author Israel Araújo
 * @version 1.0.0
 */
public interface HasAxiomas {
  /**
   * Obtém a coleção de axiomas
   *
   * @return coleção de axiomas
   */
  Collection<AxiomaDTO> getAxiomas();

}
