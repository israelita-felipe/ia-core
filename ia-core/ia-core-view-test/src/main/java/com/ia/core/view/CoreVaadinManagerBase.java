package com.ia.core.view;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Base class for Vaadin manager tests.
 * Provides common functionality and configuration for Vaadin manager testing.
 * Uses Mockito for mocking dependencies and isolating the manager layer.
 *
 * <p>This is an abstract base class and should not be executed as a test.
 * Concrete test classes should extend this class and implement actual test methods.
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
 * class MyManagerTest extends CoreVaadinManagerBase {
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
@Disabled("Abstract base class - not a test")
@ExtendWith(MockitoExtension.class)
@DisplayName("Vaadin Manager Base")
public abstract class CoreVaadinManagerBase extends CoreBaseUnitTest {
}
