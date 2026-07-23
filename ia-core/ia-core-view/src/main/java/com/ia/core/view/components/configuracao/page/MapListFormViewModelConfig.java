package com.ia.core.view.components.configuracao.page;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;

/**
 * Configuração para MapListFormViewModel.
 * <p>
 * Esta classe fornece configuração específica para view models baseados em TreeMap
 * onde os valores são listas. Estende MapFormViewModelConfig com V = List<V>.
 *
 * @param <K> Tipo da chave do mapa
 * @param <V> Tipo dos elementos da lista
 * @author Israel Araújo
 * @since 1.0.0
 */
public class MapListFormViewModelConfig<K, V> extends MapFormViewModelConfig<K, List<V>>
    implements Serializable {

    private static final long serialVersionUID = -1234567890123456773L;

    /**
     * Cria uma nova configuração.
     *
     * @param readOnly indicativo de somente leitura
     */
    public MapListFormViewModelConfig(boolean readOnly) {
        super(readOnly);
    }

    /**
     * Cria uma nova configuração com mapa inicial.
     *
     * @param readOnly   indicativo de somente leitura
     * @param initialMap mapa inicial
     */
    public MapListFormViewModelConfig(boolean readOnly, TreeMap<K, List<V>> initialMap) {
        super(readOnly, initialMap);
    }

    /**
     * Retorna o mapa inicial com tipo específico.
     */
    @SuppressWarnings("unchecked")
    public TreeMap<K, List<V>> getListInitialMap() {
        return (TreeMap<K, List<V>>) getInitialMap();
    }
}