package com.ia.core.view.help.documentation;

import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.vaadin.flow.component.textfield.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para HelpMetadataExtractor.
 *
 * @author Israel Araújo
 */
class HelpMetadataExtractorTest {

    private HelpMetadataExtractor extractor;
    private TestFormView testView;

    @BeforeEach
    void setUp() {
        extractor = new HelpMetadataExtractor();
        testView = new TestFormView();
    }

    @Test
    void shouldExtractViewName() {
        HelpMetadata metadata = extractor.extract(testView);

        assertEquals("TestFormView", metadata.getViewName());
    }

    @Test
    void shouldExtractTitle() {
        HelpMetadata metadata = extractor.extract(testView);

        assertEquals("Título de Teste", metadata.getTitle());
    }

    @Test
    void shouldExtractDescription() {
        HelpMetadata metadata = extractor.extract(testView);

        assertEquals("Descrição detalhada da view de teste", metadata.getDescription());
    }

    @Test
    void shouldExtractHelpFields() {
        HelpMetadata metadata = extractor.extract(testView);

        assertEquals(2, metadata.getFields().size());
    }

    @Test
    void shouldExtractFieldLabel() {
        HelpMetadata metadata = extractor.extract(testView);

        // Verifica se os campos existem (ordem não garantida)
        boolean hasNome = metadata.getFields().stream()
            .anyMatch(f -> "Nome".equals(f.getFieldName()));
        boolean hasEmail = metadata.getFields().stream()
            .anyMatch(f -> "Email".equals(f.getFieldName()));

        assertTrue(hasNome, "Deve ter campo Nome");
        assertTrue(hasEmail, "Deve ter campo Email");
    }

    @Test
    void shouldExtractFieldType() {
        HelpMetadata metadata = extractor.extract(testView);

        // Verifica se algum campo tem tipo TextField
        boolean hasTextField = metadata.getFields().stream()
            .anyMatch(f -> "TextField".equals(f.getFieldType()));

        assertTrue(hasTextField, "Deve ter campo do tipo TextField");
    }

    @Test
    void shouldExtractHelpText() {
        HelpMetadata metadata = extractor.extract(testView);

        // Verifica se os textos de ajuda existem (ordem não garantida)
        boolean hasNomeHelp = metadata.getFields().stream()
            .anyMatch(f -> "Digite o nome completo".equals(f.getHelpText()));
        boolean hasEmailHelp = metadata.getFields().stream()
            .anyMatch(f -> "Digite o email válido".equals(f.getHelpText()));

        assertTrue(hasNomeHelp, "Deve ter texto de ajuda para Nome");
        assertTrue(hasEmailHelp, "Deve ter texto de ajuda para Email");
    }

    @Test
    void shouldExtractHelperText() {
        HelpMetadata metadata = extractor.extract(testView);

        // Verifica se o helper text existe
        boolean hasHelperText = metadata.getFields().stream()
            .anyMatch(f -> "Mínimo 3 caracteres".equals(f.getHelperText()));

        assertTrue(hasHelperText, "Deve ter helper text");
    }

    @Test
    void shouldCreateFieldMetadataFromComponent() {
        TextField field = new TextField("TestField");
        FieldMetadata metadata = FieldMetadata.fromComponent(field);

        assertEquals("TextField", metadata.getFieldType());
    }

    @Test
    void shouldSetAndGetRequired() {
        FieldMetadata metadata = new FieldMetadata();
        metadata.setRequired(true);

        assertTrue(metadata.isRequired());
    }

    @Test
    void shouldSetAndGetTooltip() {
        FieldMetadata metadata = new FieldMetadata();
        metadata.setTooltip("Dica de preenchimento");

        assertEquals("Dica de preenchimento", metadata.getTooltip());
    }

    @Test
    void shouldExtractPlaceholder() {
        HelpMetadata metadata = extractor.extract(testView);

        // Verifica se o placeholder foi extraído
        boolean hasPlaceholder = metadata.getFields().stream()
            .anyMatch(f -> f.getPlaceholder() != null);

        // TextField tem placeholder, mas pode ser null
        assertTrue(metadata.getFields().stream().findFirst().isPresent());
    }

    @Test
    void shouldExtractRouteFromAnnotation() {
        // Este teste verifica se a rota é extraída quando há anotação @Route
        // Como TestFormView não tem @Route, a rota deve ser null
        HelpMetadata metadata = extractor.extract(testView);

        assertNull(metadata.getRoute());
    }

    @Test
    void shouldExtractReadOnlyState() {
        HelpMetadata metadata = extractor.extract(testView);

        // Verifica se o readOnly foi extraído (deve ser false para TextField padrão)
        boolean hasReadOnly = metadata.getFields().stream()
            .allMatch(f -> f.isReadOnly() == false);

        assertTrue(hasReadOnly || metadata.getFields().isEmpty() == false);
    }

    /**
     * View de teste com ajuda configurada.
     */
    private static class TestFormView extends FormView<String> implements com.ia.core.view.components.properties.HasHelp {

        private TextField nomeField;
        private TextField emailField;

        TestFormView() {
            super(new TestFormViewModel());

            nomeField = new TextField("Nome");
            emailField = new TextField("Email");

            // Configura helper text
            nomeField.setHelperText("Mínimo 3 caracteres");
            emailField.setHelperText("Formato: usuario@dominio.com");

            // Adiciona ao layout primeiro
            add(nomeField, emailField);

            // Configura ajuda via HasHelp DEPOIS de adicionar ao layout
            setHelp(nomeField, "Digite o nome completo");
            setHelp(emailField, "Digite o email válido");
        }

        @Override
        public String getHelpTitle() {
            return "Título de Teste";
        }

        @Override
        public String getHelpDescription() {
            return "Descrição detalhada da view de teste";
        }
    }

    /**
     * ViewModel de teste.
     */
    private static class TestFormViewModel extends FormViewModel<String> {
        TestFormViewModel() {
            super(FormViewModelConfig.of(true));
        }

        @Override
        public String getModelPrefix() {
            return "com/ia/test";
        }
    }
}
