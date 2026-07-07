package com.ia.test;

import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.TargetSelector;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;

/**
 * Base class for unit tests.
 * Provides common functionality and configuration for unit tests.
 * Includes fixture pattern using Instancio for automatic test data generation.
 *
 * <p>This is an abstract base class and should not be executed as a test.
 * Concrete test classes should extend this class and implement actual test methods.
 *
 * <p>Characteristics:
 * - Instancio for automatic test data generation
 * - No Spring context (pure unit tests)
 * - Test layer isolation
 *
 * @author Israel Araújo
 */
@Disabled("Abstract base class - not a test")
@DisplayName("Unit Test")
public abstract class CoreBaseUnitTest {

    /**
     * Creates a fixture instance of the specified class using Instancio.
     * Generates a fully populated object with random but reproducible data.
     *
     * @param clazz the class to create an instance of
     * @param <T> the type of the class
     * @return a new instance of the specified class with random data
     */
    protected <T> T createFixture(Class<T> clazz) {
        return Instancio.create(clazz);
    }

    /**
     * Creates a fixture instance with custom field values.
     *
     * @param clazz the class to create an instance of
     * @param selector the field selector to customize
     * @param value the value to set for the selected field
     * @param <T> the type of the class
     * @return a new instance with the specified field customized
     */
    protected <T> T createFixture(Class<T> clazz, TargetSelector selector, Object value) {
        return Instancio.of(clazz)
            .set(selector, value)
            .create();
    }

    /**
     * Creates a list of fixture instances.
     *
     * @param clazz the class to create instances of
     * @param size the number of instances to create
     * @param <T> the type of the class
     * @return a list of new instances with random data
     */
    protected <T> java.util.List<T> createFixtureList(Class<T> clazz, int size) {
        return Instancio.ofList(clazz).size(size).create();
    }

    /**
     * Creates a Model template for reusable fixture configurations.
     *
     * @param clazz the class to create a model for
     * @param <T> the type of the class
     * @return a Model instance that can be customized and reused
     */
    protected <T> Model<T> createFixtureModel(Class<T> clazz) {
        return Instancio.of(clazz).toModel();
    }
}