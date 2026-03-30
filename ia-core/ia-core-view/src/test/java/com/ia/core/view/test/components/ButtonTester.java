package com.ia.core.view.test.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page Object para operações genéricas em botões Vaadin.
 * Fornece métodos para clique, verificação de estado e manipulação de botões.
 *
 * @author ia-core-apps
 * @since 2026-03-27
 * @see ADR-039
 */
public class ButtonTester {

    private final WebDriver driver;

    /**
     * Construtor do ButtonTester.
     *
     * @param driver WebDriver instance
     */
    public ButtonTester(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Clica em um botão pelo texto visível.
     *
     * @param buttonText texto do botão
     */
    public void clickByText(String buttonText) {
        List<WebElement> buttons = driver.findElements(By.xpath(
            "//button[contains(text(),'" + buttonText + "')] | " +
            "//span[contains(@class, 'v-button') and contains(text(),'" + buttonText + "')] | " +
            "//div[contains(@class, 'v-button') and contains(text(),'" + buttonText + "')]"
        ));
        
        if (buttons.isEmpty()) {
            throw new IllegalArgumentException("Botão não encontrado: " + buttonText);
        }
        buttons.get(0).click();
    }

    /**
     * Clica em um botão pelo ID do elemento.
     *
     * @param buttonId ID do botão
     */
    public void clickById(String buttonId) {
        WebElement button = driver.findElement(By.id(buttonId));
        button.click();
    }

    /**
     * Verifica se um botão está habilitado.
     *
     * @param buttonText texto do botão
     * @return true se o botão estiver habilitado
     */
    public boolean isEnabled(String buttonText) {
        List<WebElement> buttons = driver.findElements(By.xpath(
            "//button[contains(text(),'" + buttonText + "')] | " +
            "//span[contains(@class, 'v-button') and contains(text(),'" + buttonText + "')]"
        ));
        
        if (buttons.isEmpty()) {
            return false;
        }
        
        String disabled = buttons.get(0).getAttribute("disabled");
        return disabled == null;
    }

    /**
     * Verifica se um botão está visível.
     *
     * @param buttonText texto do botão
     * @return true se o botão estiver visível
     */
    public boolean isVisible(String buttonText) {
        List<WebElement> buttons = driver.findElements(By.xpath(
            "//button[contains(text(),'" + buttonText + "')] | " +
            "//span[contains(@class, 'v-button') and contains(text(),'" + buttonText + "')]"
        ));
        
        if (buttons.isEmpty()) {
            return false;
        }
        
        return buttons.get(0).isDisplayed();
    }

    /**
     * Clica em um botão e aguarda processamento.
     *
     * @param buttonText texto do botão
     * @param waitMillis tempo de espera em milissegundos
     */
    public void clickAndWait(String buttonText, int waitMillis) {
        clickByText(buttonText);
        try {
            Thread.sleep(waitMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Verifica se existe um botão com o texto especificado.
     *
     * @param buttonText texto do botão
     * @return true se o botão existir
     */
    public boolean exists(String buttonText) {
        List<WebElement> buttons = driver.findElements(By.xpath(
            "//button[contains(text(),'" + buttonText + "')] | " +
            "//span[contains(@class, 'v-button') and contains(text(),'" + buttonText + "')]"
        ));
        return !buttons.isEmpty();
    }

    /**
     * Obtém o texto de um botão pelo ID.
     *
     * @param buttonId ID do botão
     * @return texto do botão
     */
    public String getButtonText(String buttonId) {
        WebElement button = driver.findElement(By.id(buttonId));
        return button.getText();
    }
}