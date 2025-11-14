package com.ia.core.service.dto;

import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ia.core.service.dto.properties.HasPropertyChangeSupport;

import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Classe base para DTO de uma entidade
 *
 * @author Israel Araújo
 * @param <T> {@link AbstractDTO}
 * @see DTO
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public abstract class AbstractDTO<T extends Serializable>
  implements DTO<T>, HasPropertyChangeSupport {
  /** Serial UID */
  private static final long serialVersionUID = -5587338226555597583L;

  /**
   * Suporte a mudança de propriedades
   */
  @Transient
  @JsonIgnore
  protected transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

}
