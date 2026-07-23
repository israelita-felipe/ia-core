package com.ia.core.service.configuracao;

import com.ia.core.model.configuracao.ConfiguracaoSistema;
import com.ia.core.service.CrudBaseService;
import com.ia.core.service.configuracao.dto.ConfiguracaoSistemaDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Serviço genericamente tipado para gerenciamento de configurações do sistema.
 * <p>
 * Fornece operações CRUD e métodos específicos para busca por módulo.
 *
 * @param <T> tipo da entidade de configuração (deve estender ConfiguracaoSistema)
 * @param <D> tipo do DTO de configuração
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public abstract class AbstractConfiguracaoService<T extends ConfiguracaoSistema, D extends ConfiguracaoSistemaDTO<T>>
    extends CrudBaseService<T, D> {

    /**
     * Construtor do serviço de configuração.
     *
     * @param config configuração do serviço
     */
    protected AbstractConfiguracaoService(ConfiguracaoServiceConfig<T, D> config) {
        super(config);
    }

    /**
     * Busca o provider de um módulo específico.
     *
     * @param modulo nome do módulo
     * @return Optional com o provider encontrado
     */
    public Optional<ConfigurationProvider> findProvider(String modulo) {
        return getConfig().getProviders().stream()
            .filter(p -> modulo.equals(p.getModulo()))
            .findFirst();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Após salvar, notifica os providers associados para validação e aplicação.
     */
    @Override
    public D save(D dto) {
        D saved = super.save(dto);
        // Notifica providers após salvar
        if (saved != null && saved.getModulo() != null) {
            findProvider(saved.getModulo()).ifPresent(provider -> {
                try {
                    provider.aplicar(saved);
                } catch (Exception e) {
                    log.warn("Erro ao aplicar configuração do módulo {}: {}", saved.getModulo(), e.getMessage());
                }
            });
        }
        return saved;
    }

    /**
     * Busca todas as configurações de um módulo específico.
     * <p>
     * Resultados são ordenados por categoria (ordem alfabética).
     *
     * @param modulo nome do módulo
     * @return lista de configurações do módulo
     */
    public List<D> findByModulo(String modulo) {
        log.debug("Buscando configurações do módulo: {}", modulo);
        var repository = getConfiguracaoRepository();
        var mapper = getMapper();

        if (repository == null || mapper == null) {
            return List.of();
        }

        return repository.findByModulo(modulo).stream()
            .map(mapper::toDTO)
            .sorted(Comparator.comparing(ConfiguracaoSistemaDTO::getCategoria))
            .toList();
    }

    /**
     * Busca todos os módulos distintos que possuem configurações.
     *
     * @return lista de nomes de módulos únicos
     */
    public List<String> findModulos() {
        log.debug("Buscando módulos com configurações");
        var repository = getConfiguracaoRepository();

        if (repository == null) {
            return List.of();
        }

        List<String> modulos = repository.findAllModulos();
        return modulos;
    }

    /**
     * Obtém o repositório de configuração.
     *
     * @return repositório tipado
     */
    protected ConfiguracaoRepository<T> getConfiguracaoRepository() {
        return (ConfiguracaoRepository<T>) getRepository();
    }

    @Override
    public ConfiguracaoServiceConfig<T, D> getConfig() {
        return ((ConfiguracaoServiceConfig<T, D>) super.getConfig());
    }
}
