package com.ia.core.view;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Disabled;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base class for E2E tests.
 * Provides common functionality and configuration for end-to-end testing.
 *
 * <p>This is an abstract base class and should not be executed as a test.
 * Concrete test classes should extend this class and implement actual test methods.
 *
 * @author Israel Araújo
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled("Abstract base class - not a test")
public abstract class CoreBaseE2ETest extends CoreBaseUnitTest {

    /**
     * WebDriver para testes E2E.
     * Subclasses devem inicializar este campo antes de usar.
     */
    protected WebDriver driver;

    /**
     * Inicializa o WebDriver para testes E2E.
     * Deve ser chamado em @BeforeEach se necessário.
     */
    protected void initDriver() {
        if (driver == null) {
            driver = new ChromeDriver();
        }
    }
}
