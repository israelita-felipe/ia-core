package com.ia.core.service.dto.filter;

import com.ia.core.model.filter.Operator;
import com.ia.core.service.dto.DTO;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Enum de operadores de comparação para filtros.
 * <p>
 * Define os operadores disponíveis para construção de predicados de filtro,
 * incluindo igualdade, diferença, comparação numérica, contém, e operações
 * em coleções. Cada operador implementa a lógica de comparação através de
 * reflexão para acessar os valores dos campos.
 * </p>
 *
 * @author Israel Araújo
 */
public enum OperatorDTO implements DTO<Operator> {
  /**
   * Igualdade
   */
  EQUAL {
    @Override
    public <T> java.util.function.Predicate<T> build(FilterRequestDTO request,
                                                     java.util.function.Predicate<T> predicate,
                                                     boolean disjunction) {
      Predicate<? super T> newPredicate = object -> {
        try {
          Field field = object.getClass()
              .getDeclaredField(request.getKey());
          field.setAccessible(true);
          Object value = field.get(object);
          return Objects.equals(value, request.getValue());
        } catch (Exception e) {
          return false;
        }
      };
      if (request.isNegate()) {
        newPredicate = Predicate.not(newPredicate);
      }
      if (disjunction) {
        return predicate.and(newPredicate);
      }
      return predicate.or(newPredicate);
    }
  },
  /**
   * Diferença
   */
  NOT_EQUAL {

    @Override
    public <T> java.util.function.Predicate<T> build(FilterRequestDTO request,
                                                     java.util.function.Predicate<T> predicate,
                                                     boolean disjunction) {
      Predicate<? super T> newPredicate = object -> {
        try {
          Field field = object.getClass()
              .getDeclaredField(request.getKey());
          field.setAccessible(true);
          Object value = field.get(object);
          return !Objects.equals(value, request.getValue());
        } catch (Exception e) {
          return false;
        }
      };
      if (request.isNegate()) {
        newPredicate = Predicate.not(newPredicate);
      }
      if (disjunction) {
        return predicate.and(newPredicate);
      }
      return predicate.or(newPredicate);
    }
  },
  /**
   * Contém
   */
  LIKE {

    @Override
    public <T> java.util.function.Predicate<T> build(FilterRequestDTO request,
                                                     java.util.function.Predicate<T> predicate,
                                                     boolean disjunction) {
      Predicate<? super T> newPredicate = object -> {
        try {
          Field field = object.getClass()
              .getDeclaredField(request.getKey());
          field.setAccessible(true);
          Object value = field.get(object);
          return value.toString().toUpperCase()
              .contains(request.getValue().toString().toUpperCase());
        } catch (Exception e) {
          return false;
        }
      };
      if (request.isNegate()) {
        newPredicate = Predicate.not(newPredicate);
      }
      if (disjunction) {
        return predicate.and(newPredicate);
      }
      return predicate.or(newPredicate);
    }
  },
  /**
   * Contém/Possui utilizado em coleções e arrays
   */
  IN {

    @Override
    public <T> java.util.function.Predicate<T> build(FilterRequestDTO request,
                                                     java.util.function.Predicate<T> predicate,
                                                     boolean disjunction) {
      Predicate<? super T> newPredicate = object -> {
        try {
          Field field = object.getClass()
              .getDeclaredField(request.getKey());
          field.setAccessible(true);
          Object value = field.get(object);
          var isValueNotNull = value != null;
          if (isValueNotNull && value.getClass().isArray()) {
            return Stream.of((Object[]) value)
                .anyMatch(in -> Objects.equals(in, request.getValue()));
          }
          if (isValueNotNull
              && Map.class.isAssignableFrom(value.getClass())) {
            return ((Map<?, ?>) value).containsValue(request.getValue());
          }
          if (isValueNotNull
              && Collection.class.isAssignableFrom(value.getClass())) {
            return ((Collection<?>) value).stream()
                .anyMatch(in -> Objects.equals(in, request.getValue()));
          }
          return false;
        } catch (Exception e) {
          return false;
        }
      };
      if (request.isNegate()) {
        newPredicate = Predicate.not(newPredicate);
      }
      if (disjunction) {
        return predicate.and(newPredicate);
      }
      return predicate.or(newPredicate);
    }
  },
  /**
   * Maior que
   */

