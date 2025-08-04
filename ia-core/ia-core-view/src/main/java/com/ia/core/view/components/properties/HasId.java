package com.ia.core.view.components.properties;

import java.util.UUID;

/**
 * Possui identificador
 *
 * @author Israel Araújo
 */
public interface HasId {
  /**
   * @return {@link String} id gerado por {@link UUID#randomUUID()}
   */
  default String createId() {
    return UUID.randomUUID().toString();
  }

  /**
   * Atribui o identificador
   *
   * @param id Identificador
   */
  void setId(String id);
}
