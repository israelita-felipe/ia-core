package com.ia.core.view.properties;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * Propriedade que indica que a classe possui tipos genéricos.
 *
 * @author Israel Araújo
 */
public interface HasClassType {
  /**
   * @param <T>      O tipo do argumneto.
   * @param argument O argumento a ser verificado.
   * @return Retorna o próprio {@code argument} caso não seja nulo, senão uma
   *         exceção é lançada.
   */
  default <T> T checkNotNull(T argument) {
    return Objects.requireNonNull(argument);
  }

  /**
   * Obtém o tipo parametrizado de uma classe.
   *
   * @param type   classe que fornecerá o tipo genérico.
   * @param <T>    tipo da classe retornada.
   * @param target Tipo da interface
   * @param index  índice do argumento.
   * @return o tipo genérico da classe ou null caso o tipo não seja genérico ou
   *         seu tipo parametrizado não seja uma {@link Class}.
   */
  @SuppressWarnings("unchecked")
  default <T> Class<T> getType(Class<?> type, Class<?> target, int index) {
    checkNotNull(type);
    Class<T> parameterClass = null;
    for (Type interfaceType : type.getGenericInterfaces()) {
      if (interfaceType instanceof ParameterizedType) {
        ParameterizedType parameterizedType = (ParameterizedType) interfaceType;
        if (parameterizedType != null
            && parameterizedType.getRawType().equals(target)) {
          parameterClass = (Class<T>) parameterizedType
              .getActualTypeArguments()[index];
        } else if (isNotEqualClasses(Object.class,
                                     interfaceType.getClass())) {
          parameterClass = getType(interfaceType.getClass(), target, index);
        }
      } else if (isNotEqualClasses(Object.class,
                                   interfaceType.getClass())) {
        parameterClass = getType(interfaceType.getClass(), target, index);
      }
      if (Objects.nonNull(parameterClass)) {
        break;
      }
    }
    return parameterClass;
  }

  /**
   * Captura o tipo da classe.
   *
   * @param <K>   Tipo da classe de retorno.
   * @param type  Tipo da classe
   * @param index Índice do tipo.
   * @return {@link Class} do tipo <T>.
   */
  @SuppressWarnings("unchecked")
  default <K> Class<K> getType(Class<?> type, int index) {
    Class<K> parameterClass = null;
    if (type.getGenericSuperclass() instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) type
          .getGenericSuperclass();
      if (parameterizedType != null && parameterizedType
          .getActualTypeArguments()[index] instanceof Class) {
        parameterClass = (Class<K>) parameterizedType
            .getActualTypeArguments()[index];
      } else if (!Object.class.equals(type.getSuperclass())) {
        parameterClass = getType(type.getSuperclass(), index);
      }
    } else if (!Object.class.equals(type.getSuperclass())) {
      parameterClass = getType(type.getSuperclass(), index);
    }
    return parameterClass;
  }

  /**
   * Captura a classe dao um tipo e o índice desse tipo em uma classe com
   * argumentos genéricos.
   *
   * @param <T>   Tipo do dado de retorno.
   * @param type  Tipo do dado parametrizável.
   * @param index Índice do tipo.
   * @return {@link Class}
   */
  @SuppressWarnings("unchecked")
  default <T> Class<T> getType(Type type, int index) {
    if (type instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) type;
      if (parameterizedType != null && parameterizedType
          .getActualTypeArguments()[index] instanceof Class) {
        return (Class<T>) parameterizedType.getActualTypeArguments()[index];
      }
    }
    return null;
  }

  /**
   * @param lhs O lado direito da verificação.
   * @param rhs O lado esquerdo da verificação.
   * @return {@code true} caso as duas classes não sejam iguais, senão retorna
   *         {@code false}.
   */
  default boolean isNotEqualClasses(Class<?> lhs, Class<?> rhs) {
    return !Objects.equals(lhs, rhs);
  }
}
