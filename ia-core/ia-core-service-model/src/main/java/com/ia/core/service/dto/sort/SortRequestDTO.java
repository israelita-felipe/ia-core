package com.ia.core.service.dto.sort;

import com.ia.core.model.filter.SortRequest;
import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * DTO para requisição de ordenação.
 * <p>
 * Define os critérios de ordenação para consultas, especificando o campo
 * e a direção (ascendente ou descendente).
 * </p>
 *
 * @author Israel Araújo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class SortRequestDTO
  implements DTO<SortRequest> {
  /**
   * Serial UID para serialização
   */
  private static final long serialVersionUID = -64115666573379799L;

  /**
   * Nome do campo para ordenação
   */
  private String key;

  /**
   * Direção da ordenação (ascendente ou descendente)
   */
  @Default
  private SortDirectionDTO direction = SortDirectionDTO.ASC;

  /**
   * Cria uma cópia deste DTO.
   *
   * @return Nova instância de SortRequestDTO com os mesmos valores
   */
  @Override
  public SortRequestDTO cloneObject() {
    return toBuilder().build();
  }

  /**
   * Constantes para nomes de campos utilizados em serialização/mapping.
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS {
    public static final String PROPRIEDADE = "key";
    public static final String DIRECAO = "direction";
  }
}
