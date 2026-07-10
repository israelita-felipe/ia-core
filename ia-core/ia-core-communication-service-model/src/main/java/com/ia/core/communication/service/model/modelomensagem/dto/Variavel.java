package com.ia.core.communication.service.model.modelomensagem.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Interface que representa uma variável de template de mensagem.
 * <p>
 * Implementada por enums que definem as variáveis disponíveis nos modelos de mensagem.
 * Cada variável possui uma chave (identificador usado no template como {{chave}})
 * e um valor que é retornado a partir de um contexto de dados.
 * </p>
 *
 * <p>Exemplo de uso:
 * <pre>{@code
 * // No template: "Olá {{nome}}, bem-vindo à {{igreja.nome}}!"
 * // A variável retorna o valor do contexto fornecido
 * }</pre></p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface Variavel {

    /**
     * Retorna a chave da variável que será usada no template.
     * <p>
     * A chave é o identificador usado entre chaves duplas no template.
     * Exemplos de chaves:
     * </p>
     * <ul>
     *   <li>"nome" -&gt; {{nome}}</li>
     *   <li>"igreja.nome" -&gt; {{igreja.nome}}</li>
     *   <li>"data.evento" -&gt; {{data.evento}}</li>
     * </ul>
     *
     * @return a chave da variável (não deve ser nulo ou vazio)
     */
    String getChave();

    /**
     * Retorna o valor da variável como string, dado um objeto de contexto.
     * <p>
     * O contexto contém os dados necessários para determinar o valor da variável.
     * A chave do mapa é a própria constante do enum (ex: {@code VariavelTemplate.NOME}).
     * </p>
     *
     * @param contexto objeto contendo dados para substituição (pode ser null)
     * @return o valor da variável como string (nunca null, retorna string vazia se não houver dados)
     */
    default String getValor(Map<Variavel, Object> contexto) {
        if (contexto == null) {
            return "";
        }
        Object valor = contexto.get(this);
        return valor != null ? valor.toString() : "";
    }

    /**
     * Retorna a descrição da variável para exibição na interface.
     * <p>
     * Esta descrição é usada para mostrar ao usuário quais variáveis estão disponíveis.
     * </p>
     *
     * @return descrição amigável da variável
     */
    default String getDescricao() {
        return "";
    }
}