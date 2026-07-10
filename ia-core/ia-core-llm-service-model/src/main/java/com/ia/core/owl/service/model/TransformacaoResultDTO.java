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
 * Resultado de uma operação de transformação de ontologia OWL.
 * <p>
 * Representa o resultado de uma operação de transformação, contendo
 * axiomas gerados, inconsistências encontradas e status da operação.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TransformacaoResultDTO implements DTO<Serializable> {

  /**
   * Lista de axiomas gerados.
   */
  @Builder.Default
  private List<AxiomaDTO> axiomas = new ArrayList<>();

  /**
   * Lista de inconsistências encontradas.
   */
  @Builder.Default
  private List<String> inconsistencias = new ArrayList<>();

  /**
   * Indica se a transformação foi bem-sucedida.
   */
  private boolean sucesso;

  /**
   * Mensagem descritiva do resultado.
   */
  private String mensagem;

  /**
   * Resultado da análise de consistência e inferências.
   */
  private AnaliseInferenciaDTO analise;

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

  /**
   * Cria uma cópia deste objeto DTO.
   *
   * @return cópia do objeto
   */
  @Override
  public TransformacaoResultDTO cloneObject() {
    return this.toBuilder().build();
  }

  /**
   * Cria uma cópia deste objeto DTO com valores limpos.
   *
   * @return cópia do objeto
   */
  @Override
  public TransformacaoResultDTO copyObject() {
    return this.toBuilder().build();
  }

  /**
   * Constantes para nomes dos campos deste DTO.
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS {
    public static final String AXIOMAS = "axiomas";
    public static final String INCONSISTENCIAS = "inconsistencias";
    public static final String SUCESSO = "sucesso";
    public static final String MENSAGEM = "mensagem";
    public static final String ANALISE = "analise";

    /**
     * Retorna todos os nomes de campos deste DTO.
     *
     * @return conjunto de strings com os nomes dos campos
     */
    public static java.util.Set<String> values() {
      return java.util.Set.of(AXIOMAS, INCONSISTENCIAS, SUCESSO, MENSAGEM, ANALISE);
    }
  }
}