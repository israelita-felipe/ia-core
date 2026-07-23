package com.ia.core.view.components.configuracao.page;

import com.ia.core.view.components.form.viewModel.FormViewModelConfig;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * Configuração para MapFormViewModel.
 * <p>
 * Esta classe fornece configuração específica para view models baseados em TreeMap.
 * Herda de FormViewModelConfig para compatibilidade com o padrão existente.
 *
 * @param <K> Tipo da chave do mapa
 * @param <V> Tipo do valor do mapa
 * @author Israel Araújo
 * @since 1.0.0
 */
public class MapFormViewModelConfig<K, V> extends FormViewModelConfig<TreeMap<K, V>>
    implements Serializable {

    private static final long serialVersionUID = -1234567890123456774L;

    /**
     * Mapa inicial (opcional).
     */
    private TreeMap<K, V> initialMap;

    /**
     * Cria uma nova configuração.
     *
     * @param readOnly indicativo de somente leitura
     */
    public MapFormViewModelConfig(boolean readOnly) {
        super(readOnly);
    }

    /**
     * Cria uma nova configuração com mapa inicial.
     *
     * @param readOnly   indicativo de somente leitura
     * @param initialMap mapa inicial
     */
    public MapFormViewModelConfig(boolean readOnly, TreeMap<K, V> initialMap) {
        super(readOnly);
        this.initialMap = initialMap;
    }

    /**
     * @return mapa inicial ou null
     */
    public TreeMap<K, V> getInitialMap() {
        return initialMap;
    }

    /**
     * Define o mapa inicial.
     *
     * @param initialMap mapa inicial
     */
    public void setInitialMap(TreeMap<K, V> initialMap) {
        this.initialMap = initialMap;
    }

    /**
     * Cria uma configuração padrão.
     */
    public static <K, V> MapFormViewModelConfig<K, V> create(boolean readOnly) {
        return new MapFormViewModelConfig<>(readOnly);
    }

    /**
     * Cria uma configuração com mapa inicial.
     */
    public static <K, V> MapFormViewModelConfig<K, V> create(boolean readOnly, TreeMap<K, V> map) {
        return new MapFormViewModelConfig<>(readOnly, map);
    }
}