package com.ia.core.view.components.configuracao.page;

import com.ia.core.view.components.form.viewModel.FormViewModel;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * View model genérico para mapas simples.
 * <p>
 * Esta classe fornece uma base para views baseadas em mapas onde os valores são objetos simples.
 * Herda de FormViewModel para compatibilidade com FormView.
 *
 * <p><strong>Importante sobre Binding:</strong> O Vaadin Binder não suporta binding direto
 * com Map via reflection (os campos são dinâmicos). O binding deve ser feito manualmente
 * usando ValueChangeEvent listeners nos componentes, ou usando DTOs individualmente.
 *
 * @param <K> Tipo da chave do mapa
 * @param <V> Tipo do valor do mapa
 * @author Israel Araújo
 * @since 1.0.0
 */
public abstract class MapFormViewModel<K, V> extends FormViewModel<TreeMap<K, V>>
    implements Serializable {

    private static final long serialVersionUID = -1234567890123456775L;


    /**
     * Cria um novo MapFormViewModel.
     *
     * @param config configuração do view model
     */
    public MapFormViewModel(MapFormViewModelConfig<K, V> config) {
        super(config);
        if (config.getInitialMap() != null) {
            setModel(config.getInitialMap());
        } else {
            setModel(new TreeMap<>());
        }
    }

    /**
     * Retorna o valor associado a uma chave específica.
     */
    public V getValue(K key) {
        return getModel().get(key);
    }

    /**
     * Define o valor associado a uma chave específica.
     */
    public void setValue(K key, V value) {
        getModel().put(key, value);
    }

    /**
     * Remove uma entrada do mapa.
     */
    public V remove(K key) {
        return getModel().remove(key);
    }

    /**
     * Verifica se o mapa contém uma chave.
     */
    public boolean containsKey(K key) {
        return getModel().containsKey(key);
    }

    /**
     * Retorna o número de entradas no mapa.
     */
    public int size() {
        return getModel().size();
    }

    /**
     * Retorna a configuração.
     */
    public MapFormViewModelConfig<K, V> getConfig() {
        return super.getConfig().cast();
    }
}
