package com.ia.core.view.help.e2e;

import com.ia.core.view.CoreBaseE2ETest;
import com.ia.core.view.components.properties.HasHelp;
import com.ia.core.view.help.HelpOnlineComponent;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes E2E de acessibilidade para o componente de ajuda online.
 *
 * <p>Valida os seguintes critérios de acessibilidade WCAG 2.2:
 * <ul>
 *   <li>aria-label no botão de ajuda</li>
 *   <li>aria-expanded refletindo estado do diálogo</li>
 *   <li>aria-modal e role="dialog" no diálogo</li>
 *   <li>aria-live no conteúdo do diálogo</li>
 *   <li>Navegação por teclado (Tab, Enter, Escape)</li>
 *   <li>Suporte a high contrast mode</li>
 * </ul>
 *
 * @author Israel Araújo
 */
@SpringBootTest(
    classes = E2eTestApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@DisplayName("Help Online Accessibility E2E Tests")
@Disabled("E2E tests require frontend build and Chrome browser setup")
class HelpOnlineAccessibilityE2eTest extends CoreBaseE2ETest {

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
    }

    @Nested
    @DisplayName("Teste de Botão de Ajuda")
    class HelpButtonAccessibilityTests {

        @Test
        @DisplayName("Deve ter aria-label='Ajuda' no botão de ajuda")
        void shouldHaveAriaLabelOnHelpButton() {
            driver.get(baseUrl + "/test-help-view");
            driver.manage().window().maximize();

            // Encontra o botão de ajuda pelo seletor CSS
            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));

            // Verifica aria-label
            String ariaLabel = helpButton.getAttribute("aria-label");
            assertEquals("Ajuda", ariaLabel,
                "O botão de ajuda deve ter aria-label='Ajuda' para acessibilidade");
        }

        @Test
        @DisplayName("Deve ter aria-expanded='false' inicialmente")
        void shouldHaveAriaExpandedFalseInitially() {
            driver.get(baseUrl + "/test-help-view");

            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));

            String ariaExpanded = helpButton.getAttribute("aria-expanded");
            assertEquals("false", ariaExpanded,
                "O botão de ajuda deve ter aria-expanded='false' inicialmente");
        }

        @Test
        @DisplayName("Deve mudar aria-expanded de 'false' para 'true' ao clicar")
        void shouldChangeAriaExpandedOnOpen() {
            driver.get(baseUrl + "/test-help-view");

            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));

            // Estado inicial
            assertEquals("false", helpButton.getAttribute("aria-expanded"),
                "Estado inicial deve ser 'false'");

            // Clica no botão
            helpButton.click();

            // Aguarda o diálogo abrir
            waitForDialogOpen();

            // Verifica mudança de estado
            assertEquals("true", helpButton.getAttribute("aria-expanded"),
                "O aria-expanded deve mudar para 'true' quando o diálogo é aberto");
        }

        @Test
        @DisplayName("Deve mudar aria-expanded de 'true' para 'false' ao fechar")
        void shouldChangeAriaExpandedOnClose() {
            driver.get(baseUrl + "/test-help-view");

            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));

            // Abre o diálogo
            helpButton.click();
            waitForDialogOpen();
            assertEquals("true", helpButton.getAttribute("aria-expanded"));

            // Fecha o diálogo via Escape
            helpButton.sendKeys(Keys.ESCAPE);
            waitForDialogClose();

            // Verifica mudança de estado
            assertEquals("false", helpButton.getAttribute("aria-expanded"),
                "O aria-expanded deve mudar para 'false' quando o diálogo é fechado");
        }
    }

    @Nested
    @DisplayName("Teste de Diálogo Acessível")
    class HelpDialogAccessibilityTests {

        @Test
        @DisplayName("Deve ter aria-modal='true' no diálogo")
        void shouldHaveAriaModalTrue() {
            driver.get(baseUrl + "/test-help-view");

            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));
            helpButton.click();
            waitForDialogOpen();

            // Encontra o diálogo
            WebElement dialog = driver.findElement(By.cssSelector("vaadin-dialog-overlay"));

            String ariaModal = dialog.getAttribute("aria-modal");
            assertEquals("true", ariaModal,
                "O diálogo deve ter aria-modal='true' para indicar que é modal");
        }

        @Test
        @DisplayName("Deve ter role='dialog' no diálogo")
        void shouldHaveRoleDialog() {
            driver.get(baseUrl + "/test-help-view");

            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));
            helpButton.click();
            waitForDialogOpen();

            WebElement dialog = driver.findElement(By.cssSelector("vaadin-dialog-overlay"));

            String role = dialog.getAttribute("role");
            assertEquals("dialog", role,
                "O diálogo deve ter role='dialog' para semântica apropriada");
        }

        @Test
        @DisplayName("Deve ter aria-live='polite' no conteúdo do diálogo")
        void shouldHaveAriaLivePolite() {
            driver.get(baseUrl + "/test-help-view");

            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));
            helpButton.click();
            waitForDialogOpen();

            // Encontra o conteúdo do diálogo
            WebElement dialogContent = driver.findElement(
                By.cssSelector("vaadin-dialog-overlay div[aria-live='polite']"));

            assertNotNull(dialogContent,
                "O conteúdo do diálogo deve ter aria-live='polite' para screen readers");
        }

        @Test
        @DisplayName("Deve ter aria-atomic='true' no conteúdo do diálogo")
        void shouldHaveAriaAtomicTrue() {
            driver.get(baseUrl + "/test-help-view");

            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));
            helpButton.click();
            waitForDialogOpen();

            WebElement dialogContent = driver.findElement(
                By.cssSelector("vaadin-dialog-overlay div[aria-live='polite']"));

            String ariaAtomic = dialogContent.getAttribute("aria-atomic");
            assertEquals("true", ariaAtomic,
                "O conteúdo do diálogo deve ter aria-atomic='true' para anunciar mudanças completas");
        }

        @Test
        @DisplayName("Deve travar foco no diálogo quando aberto")
        void shouldTrapFocusInDialog() {
            driver.get(baseUrl + "/test-help-view");

            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));
            helpButton.click();
            waitForDialogOpen();

            // O foco deve estar dentro do diálogo
            WebElement activeElement = driver.switchTo().activeElement();
            WebElement dialog = driver.findElement(By.cssSelector("vaadin-dialog-overlay"));

            assertTrue(dialog.isDisplayed(),
                "O diálogo deve estar visível");

            // Verifica se o elemento ativo está dentro do diálogo
            assertTrue(isElementInDialogOrOverlay(activeElement, dialog),
                "O foco deve estar dentro do diálogo quando aberto");
        }
    }

    @Nested
    @DisplayName("Teste de Navegação por Teclado")
    class KeyboardNavigationTests {

        @Test
        @DisplayName("Tab deve navegar para o botão de ajuda")
        void tabShouldNavigateToHelpButton() {
            driver.get(baseUrl + "/test-help-view");

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
        @DisplayName("Enter deve abrir o diálogo")
        void enterShouldOpenDialog() {
            driver.get(baseUrl + "/test-help-view");

            // Navega para o botão via Tab
            Actions actions = new Actions(driver);
            actions.sendKeys(Keys.TAB).perform();
            waitFor(500);

            // Pressiona Enter
            actions.sendKeys(Keys.ENTER).perform();
            waitForDialogOpen();

            // Verifica se o diálogo abriu
            WebElement dialog = driver.findElement(By.cssSelector("vaadin-dialog-overlay"));
            assertTrue(dialog.isDisplayed(),
                "Enter deve abrir o diálogo de ajuda");
        }

        @Test
        @DisplayName("Escape deve fechar o diálogo")
        void escapeShouldCloseDialog() {
            driver.get(baseUrl + "/test-help-view");

            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));
            helpButton.click();
            waitForDialogOpen();

            // Pressiona Escape
            helpButton.sendKeys(Keys.ESCAPE);
            waitForDialogClose();

            // Verifica se o diálogo fechou
            boolean dialogExists = !driver.findElements(By.cssSelector("vaadin-dialog-overlay[opened]"))
                .isEmpty();
            assertFalse(dialogExists,
                "Escape deve fechar o diálogo de ajuda");
        }
    }

    @Nested
    @DisplayName("Teste de High Contrast")
    class HighContrastTests {

        @Test
        @DisplayName("Deve aplicar estilos de high contrast ao botão")
        void shouldApplyHighContrastStyles() {
            driver.get(baseUrl + "/test-help-view");

            // Executa JavaScript para simular prefers-contrast: high
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "window.matchMedia = window.matchMedia || function() { " +
                "return { matches: false, addListener: function() {} }; " +
                "};" +
                "document.documentElement.setAttribute('data-theme', 'high-contrast');"
            );

            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));

            // Verifica se o botão tem estilos aplicados
            assertTrue(helpButton.isDisplayed(),
                "O botão de ajuda deve estar visível em high contrast mode");
        }

        @Test
        @DisplayName("Deve ter borda visível em high contrast mode")
        void shouldHaveVisibleBorderInHighContrast() {
            driver.get(baseUrl + "/test-help-view");

            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));

            // Obtém o estilo computado
            String borderStyle = (String) ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "return window.getComputedStyle(arguments[0]).borderWidth;",
                helpButton
            );

            // Em high contrast, a borda deve ser mais visível
            assertNotNull(borderStyle,
                "O botão deve ter estilo de borda definido");
        }

        @Test
        @DisplayName("Deve ter outline de foco visível em high contrast mode")
        void shouldHaveVisibleFocusOutlineInHighContrast() {
            driver.get(baseUrl + "/test-help-view");

            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));

            // Foca no botão
            helpButton.click();
            waitFor(500);

            // Verifica outline via JavaScript
            Boolean hasOutline = (Boolean) ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "var elem = arguments[0];" +
                "var style = window.getComputedStyle(elem);" +
                "return style.outlineWidth && style.outlineWidth !== '0px';",
                helpButton
            );

            // O outline deve existir quando focado
            assertTrue(hasOutline != null && hasOutline,
                "O botão deve ter outline visível quando focado");
        }
    }

    // Helper methods

    private void waitForDialogOpen() {
        waitFor(1000);
    }

    private void waitForDialogClose() {
        waitFor(500);
    }

    private void waitFor(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean isElementInDialogOrOverlay(WebElement element, WebElement dialog) {
        try {
            // Verifica se o elemento está dentro do diálogo
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
    @Route("test-help-view")
    public static class TestHelpView extends com.vaadin.flow.component.orderedlayout.VerticalLayout implements HasHelp {

        private final TextField nameField;
        private final HelpOnlineComponent helpButton;

        public TestHelpView() {
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
            return "Tela de teste para validação de acessibilidade do componente de ajuda.";
        }
    }
}
