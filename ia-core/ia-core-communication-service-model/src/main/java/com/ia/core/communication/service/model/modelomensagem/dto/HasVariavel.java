package com.ia.core.communication.service.model.modelomensagem.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Interface que define métodos para obter variáveis de um DTO.
 * <p>
 * Usada para padronizar a extração de variáveis de diferentes objetos DTO,
 * permitindo a substituição de placeholders em templates de mensagem.
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface HasVariavel {

    /**
     * Retorna um mapa com as variáveis disponíveis neste DTO.
     * <p>
     * A chave é a variável (enum {@link Variavel}) e o valor é o campo correspondente.
     * Este mapa é usado pelo {@link ProcessadorVariaveis} para substituição.
     * </p>
     *
     * @return mapa imutável contendo variáveis e seus valores
     */
    Map<Variavel, Object> getContext();

    /**
     * Retorna uma lista com as variáveis disponíveis neste DTO.
     *
     * @return lista não modificável de variáveis (nunca null)
     */
    default List<Variavel> getVariaveis() {
        return Collections.unmodifiableList(new ArrayList<>(getContext().keySet()));
    }

    /**
     * Verifica se o DTO contém uma variável com o identificador especificado.
     *
     * @param variavel a variável a ser verificada (não pode ser null)
     * @return {@code true} se contém a variável, {@code false} caso contrário
     */
    default boolean contemVariavel(Variavel variavel) {
        return getContext().containsKey(variavel);
    }

    /**
     * Cria uma cópia superficial (clone) deste objeto.
     *
     * @return novo objeto com os mesmos valores
     */
    default HasVariavel cloneObject() {
        return this;
    }
}