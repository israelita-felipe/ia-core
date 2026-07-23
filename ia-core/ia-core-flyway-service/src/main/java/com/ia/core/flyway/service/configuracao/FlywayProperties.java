package com.ia.core.flyway.service.configuracao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Propriedades de configuração centralizada para o módulo Flyway.
 *
 * <p>Permite configuração via YAML (application.yml) e programática.
 * Cada propriedade do Flyway pode ser configurada individualmente.</p>
 *
 * <p>Estrutura de configuração YAML:</p>
 * <pre>
 * ia-core:
 *   flyway:
 *     enabled: true
 *     validate-on-migrate: true
 *     repair-on-failure: false
 *     baseline-on-empty: false
 *     locations: classpath:db/migrations/{vendor}
 *     baseline-version: 1
 *     baseline-description: Baseline migration
 *     clean-disabled: false
 *     out-of-order: false
 *     validate-migration-naming: false
 *     schemas:
 *       - public
 *     default-schema: public
 *     table: flyway_schema_history
 *     placeholder-replacement: true
 *     placeholder-prefix: ${ 
 *     placeholder-suffix: }
 *     create-schemas: true
 * </pre>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.flyway")
public class FlywayProperties {

    /**
     * Habilitar Flyway.
     */
    private boolean enabled = true;

    /**
     * Validar em migração.
     */
    private boolean validateOnMigrate = true;

    /**
     * Reparar em falha.
     */
    private boolean repairOnFailure = false;

    /**
     * Baseline ao executar migrações.
     */
    private boolean baselineOnMigrate = true;

    /**
     * Baseline se vazio.
     */
    private boolean baselineOnEmpty = false;

    /**
     * Locais de scripts de migração.
     */
    private String locations = "classpath:db/migrations/{vendor}";

    /**
     * Versão do baseline.
     */
    private String baselineVersion = "1";

    /**
     * Descrição do baseline.
     */
    private String baselineDescription = "Baseline migration";

    /**
     * Desabilitar comando clean.
     */
    private boolean cleanDisabled = false;

    /**
     * Permitir migrações out-of-order.
     */
    private boolean outOfOrder = false;

    /**
     * Validar nomenclatura de migrations.
     */
    private boolean validateMigrationNaming = false;

    /**
     * Schemas gerenciados.
     */
    private java.util.List<String> schemas = new java.util.ArrayList<>(List.of("public"));

    /**
     * Schema padrão.
     */
    private String defaultSchema = "public";

    /**
     * Tabela de histórico.
     */
    private String table = "flyway_schema_history";

    /**
     * Habilitar substituição de placeholders.
     */
    private boolean placeholderReplacement = true;

    /**
     * Prefixo de placeholder.
     */
    private String placeholderPrefix = "${";

    /**
     * Sufixo de placeholder.
     */
    private String placeholderSuffix = "}";

    /**
     * Criar schemas automaticamente.
     */
    private boolean createSchemas = true;
}
