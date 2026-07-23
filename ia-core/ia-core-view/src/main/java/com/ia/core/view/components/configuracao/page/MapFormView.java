package com.ia.core.view.components.configuracao.page;

import com.ia.core.view.components.form.FormView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * View genérica para formulários baseados em mapas simples.
 * <p>
 * Fornece funcionalidade básica para exibição e edição de mapas,
 * delegando a criação dos campos para as subclasses.
 * <p>
 * <strong>Binding:</strong> Os campos devem ser atualizados manualmente
 * pois Map não tem propriedades fixas para reflexão.
 *
 * @param <K> Tipo da chave do mapa
 * @param <V> Tipo do valor do mapa
 * @author Israel Araújo
 * @since 1.0.0
 */
public abstract class MapFormView<K, V> extends FormView<TreeMap<K, V>>
    implements Serializable {

    private static final long serialVersionUID = -1234567890123456776L;

    /**
     * Cria uma nova MapFormView.
     *
     * @param viewModel view model para mapas
     */
    public MapFormView(MapFormViewModel<K, V> viewModel) {
        super(viewModel);
        setSizeFull();
    }

    @Override
    protected List<FormLayout.ResponsiveStep> createResponsiveSteps() {
        return Arrays.asList(step(ScreenSize.XSM, xsm()));
    }

    /**
     * Cria o layout com os campos do mapa.
     * Implementação padrão itera sobre as chaves do mapa.
     */
    @Override
    public void createLayout() {
        getViewModel().getModel().forEach((key, value) -> {
            Component field = createFieldForKey(key, value);
            if (field != null) {
                add(field);
            }
        });
    }

    /**
     * Cria um campo para uma chave/valor específica do mapa.
     *
     * @param key   chave do mapa
     * @param value valor associado
     * @return componente Vaadin para o campo
     */
    protected abstract Component createFieldForKey(K key, V value);

}
