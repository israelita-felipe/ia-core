package com.ia.core.security.service.strategy;

import java.util.Collection;

import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Interface estratégia para resolver diferentes tipos de contextos de segurança.
 * 
 * Padrão: Strategy Pattern
 * Princípio: OCP (Open/Closed Principle)
 * 
 * Diferentes tipos de contexto (ID, Role, Department, etc) implementam esta interface
 * para resolver seus valores e validar correspondência sem modificar a classe base.
 *
 * @author Israel Araújo
 */
public interface ContextResolveStrategy {

  /**
   * Obtém a chave de contexto que esta estratégia resolve.
   *
   * @return Chave identificadora do contexto
   */
  String getContextKey();

  /**
   * Resolve valores de contexto baseado nos valores iniciais fornecidos.
   *
   * @param values Valores iniciais para resolução
   * @param repository Repositório para buscar dados se necessário
   * @return Coleção de valores de contexto resolvidos
   */
  Collection<Object> resolveContextValues(Collection<String> values, BaseEntityRepository<?> repository);

  /**
   * Verifica se um valor de contexto do usuário corresponde ao valor do serviço.
   *
   * @param serviceContextValue Valor de contexto armazenado
   * @param userContextValue Valor de contexto do usuário
   * @return true se corresponde, false caso contrário
   */
  boolean matches(String serviceContextValue, String userContextValue);

  /**
   * Verifica se esta estratégia pode processar uma chave de contexto específica.
   *
   * @param contextKey Chave a validar
   * @return true se pode processar, false caso contrário
   */
  boolean canHandle(String contextKey);
}
