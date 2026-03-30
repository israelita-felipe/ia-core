package com.ia.core.view.test.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page Object para operações genéricas em tabelas/grades Vaadin.
 * Fornece métodos para ordenação, filtragem e navegação em grids.
 *
 * @author ia-core-apps
 * @since 2026-03-27
 * @see ADR-039
 */
public class GridTester {

    private final WebDriver driver;

    /**
     * Construtor do GridTester.
     *
     * @param driver WebDriver instance
     */
    public GridTester(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Obtém a quantidade de linhas visíveis na grade.
     *
     * @return número de linhas
     */
    public int getRowCount() {
        List<WebElement> rows = driver.findElements(By.xpath(
            "//table[contains(@class, 'v-grid-tablewrapper')]//tbody/tr | " +
            "//div[contains(@class, 'v-grid')]//tr"
        ));
        return rows.size();
    }

    /**
     * Obtém todas as linhas da grade.
     *
     * @return lista de elementos de linha
     */
    public List<WebElement> getRows() {
        return driver.findElements(By.xpath(
            "//table[contains(@class, 'v-grid-tablewrapper')]//tbody/tr | " +
            "//div[contains(@class, 'v-grid')]//tr"
        ));
    }

    /**
     * Clica em uma célula específica da grade.
     *
     * @param rowIndex índice da linha (0-based)
     * @param columnIndex índice da coluna (0-based)
     */
    public void clickCell(int rowIndex, int columnIndex) {
        List<WebElement> rows = getRows();
        if (rowIndex >= rows.size()) {
            throw new IllegalArgumentException("Linha não encontrada: " + rowIndex);
        }
        
        List<WebElement> cells = rows.get(rowIndex).findElements(By.xpath(".//td"));
        if (columnIndex >= cells.size()) {
            throw new IllegalArgumentException("Coluna não encontrada: " + columnIndex);
        }
        
        cells.get(columnIndex).click();
    }

    /**
     * Clica em uma linha específica.
     *
     * @param rowIndex índice da linha (0-based)
     */
    public void clickRow(int rowIndex) {
        List<WebElement> rows = getRows();
        if (rowIndex >= rows.size()) {
            throw new IllegalArgumentException("Linha não encontrada: " + rowIndex);
        }
        rows.get(rowIndex).click();
    }

    /**
     * Verifica se uma célula contém um texto específico.
     *
     * @param rowIndex índice da linha
     * @param columnIndex índice da coluna
     * @param text texto a verificar
     * @return true se o texto estiver presente
     */
    public boolean cellContainsText(int rowIndex, int columnIndex, String text) {
        List<WebElement> rows = getRows();
        if (rowIndex >= rows.size()) {
            return false;
        }
        
        List<WebElement> cells = rows.get(rowIndex).findElements(By.xpath(".//td"));
        if (columnIndex >= cells.size()) {
            return false;
        }
        
        String cellText = cells.get(columnIndex).getText();
        return cellText != null && cellText.contains(text);
    }

    /**
     * Obtém o texto de uma célula específica.
     *
     * @param rowIndex índice da linha
     * @param columnIndex índice da coluna
     * @return texto da célula
     */
    public String getCellText(int rowIndex, int columnIndex) {
        List<WebElement> rows = getRows();
        if (rowIndex >= rows.size()) {
            return null;
        }
        
        List<WebElement> cells = rows.get(rowIndex).findElements(By.xpath(".//td"));
        if (columnIndex >= cells.size()) {
            return null;
        }
        
        return cells.get(columnIndex).getText();
    }

    /**
     * Verifica se a grade contém um texto específico.
     *
     * @param text texto a verificar
     * @return true se o texto for encontrado
     */
    public boolean containsText(String text) {
        return driver.getPageSource().toLowerCase().contains(text.toLowerCase());
    }

    /**
     * Obtém a quantidade de colunas.
     *
     * @return número de colunas
     */
    public int getColumnCount() {
        List<WebElement> headerCells = driver.findElements(By.xpath(
            "//table[contains(@class, 'v-grid-tablewrapper')]//thead//th | " +
            "//div[contains(@class, 'v-grid')]//th"
        ));
        return headerCells.size();
    }
}