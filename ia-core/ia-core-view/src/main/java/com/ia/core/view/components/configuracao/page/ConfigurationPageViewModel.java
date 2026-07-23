package com.ia.core.view.components.configuracao.page;

import com.ia.core.service.configuracao.dto.ConfiguracaoSistemaDTO;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.view.components.form.viewModel.FormViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Implementação padrão de um view model para página de configurações.
 * <p>
 * Responsável por gerenciar a lista de configurações e criar view models
 * para as abas de cada módulo.
 *
 * @param <T> Tipo da configuração
 * @author Israel Araújo
 */
public abstract class ConfigurationPageViewModel<T extends ConfiguracaoSistemaDTO<?>>
    extends FormViewModel<ArrayList<T>> {

    /**
     * @param readOnly Indicativo de somente leitura
     */
    public ConfigurationPageViewModel(ConfigurationPageViewModelConfig<T> config) {
        super(config);
        setModel(new ArrayList<>());
    }

    /**
     * Busca todos os módulos com configurações.
     *
     * @return lista de nomes de módulos
     */
    public List<String> getModulosConfiguracao() {
        return getConfig().getConfigurationManager().getModulosConfiguracao();
    }

    /**
     * Busca configurações de um módulo específico.
     *
     * @param modulo nome do módulo
     * @return lista de configurações do módulo
     */
    public List<T> getConfiguracoesModulo(String modulo) {
        return getConfig().getConfigurationManager().getConfiguracoesModulo(modulo);
    }

    /**
     * Cria um view model para uma aba de configuração específica de módulo.
     *
     * @param config configuração do view model da aba
     * @return view model para a aba de configuração
     */
    protected ConfigurationTabViewModel<T> createConfigurationTabViewModel(String modulo) {
        List<T> configuracoes = getConfiguracoesModulo(modulo);
        getModel().addAll(configuracoes);
        ConfigurationTabViewModel<T> tabViewModel = new ConfigurationTabViewModel<>(getConfig().createConfigurationTabViewModelConfig(isReadOnly(), modulo));
        // Agrupa por categoria (ordem alfabética via TreeMap)
        TreeMap<String, List<T>> porCategoria = configuracoes.stream()
            .collect(Collectors.groupingBy(
                ConfiguracaoSistemaDTO::getCategoria,
                TreeMap::new,
                Collectors.toList()
            ));
        tabViewModel.setModel(porCategoria);
        return tabViewModel;
    }

    protected void save() {
        ServiceException ex = new ServiceException();
        getModel().forEach(config -> {
            try {
                getConfig().getConfigurationManager().save(config);
            } catch (Exception e) {
                ex.add(e);
            }
        });
        if (ex.hasErros()) {
            throw ex;
        }
    }

    @Override
    public ConfigurationPageViewModelConfig<T> getConfig() {
        return super.getConfig().cast();
    }

}
