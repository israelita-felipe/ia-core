package com.ia.core.view.components.configuracao.page;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;

import java.io.Serializable;
import java.util.List;

/**
 * View genérica para formulários baseados em mapas onde os valores são Listas.
 * <p>
 * Estende MapFormView especializando V para List<V>, delegando a criação dos campos
 * para as subclasses via createFieldForItem.
 *
 * <p><strong>Binding:</strong> Os campos devem ser atualizados manualmente
 * pois Map não tem propriedades fixas para reflexão.
 *
 * @param <K> Tipo da chave do mapa
 * @param <V> Tipo dos elementos da lista
 * @author Israel Araújo
 * @since 1.0.0
 */
public abstract class MapListFormView<K, V> extends MapFormView<K, List<V>>
    implements Serializable {

    private static final long serialVersionUID = -1234567890123456775L;

    /**
     * Cria uma nova MapListFormView.
     *
     * @param viewModel view model para mapas de lista
     */
    protected MapListFormView(MapListFormViewModel<K, V> viewModel) {
        super(viewModel);
    }

    /**
     * Cria o layout para uma chave do mapa, iterando sobre os valores da lista.
     * Chama createFieldForItem para cada item da lista.
     *
     * @param key   chave do mapa
     * @param value lista de valores associada à chave
     * @return FormLayout contendo os campos para todos os itens da lista
     */
    @Override
    protected Component createFieldForKey(K key, List<V> value) {
        FormLayout formLayout = createFormLayoutForKey(key);
        value.forEach(item -> {
            Component field = createFieldForItem(key, item);
            if (field != null) {
                formLayout.add(field);
            }
        });
        return formLayout;
    }

    /**
     * Cria um FormLayout para uma chave do mapa.
     * Pode ser sobrescrito para customizar (ex: configurar responsive steps).
     *
     * @param key chave do mapa
     * @return FormLayout configurado
     */
    protected FormLayout createFormLayoutForKey(K key) {
        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        return formLayout;
    }

    /**
     * Cria um campo para um item específico da lista.
     * Deve ser implementado pelas subclasses.
     *
     * @param key   chave do mapa
     * @param value item da lista
     * @return componente Vaadin para o campo
     */
    protected abstract Component createFieldForItem(K key, V value);

    /**
     * Retorna o view model tipado.
     */
    public MapListFormViewModel<K, V> getListViewModel() {
        return (MapListFormViewModel<K, V>) getViewModel();
    }
}
