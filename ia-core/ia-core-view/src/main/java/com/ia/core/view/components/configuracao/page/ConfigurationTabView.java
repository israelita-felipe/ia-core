package com.ia.core.view.components.configuracao.page;

import com.ia.core.service.configuracao.dto.ConfiguracaoSistemaDTO;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import java.io.Serializable;

/**
 * Componente de aba de configuração para um módulo específico.
 * <p>
 * Exibe as configurações agrupadas por categoria em um accordion.
 * Cada configuração é exibida como um campo editável apropriado ao seu tipo.
 *
 * <p><strong>Binding Manual:</strong> Como o Vaadin Binder não suporta binding direto
 * com Map via reflection, os campos são atualizados manualmente usando ValueChangeEvent
 * listeners. Veja createFieldForItem para o padrão de implementação.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class ConfigurationTabView<T extends ConfiguracaoSistemaDTO<?>>
    extends MapListFormView<String, T>
    implements Serializable {

    private static final long serialVersionUID = -1234567890123456777L;

    /**
     * Cria uma nova aba de configuração.
     *
     * @param viewModel view model previamente configurado
     */
    public ConfigurationTabView(ConfigurationTabViewModel<T> viewModel) {
        super(viewModel);
    }


    /**
     * Retorna o módulo desta aba.
     */
    public String getModulo() {
        return ((ConfigurationTabViewModel<T>) getViewModel()).getModulo();
    }

    /**
     * Cria um FormLayout para cada categoria (usado como Accordion Panel).
     */
    @Override
    protected FormLayout createFormLayoutForKey(String categoria) {
        FormLayout formLayout = createFormLayout();
        formLayout.add(new Span($(categoria)));
        formLayout.setWidthFull();
        return formLayout;
    }

    /**
     * Cria um campo de formulário apropriado ao tipo da configuração.
     *
     * @param categoria categoria (não usado para configurações individuais)
     * @param config    DTO da configuração
     * @return componente Vaadin para edição da configuração
     */
    @Override
    protected Component createFieldForItem(String categoria, T config) {
        String label = $(config.getChave());
        String help = config.getDescricao() != null ? config.getDescricao() : label;

        return switch (config.getTipo()) {
            case BOOLEAN -> {
                Checkbox checkbox = new Checkbox(label);
                checkbox.setValue(Boolean.parseBoolean(config.getValor()));
                checkbox.addValueChangeListener(event -> {
                    config.setValor(String.valueOf(event.getValue()));
                });
                checkbox.setHelperText(help);
                setHelp(checkbox, help);
                yield checkbox;
            }
            case INTEGER -> {
                TextField textField = createInteiroTextField(label, help);
                textField.setValue(config.getValor());
                textField.addValueChangeListener(event -> {
                    config.setValor(String.valueOf(event.getValue()));
                });
                yield textField;
            }
            case JSON, ENCRYPTED_STRING -> {
                TextArea textArea = new TextArea(label);
                textArea.setValue(config.getValor());
                textArea.addValueChangeListener(event -> {
                    config.setValor(String.valueOf(event.getValue()));
                });
                textArea.setHelperText(help);
                textArea.setWidthFull();
                setHelp(textArea, help);
                yield textArea;
            }
            case STRING -> {
                TextField textField = createTextField(label, help);
                textField.setValue(config.getValor());
                textField.addValueChangeListener(event -> {
                    config.setValor(String.valueOf(event.getValue()));
                });
                yield textField;
            }
        };
    }


    @Override
    public String getHelpDescription() {
        return "Configurações do módulo: " + getModulo();
    }

    @Override
    public String getHelpTitle() {
        return getModulo();
    }
}
