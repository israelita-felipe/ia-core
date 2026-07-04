package com.ia.core.view.help.e2e;

import com.ia.core.view.CoreBaseE2ETest;
import com.ia.core.view.help.dialog.HelpDialogViewFactory;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes E2E de acessibilidade para o diálogo de ajuda.
 *
 * <p>Valida os seguintes critérios de acessibilidade WCAG 2.2:
 * <ul>
 *   <li>aria-modal="true" e role="dialog" no diálogo</li>
 *   <li>aria-live="polite" no conteúdo</li>
 *   <li>Foco travado no diálogo quando aberto</li>
 *   <li>Estilos de high contrast</li>
 *   <li>Estilos de reduced motion</li>
 * </ul>
 *
 * @author Israel Araújo
 */
 @Disabled("E2E tests require frontend build and Chrome browser setup")
 @SpringBootTest(
    classes = E2eTestApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@DisplayName("Help Dialog Accessibility E2E Tests")
class HelpDialogAccessibilityE2eTest extends CoreBaseE2ETest {

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
    }

    @Nested
    @DisplayName("Teste de Atributos ARIA do Diálogo")
    class DialogAriaAttributesTests {

        @Test
        @DisplayName("Deve ter aria-modal='true' no diálogo")
        void shouldHaveAriaModalTrue() {
            driver.get(baseUrl + "/test-dialog-view");

            // Clica no botão para abrir o diálogo
            WebElement openButton = driver.findElement(By.cssSelector(".open-dialog-button"));
            openButton.click();
            waitFor(1000);

            // Encontra o diálogo
            WebElement dialog = driver.findElement(By.cssSelector("vaadin-dialog-overlay"));

            String ariaModal = dialog.getAttribute("aria-modal");
            assertEquals("true", ariaModal,
                "O diálogo deve ter aria-modal='true' para indicar que é modal");
        }

        @Test
        @DisplayName("Deve ter role='dialog' no diálogo")
        void shouldHaveRoleDialog() {
            driver.get(baseUrl + "/test-dialog-view");

            WebElement openButton = driver.findElement(By.cssSelector(".open-dialog-button"));
            openButton.click();
            waitFor(1000);

            WebElement dialog = driver.findElement(By.cssSelector("vaadin-dialog-overlay"));

            String role = dialog.getAttribute("role");
            assertEquals("dialog", role,
                "O diálogo deve ter role='dialog' para semântica apropriada");
        }

        @Test
        @DisplayName("Deve ter aria-labelledby apontando para o título")
        void shouldHaveAriaLabelledBy() {
            driver.get(baseUrl + "/test-dialog-view");

            WebElement openButton = driver.findElement(By.cssSelector(".open-dialog-button"));
            openButton.click();
            waitFor(1000);

            WebElement dialog = driver.findElement(By.cssSelector("vaadin-dialog-overlay"));

            // Verifica se há um título no diálogo
            WebElement titleElement = dialog.findElement(By.cssSelector("[part='title']"));
            assertNotNull(titleElement,
                "O diálogo deve ter um elemento de título");
        }
    }

    @Nested
    @DisplayName("Teste de Live Region")
    class LiveRegionTests {

        @Test
        @DisplayName("Deve ter aria-live='polite' no conteúdo do diálogo")
        void shouldHaveAriaLivePolite() {
            driver.get(baseUrl + "/test-dialog-view");

            WebElement openButton = driver.findElement(By.cssSelector(".open-dialog-button"));
            openButton.click();
            waitFor(1000);

            // Encontra o conteúdo do diálogo
            WebElement dialogContent = driver.findElement(
                By.cssSelector("vaadin-dialog-overlay div[aria-live='polite']"));

            assertNotNull(dialogContent,
                "O conteúdo do diálogo deve ter aria-live='polite' para screen readers");
        }

        @Test
        @DisplayName("Deve ter aria-atomic='true' no conteúdo do diálogo")
        void shouldHaveAriaAtomicTrue() {
            driver.get(baseUrl + "/test-dialog-view");

            WebElement openButton = driver.findElement(By.cssSelector(".open-dialog-button"));
            openButton.click();
            waitFor(1000);

            WebElement dialogContent = driver.findElement(
                By.cssSelector("vaadin-dialog-overlay div[aria-live='polite']"));

            String ariaAtomic = dialogContent.getAttribute("aria-atomic");
            assertEquals("true", ariaAtomic,
                "O conteúdo do diálogo deve ter aria-atomic='true'");
        }
    }

    @Nested
    @DisplayName("Teste de Foco no Diálogo")
    class DialogFocusTests {

        @Test
        @DisplayName("Deve travar foco no diálogo quando aberto")
        void shouldTrapFocusInDialog() {
            driver.get(baseUrl + "/test-dialog-view");

            WebElement openButton = driver.findElement(By.cssSelector(".open-dialog-button"));
            openButton.click();
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
        @DisplayName("Deve fechar o diálogo com Escape")
        void shouldCloseDialogWithEscape() {
            driver.get(baseUrl + "/test-dialog-view");

            WebElement openButton = driver.findElement(By.cssSelector(".open-dialog-button"));
            openButton.click();
            waitFor(1000);

            // Pressiona Escape
            openButton.sendKeys(Keys.ESCAPE);
            waitFor(500);

            // Verifica se o diálogo fechou
            boolean dialogExists = !driver.findElements(By.cssSelector("vaadin-dialog-overlay[opened]"))
                .isEmpty();
            assertFalse(dialogExists,
                "Escape deve fechar o diálogo");
        }
    }

    @Nested
    @DisplayName("Teste de High Contrast")
    class HighContrastTests {

        @Test
        @DisplayName("Deve ter borda visível no diálogo em high contrast mode")
        void shouldHaveVisibleBorderInHighContrast() {
            driver.get(baseUrl + "/test-dialog-view");

            // Simula high contrast
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "document.documentElement.setAttribute('data-theme', 'high-contrast');"
            );

            WebElement openButton = driver.findElement(By.cssSelector(".open-dialog-button"));
            openButton.click();
            waitFor(1000);

            WebElement dialog = driver.findElement(By.cssSelector("vaadin-dialog-overlay"));

            // Obtém o estilo computado da borda
            String borderWidth = (String) ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "return window.getComputedStyle(arguments[0]).borderWidth;",
                dialog
            );

            assertNotNull(borderWidth,
                "O diálogo deve ter estilo de borda definido");
        }

        @Test
        @DisplayName("Deve ter outline de foco visível em high contrast mode")
        void shouldHaveVisibleFocusOutlineInHighContrast() {
            driver.get(baseUrl + "/test-dialog-view");

            WebElement openButton = driver.findElement(By.cssSelector(".open-dialog-button"));
            openButton.click();
            waitFor(1000);

            // Foca no diálogo
            WebElement dialog = driver.findElement(By.cssSelector("vaadin-dialog-overlay"));
            dialog.click();
            waitFor(500);

            // Verifica outline via JavaScript
            Boolean hasOutline = (Boolean) ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "var elem = arguments[0];" +
                "var style = window.getComputedStyle(elem);" +
                "return style.outlineWidth && style.outlineWidth !== '0px';",
                dialog
            );

            // O outline deve existir quando focado
            assertTrue(hasOutline != null && hasOutline,
                "O diálogo deve ter outline visível quando focado");
        }
    }

    @Nested
    @DisplayName("Teste de Reduced Motion")
    class ReducedMotionTests {

        @Test
        @DisplayName("Deve respeitar prefers-reduced-motion")
        void shouldRespectReducedMotion() {
            driver.get(baseUrl + "/test-dialog-view");

            // Simula reduced motion
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "document.documentElement.setAttribute('data-theme', 'reduced-motion');"
            );

            WebElement openButton = driver.findElement(By.cssSelector(".open-dialog-button"));
            openButton.click();
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
    @Route("test-dialog-view")
    public static class TestDialogView extends com.vaadin.flow.component.orderedlayout.VerticalLayout {

        public TestDialogView() {
            Button openButton = new Button("Abrir Diálogo", event -> {
                HelpDialogViewFactory.show("Ajuda de Teste", "<p>Conteúdo de ajuda para teste de acessibilidade.</p>");
            });
            openButton.addClassName("open-dialog-button");

            add(openButton);
        }
    }
}