  @SuppressWarnings({ "unchecked", "rawtypes" })
  GREATER_THAN {
    @Override
    public <T> Predicate<T> build(FilterRequestDTO request,
                                  Predicate<T> predicate,
                                  boolean disjunction) {
      Predicate<? super T> newPredicate = object -> {
        try {
          Field field = object.getClass()
              .getDeclaredField(request.getKey());
          field.setAccessible(true);
          Object value = field.get(object);
          if (Comparable.class.isInstance(value)
              && Comparable.class.isInstance(request.getValue())) {
            return ((Comparable) value).compareTo(request.getValue()) > 0;
          }
          return Objects.toString(value)
              .compareTo(Objects.toString(request.getValue())) > 0;
        } catch (Exception e) {
          return false;
        }
      };
      if (request.isNegate()) {
        newPredicate = Predicate.not(newPredicate);
      }
      if (disjunction) {
        return predicate.and(newPredicate);
      }
      return predicate.or(newPredicate);
    }
  },
  /**
   * Maior ou igual
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  GREATER_THAN_OR_EQUAL_TO {

    @Override
    public <T> Predicate<T> build(FilterRequestDTO request,
                                  Predicate<T> predicate,
                                  boolean disjunction) {
      Predicate<? super T> newPredicate = object -> {
        try {
          Field field = object.getClass()
              .getDeclaredField(request.getKey());
          field.setAccessible(true);
          Object value = field.get(object);
          if (Comparable.class.isInstance(value)
              && Comparable.class.isInstance(request.getValue())) {
            return ((Comparable) value).compareTo(request.getValue()) >= 0;
          }
          return Objects.toString(value)
              .compareTo(Objects.toString(request.getValue())) >= 0;
        } catch (Exception e) {
          return false;
        }
      };
      if (request.isNegate()) {
        newPredicate = Predicate.not(newPredicate);
      }
      if (disjunction) {
        return predicate.and(newPredicate);
      }
      return predicate.or(newPredicate);
    }
  },
  /**
   * Menor que
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  LESS_THAN {

    @Override
    public <T> Predicate<T> build(FilterRequestDTO request,
                                  Predicate<T> predicate,
                                  boolean disjunction) {
      Predicate<? super T> newPredicate = object -> {
        try {
          Field field = object.getClass()
              .getDeclaredField(request.getKey());
          field.setAccessible(true);
          Object value = field.get(object);
          if (Comparable.class.isInstance(value)
              && Comparable.class.isInstance(request.getValue())) {
            return ((Comparable) value).compareTo(request.getValue()) < 0;
          }
          return Objects.toString(value)
              .compareTo(Objects.toString(request.getValue())) < 0;
        } catch (Exception e) {
          return false;
        }
      };
      if (request.isNegate()) {
        newPredicate = Predicate.not(newPredicate);
      }
      if (disjunction) {
        return predicate.and(newPredicate);
      }
      return predicate.or(newPredicate);
    }
  },
  /**
   * Menor ou igual
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  LESS_THAN_OR_EQUAL_TO {
    @Override
    public <T> Predicate<T> build(FilterRequestDTO request,
                                  Predicate<T> predicate,
                                  boolean disjunction) {
      Predicate<T> newPredicate = object -> {
        try {
          Field field = object.getClass()
              .getDeclaredField(request.getKey());
          field.setAccessible(true);
          Object value = field.get(object);
          if (Comparable.class.isInstance(value)
              && Comparable.class.isInstance(request.getValue())) {
            return ((Comparable) value).compareTo(request.getValue()) <= 0;
          }
          return Objects.toString(value)
              .compareTo(Objects.toString(request.getValue())) <= 0;
        } catch (Exception e) {
          return false;
        }
      };
      if (request.isNegate()) {
        newPredicate = Predicate.not(newPredicate);
      }
      if (disjunction) {
        return predicate.and(newPredicate);
      }
      return predicate.or(newPredicate);
    }
  };

  /**
   * Constrói um predicado para o operador de filtro.
   * <p>
   * Utiliza reflexão para acessar o valor do campo especificado no objeto
   * e aplica a lógica de comparação específica do operador.
   * </p>
   *
   * @param <T>         Tipo de dado do predicado
   * @param request     Requisição de filtro contendo campo e valor
   * @param predicate   Predicado base para composição
   * @param disjunction Indica se deve usar disjunção (OR) ou conjunção (AND)
   * @return Predicado configurado com a lógica do operador
   */
  public abstract <T> java.util.function.Predicate<T> build(FilterRequestDTO request,
                                                            java.util.function.Predicate<T> predicate,
                                                            boolean disjunction);

  /**
   * Retorna uma cópia deste operador (enum é imutável).
   *
   * @return A própria instância (enums são singletons)
   */
  @Override
  public DTO<Operator> cloneObject() {
    return this;
  }
}
