package com.ia.core.view.components.configuracao;

import com.ia.core.service.configuracao.dto.ConfiguracaoSistemaDTO;
import com.ia.core.view.client.BaseClient;
import com.ia.core.view.manager.DefaultBaseManager;
import com.ia.core.view.manager.DefaultBaseManagerConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Serviço para configurações do sistema
 *
 * @param <T> Tipo da configuração
 * @author Israel Araújo
 */
@Slf4j
public class ConfigurationManager<T extends ConfiguracaoSistemaDTO<?>>
    extends DefaultBaseManager<T> {

    /**
     * @param client {@link BaseClient}
     */
    public ConfigurationManager(DefaultBaseManagerConfig<T> client) {
        super(client);
    }


    @SuppressWarnings("unchecked")
    @Override
    public ConfigurationClient<T> getClient() {
        return (ConfigurationClient<T>) getConfig().getClient();
    }

    /**
     * Busca todos os módulos com configurações.
     *
     * @return lista de nomes de módulos
     */
    public List<String> getModulosConfiguracao() {
        return getClient().listModulos();
    }

    /**
     * Busca configurações de um módulo específico.
     *
     * @param modulo nome do módulo
     * @return lista de configurações do módulo
     */
    public List<T> getConfiguracoesModulo(String modulo) {
        return getClient().listByModulo(modulo);
    }
}
