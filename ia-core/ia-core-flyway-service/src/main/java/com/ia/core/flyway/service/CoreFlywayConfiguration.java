package com.ia.core.flyway.service;

import com.ia.core.flyway.service.configuracao.FlywayConfigurationProvider;
import com.ia.core.flyway.service.configuracao.FlywayProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.flyway.autoconfigure.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;

@Slf4j
@RequiredArgsConstructor
public abstract class CoreFlywayConfiguration {

    @Getter
    private final FlywayConfigurationProvider flywayConfigurationProvider;

    @Bean
    Flyway createFlyway() {
        return Flyway.configure().configuration(flywayConfigurationProvider.getProperties()).load();
    }

    @Bean
    FlywayMigrationStrategy flywayMigrationStrategy() {
        FlywayProperties flywayProperties = flywayConfigurationProvider.getFlywayProperties();

        return flyway -> {
            if (!flywayProperties.isEnabled()) {
                log.info("Flyway desabilitado via configuração; pulando migração.");
                return;
            }

            log.info("Realizando migração com flyway");
            if (flywayProperties.isValidateOnMigrate()) {
                var validateWithResult = flyway.validateWithResult();
                if (!validateWithResult.validationSuccessful) {
                    if (flywayProperties.isRepairOnFailure()) {
                        log.info("Realizando reparo de migração");
                        flyway.repair();
                        log.info("Reparo de migração finalizado");
                    } else {
                        log.warn("Validação do Flyway falhou e repair-on-failure está desativado.");
                    }
                }
            }
            var info = flyway.info();
            var infoResult = info.getInfoResult();
            if (infoResult != null && infoResult.allSchemasEmpty && flywayProperties.isBaselineOnEmpty()) {
                log.info("Inicializando flyway pela primeira vez");
                flyway.baseline();
                log.info("Flyway inicializado");
            }
            flyway.migrate();
            log.info("Migração com flyway executada com sucesso");
        };
    }
}
