package com.ia.core.view;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Base class for Vaadin manager tests.
 * Provides common functionality and configuration for Vaadin manager testing.
 * Uses Mockito for mocking dependencies and isolating the manager layer.
 *
 * <p>Characteristics:
 * - Mockito extension for mocking
 * - No Spring context (pure unit tests)
 * - Manager layer isolation
 * - Focus on business logic in managers
 *
 * <p>Usage:
 * <pre>
 * {@code
 * @ExtendWith(MockitoExtension.class)
 * class MyManagerTest extends CoreBaseVaadinManagerTest {
 *     @Mock
 *     private MyRepository repository;
 *
 *     @InjectMocks
 *     private MyManager manager;
 *
 *     @Test
 *     void testManagerMethod() {
 *         // Your test logic
 *     }
 * }
 * }
 * </pre>
 *
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Vaadin Manager Test")
public abstract class CoreBaseVaadinManagerTest extends CoreBaseUnitTest {
}
