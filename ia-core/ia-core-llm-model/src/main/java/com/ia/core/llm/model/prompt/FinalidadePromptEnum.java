package com.ia.core.llm.model.prompt;

import org.springframework.core.ParameterizedTypeReference;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Enumeração que define a finalidade de um {@link Prompt} de catálogo.
 * <p>
 * Define o tipo de resposta esperado do modelo de linguagem: resposta textual,
 * extração de objeto ou extração de lista.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public enum FinalidadePromptEnum {

  RESPOSTA_TEXTUAL(1, null),
  EXTRAIR_OBJETO(2, new ParameterizedTypeReference<HashMap>() {}),
  EXTRAIR_LISTA(3, new ParameterizedTypeReference<ArrayList>() {});

  private final Integer codigo;
  private final ParameterizedTypeReference<?> returnType;

  FinalidadePromptEnum(Integer codigo, ParameterizedTypeReference<?> returnType) {
    this.codigo = codigo;
    this.returnType = returnType;
  }

  public Integer getCodigo() {
    return codigo;
  }

  public ParameterizedTypeReference<?> getReturnType() {
    return returnType;
  }
}
