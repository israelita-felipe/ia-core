package com.ia.core.llm.support;

import com.ia.core.llm.model.prompt.FinalidadePromptEnum;
import com.ia.core.llm.model.prompt.Prompt;
import com.ia.core.llm.model.template.Template;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Factory for creating test data entities.
 * Provides fluent builders for test data creation.
 *
 * Features:
 * - Template builders for all domain entities
 * - Customizable entity attributes
 * - Batch creation support
 * - Random data generation
 *
 * Usage:
 * <pre>
 * {@code
 * Prompt prompt = testDataFactory.criarPrompt()
 *     .titulo("Test Prompt")
 *     .finalidade(FinalidadePromptEnum.RESPOSTA_TEXTUAL)
 *     .build();
 * }
 * </pre>
 *
 * @author Israel Araújo
 */

@Component
public class TestDataFactory {

    private int counter = 0;

    // ========== Template Builders ==========

    public TemplateBuilder criarTemplate() {
        return new TemplateBuilder(++counter);
    }

    public Template criarTemplate(Long id) {
        return new TemplateBuilder(id.intValue()).build();
    }

    public Template criarTemplate(Long id, String titulo) {
        return new TemplateBuilder(id.intValue())
            .titulo(titulo)
            .build();
    }

    // ========== Prompt Builders ==========

    public PromptBuilder criarPrompt() {
        return new PromptBuilder(++counter);
    }

    public Prompt criarPrompt(Long id) {
        return new PromptBuilder(id.intValue()).build();
    }

    public Prompt criarPrompt(Long id, FinalidadePromptEnum finalidade) {
        return new PromptBuilder(id.intValue())
            .finalidade(finalidade)
            .build();
    }

    public List<Prompt> criarListaPrompts(int quantidade) {
        List<Prompt> prompts = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            prompts.add(criarPrompt().build());
        }
        return prompts;
    }

    // ========== Template Builder ==========

    public static class TemplateBuilder {
        private final int id;
        private String titulo;
        private String conteudo;
        private boolean exigeContexto = false;

        public TemplateBuilder(int id) {
            this.id = id;
            this.titulo = "Template " + id;
            this.conteudo = "Conteúdo do template " + id;
        }

        public TemplateBuilder titulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public TemplateBuilder conteudo(String conteudo) {
            this.conteudo = conteudo;
            return this;
        }

        public TemplateBuilder exigeContexto(boolean exige) {
            this.exigeContexto = exige;
            return this;
        }

        public Template build() {
            return Template.builder()
                .titulo(titulo)
                .conteudo(conteudo)
                .exigeContexto(exigeContexto)
                .build();
        }
    }

    // ========== Prompt Builder ==========

    public static class PromptBuilder {
        private final int id;
        private String titulo;
        private String entrada;
        private FinalidadePromptEnum finalidade;
        private Template template;

        public PromptBuilder(int id) {
            this.id = id;
            this.titulo = "Prompt " + id;
            this.entrada = "Entrada do prompt " + id;
            this.finalidade = FinalidadePromptEnum.RESPOSTA_TEXTUAL;
        }

        public PromptBuilder titulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public PromptBuilder entrada(String entrada) {
            this.entrada = entrada;
            return this;
        }

        public PromptBuilder finalidade(FinalidadePromptEnum finalidade) {
            this.finalidade = finalidade;
            return this;
        }

        public PromptBuilder template(Template template) {
            this.template = template;
            return this;
        }

        public Prompt build() {
            return Prompt.builder()
                .titulo(titulo)
                .entrada(entrada)
                .finalidade(finalidade)
                .template(template)
                .build();
        }
    }

    // ========== Utility Methods ==========

    /**
     * Generates a random UUID string.
     *
     * @return random UUID string
     */
    public String randomUuid() {
        return UUID.randomUUID().toString();
    }

}
