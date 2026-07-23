package com.ia.core.view.components.configuracao.page;

import com.ia.core.service.configuracao.dto.ConfiguracaoSistemaDTO;
import com.ia.core.view.components.form.FormView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Página principal de configurações do sistema.
 * <p>
 * Exibe abas para cada módulo com configurações, organizadas por categoria.
 * Segue o padrão MVVM (ADR-008) com CoreConfigurationViewModel.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public abstract class ConfigurationPageView<T extends ConfiguracaoSistemaDTO<?>> extends FormView<ArrayList<T>> {
    /**
     * Rota padrão
     */
    public static final String DEFAULT_ROUTE = "configuracoes";

    /**
     * Construtor padrão.
     *
     * @param viewModel configuração do view model
     */
    public ConfigurationPageView(ConfigurationPageViewModel<T> viewModel) {
        super(viewModel);
    }

    @Override
    protected List<FormLayout.ResponsiveStep> createResponsiveSteps() {
        return Arrays
            .asList(step(ScreenSize.XSM, xsm()));
    }

    @Override
    public void createLayout() {
        TabSheet tabSheet = createTabSheet();
        tabSheet.setSizeFull();

        // Obtém todos os módulos e cria uma aba para cada um
        List<String> modulos = getViewModel().getModulosConfiguracao();

        for (String modulo : modulos) {
            ConfigurationTabView<T> aba = new ConfigurationTabView<>(getViewModel().createConfigurationTabViewModel(modulo));
            createTab(tabSheet, $(modulo), aba);
        }
        add(tabSheet);
        add(new Button($("configuracoes.salvar"), e -> {
            try {
                getViewModel().save();
                getUI().ifPresent(ui -> ui.getPage().reload());
            } catch (Exception ex) {
                handleError(ex);
            }
        }));
    }

    @Override
    public String getHelpDescription() {
        return "Página de configurações do sistema. Permite visualizar e editar configurações por módulo.";
    }

    @Override
    public String getHelpTitle() {
        return "Configurações";
    }

    @Override
    public ConfigurationPageViewModel<T> getViewModel() {
        return super.getViewModel().cast();
    }
}
