package com.ia.core.view.help;

import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.properties.HasHelp;
import com.ia.core.view.CoreBaseVaadinViewTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para {@link HelpOnlineComponent}.
 */
@DisplayName("Help Online Component Tests")
class HelpOnlineComponentTest extends CoreBaseVaadinViewTest {

    private TestViewWithHelp testView;
    private HelpOnlineComponent helpComponent;

    @BeforeEach
    void setUp() {
        testView = new TestViewWithHelp();
        helpComponent = new HelpOnlineComponent(testView);
    }

    @Nested
    @DisplayName("Testes de Inicialização")
    class InitializationTests {

        @Test
        @DisplayName("Deve ter aria-label definido")
        void shouldHaveAriaLabel() {
            // Then
            assertEquals("Ajuda", helpComponent.getElement().getAttribute("aria-label"));
        }

        @Test
        @DisplayName("Deve ter aria-expanded definido como false inicialmente")
        void shouldHaveAriaExpandedFalseInitially() {
            // Then
            assertEquals("false", helpComponent.getElement().getAttribute("aria-expanded"));
        }

        @Test
        @DisplayName("Deve ter className help-button")
        void shouldHaveHelpButtonClassName() {
            // Then
            assertTrue(helpComponent.getClassName().contains("help-button"));
        }

        @Test
        @DisplayName("Deve iniciar invisível")
        void shouldStartInvisible() {
            // Then
            assertFalse(helpComponent.isVisible());
        }
    }

    @Nested
    @DisplayName("Testes de Geração de Texto de Ajuda")
    class GenerateHelpTextTests {

        @Test
        @DisplayName("Deve gerar texto de ajuda vazio quando não há componentes")
        void shouldGenerateEmptyHelpTextWhenNoComponents() {
            // Given
            TestViewWithHelp view = new TestViewWithHelp();
            HelpOnlineComponent component = new HelpOnlineComponent(view);

            // When
            String result = component.generateHelpText();

            // Then
            assertNotNull(result);
        }

        @Test
        @DisplayName("Deve gerar texto de ajuda com customHelpText")
        void shouldGenerateHelpTextWithCustomText() {
            // Given
            String customText = "Texto customizado de ajuda";

            // When
            String result = helpComponent.generateHelpText(customText);

            // Then
            assertNotNull(result);
            assertTrue(result.contains(customText));
        }

        @Test
        @DisplayName("Deve gerar texto de ajuda sem customHelpText")
        void shouldGenerateHelpTextWithoutCustomText() {
            // When
            String result = helpComponent.generateHelpText((String) null);

            // Then
            assertNotNull(result);
        }
    }

    @Nested
    @DisplayName("Testes de Geração com Screenshots")
    class GenerateHelpTextWithScreenshotsTests {

        @Test
        @DisplayName("Deve aceitar callback para geração com screenshots")
        @Disabled("Requires component attached to DOM")
        void shouldAcceptCallbackForGenerationWithScreenshots() {
            // Given
            String customText = "Texto customizado";

            // When
            helpComponent.generateHelpTextWithScreenshots(customText, markdown -> {
                // Then
                assertNotNull(markdown);
            });
        }

        @Test
        @DisplayName("Deve aceitar callback sem customHelpText")
        @Disabled("Requires component attached to DOM")
        void shouldAcceptCallbackWithoutCustomHelpText() {
            // When
            helpComponent.generateHelpTextWithScreenshots(markdown -> {
                // Then
                assertNotNull(markdown);
            });
        }
    }

    @Nested
    @DisplayName("Testes de Locale")
    class LocaleTests {

        @Test
        @DisplayName("Deve retornar locale padrão")
        void shouldReturnDefaultLocale() {
            // When
            Locale locale = helpComponent.getLocale();

            // Then
            assertNotNull(locale);
        }
    }

    /**
     * View de teste que implementa HasHelp.
     */
    private static class TestViewWithHelp extends FormView<String> implements HasHelp {

        public TestViewWithHelp() {
            super(new FormViewModel<String>(FormViewModelConfig.of(true)) {
                @Override
                public boolean isReadOnly() {
                    return super.isReadOnly();
                }
            });
        }

        @Override
        public String getHelpTitle() {
            return "Tela de Teste";
        }

        @Override
        public String getHelpDescription() {
            return "Descrição da tela de teste";
        }
    }
}
