package com.ia.core.owl.service.model;

import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Resultado da análise de consistência e inferências.
 * <p>
 * Representa o resultado de uma análise de consistência de ontologia OWL,
 * contendo informações sobre axiomas inferidos e inconsistências encontradas.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AnaliseInferenciaDTO implements DTO<Serializable> {

  /**
   * Indica se a ontologia está consistente.
   */
  private boolean consistente;

  /**
   * Lista de mensagens de inconsistências encontradas.
   */
  @Builder.Default
  private List<String> inconsistencias = new ArrayList<>();

  /**
   * Lista de axiomas inferidos durante a análise.
   */
  @Builder.Default
  private List<AxiomaDTO> axiomasInferidos = new ArrayList<>();

  /**
   * Número total de inferências realizadas.
   */
  private int totalInferencias;

  /**
   * Retorna o número de axiomas inferidos.
   *
   * @return número de axiomas inferidos
   */
  public int getNumeroAxiomasInferidos() {
    return axiomasInferidos.size();
  }

  /**
   * Verifica se existem inconsistências.
   *
   * @return true se existirem inconsistências
   */
  public boolean temInconsistencias() {
    return !inconsistencias.isEmpty();
  }

  /**
   * Cria uma cópia deste objeto DTO.
   *
   * @return cópia do objeto
   */
  @Override
  public AnaliseInferenciaDTO cloneObject() {
    return this.toBuilder().build();
  }

  /**
   * Cria uma cópia deste objeto DTO com valores limpos.
   *
   * @return cópia do objeto
   */
  @Override
  public AnaliseInferenciaDTO copyObject() {
    return this.toBuilder().build();
  }

  /**
   * Constantes para nomes dos campos deste DTO.
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS {
    public static final String CONSISTENTE = "consistente";
    public static final String INCONSISTENCIAS = "inconsistencias";
    public static final String AXIOMAS_INFERIDOS = "axiomasInferidos";
    public static final String TOTAL_INFERENCIAS = "totalInferencias";

    /**
     * Retorna todos os nomes de campos deste DTO.
     *
     * @return conjunto de strings com os nomes dos campos
     */
    public static java.util.Set<String> values() {
      return java.util.Set.of(CONSISTENTE, INCONSISTENCIAS, AXIOMAS_INFERIDOS, TOTAL_INFERENCIAS);
    }
  }
}