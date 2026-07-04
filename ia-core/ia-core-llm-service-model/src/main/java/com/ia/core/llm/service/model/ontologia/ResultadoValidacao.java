package com.ia.core.llm.service.model.ontologia;

import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO para resultado de validação de ontologia ou axioma.
 * <p>
 * Contém informações sobre consistência, inconsistências detectadas e explicações.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoValidacao implements DTO<String> {

  @Override
  public DTO<String> cloneObject() {
    return toBuilder()
        .classesInsatisfativeis(new ArrayList<>(classesInsatisfativeis))
        .axiomasConflitantes(new ArrayList<>(axiomasConflitantes))
        .sugestoes(new ArrayList<>(sugestoes))
        .build();
  }

  /**
   * Indica se a ontologia/axioma é consistente.
   */
  private boolean consistente;

  /**
   * Lista de classes insatisfatíveis detectadas.
   */
  @Builder.Default
  private List<String> classesInsatisfativeis = new ArrayList<>();

  /**
   * Lista de axiomas que causam inconsistência.
   */
  @Builder.Default
  private List<String> axiomasConflitantes = new ArrayList<>();

  /**
   * Explicação em linguagem natural do resultado.
   */
  private String explicacao;

  /**
   * Sugestões de correção para inconsistências.
   */
  @Builder.Default
  private List<String> sugestoes = new ArrayList<>();

  /**
   * Número de iterações usadas na validação (para loops LLM-Reasoner).
   */
  private int iteracoesUsadas;

  /**
   * Tempo de processamento em milissegundos.
   */
  private long tempoProcessamentoMs;
}
