package com.ia.core.service.dto.filter;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.ia.core.model.filter.FilterRequest;

/**
 * Operadores
 */
public enum OperatorDTO {
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
   * Realiza o build
   *
   * @param <T>         Tipo de dado.
   * @param request     {@link FilterRequest}
   * @param predicate   predicado
   * @param disjunction Indicativo de disjunção
   * @return {@link java.util.function.Predicate}
   */
  public abstract <T> java.util.function.Predicate<T> build(FilterRequestDTO request,
                                                            java.util.function.Predicate<T> predicate,
                                                            boolean disjunction);
}
