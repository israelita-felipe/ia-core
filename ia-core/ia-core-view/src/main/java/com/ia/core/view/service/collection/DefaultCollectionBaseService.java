package com.ia.core.view.service.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.function.BiConsumer;

import com.ia.core.service.dto.DTO;
import com.ia.core.view.client.collection.DefaultCollectionBaseClient;
import com.ia.core.view.exception.ValidationException;
import com.ia.core.view.service.CountBaseService;
import com.ia.core.view.service.DefaultBaseService;
import com.ia.core.view.service.DeleteBaseService;
import com.ia.core.view.service.FindBaseService;
import com.ia.core.view.service.ListBaseService;
import com.ia.core.view.service.SaveBaseService;

import lombok.extern.slf4j.Slf4j;

/**
 * Classe padrão para criação de um serviço da view com todos os clientes.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 */
@Slf4j
public abstract class DefaultCollectionBaseService<D extends Serializable>
  extends DefaultBaseService<D>
  implements CountBaseService<D>, FindBaseService<D>, DeleteBaseService<D>,
  ListBaseService<D>, SaveBaseService<D> {

  /**
   * @param config {@link DefaultCollectionBaseClient}
   */
  public DefaultCollectionBaseService(DefaultCollectionServiceConfig<D> config) {
    super(config);
  }

  /**
   * Sobe o objeto da coleção
   *
   * @param object              Objeto a ser movido
   * @param changeIndexListener escutador do objeto na antiga posição e o novo
   *                            índice do objeto atual
   */
  public void down(D object, BiConsumer<D, Integer> changeIndexListener) {
    Collection<D> data = getClient().getData();
    if (data.size() == 1) {
      return;
    }
    ArrayList<D> list = new ArrayList<>(data);
    int index = list.indexOf(object);
    if (index < 0 || index >= data.size() - 1) {
      return;
    }
    int newIndex = index + 1;
    Collections.swap(list, index, newIndex);
    data.clear();
    data.addAll(list);
    changeIndexListener.accept(list.get(index), newIndex);
  }

  @SuppressWarnings("unchecked")
  @Override
  public DefaultCollectionBaseClient<D> getClient() {
    return (DefaultCollectionBaseClient<D>) getConfig().getClient();
  }

  /**
   * @return {@link DefaultCollectionBaseClient#getData()}
   */
  public Collection<D> getData() {
    return getClient().getData();
  }

  /**
   * Captura o {@link UUID} do objeto
   *
   * @param object objeto a ser recuperado o identificador
   * @return {@link UUID}. Delega a recuperação para
   *         {@link DefaultCollectionBaseClient#getId(Serializable)}
   */
  public UUID getId(D object) {
    return getClient().getId(object);
  }

  /**
   * Desce o objeto da coleção
   *
   * @param object              Objeto a ser movido
   * @param changeIndexListener escutador do objeto na antiga posição e o novo
   *                            índice do objeto atual
   */
  public void up(D object, BiConsumer<D, Integer> changeIndexListener) {
    Collection<D> data = getClient().getData();
    if (data.size() == 1) {
      return;
    }
    ArrayList<D> list = new ArrayList<>(data);
    int index = list.indexOf(object);
    if (index <= 0) {
      return;
    }
    int newIndex = index - 1;
    Collections.swap(list, index, newIndex);
    data.clear();
    data.addAll(list);
    changeIndexListener.accept(list.get(index), newIndex);
  }

  @Override
  public D update(D old, D updated)
    throws ValidationException {
    if (old == null) {
      throw new ValidationException("Old object is null");
    }
    Collection<D> data = getClient().getData();
    data.add(updated);

    ArrayList<D> list = new ArrayList<>(data);
    int index = list.indexOf(old);
    if (index > -1) {
      Collections.swap(list, index, list.size() - 1);
      list.remove(old);
      data.clear();
      data.addAll(list);
    } else {
      data.remove(updated);
    }
    return updated;
  }

}
