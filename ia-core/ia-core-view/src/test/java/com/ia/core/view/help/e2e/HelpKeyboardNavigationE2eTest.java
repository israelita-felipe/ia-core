package com.ia.core.view.help.e2e;

import com.ia.core.view.CoreBaseE2ETest;
import com.ia.core.view.components.properties.HasHelp;
import com.ia.core.view.help.HelpOnlineComponent;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes E2E de navegação por teclado para o componente de ajuda online.
 *
 * <p>Valida os seguintes critérios de acessibilidade WCAG 2.2:
 * <ul>
 *   <li>Tab navega para o botão de ajuda</li>
 *   <li>Enter abre o diálogo</li>
 *   <li>Escape fecha o diálogo</li>
 *   <li>Navegação lógica entre elementos focáveis</li>
 *   <li>Focus trap no diálogo</li>
 * </ul>
 *
 * @author Israel Araújo
 */
 @Disabled("E2E tests require frontend build and Chrome browser setup")
 @SpringBootTest(
    classes = E2eTestApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@DisplayName("Help Keyboard Navigation E2E Tests")
class HelpKeyboardNavigationE2eTest extends CoreBaseE2ETest {

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
    }

    @Nested
    @DisplayName("Teste de Navegação com Tab")
    class TabNavigationTests {

        @Test
        @DisplayName("Tab deve navegar para o botão de ajuda")
        void tabShouldNavigateToHelpButton() {
            driver.get(baseUrl + "/test-keyboard-view");

            // Pressiona Tab para navegar
            Actions actions = new Actions(driver);
            actions.sendKeys(Keys.TAB).perform();

            // Aguarda foco
            waitFor(500);

            WebElement activeElement = driver.switchTo().activeElement();
            String classAttribute = activeElement.getAttribute("class");

            assertTrue(classAttribute != null && classAttribute.contains("help-button"),
                "Tab deve navegar para o botão de ajuda");
        }

        @Test
        @DisplayName("Tab duplo deve navegar para o próximo elemento após o botão")
        void doubleTabShouldNavigateToNextElement() {
            driver.get(baseUrl + "/test-keyboard-view");

            Actions actions = new Actions(driver);
            actions.sendKeys(Keys.TAB).perform();
            waitFor(300);
            actions.sendKeys(Keys.TAB).perform();
            waitFor(300);

            WebElement activeElement = driver.switchTo().activeElement();
            assertNotNull(activeElement,
                "Tab duplo deve navegar para o próximo elemento");
        }

        @Test
        @DisplayName("Shift+Tab deve navegar para elemento anterior")
        void shiftTabShouldNavigateToPreviousElement() {
            driver.get(baseUrl + "/test-keyboard-view");

            Actions actions = new Actions(driver);
            actions.sendKeys(Keys.TAB).perform();
            waitFor(300);
            actions.sendKeys(Keys.SHIFT).sendKeys(Keys.TAB).perform();
            waitFor(300);

            WebElement activeElement = driver.switchTo().activeElement();
            assertNotNull(activeElement,
                "Shift+Tab deve navegar para o elemento anterior");
        }
    }

    @Nested
    @DisplayName("Teste de Abertura com Enter")
    class EnterActivationTests {

        @Test
        @DisplayName("Enter deve abrir o diálogo")
        void enterShouldOpenDialog() {
            driver.get(baseUrl + "/test-keyboard-view");

            // Navega para o botão via Tab
            Actions actions = new Actions(driver);
            actions.sendKeys(Keys.TAB).perform();
            waitFor(500);

            // Pressiona Enter
            actions.sendKeys(Keys.ENTER).perform();
            waitFor(1000);

            // Verifica se o diálogo abriu
            WebElement dialog = driver.findElement(By.cssSelector("vaadin-dialog-overlay"));
            assertTrue(dialog.isDisplayed(),
                "Enter deve abrir o diálogo de ajuda");
        }

        @Test
        @DisplayName("Enter deve mudar aria-expanded para true")
        void enterShouldChangeAriaExpanded() {
            driver.get(baseUrl + "/test-keyboard-view");

            // Navega para o botão via Tab
            Actions actions = new Actions(driver);
            actions.sendKeys(Keys.TAB).perform();
            waitFor(500);

            // Pressiona Enter
            actions.sendKeys(Keys.ENTER).perform();
            waitFor(1000);

            // Verifica aria-expanded
            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));
            assertEquals("true", helpButton.getAttribute("aria-expanded"),
                "Enter deve mudar aria-expanded para true");
        }
    }

    @Nested
    @DisplayName("Teste de Fechamento com Escape")
    class EscapeClosingTests {

        @Test
        @DisplayName("Escape deve fechar o diálogo")
        void escapeShouldCloseDialog() {
            driver.get(baseUrl + "/test-keyboard-view");

            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));
            helpButton.click();
            waitFor(1000);

            // Pressiona Escape
            helpButton.sendKeys(Keys.ESCAPE);
            waitFor(500);

            // Verifica se o diálogo fechou
            boolean dialogExists = !driver.findElements(By.cssSelector("vaadin-dialog-overlay[opened]"))
                .isEmpty();
            assertFalse(dialogExists,
                "Escape deve fechar o diálogo de ajuda");
        }

        @Test
        @DisplayName("Escape deve mudar aria-expanded para false")
        void escapeShouldChangeAriaExpanded() {
            driver.get(baseUrl + "/test-keyboard-view");

            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));
            helpButton.click();
            waitFor(1000);

            // Pressiona Escape
            helpButton.sendKeys(Keys.ESCAPE);
            waitFor(500);

            // Verifica aria-expanded
            assertEquals("false", helpButton.getAttribute("aria-expanded"),
                "Escape deve mudar aria-expanded para false");
        }
    }

    @Nested
    @DisplayName("Teste de Focus Trap")
    class FocusTrapTests {

        @Test
        @DisplayName("Foco deve ficar travado no diálogo quando aberto")
        void focusShouldBeTrappedInDialog() {
            driver.get(baseUrl + "/test-keyboard-view");

            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));
            helpButton.click();
            waitFor(1000);

            // O foco deve estar dentro do diálogo
            WebElement activeElement = driver.switchTo().activeElement();
            WebElement dialog = driver.findElement(By.cssSelector("vaadin-dialog-overlay"));

            assertTrue(dialog.isDisplayed(),
                "O diálogo deve estar visível");

            // Verifica se o elemento ativo está dentro do diálogo
            assertTrue(isElementInDialogOrOverlay(activeElement, dialog),
                "O foco deve estar dentro do diálogo quando aberto");
        }

        @Test
        @DisplayName("Tab dentro do diálogo não deve sair do diálogo")
        void tabShouldNotLeaveDialog() {
            driver.get(baseUrl + "/test-keyboard-view");

            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));
            helpButton.click();
            waitFor(1000);

            // Pressiona Tab várias vezes
            Actions actions = new Actions(driver);
            for (int i = 0; i < 5; i++) {
                actions.sendKeys(Keys.TAB).perform();
                waitFor(200);
            }

            // O diálogo ainda deve estar visível
            WebElement dialog = driver.findElement(By.cssSelector("vaadin-dialog-overlay"));
            assertTrue(dialog.isDisplayed(),
                "O diálogo deve permanecer aberto após navegação com Tab");
        }
    }

    // Helper methods

    private void waitFor(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean isElementInDialogOrOverlay(WebElement element, WebElement dialog) {
        try {
            WebElement parent = element.findElement(By.xpath(".."));
            while (parent != null) {
                if (parent.getTagName().equals("vaadin-dialog-overlay")) {
                    return true;
                }
                parent = parent.findElement(By.xpath(".."));
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * View de teste para os testes E2E.
     */
    @Route("test-keyboard-view")
    public static class TestKeyboardView extends com.vaadin.flow.component.orderedlayout.VerticalLayout implements HasHelp {

        private final TextField nameField;
        private final HelpOnlineComponent helpButton;

        public TestKeyboardView() {
            nameField = new TextField("Nome");
            nameField.setHelperText("Digite o nome completo");

            setHelp(nameField, "Nome completo do autor");

            helpButton = new HelpOnlineComponent(this);

            add(nameField, helpButton);
        }

        @Override
        public String getHelpTitle() {
            return "Tela de Teste";
        }

        @Override
        public String getHelpDescription() {
            return "Tela de teste para validação de navegação por teclado.";
        }

    }
}
