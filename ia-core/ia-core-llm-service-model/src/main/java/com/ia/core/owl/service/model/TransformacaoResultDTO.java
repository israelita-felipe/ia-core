package com.ia.core.owl.service.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.ia.core.owl.service.model.axioma.AxiomaDTO;

/**
 * Record que representa o resultado de uma operação de transformação.
 *
 * @param axiomas         a lista de axiomas gerados (nunca nula)
 * @param inconsistencias a lista de inconsistências encontradas (nunca nula)
 * @param sucesso         indica se a transformação foi bem-sucedida
 * @param mensagem        mensagem descritiva do resultado
 * @param analise         resultado da análise de consistência e inferências
 */
public record TransformacaoResultDTO(List<AxiomaDTO> axiomas,
                                     List<String> inconsistencias,
                                     boolean sucesso, String mensagem,
                                     AnaliseInferenciaDTO analise) {
  /**
   * Constrói um resultado de transformação com validação de parâmetros.
   *
   * @param axiomas         lista de axiomas
   * @param inconsistencias lista de inconsistências
   * @param sucesso         status de sucesso
   * @param mensagem        mensagem descritiva
   */
  public TransformacaoResultDTO {
    if (axiomas == null) {
      axiomas = Collections.emptyList();
    } else {
      axiomas = List.copyOf(axiomas); // Retorna lista imutável
    }

    if (inconsistencias == null) {
      inconsistencias = Collections.emptyList();
    } else {
      inconsistencias = List.copyOf(inconsistencias); // Retorna lista
                                                      // imutável
    }

    Objects.requireNonNull(mensagem, "Mensagem não pode ser nula");
  }

  /**
   * Retorna o número de axiomas gerados.
   *
   * @return o número de axiomas
   */
  public int getNumeroAxiomas() {
    return axiomas.size();
  }

  /**
   * Verifica se existem inconsistências no resultado.
   *
   * @return true se existirem inconsistências, false caso contrário
   */
  public boolean temInconsistencias() {
    return !inconsistencias.isEmpty();
  }

  /**
   * Retorna uma representação resumida do resultado.
   *
   * @return string resumida do resultado
   */
  public String getResumo() {
    return String
        .format("Transformação %s - %d axiomas gerados, %d inconsistências",
                sucesso ? "bem-sucedida" : "falhou", getNumeroAxiomas(),
                inconsistencias.size());
  }
}
