package com.ia.core.communication.service.model.modelomensagem.dto;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Serviço responsável por processar variáveis em templates de mensagens.
 * <p>
 * Este serviço utiliza o enum {@link VariavelTemplate} para substituir
 * placeholders no formato {{chave}} pelos valores reais em tempo de execução.
 * </p>
 *
 * <p>Exemplo de uso:
 * <pre>{@code
 * String template = "Olá {{nome}}, seu agendamento é {{evento.data}}";
 * String processed = processador.processar(template, contexto);
 * }</pre></p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Component
public class ProcessadorVariaveis {

    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{(\\w+(?:\\.\\w+)?)\\}\\}");

    /**
     * Processa um template substituindo todas as variáveis por seus valores.
     * <p>
     * O método procura por placeholders no formato {@code {{chave}}}
     * e substitui pelo valor obtido através do {@link VariavelTemplate}.
     * </p>
     *
     * @param template o template com placeholders (não pode ser null)
     * @param contexto o objeto contendo os dados para substituição (pode ser null)
     * @return o template processado com os valores substituídos, ou string vazia se template for null
     */
    public String processar(String template, Map<Variavel, Object> contexto) {
        if (template == null || template.isEmpty()) {
            return "";
        }

        Matcher matcher = PLACEHOLDER_PATTERN.matcher(template);
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
     * Processa um template usando um objeto que implementa {@link HasVariavel}.
     *
     * @param template o template com placeholders (não pode ser null)
     * @param dto o objeto que fornece as variáveis (pode ser null)
     * @return o template processado com os valores substituídos
     */
    public String processar(String template, HasVariavel dto) {
        return processar(template, dto != null ? dto.getContext() : null);
    }

    /**
     * Obtém o valor de uma variável específica.
     *
     * @param chave a chave da variável (ex: "nome", "evento.data")
     * @param contexto o mapa contendo os dados (pode ser null)
     * @return o valor formatado como string ou string vazia se não encontrado
     */
    private String obterValor(String chave, Map<Variavel, Object> contexto) {
        if (contexto == null) {
            return "";
        }
        Variavel variavel = VariavelTemplate.buscarPorChave(chave).orElse(null);
        if (variavel == null) {
            return "";
        }
        Object valor = contexto.get(variavel);
        return valor != null ? valor.toString() : "";
    }

    /**
     * Obtém a lista de todas as chaves das variáveis disponíveis.
     * Útil para exibir na interface de cadastro de templates.
     *
     * @return lista de chaves no formato {@code {{chave}}}
     */
    public List<String> listarChavesDisponiveis() {
        return VariavelTemplate.listarChaves();
    }
}