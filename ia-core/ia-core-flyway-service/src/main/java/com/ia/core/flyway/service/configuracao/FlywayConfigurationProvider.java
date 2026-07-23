package com.ia.core.flyway.service.configuracao;

import com.ia.core.flyway.model.FlywayModel;
import com.ia.core.model.configuracao.TipoConfiguracao;
import com.ia.core.service.configuracao.ConfigurationProvider;
import com.ia.core.service.configuracao.dto.ConfiguracaoSistemaDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Provider de configuração para o módulo Flyway.
 * <p>
 * Fornece configurações específicas para o gerenciamento de migrações
 * do Flyway, incluindo validação de schemas e reparos automáticos.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
@EnableConfigurationProperties(FlywayProperties.class)
public class FlywayConfigurationProvider implements ConfigurationProvider {

    private static final Set<String> ALLOWED_CHAVES = Set.of(
        FlywayPropertiesConstants.ENABLED,
        FlywayPropertiesConstants.VALIDATE_ON_MIGRATE,
        FlywayPropertiesConstants.REPAIR_ON_FAILURE,
        FlywayPropertiesConstants.BASELINE_ON_EMPTY,
        FlywayPropertiesConstants.BASELINE_ON_MIGRATE,
        FlywayPropertiesConstants.LOCATIONS,
        FlywayPropertiesConstants.BASELINE_VERSION,
        FlywayPropertiesConstants.BASELINE_DESCRIPTION,
        FlywayPropertiesConstants.CLEAN_DISABLED,
        FlywayPropertiesConstants.OUT_OF_ORDER,
        FlywayPropertiesConstants.VALIDATE_MIGRATION_NAMING,
        FlywayPropertiesConstants.SCHEMAS,
        FlywayPropertiesConstants.DEFAULT_SCHEMA,
        FlywayPropertiesConstants.TABLE,
        FlywayPropertiesConstants.PLACEHOLDER_REPLACEMENT,
        FlywayPropertiesConstants.PLACEHOLDER_PREFIX,
        FlywayPropertiesConstants.PLACEHOLDER_SUFFIX,
        FlywayPropertiesConstants.CREATE_SCHEMAS
    );

    @Getter
    private final FlywayProperties flywayProperties;

    @Getter
    private final Properties properties;

    @Getter
    private List<ConfiguracaoSistemaDTO<?>> configurations;

    public FlywayConfigurationProvider(FlywayProperties flywayProperties) {
        this.flywayProperties = flywayProperties;
        this.properties = createProperties();
        this.configurations = buildConfigurations();
    }

    @Override
    public List<ConfiguracaoSistemaDTO<?>> getConfigurations() {
        return configurations;
    }

    @Override
    public String getModulo() {
        return FlywayModel.NAME;
    }

    @Override
    public void validar(ConfiguracaoSistemaDTO<?> config) {
        if (!ALLOWED_CHAVES.contains(config.getChave())) {
            log.debug("Chave de configuração ignorada: {}", config.getChave());
            return;
        }

        switch (config.getChave()) {
            case FlywayPropertiesConstants.ENABLED,
                 FlywayPropertiesConstants.VALIDATE_ON_MIGRATE,
                 FlywayPropertiesConstants.BASELINE_ON_MIGRATE,
                 FlywayPropertiesConstants.CLEAN_DISABLED,
                 FlywayPropertiesConstants.OUT_OF_ORDER,
                 FlywayPropertiesConstants.VALIDATE_MIGRATION_NAMING,
                 FlywayPropertiesConstants.PLACEHOLDER_REPLACEMENT,
                 FlywayPropertiesConstants.CREATE_SCHEMAS:
                if (config.getValor() != null) {
                    Boolean.parseBoolean(config.getValor());
                }
                break;
            default:
                break;
        }
        log.debug("Validação concluída para configuração Flyway: {}", config.getChave());
    }

