package com.ia.core.view.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe base para testes de formulários Vaadin.
 * Fornece métodos para preenchimento, validação e submissão de formulários.
 *
 * @author ia-core-apps
 * @since 2026-03-27
 * @see ADR-039
 */
public abstract class AbstractFormTest extends AbstractViewTest {

    /**
     * Obtém o formulário principal da página.
     *
     * @return elemento do formulário
     */
    protected WebElement getForm() {
        var forms = driver.findElements(By.xpath(
            "//div[contains(@class, 'v-formlayout')] | " +
            "//form"
        ));
        assertFalse(forms.isEmpty(), "Nenhum formulário encontrado");
        return forms.get(0);
    }

    /**
     * Preenche um campo de texto pelo nome do campo (label).
     *
     * @param fieldLabel label do campo
     * @param value valor a ser preenchido
     */
    protected void fillTextField(String fieldLabel, String value) {
        List<WebElement> fields = driver.findElements(By.xpath(
            "//div[contains(@class, 'v-textfield') and .//label[contains(text(),'" + fieldLabel + "')]] | " +
            "//input[../label[contains(text(),'" + fieldLabel + "')]]"
        ));
        
        if (fields.isEmpty()) {
            fail("Campo não encontrado: " + fieldLabel);
        }
        
        fields.get(0).clear();
        fields.get(0).sendKeys(value);
    }

    /**
     * Preenche um campo de texto pelo ID do elemento.
     *
     * @param fieldId ID do campo
     * @param value valor a ser preenchido
     */
    protected void fillTextFieldById(String fieldId, String value) {
        WebElement field = driver.findElement(By.id(fieldId));
        field.clear();
        field.sendKeys(value);
    }

    /**
     * Obtém o valor de um campo de texto pelo nome do campo.
     *
     * @param fieldLabel label do campo
     * @return valor do campo
     */
    protected String getTextFieldValue(String fieldLabel) {
        List<WebElement> fields = driver.findElements(By.xpath(
            "//div[contains(@class, 'v-textfield') and .//label[contains(text(),'" + fieldLabel + "')]] | " +
            "//input[../label[contains(text(),'" + fieldLabel + "')]]"
        ));
        
        if (fields.isEmpty()) {
            return null;
        }
        
        return fields.get(0).getAttribute("value");
    }

    /**
     * Verifica se um campo está habilitado.
     *
     * @param fieldLabel label do campo
     * @return true se o campo estiver habilitado
     */
    protected boolean isFieldEnabled(String fieldLabel) {
        List<WebElement> fields = driver.findElements(By.xpath(
            "//div[contains(@class, 'v-textfield') and .//label[contains(text(),'" + fieldLabel + "')]]"
        ));
        
        if (fields.isEmpty()) {
            return false;
        }
        
        String disabled = fields.get(0).getAttribute("disabled");
        return disabled == null;
    }

    /**
     * Verifica se há erros de validação visíveis na página.
     *
     * @return true se houver erros
     */
    protected boolean hasValidationErrors() {
        List<WebElement> errors = driver.findElements(By.xpath(
            "//div[contains(@class, 'v-errorindicator')] | " +
            "//span[contains(@class, 'v-error')] | " +
            "//div[contains(@class, 'error')]"
        ));
        return !errors.isEmpty();
    }

    /**
     * Clica no botão de salvar/submeter formulário.
     *
     * @param buttonText texto do botão (padrão: "Salvar", "Save", "Enviar")
     */
    protected void clickSaveButton(String... buttonText) {
        String button = buttonText.length > 0 ? buttonText[0] : "Salvar";
        
        List<WebElement> buttons = driver.findElements(By.xpath(
            "//button[contains(text(),'" + button + "')] | " +
            "//span[contains(@class, 'v-button') and contains(text(),'" + button + "')]"
        ));
        
        assertFalse(buttons.isEmpty(), "Botão não encontrado: " + button);
        buttons.get(0).click();
    }

    /**
     * Verifica se existe uma notificação de erro.
     *
     * @return true se houver notificação de erro
     */
    protected boolean hasErrorNotification() {
        List<WebElement> notifications = driver.findElements(By.xpath(
            "//div[contains(@class, 'v-notification') and contains(@class, 'error')] | " +
            "//div[contains(@class, 'v-notification') and contains(text(), 'erro')]"
        ));
        return !notifications.isEmpty();
    }

    /**
     * Verifica se o formulário foi enviado com sucesso (sem erros).
     *
     * @return true se o formulário foi enviado sem erros
     */
    protected boolean isFormSubmittedSuccessfully() {
        return !hasErrorNotification();
    }
}