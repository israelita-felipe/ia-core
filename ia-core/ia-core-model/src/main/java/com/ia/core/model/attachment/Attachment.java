package com.ia.core.model.attachment;

import com.ia.core.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author Israel Araújo
 */
@SuppressWarnings("serial")
@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Attachment
  extends BaseEntity {
  /**
   * Descrição
   */
  @Column(name = "description", length = 500)
  private String description;

  /**
   * Nome do arquivo.
   */
  @Column(name = "filename", nullable = false)
  private String filename;

  /**
   * Tamanho do arquivo em <i>bytes</i>.
   */
  @Column(name = "size", nullable = false)
  private Long size;

  /**
   * É o MIME Type do arquivo
   */
  @Column(name = "media_type", nullable = false)
  private String mediaType;

}
