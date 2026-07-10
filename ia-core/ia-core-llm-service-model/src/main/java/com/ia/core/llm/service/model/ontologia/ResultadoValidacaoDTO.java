package com.ia.core.llm.service.model.ontologia;

import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
public class ResultadoValidacaoDTO implements DTO<String> {

  /** Serial UID */
  private static final long serialVersionUID = 174920123456789123L;

  @Override
  public ResultadoValidacaoDTO cloneObject() {
    return toBuilder()
        .classesInsatisfativeis(new ArrayList<>(classesInsatisfativeis))
        .axiomasConflitantes(new ArrayList<>(axiomasConflitantes))
        .sugestoes(new ArrayList<>(sugestoes))
        .build();
  }

  /**
   * Retorna uma representação em string deste objeto.
   *
   * @return string contendo o status de consistência
   */
  @Override
  public String toString() {
    return String.format("ResultadoValidacaoDTO{consistente=%s}", consistente);
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

  public static class CAMPOS {
    public static final String CONSISTENTE = "consistente";
    public static final String CLASSES_INSATISFATIVEIS = "classesInsatisfativeis";
    public static final String AXIOMAS_CONFLITANTES = "axiomasConflitantes";
    public static final String EXPLICACAO = "explicacao";
    public static final String SUGESTOES = "sugestoes";
    public static final String ITERACOES_USADAS = "iteracoesUsadas";
    public static final String TEMPO_PROCESSAMENTO_MS = "tempoProcessamentoMs";

    public static Set<String> values() {
      return Collections.unmodifiableSet(Set.of(CONSISTENTE, CLASSES_INSATISFATIVEIS, AXIOMAS_CONFLITANTES,
          EXPLICACAO, SUGESTOES, ITERACOES_USADAS, TEMPO_PROCESSAMENTO_MS));
    }
  }
}