package com.ia.core.service.configuracao;

import com.ia.core.service.configuracao.dto.ConfiguracaoSistemaDTO;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Provider de configuração para módulos específicos.
 * <p>
 * Implementações desta interface permitem que cada módulo contribua
 * com suas próprias regras de validação e aplicação de configurações.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Validated
public interface ConfigurationProvider {

    /**
     * Retorna o nome do módulo ao qual este provider está associado.
     *
     * @return nome do módulo
     */
    String getModulo();

    /**
     * Valida uma configuração específica do módulo.
     *
     * @param config DTO da configuração a ser validada
     */
    void validar(ConfiguracaoSistemaDTO<?> config);

    /**
     * Aplica uma configuração após salvar.
     * <p>
     * Este método pode ser usado para atualizar caches, recarregar propriedades,
     * ou qualquer outra ação necessária após a persistência.
     *
     * @param config DTO da configuração a ser aplicada
     */
    default void aplicar(ConfiguracaoSistemaDTO<?> config) {
        validar(config);
        int index = getConfigurations().indexOf(config);
        if (index != -1) {
            getConfigurations().set(index, config);
        } else {
            getConfigurations().add(config);
        }
    }

    /**
     * Retorna a lista de configurações padrão deste módulo.
     *
     * @return lista de configurações padrão
     */
    List<ConfiguracaoSistemaDTO<?>> getConfigurations();
}
