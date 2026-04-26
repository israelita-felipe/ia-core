package com.ia.core.view.test;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

/**
 * Executor customizado para testes E2E com Vaadin.
 * Gerencia o ciclo de vida do WebDriver e configurações de teste.
 *
 * @author ia-core-apps
 * @since 2026-03-27
 * @see ADR-039
 */
public class VaadinTestRunner implements Extension, BeforeEachCallback, AfterEachCallback {

    private static final String BASE_URL = "http://localhost:8080";
    private static final int TIMEOUT_SECONDS = 30;
    private static final int IMPLICIT_WAIT_SECONDS = 10;

    private WebDriver driver;

    /**
     * Obtém a URL base da aplicação.
     *
     * @return URL base
     */
    public String getBaseUrl() {
        return BASE_URL;
    }

    /**
     * Obtém o WebDriver configurado.
     *
     * @return WebDriver instance
     */
    public WebDriver getDriver() {
        return driver;
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        if (driver == null) {
            driver = createWebDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TIMEOUT_SECONDS));
            driver.manage().window().maximize();
        }
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        // Não fecha o driver automaticamente para reutilização entre testes
        // Os testes devem chamar close() explicitamente se necessário
    }

    /**
     * Cria uma instância do WebDriver com configurações padrão.
     *
     * @return WebDriver configurado
     */
    private WebDriver createWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-logging");
        options.addArguments("--log-level=3");

        return new ChromeDriver(options);
    }

    /**
     * Cria uma instância do WebDriver com configurações customizadas.
     *
     * @param options configurações do Chrome
     * @return WebDriver configurado
     */
    public WebDriver createWebDriver(ChromeOptions options) {
        if (driver != null) {
            driver.quit();
        }

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TIMEOUT_SECONDS));

        return driver;
    }

    /**
     * Navega para uma rota específica.
     *
     * @param route rota Vaadin
     */
    public void navigateTo(String route) {
        String url = BASE_URL;
        if (route != null && !route.isEmpty()) {
            if (!route.startsWith("/")) {
                route = "/" + route;
            }
            url += route;
        }
        driver.get(url);
    }

    /**
     * Aguarda o carregamento da página.
     *
     * @param millis milissegundos de espera
     */
    public void waitFor(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Fecha o WebDriver.
     */
    public void close() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    /**
     * Verifica se a página contém um texto específico.
     *
     * @param text texto a verificar
     * @return true se o texto for encontrado
     */
    public boolean pageContains(String text) {
        return driver.getPageSource().toLowerCase().contains(text.toLowerCase());
    }
}
