package com.ia.core.view.components.configuracao.page;

import com.ia.core.service.configuracao.dto.ConfiguracaoSistemaDTO;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;

/**
 * View model para ConfigurationTabView.
 * <p>
 * Herda de MapListFormViewModel com atributos específicos para configuração:
 * - modulo: nome do módulo (adicionado via ConfigurationTabViewModelConfig)
 * - readOnly: status de somente leitura (já herdado)
 *
 * @param <T> Tipo da configuração do sistema
 * @author Israel Araújo
 * @since 1.0.0
 */
public class ConfigurationTabViewModel<T extends ConfiguracaoSistemaDTO<?>>
    extends MapListFormViewModel<String, T> implements Serializable {

    private static final long serialVersionUID = -1234567890123456783L;

    /**
     * Cria um novo ConfigurationTabViewModel.
     *
     * @param config configuração do view model
     */
    public ConfigurationTabViewModel(ConfigurationTabViewModelConfig<T> config) {
        super(config);
    }

    /**
     * Cria um novo ConfigurationTabViewModel (conveniência).
     *
     * @param readOnly   indicativo de somente leitura
     * @param modulo     nome do módulo
     * @param initialMap mapa inicial (categoria -> lista de configurações)
     */
    public ConfigurationTabViewModel(boolean readOnly, String modulo, TreeMap<String, List<T>> initialMap) {
        this(new ConfigurationTabViewModelConfig<>(readOnly, modulo, initialMap));
    }

    /**
     * Retorna o módulo desta aba.
     */
    public String getModulo() {
        return getConfig().getModulo();
    }

    /**
     * Retorna todas as configurações do modelo como lista plana.
     */
    public List<T> getAllConfiguracoes() {
        return getModel().values().stream()
            .flatMap(List::stream)
            .toList();
    }

    @Override
    public ConfigurationTabViewModelConfig<T> getConfig() {
        return (ConfigurationTabViewModelConfig<T>) super.getConfig();
    }
}
