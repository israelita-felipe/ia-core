package com.ia.core.llm.service.ferramenta;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * Catálogo de ferramentas Spring AI expostas ao orquestrador e MCP (piloto ADR-048).
 * <p>
 * Define ferramentas básicas que podem ser utilizadas por agentes de IA para validação
 * e testes de funcionalidade de tool calling.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Component
public class LlmToolCatalog {

  @Tool(name = "llm_echo", description = "Ferramenta de eco para validação de funcionalidade de tool calling. " +
                                         "Recebe um texto como entrada e retorna o mesmo texto como saída. " +
                                         "Útil para testar se o orquestrador consegue invocar ferramentas corretamente " +
                                         "e verificar se os parâmetros estão sendo passados adequadamente. " +
                                         "Retorna string vazia se o texto de entrada for nulo.")
  public String echo(@ToolParam(description = "Texto de entrada a ser ecoado. Pode ser qualquer string. " +
                                   "Se for nulo, retorna string vazia. Útil para testar passagem de parâmetros.",
                      required = true) String text) {
    return text == null ? "" : text;
  }
}
