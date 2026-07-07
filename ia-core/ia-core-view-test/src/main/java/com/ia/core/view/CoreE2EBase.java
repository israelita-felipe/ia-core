package com.ia.core.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base class for end-to-end tests.
 * Provides common functionality and configuration for E2E tests.
 * Uses Selenium WebDriver for browser automation.
 *
 * <p>This is an abstract base class and should not be executed as a test.
 * Concrete test classes should extend this class and implement actual test methods.
 *
 * <p>Characteristics:
 * - Full Spring Boot application context
 * - PostgreSQL database via TestContainers (inherited)
 * - WebDriver for browser automation
 * - Automatic browser cleanup after each test
 *
 * <p>Usage:
 * <pre>
 * {@code
 * @SpringBootTest(classes = MyApplication.class)
 * class MyE2ETest extends CoreE2EBase {
 *     @Test
 *     void testCompleteUserFlow() {
 *         // Your E2E test with WebDriver
 *     }
 * }
 * }
 * </pre>
 *
 * @author Israel Araújo
 */
@Disabled("Abstract base class - not a test")
@SpringBootTest
@DisplayName("E2E Base")
public abstract class CoreE2EBase {

    protected WebDriver driver;

    @BeforeEach
    void setUpWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDownWebDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
