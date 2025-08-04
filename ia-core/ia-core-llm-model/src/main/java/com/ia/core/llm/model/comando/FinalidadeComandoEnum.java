package com.ia.core.llm.model.comando;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.core.ParameterizedTypeReference;

/**
 * Finalidade de um comando
 *
 * @author Israel Araújo
 */
public enum FinalidadeComandoEnum {

  /** Resposta textual */
  RESPOSTA_TEXTUAL(1, null),
  /** Extrai um objeto */
  EXTRAIR_OBJETO(2, new ParameterizedTypeReference<HashMap>() {
  }),
  /** Extrai uma lista */
  EXTRAIR_LISTA(3, new ParameterizedTypeReference<ArrayList>() {
  });

  /** Código da finalidade */
  private final Integer codigo;
  /** Tipo de retorno */
  private final ParameterizedTypeReference<?> returnType;

  /**
   * @param codigo     Código da finalidade
   * @param returnType Tipo de retorno da finalidade
   */
  private FinalidadeComandoEnum(Integer codigo,
                                ParameterizedTypeReference<?> returnType) {
    this.codigo = codigo;
    this.returnType = returnType;
  }

  /**
   * @return {@link #codigo}
   */
  public Integer getCodigo() {
    return codigo;
  }

  /**
   * @return {@link #returnType}
   */
  public ParameterizedTypeReference<?> getReturnType() {
    return returnType;
  }

}
