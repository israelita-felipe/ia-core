package com.ia.core.security.service.strategy.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.service.strategy.ContextResolveStrategy;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementação de estratégia para resolver contexto de ID. Esta é a estratégia
 * padrão fornecida pelo framework. Resolve valores de contexto baseado no ID da
 * entidade.
 *
 * @author Israel Araújo
 */
@Component
@Slf4j
public class IdContextResolveStrategy
  implements ContextResolveStrategy {

  @Override
  public String getContextKey() {
    return AbstractBaseEntityDTO.CAMPOS.ID;
  }

  /**
   * Resolve IDs de entidades a partir de uma coleção de valores.
   *
   * @param values     Valores iniciais (IDs a buscar)
   * @param repository Repositório para buscar entidades
   * @return Coleção de IDs resolvidos (como String)
   */
  @Override
  public Collection<Object> resolveContextValues(Collection<String> values,
                                                 BaseEntityRepository<?> repository) {
    Objects.requireNonNull(values, "Values não pode ser nulo");
    Objects.requireNonNull(repository, "Repository não pode ser nulo");

    if (values.isEmpty()) {
      return Collections.emptyList();
    }

    try {
      return repository
          .findAllById(values.stream().map(Long::valueOf).toList()).stream()
          .map(BaseEntity::getId).map(String::valueOf)
          .collect(Collectors.toSet());
    } catch (Exception e) {
      log.error("Erro ao resolver contexto de ID", e);
      return Collections.emptyList();
    }
  }

  /**
   * Valida se um ID do usuário corresponde aos IDs do serviço.
   *
   * @param serviceContextValue JSON com lista de IDs do serviço
   * @param userContextValue    ID do usuário
   * @return true se ID do usuário está na lista, false caso contrário
   */
  @Override
  public boolean matches(String serviceContextValue,
                         String userContextValue) {
    Objects.requireNonNull(serviceContextValue,
                           "Service context value não pode ser nulo");
    Objects.requireNonNull(userContextValue,
                           "User context value não pode ser nulo");

    try {
      List<String> fromJson = JsonUtil
          .fromJson(serviceContextValue,
                    new ParameterizedTypeReference<List<String>>() {
                    }.getType());

      return fromJson.stream().collect(Collectors.toSet())
          .contains(userContextValue);
    } catch (Exception e) {
      log.error("Erro ao validar correspondência de contexto de ID", e);
      return false;
    }
  }

  @Override
  public boolean canHandle(String contextKey) {
    return AbstractBaseEntityDTO.CAMPOS.ID.equals(contextKey);
  }
}
