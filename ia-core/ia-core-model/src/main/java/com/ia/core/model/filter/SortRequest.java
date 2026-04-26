package com.ia.core.model.filter;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * Classe de requisição de ordenação para buscas.
 *
 * <p>Define o campo e a direção (ascendente ou descendente) para ordenação
 * dos resultados de uma consulta.
 *
 * <p><b>Exemplo de uso:</b></p>
 * {@code
 * SortRequest ordenacao = SortRequest.builder()
 *     .key("nome")
 *     .direction(SortDirection.DESC)
 *     .build();
 * }
 *
 * @author Israel Araújo
 * @see SearchRequest
 * @see SortDirection
 * @since 1.0.0
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
   * Identificador de versão para serialização.
   */
  private static final long serialVersionUID = 3194362295851723069L;

  /**
   * Nome do campo pela qual a ordenação será aplicada.
   *
   * <p>Pode ser um atributo simples (ex: "nome", "dataCriacao") ou um caminho
   * composto separado por ponto (ex: "endereco.cidade.nome").
   */
  private String key;

  /**
   * Direção da ordenação (ascendente ou descendente).
   *
   * @see SortDirection
   */
  @Default
  private SortDirection direction = SortDirection.ASC;

}
