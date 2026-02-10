package com.ia.core.llm.support;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Base class for unit tests of Service layer components.
 * Provides common setup and utilities for service testing.
 * 
 * Features:
 * - Mockito extension for mocking
 * - Common test utilities
 * - Logging support
 * 
 * Usage:
 * <pre>
 * {@code @ExtendWith(MockitoExtension.class)}
 * class MyServiceTest extends AbstractServiceTest {
 *     // Your test methods
 * }
 * </pre>
 * 
 * @author Israel Araújo
 */
@ExtendWith(MockitoExtension.class)
public abstract class AbstractServiceTest {

    /**
     * Creates a test message for display purposes.
     * 
     * @param testName the name of the test
     * @return formatted test message
     */
    protected String testMessage(String testName) {
        return String.format("[TEST] %s - %s", 
            this.getClass().getSimpleName(), testName);
    }

    /**
     * Verifies that an exception message contains expected text.
     * 
     * @param message the exception message
     * @param expectedText the expected text
     * @return true if message contains expected text
     */
    protected boolean messageContains(String message, String expectedText) {
        return message != null && message.contains(expectedText);
    }
}
