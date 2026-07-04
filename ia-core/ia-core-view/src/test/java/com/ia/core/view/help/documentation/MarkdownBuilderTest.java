package com.ia.core.view.help.documentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes para MarkdownBuilder.
 *
 * <p>Testa a geração de documentação Markdown padronizada.
 *
 * @author Israel Araújo
 */
class MarkdownBuilderTest {

    private MarkdownBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new MarkdownBuilder();
    }

    @Test
    void shouldBuildBasicMarkdown() {
        HelpMetadata metadata = new HelpMetadata();
        metadata.setViewName("TestView");
        metadata.setTitle("Título da View");
        metadata.setDescription("Descrição da view");

        String markdown = builder.build(metadata);

        assertTrue(markdown.contains("# Título da View"));
        assertTrue(markdown.contains("## Descrição"));
        assertTrue(markdown.contains("Descrição da view"));
    }

    @Test
    void shouldBuildMarkdownWithFields() {
        HelpMetadata metadata = new HelpMetadata();
        metadata.setViewName("TestView");
        metadata.setTitle("Título da View");

        FieldMetadata field = new FieldMetadata();
        field.setFieldName("Nome");
        field.setFieldType("TextField");
        field.setHelpText("Digite o nome");
        field.setHelperText("Mínimo 3 caracteres");
        field.setRequired(true);

        metadata.addField(field);

        String markdown = builder.build(metadata);

        assertTrue(markdown.contains("## Campos"));
        assertTrue(markdown.contains("### Nome"));
        assertTrue(markdown.contains("Digite o nome"));
        assertTrue(markdown.contains("Mínimo 3 caracteres"));
        assertTrue(markdown.contains("**Obrigatório:** Sim"));
    }

    @Test
    void shouldBuildMarkdownWithRoute() {
        HelpMetadata metadata = new HelpMetadata();
        metadata.setViewName("TestView");
        metadata.setTitle("Título da View");
        metadata.setRoute("minha-view");

        String markdown = builder.build(metadata);

        assertTrue(markdown.contains("Rota:"));
        assertTrue(markdown.contains("minha-view"));
    }

    @Test
    void shouldEscapeMarkdownSpecialCharacters() {
        HelpMetadata metadata = new HelpMetadata();
        metadata.setViewName("TestView");
        metadata.setTitle("Título com * caracteres _ especiais");

        String markdown = builder.build(metadata);

        // Verifica que os caracteres foram escapados
        assertTrue(markdown.contains("\\*"));
        assertTrue(markdown.contains("\\_"));
    }

    @Test
    void shouldBuildEmptyMarkdownForNullTitle() {
        HelpMetadata metadata = new HelpMetadata();
        metadata.setViewName("TestView");
        // title is null

        String markdown = builder.build(metadata);

        // Deve usar o viewName como título
        assertTrue(markdown.contains("# TestView"));
    }

    @Test
    void shouldIncludeFieldTypeAndPlaceholder() {
        HelpMetadata metadata = new HelpMetadata();
        metadata.setViewName("TestView");
        metadata.setTitle("Test");

        FieldMetadata field = new FieldMetadata();
        field.setFieldName("Email");
        field.setFieldType("EmailField");
        field.setPlaceholder("exemplo@email.com");
        field.setRequired(false);

        metadata.addField(field);

        String markdown = builder.build(metadata);

        assertTrue(markdown.contains("**Tipo:** `EmailField`"));
         assertTrue(markdown.contains("**Placeholder:** `exemplo@email.com`"));
         assertFalse(markdown.contains("**Obrigatório:** Sim"));
    }
}
