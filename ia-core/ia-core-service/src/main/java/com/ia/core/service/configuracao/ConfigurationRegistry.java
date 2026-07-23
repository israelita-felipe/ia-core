package com.ia.core.service.configuracao;

import com.ia.core.model.configuracao.ConfiguracaoSistema;
import com.ia.core.service.configuracao.dto.ConfiguracaoSistemaDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Registry responsável por inicializar as configurações padrão dos módulos.
 * <p>
 * Executado após a inicialização do Spring Context, garante que todas as
 * configurações padrão definidas nos providers sejam persistidas e remove
 * automaticamente configurações órfãs que existam no banco mas não estejam
 * mais definidas nos providers.
 * <p>
 * Usa repository diretamente para evitar falhas de permissão, pois não há
 * usuário ativo na request durante a inicialização.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public abstract class ConfigurationRegistry<T extends ConfiguracaoSistema, D extends ConfiguracaoSistemaDTO<T>> {

    private final List<ConfigurationProvider> providers;
    private final ConfiguracaoRepository<T> repository;
    private final ConfiguracaoMapper<T, D> mapper;

    @PostConstruct
    public void registry() {
        log.debug("Inicializando configurações padrão dos módulos");
        providers.forEach(this::initializeDefaultConfigurations);
        cleanUpOrphanedConfigurations();
    }

    private void initializeDefaultConfigurations(ConfigurationProvider provider) {
        log.debug("Processando configurações do módulo: {}", provider.getModulo());
        new ArrayList<>(provider.getConfigurations()).forEach(configDto -> {
            try {
                upsertConfiguration(provider, build(configDto));
            } catch (Exception e) {
                log.warn("Erro ao processar configuração {} do módulo {}: {}",
                    configDto.getChave(), provider.getModulo(), e.getMessage());
            }
        });
    }

    private void upsertConfiguration(ConfigurationProvider provider, D configDto) {
        // Busca configuração existente por chave
        var existingEntity = repository.findByChave(configDto.getChave()).stream()
            .findFirst();

        D dtoToSave = null;

        if (existingEntity.isPresent()) {
            log.debug("Configuração {} do módulo {} já existe",
                configDto.getChave(), provider.getModulo());
            dtoToSave = mapper.toDTO((T) existingEntity.get());
        } else {
            log.debug("Configuração {} do módulo {} não existe, salvando...",
                configDto.getChave(), provider.getModulo());
            // Converte DTO para entidade usando mapper.toModel(dto)
            var entityToSave = mapper.toModel(configDto);
            var savedEntity = repository.save(entityToSave);
            // Converte entidade salva de volta para DTO
            dtoToSave = mapper.toDTO(savedEntity);
        }
        if (dtoToSave != null) {
            log.debug("Aplicando {} do módulo {}",
                dtoToSave.getChave(), provider.getModulo());
            provider.aplicar(dtoToSave);
        }
    }

    private void cleanUpOrphanedConfigurations() {
        Set<String> validChaves = providers.stream()
            .flatMap(provider -> provider.getConfigurations().stream())
            .map(ConfiguracaoSistemaDTO::getChave)
            .collect(Collectors.toSet());

        Set<String> modules = new HashSet<>(repository.findAllModulos());

        for (String modulo : modules) {
            List<T> moduleConfigs = repository.findByModulo(modulo);
            for (T config : moduleConfigs) {
                if (!validChaves.contains(config.getChave())) {
                    log.info("Removendo configuração órfã: chave={}, modulo={}", config.getChave(), modulo);
                    repository.delete(config);
                }
            }
        }
    }

    protected abstract D build(ConfiguracaoSistemaDTO<?> configDto);
}
