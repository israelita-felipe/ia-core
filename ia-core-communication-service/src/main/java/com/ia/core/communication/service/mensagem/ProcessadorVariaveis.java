package com.ia.core.communication.service.mensagem;

import com.ia.core.communication.model.mensagem.VariavelTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Serviço responsável por processar variáveis em templates de mensagens.
 * <p>
 * Este serviço utiliza o enum {@link VariavelTemplate} para substituir
 * placeholders no formato {{chave}} pelos valores reais em tempo de execução.
 * </p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>
 * String template = "Olá {{nome}}, seu agendamento é {{evento.data}}";
 * String processed = processar(template, contexto);
 * </pre>
 * </p>
 */
public class ProcessadorVariaveis {

    /**
     * Processa um template substituindo todas as variáveis por seus valores.
     * <p>
     * O método procura por placeholders no formato {@code {{chave}}}
     * e substitui pelo valor obtido através do {@link VariavelTemplate}.
     * </p>
     *
     * @param template o template com placeholders
     * @param contexto o objeto contendo os dados para substituição
     * @return o template processado com os valores substituídos
     */
    public String processar(String template, Object contexto) {
        if (template == null || template.isEmpty()) {
            return "";
        }

        // Regex para encontrar placeholders {{chave}}
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\{\\{(\\w+(?:\\.\\w+)?)\\}\\}");
        java.util.regex.Matcher matcher = pattern.matcher(template);

        StringBuilder resultado = new StringBuilder();
        while (matcher.find()) {
            String chave = matcher.group(1);
            String valor = obterValor(chave, contexto);
            matcher.appendReplacement(resultado, Matcher.quoteReplacement(valor));
        }
        matcher.appendTail(resultado);

        return resultado.toString();
    }

    /**
     * Obtém o valor de uma variável específica.
     * 
     * @param chave a chave da variável (ex: "nome", "evento.data")
     * @param contexto o objeto contendo os dados
     * @return o valor formatado como string ou string vazia se não encontrado
     */
    private String obterValor(String chave, Map<String, Object> contexto) {
        // Primeiro tenta encontrar na enum
        return VariavelTemplate.buscarPorChave(chave)
                .map(variavel -> variavel.getValor(contexto))
                .orElseGet(() -> {
                    // Se não encontrar na enum, tenta buscar em mapas
                    if (contexto instanceof Map) {
                        return (() contexto).getOrDefault(chave, "").toString();
                    }
                    return "";
                });
    }

    /**
     * Obtém a lista de todas as chaves das variáveis disponíveis.
     * Útil para exibir na interface de cadastro de templates.
     * 
     * @return lista de chaves das variáveis disponíveis
     */
    public List<String> listarChavesDisponiveis() {
        return VariavelTemplate.listarChaves();
    }

}