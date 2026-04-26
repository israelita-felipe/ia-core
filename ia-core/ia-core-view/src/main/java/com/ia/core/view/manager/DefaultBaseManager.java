package com.ia.core.view.manager;

import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.usecase.CrudUseCase;
import com.ia.core.view.client.BaseClient;
import com.ia.core.view.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import java.io.Serializable;

/**
 * Classe padrão para criação de um serviço da view com todos os clientes.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 */
@Slf4j
public abstract class DefaultBaseManager<D extends Serializable>
  extends AbstractBaseManager<D>
  implements CountBaseManager<D>, FindBaseManager<D>, DeleteBaseManager<D>,
  ListBaseManager<D>, SaveBaseManager<D>, CrudUseCase<D> {

  /**
   * @param client {@link BaseClient} de comunicação
   */
  public DefaultBaseManager(DefaultBaseManagerConfig<D> client) {
    super(client);
  }

  @Override
  public D save(D dto)
    throws ValidationException {
    return SaveBaseManager.super.save(dto);
  }

  @Override
  public void delete(Long id)
    throws ValidationException {
    DeleteBaseManager.super.delete(id);
  }

  @Override
  public D find(Long id) {
    return FindBaseManager.super.find(id);
  }

  @Override
  public Page<D> findAll(SearchRequestDTO request) {
    return ListBaseManager.super.findAll(request);
  }

  @Override
  public int count(SearchRequestDTO searchRequest) {
    return CountBaseManager.super.count(searchRequest);
  }
}