    @Override
    public void aplicar(ConfiguracaoSistemaDTO<?> config) {
        validar(config);

        String chave = config.getChave();
        String valor = config.getValor();

        int index = configurations.indexOf(config);
        if (index != -1) {
            configurations.set(index, config);
        } else {
            configurations.add(config);
        }

        switch (chave) {
            case FlywayPropertiesConstants.ENABLED -> flywayProperties.setEnabled(Boolean.parseBoolean(valor));
            case FlywayPropertiesConstants.VALIDATE_ON_MIGRATE -> flywayProperties.setValidateOnMigrate(Boolean.parseBoolean(valor));
            case FlywayPropertiesConstants.REPAIR_ON_FAILURE -> flywayProperties.setRepairOnFailure(Boolean.parseBoolean(valor));
            case FlywayPropertiesConstants.BASELINE_ON_EMPTY -> flywayProperties.setBaselineOnEmpty(Boolean.parseBoolean(valor));
            case FlywayPropertiesConstants.LOCATIONS -> flywayProperties.setLocations(valor);
            case FlywayPropertiesConstants.BASELINE_VERSION -> flywayProperties.setBaselineVersion(valor);
            case FlywayPropertiesConstants.BASELINE_DESCRIPTION -> flywayProperties.setBaselineDescription(valor);
            case FlywayPropertiesConstants.CLEAN_DISABLED -> flywayProperties.setCleanDisabled(Boolean.parseBoolean(valor));
            case FlywayPropertiesConstants.OUT_OF_ORDER -> flywayProperties.setOutOfOrder(Boolean.parseBoolean(valor));
            case FlywayPropertiesConstants.VALIDATE_MIGRATION_NAMING -> flywayProperties.setValidateMigrationNaming(Boolean.parseBoolean(valor));
            // schemas is a list - handled as CSV for simplicity in DTOs
            case FlywayPropertiesConstants.SCHEMAS -> flywayProperties.setSchemas(new ArrayList<>(Arrays.asList(valor.split(","))));
            case FlywayPropertiesConstants.DEFAULT_SCHEMA -> flywayProperties.setDefaultSchema(valor);
            case FlywayPropertiesConstants.TABLE -> flywayProperties.setTable(valor);
            case FlywayPropertiesConstants.PLACEHOLDER_REPLACEMENT -> flywayProperties.setPlaceholderReplacement(Boolean.parseBoolean(valor));
            case FlywayPropertiesConstants.PLACEHOLDER_PREFIX -> flywayProperties.setPlaceholderPrefix(valor);
            case FlywayPropertiesConstants.PLACEHOLDER_SUFFIX -> flywayProperties.setPlaceholderSuffix(valor);
            case FlywayPropertiesConstants.CREATE_SCHEMAS -> flywayProperties.setCreateSchemas(Boolean.parseBoolean(valor));
            default -> {
                log.debug("Chave de configuração Flyway não mapeada para aplicação: {}", chave);
                return;
            }
        }

        this.properties.clear();
        this.properties.putAll(createProperties());

        log.info("Configuração Flyway aplicada: chave={}, valor={}", chave, valor);
    }

    private List<ConfiguracaoSistemaDTO<?>> buildConfigurations() {
        List<ConfiguracaoSistemaDTO<?>> configs = new ArrayList<>();

        // Core
        add(configs, FlywayPropertiesConstants.ENABLED, flywayProperties.isEnabled(), TipoConfiguracao.BOOLEAN, "Flyway", "Habilitar Flyway");
        add(configs, FlywayPropertiesConstants.VALIDATE_ON_MIGRATE, flywayProperties.isValidateOnMigrate(), TipoConfiguracao.BOOLEAN, "Flyway", "Validar em migração");
        add(configs, FlywayPropertiesConstants.REPAIR_ON_FAILURE, flywayProperties.isRepairOnFailure(), TipoConfiguracao.BOOLEAN, "Flyway", "Reparar em falha");
        add(configs, FlywayPropertiesConstants.BASELINE_ON_MIGRATE, flywayProperties.isBaselineOnMigrate(), TipoConfiguracao.BOOLEAN, "Flyway", "Baseline ao executar migrações");
        add(configs, FlywayPropertiesConstants.BASELINE_ON_EMPTY, flywayProperties.isBaselineOnEmpty(), TipoConfiguracao.BOOLEAN, "Flyway", "Baseline se vazio");

        // Migration
        add(configs, FlywayPropertiesConstants.LOCATIONS, flywayProperties.getLocations(), TipoConfiguracao.STRING, "Migration", "Locais de scripts de migração");
        add(configs, FlywayPropertiesConstants.BASELINE_VERSION, flywayProperties.getBaselineVersion(), TipoConfiguracao.STRING, "Migration", "Versão do baseline");
        add(configs, FlywayPropertiesConstants.BASELINE_DESCRIPTION, flywayProperties.getBaselineDescription(), TipoConfiguracao.STRING, "Migration", "Descrição do baseline");
        add(configs, FlywayPropertiesConstants.CLEAN_DISABLED, flywayProperties.isCleanDisabled(), TipoConfiguracao.BOOLEAN, "Migration", "Desabilitar comando clean");
        add(configs, FlywayPropertiesConstants.OUT_OF_ORDER, flywayProperties.isOutOfOrder(), TipoConfiguracao.BOOLEAN, "Migration", "Permitir migrações out-of-order");
        add(configs, FlywayPropertiesConstants.VALIDATE_MIGRATION_NAMING, flywayProperties.isValidateMigrationNaming(), TipoConfiguracao.BOOLEAN, "Migration", "Validar nomenclatura de migrations");

        // Schema
        add(configs, FlywayPropertiesConstants.SCHEMAS, String.join(",", flywayProperties.getSchemas()), TipoConfiguracao.STRING, "Schema", "Schemas gerenciados");
        add(configs, FlywayPropertiesConstants.DEFAULT_SCHEMA, flywayProperties.getDefaultSchema(), TipoConfiguracao.STRING, "Schema", "Schema padrão");
        add(configs, FlywayPropertiesConstants.TABLE, flywayProperties.getTable(), TipoConfiguracao.STRING, "Schema", "Tabela de histórico");

        // Placeholder
        add(configs, FlywayPropertiesConstants.PLACEHOLDER_REPLACEMENT, flywayProperties.isPlaceholderReplacement(), TipoConfiguracao.BOOLEAN, "Placeholder", "Habilitar substituição de placeholders");
        add(configs, FlywayPropertiesConstants.PLACEHOLDER_PREFIX, flywayProperties.getPlaceholderPrefix(), TipoConfiguracao.STRING, "Placeholder", "Prefixo de placeholder");
        add(configs, FlywayPropertiesConstants.PLACEHOLDER_SUFFIX, flywayProperties.getPlaceholderSuffix(), TipoConfiguracao.STRING, "Placeholder", "Sufixo de placeholder");

        // General
        add(configs, FlywayPropertiesConstants.CREATE_SCHEMAS, flywayProperties.isCreateSchemas(), TipoConfiguracao.BOOLEAN, "General", "Criar schemas automaticamente");

        return configs;
    }

