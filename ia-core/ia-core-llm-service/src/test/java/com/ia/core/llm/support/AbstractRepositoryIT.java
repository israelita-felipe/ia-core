package com.ia.core.llm.support;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Base class for integration tests of Repository layer.
 * Uses HSQLDB in-memory database for fast testing.
 * 
 * Features:
 * - Automatic transaction rollback after each test
 * - EntityManager injection for manual operations
 * - Test profile activation
 * 
 * Usage:
 * <pre>
 * {@code @DataJpaTest}
 * {@code @ActiveProfiles("test")}
 * class MyRepositoryIT extends AbstractRepositoryIT {
 *     // Your test methods
 * }
 * </pre>
 * 
 * @author Israel Araújo
 */
@DataJpaTest
@ActiveProfiles("test")
public abstract class AbstractRepositoryIT {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected TestDataFactory testDataFactory;

    /**
     * Clear the entity manager before each test to ensure isolation.
     */
    @BeforeEach
    void setUp() {
        entityManager.clear();
    }

    /**
     * Flush and clear the persistence context.
     * Useful to force synchronization with database.
     */
    protected void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

    /**
     * Persist and flush an entity.
     * 
     * @param entity the entity to persist
     * @return the persisted entity
     */
    protected <T> T persistAndFlush(T entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }
}
