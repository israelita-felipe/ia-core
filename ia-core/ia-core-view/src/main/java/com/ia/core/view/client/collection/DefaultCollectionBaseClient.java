package com.ia.core.view.client.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.ia.core.model.filter.SearchRequest;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.specification.CollectionSearchSpecification;
import com.ia.core.view.client.CountBaseClient;
import com.ia.core.view.client.DeleteBaseClient;
import com.ia.core.view.client.FindBaseClient;
import com.ia.core.view.client.ListBaseClient;
import com.ia.core.view.client.SaveBaseClient;
import com.ia.core.view.manager.ListBaseManager;

import jakarta.validation.ValidationException;

/**
 * Classe padrão para criação de um client com todos os serviços.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link Serializable}
 * @see CountBaseClient
 * @see FindBaseClient
 * @see DeleteBaseClient
 * @see ListBaseClient
 * @see SaveBaseClient
 */
public interface DefaultCollectionBaseClient<D extends Serializable>
  extends CountBaseClient<D>, FindBaseClient<D>, DeleteBaseClient<D>,
  ListBaseClient<D>, SaveBaseClient<D>, CollectionBaseClient<D> {

  /**
   * Realiza o count.
   *
   * @param searchRequest {@link SearchRequestDTO}
   * @return {@link Integer}
   */
  @Override
  default int count(SearchRequestDTO searchRequest) {
    if (Objects.isNull(searchRequest)) {
      searchRequest = SearchRequestDTO.builder().build();
    }
    CollectionSearchSpecification<D> specification = new CollectionSearchSpecification<>(searchRequest);
    return (int) getData().stream().filter(specification.toPredicate())
        .count();
  }

  /**
   * @param id Id do objeto.
   */
  @Override
  default void delete(UUID id) {
    getData().removeIf(object -> Objects.equals(getId(object), id));
  }

  /**
   * @param id Id do objeto.
   * @return {@link Serializable}
   */
  @Override
  default D find(UUID id) {
    return getData().stream()
        .filter(object -> Objects.equals(getId(object), id)).findAny()
        .orElse(null);
  }

  /**
   * Busca os elementos.
   *
   * @param request {@link SearchRequest}
   * @return Page do tipo {@link Serializable}.
   * @see ListBaseManager#findAll(SearchRequestDTO)
   */

  @Override
  default Page<D> findAll(SearchRequestDTO request) {
    if (Objects.isNull(request)) {
      request = SearchRequestDTO.builder().build();
    }
    int inicio = request.getPage() * request.getSize();
    int tamanho = inicio + request.getSize();
    CollectionSearchSpecification<D> specification = new CollectionSearchSpecification<>(request);
    return new PageImpl<>(getData().stream()
        .filter(specification.toPredicate()).skip(inicio).limit(tamanho)
        .toList());
  }

  /**
   * @param Serializable O objeto {@link Serializable} a ser salvo.
   * @return Objeto <D> do tipo {@link Serializable} salvo.
   * @throws ValidationException exceção de validação.
   * @see SaveBaseClient#save(Serializable)
   */
  @Override
  default D save(D Serializable) {
    if (find(getId(Serializable)) == null) {
      getData().add(Serializable);
    }
    return Serializable;
  }

  /**
   * Realiza a validação de um objeto.
   *
   * @param Serializable {@link Serializable} a ser validado.
   * @return Coleção de erros apurados.
   */
  @Override
  default Collection<String> validate(D Serializable) {
    return Collections.emptyList();
  }
}
