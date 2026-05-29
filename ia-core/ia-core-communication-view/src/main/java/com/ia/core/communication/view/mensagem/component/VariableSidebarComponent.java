package com.ia.core.communication.view.mensagem.component;

import com.ia.core.communication.service.model.modelomensagem.dto.Variavel;
import com.vaadin.flow.component.badge.Badge;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexLayout;

import java.util.List;
import java.util.function.Supplier;

/**
 * Componente de sidebar que exibe as variáveis disponíveis para edição de templates.
 * <p>
 * Permite que o usuário selecione variáveis para inserir no template de mensagem.
 * Exibe badges clicáveis para cada variável disponível.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class VariableSidebarComponent extends FlexLayout {

    private final Supplier<List<Variavel>> variaveisSupplier;
    private VariableInsertListener insertListener;

    /**
     * Construtor que aceita um supplier genérico de variáveis
     *
     * @param variaveisSupplier função que fornece a lista de variáveis disponíveis
     */
    public VariableSidebarComponent(Supplier<List<Variavel>> variaveisSupplier) {
        this.variaveisSupplier = variaveisSupplier;
        initComponent();
    }

    private void initComponent() {
        // Carregar variáveis
        loadVariables();
        setFlexWrap(FlexWrap.WRAP);
        setAlignContent(ContentAlignment.STRETCH);
        setAlignItems(Alignment.STRETCH);
    }

    private void loadVariables() {
        removeAll();

        List<Variavel> variables = variaveisSupplier.get();

        for (Variavel variable : variables) {
            Div variableBadgeContainer = createVariableButton(variable);
            add(variableBadgeContainer);
        }
    }

    private Div createVariableButton(Variavel variable) {
        // Criar container clicável para o badge
        Div container = new Div();

        // Criar badge com apenas a chave da variável
        Badge badge = new Badge(variable.getChave());

        // Configurar tooltip com a descrição usando elemento
        badge.getElement().setProperty("title", variable.getDescricao());

        // Adicionar classe para estilização
        badge.addClassName("variable-badge");

        // Adicionar listener de clique no container
        container.addClickListener(event -> {
            if (insertListener != null) {
                insertListener.onVariableInsert(variable.getChave());
            }
        });

        // Estilização do container
        container.addClassName("variable-badge-container");
        container.getElement().getStyle().set("margin", "4px");
        container.getElement().getStyle().set("cursor", "pointer");
        container.getElement().getStyle().set("display", "inline-block");
        container.add(badge);

        return container;
    }

    /**
     * Listener para eventos de inserção de variável
     */
    public interface VariableInsertListener {
        void onVariableInsert(String variableKey);
    }

    /**
     * Define o listener para eventos de inserção de variável
     */
    public void setVariableInsertListener(VariableInsertListener listener) {
        this.insertListener = listener;
    }
}