    private void add(List<ConfiguracaoSistemaDTO<?>> configs, String chave, Object valor, TipoConfiguracao tipo, String categoria, String descricao) {
        configs.add(ConfiguracaoSistemaDTO.builder()
            .modulo(getModulo())
            .chave(chave)
            .valor(convertValue(valor))
            .tipo(tipo)
            .categoria(categoria)
            .descricao(descricao)
            .build());
    }

    private String convertValue(Object valor) {
        if (valor == null) {
            return "";
        }
        return String.valueOf(valor);
    }

    private Properties createProperties() {
        Properties properties = new Properties();

        properties.setProperty(FlywayPropertiesConstants.ENABLED, String.valueOf(flywayProperties.isEnabled()));
        properties.setProperty(FlywayPropertiesConstants.VALIDATE_ON_MIGRATE, String.valueOf(flywayProperties.isValidateOnMigrate()));
        properties.setProperty(FlywayPropertiesConstants.REPAIR_ON_FAILURE, String.valueOf(flywayProperties.isRepairOnFailure()));
        properties.setProperty(FlywayPropertiesConstants.BASELINE_ON_MIGRATE, String.valueOf(flywayProperties.isBaselineOnMigrate()));
        properties.setProperty(FlywayPropertiesConstants.BASELINE_ON_EMPTY, String.valueOf(flywayProperties.isBaselineOnEmpty()));
        properties.setProperty(FlywayPropertiesConstants.LOCATIONS, flywayProperties.getLocations());
        properties.setProperty(FlywayPropertiesConstants.BASELINE_VERSION, flywayProperties.getBaselineVersion());
        properties.setProperty(FlywayPropertiesConstants.BASELINE_DESCRIPTION, flywayProperties.getBaselineDescription());
        properties.setProperty(FlywayPropertiesConstants.CLEAN_DISABLED, String.valueOf(flywayProperties.isCleanDisabled()));
        properties.setProperty(FlywayPropertiesConstants.OUT_OF_ORDER, String.valueOf(flywayProperties.isOutOfOrder()));
        properties.setProperty(FlywayPropertiesConstants.VALIDATE_MIGRATION_NAMING, String.valueOf(flywayProperties.isValidateMigrationNaming()));
        properties.setProperty(FlywayPropertiesConstants.SCHEMAS, String.join(",", flywayProperties.getSchemas()));
        properties.setProperty(FlywayPropertiesConstants.DEFAULT_SCHEMA, flywayProperties.getDefaultSchema());
        properties.setProperty(FlywayPropertiesConstants.TABLE, flywayProperties.getTable());
        properties.setProperty(FlywayPropertiesConstants.PLACEHOLDER_REPLACEMENT, String.valueOf(flywayProperties.isPlaceholderReplacement()));
        properties.setProperty(FlywayPropertiesConstants.PLACEHOLDER_PREFIX, flywayProperties.getPlaceholderPrefix());
        properties.setProperty(FlywayPropertiesConstants.PLACEHOLDER_SUFFIX, flywayProperties.getPlaceholderSuffix());
        properties.setProperty(FlywayPropertiesConstants.CREATE_SCHEMAS, String.valueOf(flywayProperties.isCreateSchemas()));

        return properties;
    }
}
