package com.ia.core.view.components.configuracao.page;

import com.ia.core.service.configuracao.dto.ConfiguracaoSistemaDTO;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;

/**
 * Configuração para ConfigurationTabViewModel.
 * <p>
 * Extende MapListFormViewModelConfig adicionando o atributo modulo.
 *
 * @param <T> Tipo da configuração do sistema
 * @author Israel Araújo
 * @since 1.0.0
 */
public class ConfigurationTabViewModelConfig<T extends ConfiguracaoSistemaDTO<?>>
    extends MapListFormViewModelConfig<String, T> implements Serializable {

    private static final long serialVersionUID = -1234567890123456782L;

    /**
     * Nome do módulo desta aba de configuração.
     */
    private final String modulo;

    /**
     * Cria uma nova configuração.
     *
     * @param readOnly indicativo de somente leitura
     * @param modulo   nome do módulo
     */
    public ConfigurationTabViewModelConfig(boolean readOnly, String modulo) {
        super(readOnly);
        this.modulo = modulo;
    }

    /**
     * Cria uma nova configuração com mapa inicializado.
     *
     * @param readOnly   indicativo de somente leitura
     * @param modulo     nome do módulo
     * @param initialMap mapa inicial (categoria -> lista de configurações)
     */
    public ConfigurationTabViewModelConfig(boolean readOnly, String modulo, TreeMap<String, List<T>> initialMap) {
        super(readOnly, initialMap);
        this.modulo = modulo;
    }

    /**
     * Retorna o nome do módulo.
     */
    public String getModulo() {
        return modulo;
    }

    /**
     * Cria uma configuração padrão.
     */
    public static <T extends ConfiguracaoSistemaDTO<?>> ConfigurationTabViewModelConfig<T> create(
        boolean readOnly, String modulo) {
        return new ConfigurationTabViewModelConfig<>(readOnly, modulo);
    }

    /**
     * Cria uma configuração com mapa inicializado.
     */
    public static <T extends ConfiguracaoSistemaDTO<?>> ConfigurationTabViewModelConfig<T> create(
        boolean readOnly, String modulo, TreeMap<String, List<T>> map) {
        return new ConfigurationTabViewModelConfig<>(readOnly, modulo, map);
    }
}