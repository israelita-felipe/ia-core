package com.ia.core.view.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import com.ia.core.view.client.collection.DefaultCollectionBaseClient;
import com.ia.core.view.service.collection.DefaultCollectionBaseService;
import com.ia.core.view.service.collection.DefaultCollectionServiceConfig;

/**
 * Classe que implementa os padrão factory para os serviços
 *
 * @author Israel Araújo
 */
public class ServiceFactory {

  /**
   * Cria um {@link DefaultCollectionBaseClient} baseado em uma coleção e uma
   * função que define o identificador do objeto
   *
   * @param <T>        Tipo do objeto a ser manipulado pelo serviço.
   * @param data       Coleção de dados manipulados
   * @param idSupplier {@link Function} de definição do ID do objeto
   * @return {@link DefaultCollectionBaseClient}
   */
  protected static <T extends Serializable> DefaultCollectionBaseClient<T> createCollectionBaseClient(Supplier<Collection<T>> data,
                                                                                                      Function<T, UUID> idSupplier) {
    return new DefaultCollectionBaseClient<T>() {

      @Override
      public Collection<T> getData() {
        return data.get();
      }

      @Override
      public UUID getId(T object) {
        return idSupplier.apply(object);
      }
    };
  }

  /**
   * Cria um {@link DefaultCollectionBaseService} baseado em uma coleção e uma
   * função que define o identificador do objeto
   *
   * @param <T>        Tipo do objeto a ser manipulado pelo serviço.
   * @param data       Coleção de dados manipulados
   * @param idSupplier {@link Function} de definição do ID do objeto
   * @return {@link DefaultCollectionBaseService}
   */
  public static <T extends Serializable> DefaultCollectionBaseService<T> createServiceFromCollection(Supplier<Collection<T>> data,
                                                                                                     Function<T, UUID> idSupplier) {
    return new DefaultCollectionBaseService<T>(createCollectionServiceConfig(data, idSupplier)) {
    };
  }

  /**
   * @param <T>
   * @param data
   * @param idSupplier
   * @return
   */
  protected static <T extends Serializable> DefaultCollectionServiceConfig<T> createCollectionServiceConfig(Supplier<Collection<T>> data,
                                                                                                            Function<T, UUID> idSupplier) {
    return new DefaultCollectionServiceConfig<>(createCollectionBaseClient(data,
                                                                                                               idSupplier));
  }
}
