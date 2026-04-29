package com.ia.core.model.attachment;

import com.ia.core.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Classe base abstrata para entidades de anexos/arquivos.
 *
 * <p>Fornece campos comuns para manipulação de arquivos, incluindo
 * descrição, nome do arquivo, tamanho e tipo MIME.
 *
 * <p><b>Campos definidos:</b></p>
 * <ul>
 *   <li>{@link #description} - Descrição do arquivo</li>
 *   <li>{@link #filename} - Nome original do arquivo</li>
 *   <li>{@link #size} - Tamanho do arquivo em bytes</li>
 *   <li>{@link #mediaType} - Tipo MIME do arquivo</li>
 * </ul>
 *
 * <p><b>Por quê usar Attachment?</b></p>
 * <ul>
 *   <li>Padroniza o modelo de dados para arquivos</li>
 *   <li>Evita duplicação de campos em múltiplas entidades de arquivo</li>
 *   <li>Integra com o ciclo de vida do {@link BaseEntity}</li>
 * </ul>
 *
 * @author Israel Araújo
 * @see BaseEntity
 * @since 1.0.0
 */
@SuppressWarnings("serial")
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Attachment
  extends BaseEntity {

  /**
   * Descrição do arquivo.
   *
   * <p>Pode ser usada para fornecer contexto adicional sobre o arquivo.
   */
  @Column(name = "description", length = 500)
  private String description;

  /**
   * Nome original do arquivo.
   *
   * <p>Deve ser preenchido com o nome do arquivo conforme enviado pelo usuário.
   */
  @Column(name = "filename", nullable = false)
  private String filename;

  /**
   * Tamanho do arquivo em bytes.
   *
   * <p>Usado para validação de limites de upload e exibição.
   */
  @Column(name = "size", nullable = false)
  private Long size;

  /**
   * Tipo MIME (Multipurpose Internet Mail Extensions) do arquivo.
   *
   * <p>Exemplos: "image/png", "application/pdf", "text/plain"
   */
  @Column(name = "media_type", nullable = false)
  private String mediaType;

}
