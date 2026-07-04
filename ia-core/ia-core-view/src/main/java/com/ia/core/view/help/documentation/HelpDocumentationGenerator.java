package com.ia.core.view.help.documentation;

import com.ia.core.view.components.properties.HasHelp;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Gerador de documentação de help em Markdown.
 *
 * <p>Esta classe é o ponto de entrada principal para geração
 * de documentação automática de componentes HasHelp.
 *
 * @author Israel Araújo
 */
public class HelpDocumentationGenerator {

    private final HelpMetadataExtractor extractor;
    private final MarkdownBuilder markdownBuilder;

    /**
     * Cria um novo gerador de documentação.
     */
    public HelpDocumentationGenerator() {
        this.extractor = new HelpMetadataExtractor();
        this.markdownBuilder = new MarkdownBuilder();
    }

    /**
     * Gera documentação Markdown para um componente HasHelp.
     *
     * @param hasHelp componente para gerar documentação
     * @return documento Markdown
     */
    public String generate(HasHelp hasHelp) {
        if (hasHelp == null) {
            throw new IllegalArgumentException("HasHelp não pode ser null");
        }

        HelpMetadata metadata = extractor.extract(hasHelp);
        return markdownBuilder.build(metadata);
    }

    /**
     * Gera documentação Markdown e salva em arquivo.
     *
     * @param hasHelp componente para gerar documentação
     * @param outputPath caminho do arquivo de saída
     * @throws IOException se houver erro ao escrever o arquivo
     */
    public void generateToFile(HasHelp hasHelp, String outputPath) throws IOException {
        String markdown = generate(hasHelp);
        Files.writeString(Path.of(outputPath), markdown);
    }

    /**
     * Gera documentação Markdown e salva em arquivo.
     *
     * @param hasHelp componente para gerar documentação
     * @param outputPath caminho do arquivo de saída
     * @throws IOException se houver erro ao escrever o arquivo
     */
    public void generateToFile(HasHelp hasHelp, Path outputPath) throws IOException {
        String markdown = generate(hasHelp);
        Files.writeString(outputPath, markdown);
    }

    /**
     * Gera documentação Markdown e escreve para um OutputStream.
     *
     * <p>Este método é útil para escrita direta em streams,
     * como resposta HTTP ou arquivos grandes.
     *
     * @param hasHelp componente para gerar documentação
     * @param outputStream stream de saída onde escrever o Markdown
     * @throws IOException se houver erro ao escrever no stream
     */
    public void generateToOutputStream(HasHelp hasHelp, OutputStream outputStream) throws IOException {
        if (hasHelp == null) {
            throw new IllegalArgumentException("HasHelp não pode ser null");
        }
        if (outputStream == null) {
            throw new IllegalArgumentException("OutputStream não pode ser null");
        }

        String markdown = generate(hasHelp);
        outputStream.write(markdown.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
    }
}
