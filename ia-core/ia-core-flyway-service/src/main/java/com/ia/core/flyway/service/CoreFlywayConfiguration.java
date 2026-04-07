package com.ia.core.flyway.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

@Slf4j
public abstract class CoreFlywayConfiguration {

  @Bean
  static org.springframework.boot.flyway.autoconfigure.FlywayMigrationStrategy flywayMigrationStrategy() {
    return flyway -> {
      log.info("Realizando migração com flyway");
      var validateWithResult = flyway.validateWithResult();
      if (!validateWithResult.validationSuccessful) {
        log.info("Realizando reparo de migração");
        flyway.repair();
        log.info("Reparo de migração finalizado");
      }
      var info = flyway.info();
      if (info.getInfoResult().allSchemasEmpty) {
        log.info("Inicializando flyway pela primeira vez");
        flyway.baseline();
        log.info("Flyway inicializado");
      }
      flyway.migrate();
      log.info("Migração com flyway executada com sucesso");
    };
  }
}
