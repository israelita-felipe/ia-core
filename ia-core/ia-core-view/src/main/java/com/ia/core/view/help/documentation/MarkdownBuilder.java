package com.ia.core.view.help.documentation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Constrói documento Markdown a partir de metadados.
 *
 * <p>Esta classe gera documentação estruturada seguindo padrões
 * de CommonMark/GitHub Markdown.
 *
 * <p>Formato padrão gerado:
 * <pre>
 * # [Título da View]
 *
 * > **Rota:** `rota`
 *
 * ## Descrição
 *
 * [Descrição da view]
 *
 * ## Campos
 *
 * ### [Nome do Campo]
 *
 * > **Tipo:** `TextField`
 * > **Obrigatório:** Sim
 * > **Helper Text:** texto de ajuda inline
 *
 * [Texto de ajuda do campo]
 *
 * ---
 *
 * *Documentação gerada automaticamente*
 * </pre>
 *
 * @author Israel Araújo
 */
public class MarkdownBuilder {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Constrói documento Markdown a partir de metadados.
     *
     * @param metadata metadados da view
     * @return documento Markdown formatado
     */
    public String build(HelpMetadata metadata) {
        StringBuilder md = new StringBuilder();

        // Título principal
        buildTitle(md, metadata);

        // Rota (se houver)
        buildRoute(md, metadata);

        // Descrição
        buildDescription(md, metadata);

        // Campos
        buildFields(md, metadata);

        // Rodapé
        buildFooter(md);

        return md.toString();
    }

    /**
     * Constrói o título principal.
     */
    private void buildTitle(StringBuilder md, HelpMetadata metadata) {
        String title = metadata.getTitle() != null ? metadata.getTitle() : metadata.getViewName();
        md.append("# ").append(escapeMarkdown(title)).append("\n\n");
    }

    /**
     * Constrói a seção de rota.
     */
    private void buildRoute(StringBuilder md, HelpMetadata metadata) {
        if (metadata.getRoute() != null && !metadata.getRoute().isEmpty()) {
            md.append("> **Rota:** `").append(escapeMarkdown(metadata.getRoute())).append("`\n\n");
        }
    }

    /**
     * Constrói a seção de descrição.
     */
    private void buildDescription(StringBuilder md, HelpMetadata metadata) {
        if (metadata.getDescription() != null && !metadata.getDescription().isEmpty()) {
            md.append("## Descrição\n\n");
            md.append(escapeMarkdown(metadata.getDescription())).append("\n\n");
        }
    }

    /**
     * Constrói a seção de campos.
     */
    private void buildFields(StringBuilder md, HelpMetadata metadata) {
        if (metadata.getFields().isEmpty()) {
            return;
        }

        md.append("## Campos\n\n");

        for (FieldMetadata field : metadata.getFields()) {
            buildFieldSection(md, field);
        }
    }

    /**
     * Constrói a documentação de um campo individual.
     */
    private void buildFieldSection(StringBuilder md, FieldMetadata field) {
        // Título do campo
        md.append("### ").append(escapeMarkdown(field.getFieldName())).append("\n\n");

        // Metadados do campo
        if (field.getFieldType() != null) {
            md.append("> **Tipo:** `").append(escapeMarkdown(field.getFieldType())).append("`\n");
        }

        if (field.isRequired()) {
            md.append("> **Obrigatório:** Sim\n");
        }

        if (field.getHelperText() != null && !field.getHelperText().isEmpty()) {
            md.append("> **Ajuda inline:** ").append(escapeMarkdown(field.getHelperText())).append("\n");
        }

        if (field.getPlaceholder() != null && !field.getPlaceholder().isEmpty()) {
            md.append("> **Placeholder:** `").append(escapeMarkdown(field.getPlaceholder())).append("`\n");
        }

        md.append("\n");

        // Texto de ajuda
        if (field.getHelpText() != null && !field.getHelpText().isEmpty()) {
            md.append(escapeMarkdown(field.getHelpText())).append("\n\n");
        }
    }

    /**
     * Constrói o rodapé do documento.
     */
    private void buildFooter(StringBuilder md) {
        md.append("---\n\n");
        md.append("*Documentação gerada automaticamente pelo sistema de Help Online*\n");
    }

    /**
     * Escapa caracteres especiais do Markdown.
     *
     * @param text texto a escapar
     * @return texto escapado
     */
    private String escapeMarkdown(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("\\", "\\\\")
                   .replace("*", "\\*")
                   .replace("_", "\\_")
                   .replace("`", "\\`")
                   .replace("[", "\\[")
                   .replace("]", "\\]");
    }
}
