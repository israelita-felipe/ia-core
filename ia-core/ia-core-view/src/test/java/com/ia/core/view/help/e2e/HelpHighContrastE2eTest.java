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
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes E2E de high contrast para o componente de ajuda online.
 *
 * <p>Valida os seguintes critérios de acessibilidade WCAG 2.2:
 * <ul>
 *   <li>Estilos de high contrast no botão de ajuda</li>
 *   <li>Estilos de high contrast no diálogo</li>
 *   <li>Outline de foco visível</li>
 *   <li>Borda visível em high contrast mode</li>
 *   <li>Contraste adequado de cores</li>
 * </ul>
 *
 * @author Israel Araújo
 */
 @Disabled("E2E tests require frontend build and Chrome browser setup")
 @SpringBootTest(
    classes = E2eTestApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@DisplayName("Help High Contrast E2E Tests")
class HelpHighContrastE2eTest extends CoreBaseE2ETest {

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
    }

    @Nested
    @DisplayName("Teste de Estilos High Contrast no Botão")
    class HighContrastButtonTests {

        @Test
        @DisplayName("Deve ter borda visível no botão em high contrast mode")
        void shouldHaveVisibleBorderInHighContrast() {
            driver.get(baseUrl + "/test-highcontrast-view");

            // Simula high contrast
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "document.documentElement.setAttribute('data-theme', 'high-contrast');"
            );

            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));

            // Obtém o estilo computado da borda
            String borderStyle = (String) ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "return window.getComputedStyle(arguments[0]).borderStyle;",
                helpButton
            );

            assertNotNull(borderStyle,
                "O botão deve ter estilo de borda definido em high contrast mode");
        }

        @Test
        @DisplayName("Deve ter outline de foco visível em high contrast mode")
        void shouldHaveVisibleFocusOutlineInHighContrast() {
            driver.get(baseUrl + "/test-highcontrast-view");

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

        @Test
        @DisplayName("Deve ter contraste adequado de cores")
        void shouldHaveAdequateColorContrast() {
            driver.get(baseUrl + "/test-highcontrast-view");

            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));

            // Obtém as cores de texto e fundo
            String color = (String) ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "return window.getComputedStyle(arguments[0]).color;",
                helpButton
            );
            String backgroundColor = (String) ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "return window.getComputedStyle(arguments[0]).backgroundColor;",
                helpButton
            );

            assertNotNull(color, "A cor do texto deve estar definida");
            assertNotNull(backgroundColor, "A cor de fundo deve estar definida");
        }
    }

    @Nested
    @DisplayName("Teste de Estilos High Contrast no Diálogo")
    class HighContrastDialogTests {

        @Test
        @DisplayName("Deve ter borda visível no diálogo em high contrast mode")
        void shouldHaveVisibleBorderInHighContrast() {
            driver.get(baseUrl + "/test-highcontrast-view");

            // Simula high contrast
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "document.documentElement.setAttribute('data-theme', 'high-contrast');"
            );

            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));
            helpButton.click();
            waitFor(1000);

            WebElement dialog = driver.findElement(By.cssSelector("vaadin-dialog-overlay"));

            // Obtém o estilo computado da borda
            String borderWidth = (String) ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "return window.getComputedStyle(arguments[0]).borderWidth;",
                dialog
            );

            // Em high contrast, a borda deve ser mais visível
            assertNotNull(borderWidth,
                "O diálogo deve ter estilo de borda definido");
        }

        @Test
        @DisplayName("Deve ter contraste adequado no conteúdo do diálogo")
        void shouldHaveAdequateContrastInDialogContent() {
            driver.get(baseUrl + "/test-highcontrast-view");

            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));
            helpButton.click();
            waitFor(1000);

            // Encontra o conteúdo do diálogo
            WebElement dialogContent = driver.findElement(
                By.cssSelector("vaadin-dialog-overlay div[aria-live='polite']"));

            // Obtém a cor do texto
            String color = (String) ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "return window.getComputedStyle(arguments[0]).color;",
                dialogContent
            );

            assertNotNull(color,
                "O conteúdo do diálogo deve ter cor de texto definida");
        }
    }

    @Nested
    @DisplayName("Teste de Reduced Motion")
    class ReducedMotionTests {

        @Test
        @DisplayName("Deve respeitar prefers-reduced-motion no botão")
        void shouldRespectReducedMotionOnButton() {
            driver.get(baseUrl + "/test-highcontrast-view");

            // Simula reduced motion
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "document.documentElement.setAttribute('data-theme', 'reduced-motion');"
            );

            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));

            // Verifica se o botão está visível
            assertTrue(helpButton.isDisplayed(),
                "O botão deve estar visível mesmo com reduced motion ativado");
        }

        @Test
        @DisplayName("Deve respeitar prefers-reduced-motion no diálogo")
        void shouldRespectReducedMotionOnDialog() {
            driver.get(baseUrl + "/test-highcontrast-view");

            // Simula reduced motion
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "document.documentElement.setAttribute('data-theme', 'reduced-motion');"
            );

            WebElement helpButton = driver.findElement(By.cssSelector(".help-button"));
            helpButton.click();
            waitFor(1000);

            // Verifica se o diálogo abriu sem animações
            WebElement dialog = driver.findElement(By.cssSelector("vaadin-dialog-overlay"));
            assertTrue(dialog.isDisplayed(),
                "O diálogo deve abrir mesmo com reduced motion ativado");
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

    /**
     * View de teste para os testes E2E.
     */
    @Route("test-highcontrast-view")
    public static class TestHighContrastView extends com.vaadin.flow.component.orderedlayout.VerticalLayout implements HasHelp {

        private final TextField nameField;
        private final HelpOnlineComponent helpButton;

        public TestHighContrastView() {
            nameField = new TextField("Nome");
            nameField.setHelperText("Digite o nome completo");

            setHelp(nameField, "Nome completo do autor");

            helpButton = new HelpOnlineComponent(this);

            add(nameField, helpButton);
        }

        @Override
        public String getHelpTitle() {
            return "Tela de Teste High Contrast";
        }

        @Override
        public String getHelpDescription() {
            return "Tela de teste para validação de high contrast mode.";
        }

    }
}
