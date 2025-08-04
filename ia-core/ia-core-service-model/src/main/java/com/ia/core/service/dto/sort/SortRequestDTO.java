package com.ia.core.service.dto.sort;

import com.ia.core.model.filter.SortRequest;
import com.ia.core.service.dto.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Israel Araújo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class SortRequestDTO
  implements DTO<SortRequest> {
  /**
   * Serial UID
   */
  private static final long serialVersionUID = -64115666573379799L;

  /**
   * Nome do campo.
   */
  private String key;

  /**
   * Direção do campo.
   */
  @Default
  private SortDirectionDTO direction = SortDirectionDTO.ASC;

  @Override
  public SortRequestDTO cloneObject() {
    return toBuilder().build();
  }

  @SuppressWarnings("javadoc")
  public static class CAMPOS {
    public static final String PROPRIEDADE = "key";
    public static final String DIRECAO = "direction";
  }
}
