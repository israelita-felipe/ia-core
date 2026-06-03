package com.ia.core.llm.service.model.ferramenta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO para requisição de execução de ferramenta OWL 2 DL.
 * <p>
 * Contém a descrição em linguagem natural e contexto ontológico.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequisicaoFerramenta {

  /**
   * Descrição em linguagem natural para converter em axioma OWL.
   */
  @NotBlank(message = "A descrição é obrigatória")
  private String descricaoNatureza;

  /**
   * Contexto ontológico atual (ontologia em Manchester Syntax).
   */
  private String contextoOntologia;

  /**
   * Construtor OWL específico a usar (opcional, se não especificado o agente decide).
   */
  @Size(max = 50)
  private String construtor;

  /**
   * Parâmetros adicionais específicos da ferramenta.
   */
  private Map<String, Object> parametrosAdicionais;

  /**
   * Constantes dos campos do DTO para uso type-safe.
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS {
    public static final String DESCRICAO_NATUREZA = "descricaoNatureza";
    public static final String CONTEXTO_ONTOLOGIA = "contextoOntologia";
    public static final String CONSTRUTOR = "construtor";
    public static final String PARAMETROS_ADICIONAIS = "parametrosAdicionais";

    public static java.util.Set<String> values() {
      return java.util.Set.of(
          DESCRICAO_NATUREZA, CONTEXTO_ONTOLOGIA, CONSTRUTOR, PARAMETROS_ADICIONAIS
      );
    }
  }
}
