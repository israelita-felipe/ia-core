package com.ia.test.service;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Base class for service layer tests.
 * Provides common functionality and configuration for service tests.
 * Uses Mockito for mocking dependencies and isolating the service layer.
 *
 * <p>This is an abstract base class and should not be executed as a test.
 * Concrete test classes should extend this class and implement actual test methods.
 *
 * <p>Characteristics:
 * - Mockito extension for mocking
 * - No Spring context (pure unit tests)
 * - Service layer isolation
 *
 * <p>Usage:
 * <pre>
 * {@code
 * @ExtendWith(MockitoExtension.class)
 * class MyServiceTest extends CoreServiceBase {
 *     @Mock
 *     private MyRepository repository;
 *
 *     @InjectMocks
 *     private MyService service;
 *
 *     @Test
 *     void testServiceMethod() {
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
@DisplayName("Service Base")
public abstract class CoreServiceBase extends CoreBaseUnitTest {
}
