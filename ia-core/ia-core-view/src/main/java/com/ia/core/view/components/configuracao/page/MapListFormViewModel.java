package com.ia.core.view.components.configuracao.page;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * View model genérico para mapas com valores do tipo List.
 * <p>
 * Esta classe estende MapFormViewModel, especializando V para List<V>.
 * Fornece métodos convenientes para manipulação de listas como valores do mapa.
 *
 * <p><strong>Importante sobre Binding:</strong> O Vaadin Binder não suporta binding direto
 * com Map via reflection (os campos são dinâmicos). O binding deve ser feito manualmente
 * usando ValueChangeEvent listeners nos componentes, como demonstrado em ConfigurationTabView.
 *
 * @param <K> Tipo da chave do mapa
 * @param <V> Tipo dos elementos da lista
 * @author Israel Araújo
 * @since 1.0.0
 */
public abstract class MapListFormViewModel<K, V> extends MapFormViewModel<K, List<V>>
    implements Serializable {

    private static final long serialVersionUID = -1234567890123456784L;


    /**
     * Cria um novo MapListFormViewModel.
     *
     * @param config configuração do view model
     */
    public MapListFormViewModel(MapListFormViewModelConfig<K, V> config) {
        super(config);
    }


    /**
     * Retorna todos os valores da lista associada a uma chave.
     */
    public List<V> getList(K key) {
        return getModel().containsKey(key) ? getModel().get(key) : Collections.emptyList();
    }

    /**
     * Retorna o primeiro valor da lista associada a uma chave.
     *
     * @return primeiro valor da lista ou null se vazia
     */
    public V getFirstValue(K key) {
        List<V> list = getList(key);
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * Adiciona um valor à lista associada a uma chave.
     */
    public void addToList(K key, V value) {
        getModel().computeIfAbsent(key, k -> new java.util.ArrayList<>()).add(value);
    }

    /**
     * Remove um valor da lista associada a uma chave.
     */
    public boolean removeFromList(K key, V value) {
        if (getModel().containsKey(key)) {
            return getModel().get(key).remove(value);
        }
        return false;
    }

    /**
     * Retorna a configuração.
     */
    public MapListFormViewModelConfig<K, V> getConfig() {
        return super.getConfig().cast();
    }

    @Override
    public String getModelPrefix() {
        return "model";
    }
}
