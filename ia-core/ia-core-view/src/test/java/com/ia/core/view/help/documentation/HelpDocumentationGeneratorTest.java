package com.ia.core.view.help.documentation;

import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.vaadin.flow.component.textfield.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para HelpDocumentationGenerator.
 *
 * @author Israel Araújo
 */
class HelpDocumentationGeneratorTest {

    private HelpDocumentationGenerator generator;
    private TestFormView testView;

    @BeforeEach
    void setUp() {
        generator = new HelpDocumentationGenerator();
        testView = new TestFormView();
    }

    @Test
    void shouldGenerateMarkdown() {
        String markdown = generator.generate(testView);

        assertNotNull(markdown);
        assertFalse(markdown.isEmpty());
        assertTrue(markdown.contains("#"));
    }

    @Test
    void shouldGenerateMarkdownWithCorrectTitle() {
        String markdown = generator.generate(testView);

        assertTrue(markdown.contains("Título de Teste"));
    }

    @Test
    void shouldGenerateMarkdownWithFields() {
        String markdown = generator.generate(testView);

        assertTrue(markdown.contains("## Campos"));
        assertTrue(markdown.contains("Nome"));
        assertTrue(markdown.contains("Email"));
    }

    @Test
     void shouldGenerateToFile(@TempDir Path tempDir) throws IOException {
         Path outputFile = tempDir.resolve("test-view.md");

         generator.generateToFile(testView, outputFile.toString());

         assertTrue(outputFile.toFile().exists());
         String content = java.nio.file.Files.readString(outputFile);
         assertFalse(content.isEmpty());
     }

     @Test
     void shouldGenerateToFileWithPath(@TempDir Path tempDir) throws IOException {
         Path outputFile = tempDir.resolve("test-view-path.md");

         generator.generateToFile(testView, outputFile);

         assertTrue(outputFile.toFile().exists());
         String content = java.nio.file.Files.readString(outputFile);
         assertFalse(content.isEmpty());
         assertTrue(content.contains("Título de Teste"));
     }

    @Test
     void shouldThrowExceptionForNullView() {
         assertThrows(IllegalArgumentException.class, () -> generator.generate(null));
     }

    @Test
     void shouldGenerateToOutputStream() throws IOException {
         OutputStream outputStream = new ByteArrayOutputStream();

         generator.generateToOutputStream(testView, outputStream);

         String content = outputStream.toString();
         assertNotNull(content);
         assertFalse(content.isEmpty());
         assertTrue(content.contains("#"));
         assertTrue(content.contains("Título de Teste"));
     }

    @Test
     void shouldThrowExceptionForNullOutputStream() {
         assertThrows(IllegalArgumentException.class, () -> generator.generateToOutputStream(testView, null));
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

            setHelp(nomeField, "Digite o nome completo");
            setHelp(emailField, "Digite o email válido");

            add(nomeField, emailField);
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
