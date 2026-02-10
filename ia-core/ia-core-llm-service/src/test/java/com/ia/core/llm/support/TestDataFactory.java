package com.ia.core.llm.support;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.ia.core.llm.model.comando.ComandoSistema;
import com.ia.core.llm.model.comando.FinalidadeComandoEnum;
import com.ia.core.llm.model.template.Template;

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
 * ComandoSistema comando = testDataFactory.criarComandoSistema()
 *     .comTitulo("Test Command")
 *     .comFinalidade(FinalidadeComandoEnum.RESPOSTA_TEXTUAL)
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

    // ========== ComandoSistema Builders ==========

    public ComandoSistemaBuilder criarComandoSistema() {
        return new ComandoSistemaBuilder(++counter);
    }

    public ComandoSistema criarComandoSistema(Long id) {
        return new ComandoSistemaBuilder(id.intValue()).build();
    }

    public ComandoSistema criarComandoSistema(Long id, FinalidadeComandoEnum finalidade) {
        return new ComandoSistemaBuilder(id.intValue())
            .finalidade(finalidade)
            .build();
    }

    public List<ComandoSistema> criarListaComandos(int quantidade) {
        List<ComandoSistema> comandos = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            comandos.add(criarComandoSistema().build());
        }
        return comandos;
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

    // ========== ComandoSistema Builder ==========

    public static class ComandoSistemaBuilder {
        private final int id;
        private String titulo;
        private String comando;
        private FinalidadeComandoEnum finalidade;
        private Template template;
        private boolean ativo = true;

        public ComandoSistemaBuilder(int id) {
            this.id = id;
            this.titulo = "Comando " + id;
            this.comando = "Conteúdo do comando " + id;
            this.finalidade = FinalidadeComandoEnum.RESPOSTA_TEXTUAL;
        }

        public ComandoSistemaBuilder titulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public ComandoSistemaBuilder comando(String comando) {
            this.comando = comando;
            return this;
        }

        public ComandoSistemaBuilder finalidade(FinalidadeComandoEnum finalidade) {
            this.finalidade = finalidade;
            return this;
        }

        public ComandoSistemaBuilder template(Template template) {
            this.template = template;
            return this;
        }

        public ComandoSistemaBuilder ativo(boolean ativo) {
            this.ativo = ativo;
            return this;
        }

        public ComandoSistema build() {
            return ComandoSistema.builder()
                .titulo(titulo)
                .comando(comando)
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

    /**
     * Generates a unique test name based on counter.
     * 
     * @param prefix the prefix for the name
     * @return unique test name
     */
    public String uniqueName(String prefix) {
        return prefix + " " + ++counter + " " + System.currentTimeMillis();
    }
}
