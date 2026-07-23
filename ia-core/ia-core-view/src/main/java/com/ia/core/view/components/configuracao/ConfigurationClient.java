package com.ia.core.view.components.configuracao;

import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import com.ia.core.service.configuracao.dto.ConfiguracaoSistemaDTO;
import com.ia.core.view.client.DefaultBaseClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Cliente para configurações
 *
 * @param <T> Tipo da configuração
 * @author Israel Araújo
 */
public interface ConfigurationClient<T extends ConfiguracaoSistemaDTO<?>>
    extends DefaultBaseClient<T> {

    /**
     * Nome do cliente.
     */
    String NOME = "configuracao";
    /**
     * URL do cliente.
     */
    String URL = "${feign.host}/api/${api.version}/${feign.url.configuracao}";

    /**
     * Lista todos os módulos com configurações.
     *
     * @return lista de nomes de módulos
     */
    @GetMapping("/modulos")
    @Resilient(ResilienceProfile.EXTERNAL_API)
    List<String> listModulos();

    /**
     * Lista configurações de um módulo específico.
     *
     * @param modulo nome do módulo
     * @return lista de configurações do módulo
     */
    @GetMapping("/modulo/{modulo}")
    @Resilient(ResilienceProfile.EXTERNAL_API)
    List<T> listByModulo(@PathVariable("modulo") String modulo);
}
