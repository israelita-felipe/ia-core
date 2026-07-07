package com.ia.core.flyway.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
/**
 * Configuração central do Flyway para o sistema.
 * <p>
 * Responsável por configurar a estratégia de migração do Flyway, incluindo
 * validação, reparo automático e inicialização de baseline quando necessário.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */

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
      var infoResult = info.getInfoResult();
      if (infoResult != null && infoResult.allSchemasEmpty) {
        log.info("Inicializando flyway pela primeira vez");
        flyway.baseline();
        log.info("Flyway inicializado");
      }
      flyway.migrate();
      log.info("Migração com flyway executada com sucesso");
    };
  }
}
