package com.ia.core.view.help.documentation;

import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import java.util.Collections;

/**
 * Renderizador seguro de Markdown usando flexmark com sanitizacao OWASP.
 *
 * <p>Este renderizador fornece:
 * - Parse seguro de Markdown usando flexmark-java
 * - Geracao de HTML bem-formado
 * - Suporte a extensoes (tabelas, strikethrough, etc.)
 * - Sanitizacao OWASP contra XSS
 *
 * @author Israel Araujo
 */
public class FlexmarkHelpRenderer {

    private final Parser parser;
    private final HtmlRenderer renderer;
    private final PolicyFactory sanitizer;

    /**
     * Cria um novo renderizador de Markdown seguro.
     */
    public FlexmarkHelpRenderer() {
        // Configuracao do parser com extensoes
        MutableDataSet options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, Collections.singletonList(
            TablesExtension.create()
        ));

        this.parser = Parser.builder(options).build();
        this.renderer = HtmlRenderer.builder(options).build();

        // Configuracao do sanitizador OWASP - apenas tags seguras
              this.sanitizer = new HtmlPolicyBuilder()
            .allowElements(
                "p", "br", "b", "strong", "i", "em", "u", "code", "pre",
                "ul", "ol", "li", "h1", "h2", "h3", "h4", "h5", "h6",
                "a", "table", "thead", "tbody", "tr", "td", "th",
                "blockquote", "hr", "img"
            )
            .allowAttributes("href").onElements("a")
            .allowAttributes("src", "alt").matching(value ->
            value.startsWith("data:image/png;base64,") ||
                value.startsWith("data:image/jpeg;base64,") ||
                value.startsWith("data:image/gif;base64,") ||
                value.startsWith("http://") ||
                value.startsWith("https://")
        ).onElements("img")
            .allowAttributes("colspan", "rowspan").onElements("td", "th")
            .allowAttributes("align").onElements("table", "tr", "td", "th")

            .allowUrlProtocols("http", "https", "data")

            .toFactory();
    }

    /**
     * Renderiza Markdown para HTML sanitizado.
     *
     * @param markdown conteudo em Markdown
     * @return HTML sanitizado a partir do Markdown
     */
    public String renderToHtml(String markdown) {
        if (markdown == null || markdown.isEmpty()) {
            return "";
        }

        try {
            // Parse Markdown -> AST
            var document = parser.parse(markdown);

            // Renderiza AST -> HTML
            String html = renderer.render(document);

            // Sanitiza o HTML para prevenir XSS
            return sanitizeHtml(html);
        } catch (Exception e) {
            // Em caso de erro, retorna conteudo como texto puro seguro
            return escapeHtml(markdown);
        }
    }

    /**
     * Sanitiza HTML usando OWASP Java HTML Sanitizer.
     *
     * @param html HTML a ser sanitizado
     * @return HTML sanitizado
     */
    private String sanitizeHtml(String html) {
        return sanitizer.sanitize(html);
    }

    /**
     * Escapa caracteres especiais de HTML para renderizacao segura como texto.
     *
     * @param text texto a ser escapado
     * @return texto escapado
     */
    private String escapeHtml(String text) {
        return text
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("\'", "&#x27;");
    }

    /**
     * Renderiza Markdown e retorna em formato pronto para insercao em componente Vaadin.
     *
     * @param markdown conteudo em Markdown
     * @return HTML gerado a partir do Markdown
     */
    public String render(String markdown) {
        return renderToHtml(markdown);
    }
}
