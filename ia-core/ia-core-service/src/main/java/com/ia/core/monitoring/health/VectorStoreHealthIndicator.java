package com.ia.core.monitoring.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import com.ia.core.llm.service.vector.VectorStoreOperations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Health Indicator para verificação do Vector Store.
 * 
 * Este componente verifica se o Vector Store está operacional
 * e reporta métricas relevantes no endpoint /actuator/health.
 * 
 * @author Israel Araújo
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class VectorStoreHealthIndicator extends AbstractHealthIndicator {

    private final VectorStoreOperations vectorStoreOperations;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        try {
            int documentCount = vectorStoreOperations.getDocumentCount();
            
            builder.up()
                .withDetail("type", "VectorStore")
                .withDetail("documentCount", documentCount)
                .withDetail("status", "operational");
                
        } catch (Exception e) {
            log.error("Health check do Vector Store falhou", e);
            builder.down()
                .withDetail("type", "VectorStore")
                .withDetail("error", e.getMessage())
                .withDetail("status", "error");
        }
    }
}
