package com.ia.core.model.filter;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Classe de requisição de sorting.
 *
 * @author Israel Araújo
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
// @JsonIgnoreProperties(ignoreUnknown = true)
// @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SortRequest
  implements Serializable {
  /**
   * Serial UID.
   */
  private static final long serialVersionUID = 3194362295851723069L;

  /**
   * Nome do campo.
   */
  private String key;

  /**
   * Direção do campo.
   */
  @Default
  private SortDirection direction = SortDirection.ASC;

}
