package com.ia.core.service.mapper;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.dto.DTO;

/**
 * Interface para mapeamento entre entidades e DTOs.
 * <p>
 * Implementações devem definir os métodos {@link #toDTO(Object)} e {@link #toModel(DTO)}
 * para conversão bidirecional entre modelos de domínio e objetos de transferência.
 * </p>
 * <p>
 * <b>Nota sobre null safety:</b> Os métodos {@code toDTO()} e {@code toModel()} podem
 * retornar {@code null} se a entrada for {@code null} ou se a conversão falhar.
 * 调用者 deve tratar adequadamente valores nulos retornados.
 * </p>
 *
 * @param <T> Tipo da entidade (deve implementar {@link Serializable})
 * @param <D> Tipo do DTO (deve implementar {@link DTO})
 */
public interface Mapper<T extends Serializable, D extends DTO<?>> {

  /**
   * Mapeamento {@link BaseEntity} to {@link DTO}
   *
   * @param t {@link BaseEntity}
   * @return {@link DTO} ou null se a entrada for null
   */
  D toDTO(T t);

  /**
   * Mapeamento {@link DTO} para {@link BaseEntity}
   *
   * @param dto {@link DTO}
   * @return {@link BaseEntity} ou null se a entrada for null
   */
  T toModel(D dto);

  /**
   * Mapeia uma lista de entidades para uma lista de DTOs.
   * <p>
   * Método utilitário que itera sobre a lista de entrada e aplica {@link #toDTO(Object)}
   * para cada elemento. Valores nulos na lista de entrada são preservados como nulos
   * na lista de saída.
   * </p>
   *
   * @param entities Lista de entidades a serem mapeadas
   * @return Lista de DTOs mapeados (nunca retorna null, retorna lista vazia se entrada for vazia ou null)
   */
  default List<D> toDTOList(List<T> entities) {
    if (entities == null || entities.isEmpty()) {
      return Collections.emptyList();
    }
    return entities.stream()
      .map(this::toDTO)
      .collect(Collectors.toList());
  }

  /**
   * Mapeia um set de entidades para um set de DTOs.
   *
   * @param entities Set de entidades a serem mapeadas
   * @return Set de DTOs mapeados (nunca retorna null, retorna set vazio se entrada for vazia ou null)
   */
  default Set<D> toDTOSet(Set<T> entities) {
    if (entities == null || entities.isEmpty()) {
      return Collections.emptySet();
    }
    return entities.stream()
      .map(this::toDTO)
      .collect(Collectors.toCollection(HashSet::new));
  }

  /**
   * Mapeia uma lista de DTOs para uma lista de entidades.
   *
   * @param dtos Lista de DTOs a serem mapeados
   * @return Lista de entidades mapeadas (nunca retorna null, retorna lista vazia se entrada for vazia ou null)
   */
  default List<T> toModelList(List<D> dtos) {
    if (dtos == null || dtos.isEmpty()) {
      return Collections.emptyList();
    }
    return dtos.stream()
      .map(this::toModel)
      .collect(Collectors.toList());
  }

  /**
   * Mapeia um set de DTOs para um set de entidades.
   *
   * @param dtos Set de DTOs a serem mapeados
   * @return Set de entidades mapeadas (nunca retorna null, retorna set vazio se entrada for vazia ou null)
   */
  default Set<T> toModelSet(Set<D> dtos) {
    if (dtos == null || dtos.isEmpty()) {
      return Collections.emptySet();
    }
    return dtos.stream()
      .map(this::toModel)
      .collect(Collectors.toCollection(HashSet::new));
  }

}
