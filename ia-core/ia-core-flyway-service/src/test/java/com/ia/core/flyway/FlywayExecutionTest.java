package com.ia.core.flyway;

import com.ia.core.flyway.model.FlywayExecution;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FlywayExecutionTest {

    @Test
    void classeRepresentaDominioFlywayExecution() {
        assertTrue(FlywayExecution.class.getSimpleName().equals("FlywayExecution"));
    }

}
