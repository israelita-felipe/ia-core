package com.ia.core.service;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Base class for integration tests.
 * Provides common functionality and configuration for integration tests.
 * Uses SpringBootTest with full application context and TestContainers for PostgreSQL.
 *
 * <p>Characteristics:
 * - Full Spring Boot application context
 * - PostgreSQL database via TestContainers
 * - Transaction rollback after each test
 * - Active profile: testcontainers
 *
 * <p>Usage:
 * <pre>
 * {@code
 * @SpringBootTest(classes = MyApplication.class)
 * class MyIntegrationTest extends CoreBaseIntegrationTest {
 *     // Your test methods
 * }
 * }
 * </pre>
 *
 * @author Israel Araújo
 */
@Testcontainers
@ActiveProfiles("testcontainers")
@DisplayName("Integration Test")
public abstract class CoreBaseIntegrationTest extends CoreBaseUnitTest {

    @Container
    protected static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16-alpine")
        .withDatabaseName("testdb")
        .withUsername("com/ia/test")
        .withPassword("com/ia/test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", postgresContainer::getDriverClassName);
    }
}
