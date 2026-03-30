package com.ia.core.view.test.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page Object para navegação entre Views Vaadin.
 * Fornece métodos para navegar pelo menu e verificar rotas.
 *
 * @author ia-core-apps
 * @since 2026-03-27
 * @see ADR-039
 */
public class NavigationTester {

    private final WebDriver driver;
    private static final String BASE_URL = "http://localhost:8080";

    /**
     * Construtor do NavigationTester.
     *
     * @param driver WebDriver instance
     */
    public NavigationTester(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Navega para uma rota específica.
     *
     * @param route rota Vaadin (ex: "pessoa", "evento")
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
     * Navega para a página inicial.
     */
    public void navigateToHome() {
        navigateTo("");
    }

    /**
     * Verifica se a URL atual corresponde à rota esperada.
     *
     * @param expectedRoute rota esperada
     * @return true se corresponder
     */
    public boolean isAtRoute(String expectedRoute) {
        String currentUrl = driver.getCurrentUrl();
        if (expectedRoute == null || expectedRoute.isEmpty()) {
            return currentUrl.equals(BASE_URL + "/") || currentUrl.equals(BASE_URL);
        }
        String expectedUrl = BASE_URL + (expectedRoute.startsWith("/") ? expectedRoute : "/" + expectedRoute);
        return currentUrl.contains(expectedUrl);
    }

    /**
     * Clica em um item do menu pelo texto.
     *
     * @param menuItemText texto do item de menu
     */
    public void clickMenuItem(String menuItemText) {
        // Procura por links ou botões no menu
        List<WebElement> menuItems = driver.findElements(By.xpath(
            "//a[contains(text(),'" + menuItemText + "')] | " +
            "//button[contains(text(),'" + menuItemText + "')] | " +
            "//span[contains(text(),'" + menuItemText + "')]"
        ));
        
        for (WebElement item : menuItems) {
            if (item.isDisplayed()) {
                item.click();
                return;
            }
        }
        throw new RuntimeException("Menu item não encontrado: " + menuItemText);
    }

    /**
     * Clica no item de menu pelo ID ou classe.
     *
     * @param menuId ID do elemento de menu
     */
    public void clickMenuItemById(String menuId) {
        WebElement menuItem = driver.findElement(By.id(menuId));
        menuItem.click();
    }

    /**
     * Expande um submenu.
     *
     * @param parentMenuItem texto do item pai do submenu
     */
    public void expandSubmenu(String parentMenuItem) {
        WebElement parent = driver.findElement(By.xpath(
            "//div[contains(@class, 'menu')]//span[contains(text(),'" + parentMenuItem + "')]"
        ));
        parent.click();
    }

    /**
     * Verifica se existe um item de menu visível.
     *
     * @param menuItemText texto do item
     * @return true se o item existir e estiver visível
     */
    public boolean menuItemExists(String menuItemText) {
        try {
            List<WebElement> menuItems = driver.findElements(By.xpath(
                "//a[contains(text(),'" + menuItemText + "')] | " +
                "//button[contains(text(),'" + menuItemText + "')] | " +
                "//span[contains(text(),'" + menuItemText + "')]"
            ));
            for (WebElement item : menuItems) {
                if (item.isDisplayed()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtém o texto do item de menu atual (breadcrumb ou título).
     *
     * @return texto do título da página atual
     */
    public String getCurrentPageTitle() {
        // Procura pelo título da página (h1, h2, ou elemento com classe title/page-title)
        List<WebElement> titles = driver.findElements(By.xpath(
            "//h1 | //h2[contains(@class, 'title')] | //div[contains(@class, 'page-title')]"
        ));
        if (!titles.isEmpty()) {
            return titles.get(0).getText();
        }
        return null;
    }

    /**
     * Verifica se a navegação foi bem-sucedida comparando o título.
     *
     * @param expectedTitle título esperado da página
     * @return true se o título corresponder
     */
    public boolean isAtPage(String expectedTitle) {
        String currentTitle = getCurrentPageTitle();
        return currentTitle != null && currentTitle.contains(expectedTitle);
    }

    /**
     * Clica no botão de logout/sair.
     */
    public void logout() {
        List<WebElement> logoutButtons = driver.findElements(By.xpath(
            "//button[contains(text(),'Sair')] | " +
            "//button[contains(text(),'Logout')] | " +
            "//a[contains(text(),'Sair')] | " +
            "//a[contains(text(),'Logout')]"
        ));
        
        for (WebElement btn : logoutButtons) {
            if (btn.isDisplayed()) {
                btn.click();
                return;
            }
        }
    }

    /**
     * Obtém a URL atual.
     *
     * @return URL atual
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}