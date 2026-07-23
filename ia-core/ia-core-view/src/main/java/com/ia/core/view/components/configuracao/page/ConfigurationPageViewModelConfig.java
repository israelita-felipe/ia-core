package com.ia.core.view.components.configuracao.page;

import com.ia.core.service.configuracao.dto.ConfiguracaoSistemaDTO;
import com.ia.core.view.components.configuracao.ConfigurationManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import lombok.Getter;

import java.util.ArrayList;

/**
 * Classe de configuração para ConfigurationPageViewModel.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a ConfigurationPageViewModel
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class ConfigurationPageViewModelConfig<T extends ConfiguracaoSistemaDTO<?>>
    extends FormViewModelConfig<ArrayList<T>> {

    @Getter
    private final ConfigurationManager<T> configurationManager;

    /**
     * @param readOnly
     */
    public ConfigurationPageViewModelConfig(boolean readOnly, ConfigurationManager<T> configurationManager) {
        super(readOnly);
        this.configurationManager = configurationManager;
    }

    /**
     * Cria a configuração para uma aba de configuração específica de módulo.
     *
     * @param modulo nome do módulo
     * @return configuração do view model da aba
     */
    protected ConfigurationTabViewModelConfig<T> createConfigurationTabViewModelConfig(boolean readOnly, String modulo) {
        return ConfigurationTabViewModelConfig.create(readOnly, modulo);
    }

}
