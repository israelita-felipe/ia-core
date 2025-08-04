package com.ia.core.service.attachment.dto;

import java.util.Set;

import com.ia.core.model.attachment.Attachment;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Israel Araújo
 * @param <T> tipo do anexo
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AttachmentDTO<T extends Attachment>
  extends AbstractBaseEntityDTO<T> {
  /** Serial UID */
  private static final long serialVersionUID = -603284728960194977L;
  /** Chave de encriptação */
  public static final String PRIVATE_KEY = /** RSA 1024 */
      "MIICXQIBAAKBgQCbksa85gy9S4ec+vdy8X2I1dF91TyYk7Jfzq71A65OPujVhxRk\n"
          + "Jgb9ndXlkOhrg7J6JBwthBm1zhO5FdI/7k4H5L0aXEhbWDCNH/hYeuNHWsdhgzAr\n"
          + "huc9KDrOREODHb+LEBivaWoHjgMEfAIuzu+lldNbp6sGsPuOefHShI7EbQIDAQAB\n"
          + "AoGAUyhOsPQrBRgVYjTIBhnNZ1Ibi65qmIgCRbH91RvllQ+Nd22f2bR18iTnC7G8\n"
          + "epPH1//rS+0brRF9e2aNnvyrmjhSDxLJql0sgRYQby1pZWDgF6HxkP21HEgmgj1r\n"
          + "QVjvweWkowzNyGztzqDOzgejRJwlXpVU+IlzT7TQCzt+WqECQQDaekenVRb2t0tg\n"
          + "JLis8cvqS7IpXp7tz064Hx5vg3qH/49stKYa8ZOw1O5zN17vvx5bpRzL6L7Hh9Zw\n"
          + "bzY9gJ3VAkEAtkrOwSJhJT5eWvIgEoP/gRNkNi8nzKJ+sxX2Q6g9sl3XGoLzXYEA\n"
          + "RWcAp40in01vpjRzrJcZmWdCB5jtne0gOQJBANZmjY6+yAxRkBzHrJu7pftFUY+b\n"
          + "laeWe4/gqgORKVvxBZUOKrviOqecLmzO99Ga4nXZMMtCmoZA3cX0vWvZngkCQQCm\n"
          + "uKZ+J1ZX/MHJRfclWAo/4B9gGyK4WsmsOuLfoCjFUGQiQDAajZhU4U7M7x0CO41o\n"
          + "okJFUHC+R50xW6G/NZepAkA0tUNQ0V9Q9g7r59/5L8qZ2xz9J9SzrYSNqeO1tlXy\n"
          + "qyeZ1Z02qi7AS7REAnIagTYHrYt1Ym5m0OzH9HlKROMm";
  /** Chave de decriptação pública */
  public static final String PUBLIC_KEY = /** RSA 1024 */
      "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbksa85gy9S4ec+vdy8X2I1dF9\n"
          + "1TyYk7Jfzq71A65OPujVhxRkJgb9ndXlkOhrg7J6JBwthBm1zhO5FdI/7k4H5L0a\n"
          + "XEhbWDCNH/hYeuNHWsdhgzArhuc9KDrOREODHb+LEBivaWoHjgMEfAIuzu+lldNb\n"
          + "p6sGsPuOefHShI7EbQIDAQAB";

  /**
   * @return {@link SearchRequestDTO} de busca
   */
  public static final SearchRequestDTO getSearchRequest() {
    return new AttachmentSearchRequest();
  }

  /**
   * @return Propriedades filtráveis
   */
  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  /**
   * Descrição
   */
  private String description;

  /**
   * Nome do arquivo.
   */
  @NotNull(message = AttachmentTranslator.VALIDATION.FILE_NAME_NOT_NULL)
  @NotEmpty(message = AttachmentTranslator.VALIDATION.FILE_NAME_NOT_NULL)
  private String filename;

  /**
   * Tamanho do arquivo em <i>bytes</i>.
   */
  @NotNull(message = AttachmentTranslator.VALIDATION.SIZE_NOT_NULL)
  private Long size;

  /**
   * É o MIME Type do arquivo
   */
  @NotNull(message = AttachmentTranslator.VALIDATION.MEDIA_TYPE_NOT_NULL)
  @NotEmpty(message = AttachmentTranslator.VALIDATION.MEDIA_TYPE_NOT_NULL)
  private String mediaType;

  /** Conteúdo base64 */
  @Transient
  private String content;

  @Override
  public AttachmentDTO<T> cloneObject() {
    return toBuilder().build();
  }

  /**
   * @return <code>true</code> se houver conteúdo no anexo
   */
  public boolean hasContent() {
    return content != null;
  }

  /**
   * @return <code>true</code> Se for vazio
   */
  public boolean isEmpty() {
    return size == null;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("AttachmentDTO [");
    if (filename != null) {
      builder.append("filename=");
      builder.append(filename);
      builder.append(", ");
    }
    if (mediaType != null) {
      builder.append("mediaType=");
      builder.append(mediaType);
      builder.append(", ");
    }
    if (size != null) {
      builder.append("size=");
      builder.append(size);
      builder.append(", ");
    }
    if (content != null) {
      builder.append("content=");
      builder.append(content);
    }
    builder.append("]");
    return builder.toString();
  }

  @SuppressWarnings("javadoc")
  public static class CAMPOS
    extends AbstractBaseEntityDTO.CAMPOS {
    public static final String DESCRIPTION = "description";
    public static final String FILE_NAME = "filename";
    public static final String CONTENT = "content";
    public static final String SIZE = "size";
    public static final String MEDIA_TYPE = "mediaType";
  }
}
