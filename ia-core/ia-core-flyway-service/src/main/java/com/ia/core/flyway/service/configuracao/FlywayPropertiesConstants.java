package com.ia.core.flyway.service.configuracao;

/**
 * Constantes para as chaves de propriedades do módulo Flyway.
 *
 * <p>Estas constantes são usadas internamente por {@link FlywayProperties}
 * e podem ser usadas para referenciar as chaves de configuração.</p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public final class FlywayPropertiesConstants {

    // =========================================================================
    // Flyway Properties (spring.flyway.*)
    // =========================================================================
    /**
     * Property key: spring.flyway.enabled
     */
    public static final String ENABLED = "spring.flyway.enabled";

    /**
     * Property key: spring.flyway.validate-on-migrate
     */
    public static final String VALIDATE_ON_MIGRATE = "spring.flyway.validate-on-migrate";

    /**
     * Property key: spring.flyway.repair-on-failure
     */
    public static final String REPAIR_ON_FAILURE = "spring.flyway.repair-on-failure";

    /**
     * Property key: spring.flyway.baseline-on-empty
     */
    public static final String BASELINE_ON_EMPTY = "spring.flyway.baseline-on-empty";

    /**
     * Property key: spring.flyway.baseline-on-migrate
     */
    public static final String BASELINE_ON_MIGRATE = "spring.flyway.baseline-on-migrate";

    /**
     * Property key: spring.flyway.locations
     */
    public static final String LOCATIONS = "spring.flyway.locations";

    /**
     * Property key: spring.flyway.baseline-version
     */
    public static final String BASELINE_VERSION = "spring.flyway.baseline-version";

    /**
     * Property key: spring.flyway.baseline-description
     */
    public static final String BASELINE_DESCRIPTION = "spring.flyway.baseline-description";

    /**
     * Property key: spring.flyway.clean-disabled
     */
    public static final String CLEAN_DISABLED = "spring.flyway.clean-disabled";

    /**
     * Property key: spring.flyway.out-of-order
     */
    public static final String OUT_OF_ORDER = "spring.flyway.out-of-order";

    /**
     * Property key: spring.flyway.validate-migration-naming
     */
    public static final String VALIDATE_MIGRATION_NAMING = "spring.flyway.validate-migration-naming";

    /**
     * Property key: spring.flyway.schemas
     */
    public static final String SCHEMAS = "spring.flyway.schemas";

    /**
     * Property key: spring.flyway.default-schema
     */
    public static final String DEFAULT_SCHEMA = "spring.flyway.default-schema";

    /**
     * Property key: spring.flyway.table
     */
    public static final String TABLE = "spring.flyway.table";

    /**
     * Property key: spring.flyway.placeholder-replacement
     */
    public static final String PLACEHOLDER_REPLACEMENT = "spring.flyway.placeholder-replacement";

    /**
     * Property key: spring.flyway.placeholder-prefix
     */
    public static final String PLACEHOLDER_PREFIX = "spring.flyway.placeholder-prefix";

    /**
     * Property key: spring.flyway.placeholder-suffix
     */
    public static final String PLACEHOLDER_SUFFIX = "spring.flyway.placeholder-suffix";

    /**
     * Property key: spring.flyway.create-schemas
     */
    public static final String CREATE_SCHEMAS = "spring.flyway.create-schemas";

    private FlywayPropertiesConstants() {
        // Utility class
    }
}
