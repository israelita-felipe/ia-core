package com.ia.core.communication.service.model.modelomensagem.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Interface que define métodos para obter variáveis de um DTO.
 * Usada para padronizar a extração de variáveis de diferentes objetos DTO.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface HasVariavel {

    /**
     * Retorna um mapa com as variáveis disponíveis neste DTO.
     * A chave é o nome da variável (ex: "telefone") e o valor é o valor do campo.
     *
     * @return mapa de variáveis
     */
    Map<Variavel, Object> getContext();

    /**
     * Retorna uma lista com os nomes das variáveis disponíveis.
     *
     * @return lista de nomes de variáveis
     */
    default List<Variavel> getVariaveis() {
        return new ArrayList<>(getContext().keySet());
    }

    /**
     * Verifica se o DTO contém uma variável com o variavel especificado.
     *
     * @param variavel variavel da variável
     * @return true se contém, false caso contrário
     */
    default boolean contemVariavel(Variavel variavel) {
        return getContext().containsKey(variavel);
    }
}
