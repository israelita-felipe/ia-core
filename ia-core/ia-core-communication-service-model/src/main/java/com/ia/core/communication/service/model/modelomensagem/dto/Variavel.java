package com.ia.core.communication.service.model.modelomensagem.dto;

import java.util.Map;

/**
 * Interface que representa uma variável de template de mensagem.
 * Implementada por enums que definem as variáveis disponíveis nos modelos de mensagem.
 *
 * <p>Cada variável possui uma chave (identificador usado no template como {{chave}})
 * e um valor que é retornado a partir de um contexto de dados.</p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>
 * // No template: "Olá {{nome}}, bem-vindo à {{igreja}}!"
 * // A variável retorna o valor do contexto fornecido
 * </pre>
 *
 * @author Israel Araújo
 * @version 1.0.0
 */
public interface Variavel {

  /**
   * Retorna a chave da variável que será usada no template.
   * A chave é o identificador usado entre chaves duplas no template.
   *
   * <p>Exemplos de chaves:</p>
   * <ul>
   *   <li>"nome" -> {{nome}}</li>
   *   <li>"igreja.nome" -> {{igreja.nome}}</li>
   *   <li>"data.evento" -> {{data.evento}}</li>
   * </ul>
   *
   * @return chave da variável (não deve ser nulo ou vazio)
   */
  String getChave();

  /**
   * Retorna o valor da variável como string, dado um objeto de contexto.
   * O contexto contém os dados necessários para determinar o valor da variável.
   *
   * <p>O contexto pode ser diferentes tipos de objetos dependendo do cenário:</p>
   * <ul>
   *   <li>Map<String, Object> - para dados genéricos</li>
   * </ul>
   *
   * @param contexto objeto contendo dados para substituição (pode ser null)
   * @return valor da variável como string (nunca null, retorna string vazia se não houver dados)
   */
  default String getValor(Map<Variavel,Object> contexto){
      if(contexto==null){
          return "";
      }
      return ((Map<Variavel, Object>) contexto).get(this) != null ?
          ((Map<Variavel, Object>) contexto).get(this).toString() : "";
  }

  /**
   * Retorna a descrição da variável para exibição na interface.
   * Esta descrição é usada para mostrar ao usuário quais variáveis estão disponíveis.
   *
   * @return descrição amigável da variável
   */
  default String getDescricao() {
    return "";
  }
}
