package com.ia.core.view.test;

import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Classe base para testes de interface (E2E) de Views Vaadin.
 * Fornece métodos utilitários para navegação, espera e verificação de elementos.
 *
 * @author ia-core-apps
 * @since 2026-03-27
 * @see ADR-039
 */
public abstract class AbstractViewTest {

    protected static final String BASE_URL = "http://localhost:8080";
    protected static final int TIMEOUT_SECONDS = 10;

    protected WebDriver driver;

    /**
     * Configura o WebDriver antes de cada teste.
     * Pode ser sobrescrito para usar diferentes configurações.
     */
    @BeforeEach
    public void setUpWebDriver() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            driver = new ChromeDriver(options);
        }
    }

    /**
     * Obtém o WebDriver configurado.
     *
     * @return WebDriver instance
     */
    protected WebDriver getDriver() {
        return driver;
    }

    /**
     * Navega para uma rota específica da aplicação.
     *
     * @param route rota Vaadin (ex: "pessoa", "evento", "/")
     */
    protected void navigateTo(String route) {
        String url = BASE_URL;
        if (route != null && !route.isEmpty()) {
            if (!route.startsWith("/")) {
                route = "/" + route;
            }
            url += route;
        }
        driver.get(url);
        waitForViewLoaded();
    }

    /**
     * Aguarda o carregamento completo da view.
     */
    protected void waitForViewLoaded() {
        try {
            Thread.sleep(500); // Tempo base para renderização
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Verifica se a página atual contém o texto especificado.
     *
     * @param text texto a ser verificado
     * @return true se o texto for encontrado
     */
    protected boolean pageContains(String text) {
        return driver.getPageSource().contains(text);
    }

    /**
     * Clica em um botão pelo seu texto.
     *
     * @param buttonText texto do botão
     */
    protected void clickButton(String buttonText) {
        var buttons = driver.findElements(By.xpath(
            "//button[contains(text(),'" + buttonText + "')] | " +
            "//span[contains(@class, 'v-button') and contains(text(),'" + buttonText + "')]"
        ));

        if (!buttons.isEmpty()) {
            buttons.get(0).click();
        } else {
            fail("Botão não encontrado: " + buttonText);
        }
    }

    /**
     * Clica em um botão pelo seu ID.
     *
     * @param buttonId ID do botão
     */
    protected void clickButtonById(String buttonId) {
        driver.findElement(By.id(buttonId)).click();
    }

    /**
     * Verifica se existe um elemento com o ID especificado.
     *
     * @param elementId ID do elemento
     * @return true se o elemento existir
     */
    protected boolean elementExists(String elementId) {
        try {
            driver.findElement(By.id(elementId));
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Espera por um elemento com o texto especificado.
     *
     * @param text texto a ser esperado
     * @param timeoutSeconds tempo máximo de espera
     * @return true se o elemento for encontrado
     */
    protected boolean waitForText(String text, int timeoutSeconds) {
        long endTime = System.currentTimeMillis() + (timeoutSeconds * 1000L);
        while (System.currentTimeMillis() < endTime) {
            if (pageContains(text)) {
                return true;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return false;
    }

    /**
     * Fecha o WebDriver após os testes.
     */
    protected void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
