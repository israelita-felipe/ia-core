package com.ia.core.view.help;

import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.properties.HasHelp;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para {@link HasHelp#getHelpFields()}.
 */
class HelpExtractorTest {

    @Test
    void testGetHelpFields_WhenNoHelpSet_ReturnsEmptyMap() {
        // Given
        TestViewWithHelp view = new TestViewWithHelp();

        // When
        Map<Component, Component> result = view.getHelpFields();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetHelpFields_WhenHelpIsSet_ReturnsHelpMap() {
        // Given
        TestViewWithHelp view = new TestViewWithHelp();
        Component hasHelper = new Span("Nome");
        Component help = new Span("Digite o nome completo");
        view.setHelp(hasHelper, help);
        view.add(hasHelper); // Adiciona o componente ao view para que getHelpFields() o encontre

        // When
        Map<Component, Component> result = view.getHelpFields();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey(hasHelper));
        assertEquals(help, result.get(hasHelper));
    }

    @Test
    void testGetHelpFields_WhenMultipleHelpsSet_ReturnsAllHelpMap() {
        // Given
        TestViewWithHelp view = new TestViewWithHelp();
        Component hasHelper1 = new Span("Nome");
        Component help1 = new Span("Digite o nome");
        Component hasHelper2 = new Span("Email");
        Component help2 = new Span("Digite o email");
        view.setHelp(hasHelper1, help1);
        view.setHelp(hasHelper2, help2);
        view.add(hasHelper1, hasHelper2); // Adiciona os componentes ao view

        // When
        Map<Component, Component> result = view.getHelpFields();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsKey(hasHelper1));
        assertTrue(result.containsKey(hasHelper2));
    }

    @Test
    void testGetHelpFields_WhenHelpIsRemoved_ReturnsUpdatedMap() {
        // Given
        TestViewWithHelp view = new TestViewWithHelp();
        Component hasHelper = new Span("Nome");
        Component help = new Span("Digite o nome");
        view.setHelp(hasHelper, help);
        view.add(hasHelper); // Adiciona o componente ao view
        assertEquals(1, view.getHelpFields().size());

        // When
        view.setHelp(hasHelper, (Component) null);

        // Then
        Map<Component, Component> result = view.getHelpFields();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * View de teste que implementa HasHelp.
     */
    private static class TestViewWithHelp extends FormView<String> implements HasHelp {
        /**
         * Construtor padrão.
         *
         * @param viewModel {@link IFormViewModel}
         */
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
            return "Test View";
        }

        @Override
        public String getHelpDescription() {
            return "Descrição do teste";
        }
    }
}
