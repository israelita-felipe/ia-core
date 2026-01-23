package com.ia.core.view.manager;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.core.ParameterizedTypeReference;

import com.ia.core.service.dto.DTO;
import com.ia.core.view.client.BaseClient;

/**
 * Interface base para o cliente.
 *
 * @author Israel Araújo
 * @param <D> Tipo do {@link DTO}
 */
public interface BaseManager<D extends Serializable> {

  /**
   * Captura o cliente ativo.
   *
   * @param <C> Tipo do cliente.
   * @return {@link BaseClient}
   */
  <C extends BaseClient<D>> C getClient();

  /**
   * Extrai o tipo genérico da classe concreta.
   *
   * @return A classe do tipo genérico D
   */
  @SuppressWarnings("unchecked")
  default Class<D> extractGenericType() {
    try {
      // Obtém o tipo genérico da superclasse
      Type superClass = getClass().getGenericSuperclass();

      // Se for uma classe com parâmetros genéricos
      if (superClass instanceof ParameterizedType) {
        ParameterizedType parameterizedType = (ParameterizedType) superClass;
        Type[] typeArguments = parameterizedType.getActualTypeArguments();

        // O primeiro argumento de tipo é D
        if (typeArguments.length > 0 && typeArguments[0] instanceof Class) {
          return (Class<D>) typeArguments[0];
        }
      }

      // Alternativa: usa ParameterizedTypeReference para capturar o tipo
      ParameterizedTypeReference<D> typeRef = new ParameterizedTypeReference<D>() {
      };
      Type type = typeRef.getType();

      if (type instanceof ParameterizedType) {
        ParameterizedType pt = (ParameterizedType) type;
        Type actualType = pt.getActualTypeArguments()[0];

        if (actualType instanceof Class) {
          return (Class<D>) actualType;
        } else if (actualType instanceof ParameterizedType) {
          return (Class<D>) ((ParameterizedType) actualType).getRawType();
        }
      }

      throw new IllegalStateException("Não foi possível determinar o tipo genérico D");

    } catch (Exception e) {
      throw new RuntimeException("Falha ao determinar tipo genérico", e);
    }
  }
}
