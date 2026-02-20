package com.ia.core.view.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import com.ia.core.view.client.collection.DefaultCollectionBaseClient;
import com.ia.core.view.manager.collection.DefaultCollectionBaseManager;
import com.ia.core.view.manager.collection.DefaultCollectionManagerConfig;

/**
 * Classe que implementa os padrão factory para os serviços
 *
 * @author Israel Araújo
 */
public class ManagerFactory {

  /**
   * Cria um {@link DefaultCollectionBaseClient} baseado em uma coleção e uma
   * função que define o identificador do objeto
   *
   * @param <T>        Tipo do objeto a ser manipulado pelo serviço.
   * @param data       Coleção de dados manipulados
   * @param idSupplier {@link Function} de definição do ID do objeto
   * @return {@link DefaultCollectionBaseClient}
   */
  public static <T extends Serializable> DefaultCollectionBaseClient<T> createCollectionBaseClient(Supplier<Collection<T>> data,
                                                                                                   Function<T, Long> idSupplier) {
    return new DefaultCollectionBaseClient<T>() {

      @Override
      public Collection<T> getData() {
        return data.get();
      }

      @Override
      public Long getId(T object) {
        if (object == null) {
          return null;
        }
        Long id = idSupplier.apply(object);
        if (id == null) {
          return Long.valueOf(object.hashCode());
        }
        return id;
      }
    };
  }

  /**
   * Cria um {@link DefaultCollectionBaseManager} baseado em uma coleção e uma
   * função que define o identificador do objeto
   *
   * @param <T>        Tipo do objeto a ser manipulado pelo serviço.
   * @param data       Coleção de dados manipulados
   * @param idSupplier {@link Function} de definição do ID do objeto
   * @return {@link DefaultCollectionBaseManager}
   */
  public static <T extends Serializable> DefaultCollectionBaseManager<T> createManagerFromCollection(Supplier<Collection<T>> data,
                                                                                                     Function<T, Long> idSupplier) {
    return new DefaultCollectionBaseManager<T>(createCollectionManagerConfig(data,
                                                                             idSupplier)) {
    };
  }

  /**
   * @param <T>
   * @param data
   * @param idSupplier
   * @return
   */
  public static <T extends Serializable> DefaultCollectionManagerConfig<T> createCollectionManagerConfig(Supplier<Collection<T>> data,
                                                                                                         Function<T, Long> idSupplier) {
    return new DefaultCollectionManagerConfig<>(createCollectionBaseClient(data,
                                                                           idSupplier));
  }
}
