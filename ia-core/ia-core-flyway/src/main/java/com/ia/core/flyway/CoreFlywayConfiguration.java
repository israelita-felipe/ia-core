package com.ia.core.flyway;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CoreFlywayConfiguration {

  @Bean
  static FlywayMigrationStrategy flywayMigrationStrategy() {
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
