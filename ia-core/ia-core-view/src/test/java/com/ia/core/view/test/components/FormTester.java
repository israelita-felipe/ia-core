package com.ia.core.view.test.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page Object para operações genéricas em formulários Vaadin.
 * Fornece métodos para preenchimento, validação e manipulação de campos de formulário.
 *
 * @author ia-core-apps
 * @since 2026-03-27
 * @see ADR-039
 */
public class FormTester {

    private final WebDriver driver;

    /**
     * Construtor do FormTester.
     *
     * @param driver WebDriver instance
     */
    public FormTester(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Preenche um campo de texto pelo label.
     *
     * @param label label do campo
     * @param value valor a ser preenchido
     */
    public void fillTextField(String label, String value) {
        List<WebElement> fields = driver.findElements(By.xpath(
            "//div[contains(@class, 'v-textfield') and .//label[contains(text(),'" + label + "')]] | " +
            "//input[../label[contains(text(),'" + label + "')]]"
        ));
        
        if (fields.isEmpty()) {
            // Tenta buscar pelo caption do Vaadin
            fields = driver.findElements(By.xpath(
                "//input[contains(@placeholder, '" + label + "')] | " +
                "//input[@id and contains(@id, '" + label.toLowerCase() + "')]"
            ));
        }
        
        if (fields.isEmpty()) {
            throw new IllegalArgumentException("Campo não encontrado: " + label);
        }
        
        fields.get(0).clear();
        fields.get(0).sendKeys(value);
    }

    /**
     * Preenche um campo de texto pelo ID.
     *
     * @param fieldId ID do campo
     * @param value valor a ser preenchido
     */
    public void fillTextFieldById(String fieldId, String value) {
        WebElement field = driver.findElement(By.id(fieldId));
        field.clear();
        field.sendKeys(value);
    }

    /**
     * Obtém o valor de um campo de texto pelo label.
     *
     * @param label label do campo
     * @return valor do campo
     */
    public String getTextFieldValue(String label) {
        List<WebElement> fields = driver.findElements(By.xpath(
            "//div[contains(@class, 'v-textfield') and .//label[contains(text(),'" + label + "')]] | " +
            "//input[../label[contains(text(),'" + label + "')]]"
        ));
        
        if (fields.isEmpty()) {
            return null;
        }
        
        return fields.get(0).getAttribute("value");
    }

    /**
     * Seleciona um valor em um ComboBox pelo label.
     *
     * @param label label do campo
     * @param value valor a ser selecionado
     */
    public void selectComboBox(String label, String value) {
        List<WebElement> comboBoxes = driver.findElements(By.xpath(
            "//div[contains(@class, 'v-combobox') and .//label[contains(text(),'" + label + "')]]"
        ));
        
        if (comboBoxes.isEmpty()) {
            throw new IllegalArgumentException("ComboBox não encontrado: " + label);
        }
        
        WebElement comboBox = comboBoxes.get(0);
        comboBox.click();
        
        // Digita o valor no campo de busca do ComboBox
        WebElement input = comboBox.findElement(By.tagName("input"));
        input.clear();
        input.sendKeys(value);
        
        // Seleciona a opção
        try {
            WebElement option = driver.findElement(By.xpath(
                "//div[@class='v-filterselect-suggestmenu' and contains(text(),'" + value + "')]"
            ));
            option.click();
        } catch (Exception e) {
            // Pressiona Enter para selecionar
            input.sendKeys(org.openqa.selenium.Keys.ENTER);
        }
    }

    /**
     * Preenche uma data em um DateField.
     *
     * @param label label do campo
     * @param date data no formato yyyy-MM-dd
     */
    public void fillDateField(String label, String date) {
        List<WebElement> dateFields = driver.findElements(By.xpath(
            "//div[contains(@class, 'v-datefield') and .//label[contains(text(),'" + label + "')]]"
        ));
        
        if (dateFields.isEmpty()) {
            throw new IllegalArgumentException("DateField não encontrado: " + label);
        }
        
        WebElement input = dateFields.get(0).findElement(By.tagName("input"));
        input.clear();
        input.sendKeys(date);
    }

    /**
     * Verifica se um campo está habilitado.
     *
     * @param label label do campo
     * @return true se o campo estiver habilitado
     */
    public boolean isFieldEnabled(String label) {
        List<WebElement> fields = driver.findElements(By.xpath(
            "//div[contains(@class, 'v-textfield') and .//label[contains(text(),'" + label + "')]]"
        ));
        
        if (fields.isEmpty()) {
            return false;
        }
        
        String disabled = fields.get(0).getAttribute("disabled");
        return disabled == null;
    }

    /**
     * Verifica se um campo contém erro de validação.
     *
     * @param label label do campo
     * @return true se houver erro de validação
     */
    public boolean hasValidationError(String label) {
        List<WebElement> fields = driver.findElements(By.xpath(
            "//div[contains(@class, 'v-textfield') and .//label[contains(text(),'" + label + "')]]"
        ));
        
        if (fields.isEmpty()) {
            return false;
        }
        
        WebElement parent = fields.get(0).findElement(By.xpath("./.."));
        return parent.getAttribute("class").contains("error");
    }

    /**
     * Limpa o valor de um campo.
     *
     * @param label label do campo
     */
    public void clearField(String label) {
        List<WebElement> fields = driver.findElements(By.xpath(
            "//div[contains(@class, 'v-textfield') and .//label[contains(text(),'" + label + "')]]"
        ));
        
        if (fields.isEmpty()) {
            return;
        }
        
        fields.get(0).clear();
    }

    /**
     * Verifica se o formulário está visível.
     *
     * @return true se o formulário estiver visível
     */
    public boolean isVisible() {
        List<WebElement> forms = driver.findElements(By.xpath(
            "//div[contains(@class, 'v-formlayout')]"
        ));
        
        return !forms.isEmpty();
    }

    /**
     * Obtém a contagem de campos do formulário.
     *
     * @return número de campos
     */
    public int getFieldCount() {
        List<WebElement> fields = driver.findElements(By.xpath(
            "//div[contains(@class, 'v-textfield')] | " +
            "//div[contains(@class, 'v-combobox')] | " +
            "//div[contains(@class, 'v-datefield')]"
        ));
        
        return fields.size();
    }
}