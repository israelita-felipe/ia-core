package com.ia.core.service.dto.entity;

import java.util.Objects;
import java.util.UUID;

import com.ia.core.model.BaseEntity;
import com.ia.core.model.HasVersion;
import com.ia.core.service.dto.AbstractDTO;
import com.ia.core.service.dto.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe base para DTO
 *
 * @author Israel Araújo
 * @param <T> {@link BaseEntity}
 * @see DTO
 */
@Slf4j
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractBaseEntityDTO<T extends BaseEntity>
  extends AbstractDTO<T>
  implements BaseEntityDTO<T>, Comparable<AbstractBaseEntityDTO<T>> {
  /** Serial UID */
  private static final long serialVersionUID = -2328890490324670831L;

  /**
   * Id padrão.
   */
  protected UUID id;
  /**
   * Versão padrão do DTO
   */
  @Default
  protected Long version = HasVersion.DEFAULT_VERSION;

  /**
   * @param id {@link UUID}
   */
  public void setId(UUID id) {
    firePropertyChange(CAMPOS.ID, this.id, id);
    this.id = id;
  }

  /**
   * @param version versão
   */
  @Override
  public void setVersion(Long version) {
    firePropertyChange(CAMPOS.VERSION, this.version, version);
    this.version = version;
  }

  @Override
  public AbstractBaseEntityDTO<T> copyObject() {
    AbstractBaseEntityDTO<T> copyObject = (AbstractBaseEntityDTO<T>) super.copyObject();
    copyObject.setId(null);
    copyObject.setVersion(HasVersion.DEFAULT_VERSION);
    return copyObject;
  }

  @Override
  public int compareTo(AbstractBaseEntityDTO<T> o) {
    if (this.id == null) {
      if (o.id == null) {
        return 0;
      }
      return -1;
    }
    if (o.id == null) {
      return 1;
    }
    return this.id.compareTo(o.id);
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (id == null) {
      return this == obj;
    }
    if (!(getClass().isInstance(obj))) {
      return false;
    }
    AbstractBaseEntityDTO<T> other = (AbstractBaseEntityDTO<T>) obj;
    return Objects.equals(id, other.id);
  }

  @Override
  public int hashCode() {
    if (id != null) {
      return Objects.hash(id);
    }
    return super.hashCode();
  }

  @SuppressWarnings("javadoc")
  public static class CAMPOS {
    public static final String ID = "id";
    public static final String VERSION = "version";
  }

}
