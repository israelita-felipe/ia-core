package com.ia.core.security.service.model.authorization;

import com.ia.core.security.service.model.authorization.ContextManager.ContextDefinition;
import io.jsonwebtoken.lang.Arrays;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Interface para entidades de domínio que gerenciam contextos de autorização.
 * <p>
 * Define o contrato para classes que precisam gerenciar contextos de segurança
 * e funcionalidades relacionadas à autorização dentro do sistema.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see ContextManager
 */

public interface HasContext {

  /**
   * Obtém os valores de contexto para um objeto específico.
   * <p>
   * Este método recupera todos os pares chave-valor de contexto associados ao objeto fornecido,
   * permitindo que o sistema de autorização avalie permissões baseadas no contexto atual.
   *
   * @param object o objeto para o qual os valores de contexto devem ser obtidos (não pode ser null)
   * @return mapa contendo os pares chave-valor dos contextos, nunca null. Retorna mapa vazio se
   *         nenhum contexto estiver definido
   */
  default Map<String, String> getContextValue(Object object) {
    return new HashMap<>();
  }

  /**
   * Cria o contexto inicial para esta entidade.
   * <p>
   * Este método deve ser implementado para inicializar os contextos necessários
   * para o funcionamento da entidade. A implementação deve definir os contextos
   * padrão e registrar as definições de contexto necessárias através do método
   * {@link #addContextDefinition(String, Supplier)}.
   * <p>
   * O contexto inicial deve ser criado antes que qualquer operação de autorização
   * seja realizada sobre a entidade.
   */
  public void createContext();

  /**
   * Obtém o nome do contexto associado a esta entidade.
   *
   * @return o nome do contexto, nunca null
   */
  public String getContextName();

  /**
   * Obtém os valores de definição de contexto para uma chave específica.
   * <p>
   * Este método avalia os valores de contexto fornecidos contra a definição de contexto
   * associada à chave especificada, retornando os objetos de definição correspondentes.
   *
   * @param key a chave da definição de contexto (não pode ser null)
   * @param values os valores do contexto a serem avaliados (não pode ser null)
   * @return coleção de objetos representando as definições de contexto correspondentes.
   *         Retorna coleção vazia se nenhuma correspondência for encontrada
   */
  default Collection<Object> getContextDefinitionValue(String key,
                                                       Collection<String> values) {
    return Collections.emptyList();
  }

  /**
   * Adiciona um ou mais contextos ao contexto atual.
   * <p>
   * Este método registra os contextos fornecidos no gerenciador de contextos
   * associado ao nome do contexto atual. Os contextos são adicionados à coleção
   * existente, permitindo múltiplos contextos por entidade.
   *
   * @param contexts os contextos a serem adicionados (não pode ser null).
   *                 Cada string deve representar um contexto válido
   * @throws NullPointerException se contexts for null
   */
  default void addContext(String... contexts) {
    ContextManager.get(getContextName()).addAll(Arrays.asList(contexts));
  }

  /**
   * Obtém todos os contextos associados ao contexto atual.
   * <p>
   * Retorna a coleção de nomes de contextos registrados no gerenciador de contextos
   * para o contexto atual. Esta coleção reflete os contextos que foram adicionados
   * através do método {@link #addContext(String...)}.
   *
   * @return coleção contendo os nomes dos contextos, nunca null. Retorna coleção vazia
   *         se nenhum contexto tiver sido adicionado
   */
  default Collection<String> getContexts() {
    return ContextManager.get(getContextName());
  }

  /**
   * Adiciona uma definição de contexto com seus valores possíveis.
   * <p>
   * Registra uma definição de contexto no gerenciador de contextos, associando
   * um nome de contexto a um fornecedor de valores de definição. Esta definição
   * pode ser posteriormente recuperada e avaliada durante operações de autorização.
   *
   * @param context o nome do contexto (não pode ser null). Deve ser um identificador
   *                válido de contexto
   * @param valueSupplier fornecedor dos valores de definição do contexto (não pode ser null).
   *                      Deve fornecer uma coleção de objetos {@link ContextDefinition}
   * @throws NullPointerException se context ou valueSupplier forem null
   */
  default void addContextDefinition(String context,
                                    Supplier<Collection<ContextDefinition>> valueSupplier) {
    ContextManager.putDefinition(getContextKey(context), valueSupplier);
  }

  /**
   * Obtém o fornecedor de definições para um contexto específico.
   * <p>
   * Recupera o fornecedor de valores de definição associado ao contexto
   * especificado. O fornecedor pode ser usado para obter os valores de
   * definição de contexto disponíveis para avaliação de autorização.
   *
   * @param context o nome do contexto (não pode ser null). Deve ser um
   *                identificador de contexto válido
   * @return fornecedor das definições de contexto, pode ser null se não existir
   *         nenhuma definição para o contexto especificado
   * @throws NullPointerException se context for null
   */
  default Supplier<Collection<ContextDefinition>> getContextDefinition(String context) {
    return ContextManager.getDefinition(getContextKey(context));
  }

  /**
   * Gera uma chave de contexto combinando o nome do contexto atual com um contexto específico.
   * <p>
   * Cria uma chave de contexto hierárquica no formato "nomeContextoAtual.contextoEspecifico",
   * permitindo a organização e isolamento de contextos por entidade ou módulo.
   *
   * @param context o contexto específico a ser combinado (não pode ser null). Deve ser
   *                um identificador de contexto válido
   * @return a chave de contexto formatada, nunca null. O formato é "{@code getContextName() + "." + context}"
   * @throws NullPointerException se context for null
   */
  default String getContextKey(String context) {
    return getContextName() + "." + context;
  }

  /**
   * Verifica se um valor de contexto de serviço corresponde ao valor de contexto do usuário.
   * <p>
   * Realiza uma verificação de correspondência entre o valor de contexto do serviço e o
   * valor de contexto do usuário, determinando se o serviço atende aos requisitos
   * de autorização do usuário. A correspondência é baseada em contenção de string.
   *
   * @param contextKey a chave do contexto a ser verificado (não pode ser null). Deve ser
   *                   uma chave de contexto válida no formato "nomeContexto.tipoContexto"
   * @param serviceContextValue o valor do contexto do serviço (pode ser null). Representa
   *                            os contextos disponíveis no serviço
   * @param userContextValue o valor do contexto do usuário (pode ser null). Representa
   *                         os contextos requeridos pelo usuário
   * @return true se o valor do serviço contiver o valor do usuário, false caso contrário.
   *         Retorna false se qualquer parâmetro for null após conversão para string
   * @throws NullPointerException se contextKey for null
   * @bugfix SECURITY: Previne NullPointerException quando userContextValue é null.
   *         Validação explícita de null para evitar bypass de autorização.
   */
  default boolean matches(String contextKey, String serviceContextValue,
                          String userContextValue) {
    Objects.requireNonNull(contextKey, "contextKey cannot be null");
    if (userContextValue == null) {
      return false;
    }
    return String.valueOf(serviceContextValue).contains(userContextValue);
  }
}
