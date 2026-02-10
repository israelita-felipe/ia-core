package com.ia.core.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Classe base da entidade do banco de dados. Já implementa {@link Comparable} e
 * possui um Id do tipo {@link UUID}.
 *
 * @author Israel Araújo
 */
@SuppressWarnings("serial")
@MappedSuperclass
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity
  implements Serializable, HasVersion, Comparable<BaseEntity> {

  /**
   * Id da classe de entidade.
   */
  @Id
  private Long id;

  /** Versão para lock otimista */
  @Default
  @Version
  @Column(name = "version", columnDefinition = "bigint default 1",
          nullable = false)
  private Long version = HasVersion.DEFAULT_VERSION;

  /**
   * Ao criar gera o ID
   */
  @PrePersist
  public void onCreate() {
    if (this.id == null) {
      this.id = TSID.Factory.getTsid4096().toLong();
    }
  }

  @Override
  public int compareTo(BaseEntity o) {
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
    BaseEntity other = (BaseEntity) obj;
    return Objects.equals(id, other.id);
  }

  @Override
  public int hashCode() {
    if (id != null) {
      return Objects.hash(id);
    }
    return super.hashCode();
  }
}
