package com.ia.core.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
 * class MyE2ETest extends BaseE2ETest {
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
@SpringBootTest
@DisplayName("End-to-End Test")
public abstract class CoreBaseE2ETest {

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
