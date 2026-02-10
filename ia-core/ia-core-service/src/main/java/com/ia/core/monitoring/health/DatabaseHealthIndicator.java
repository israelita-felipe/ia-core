package com.ia.core.monitoring.health;

import java.sql.Connection;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Health Indicator para verificação de conectividade com banco de dados.
 * 
 * Este componente verifica se a conexão com o banco de dados está funcionando
 * e reporta o status no endpoint /actuator/health.
 * 
 * @author Israel Araújo
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseHealthIndicator extends AbstractHealthIndicator {

    private final HikariDataSource dataSource;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            boolean valid = connection.isValid(5);
            
            if (valid) {
                builder.up()
                    .withDetail("database", "connected")
                    .withDetail("databaseType", getDatabaseType())
                    .withDetail("minimumIdle", dataSource.getMinimumIdle())
                    .withDetail("maximumPoolSize", dataSource.getMaximumPoolSize())
                    .withDetail("activeConnections", dataSource.getHikariPoolMXBean().getActiveConnections());
            } else {
                builder.down()
                    .withDetail("database", "connection invalid")
                    .withDetail("error", "Connection validation failed");
            }
        } catch (Exception e) {
            log.error("Health check do banco de dados falhou", e);
            builder.down()
                .withDetail("database", "connection error")
                .withDetail("error", e.getMessage());
        }
    }

    private String getDatabaseType() {
        try {
            return dataSource.getConnection().getMetaData().getDatabaseProductName();
        } catch (Exception e) {
            return "unknown";
        }
    }
}
