package com.ia.core.view.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ia.core.model.filter.SortRequest;
import com.ia.core.service.dto.filter.FieldTypeDTO;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.dto.sort.SortDirectionDTO;
import com.ia.core.service.dto.sort.SortRequestDTO;
import com.ia.core.view.components.dialog.exception.ExceptionViewFactory;
import com.ia.core.view.manager.CountBaseManager;
import com.ia.core.view.manager.DefaultBaseManager;
import com.ia.core.view.manager.ListBaseManager;
import com.vaadin.flow.data.provider.CallbackDataProvider.CountCallback;
import com.vaadin.flow.data.provider.CallbackDataProvider.FetchCallback;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;

import lombok.extern.slf4j.Slf4j;

/**
 * Classe que implementa o padrão factory para os {@link DataProvider}
 *
 * @author Israel Araújo
 */
@Slf4j
public class DataProviderFactory {

  /**
   * Cria {@link DataProvider} a partir de {@link FetchCallback} e
   * {@link CountCallback} e suas propriedades para filtro
   *
   * @param <T>           Tipo de dado
   * @param fetchCallback {@link FetchCallback}
   * @param countCallback {@link CountCallback}
   * @param properties    Propriedades a serem filtradas
   * @return {@link ConfigurableFilterDataProvider}
   */
  public static <T> DataProvider<T, String> createBaseDataProviderFromCallBacks(FetchCallback<T, SearchRequestDTO> fetchCallback,
                                                                                CountCallback<T, SearchRequestDTO> countCallback,
                                                                                Set<String> properties) {
    // callback
    DataProvider<T, SearchRequestDTO> provider = DataProvider
        .fromFilteringCallbacks(fetchCallback, countCallback);

    // com filtro configurável
    DataProvider<T, String> filteredProvider = provider
        .withConfigurableFilter((search, value) -> {
          SearchRequestDTO request = SearchRequestDTO.builder()
              .disjunction(false).build();
          properties.forEach(property -> {
            request.getFilters()
                .add(FilterRequestDTO.builder().key(property)
                    .operator(OperatorDTO.LIKE)
                    .fieldType(FieldTypeDTO.STRING).value(search).build());
          });
          return request;
        });
    return filteredProvider;
  }

  /**
   * Cria um {@link DataProvider}
   *
   * @param <T>        Tipo de dado.
   * @param <S>        Tipo do serviço
   * @param service    {@link DefaultBaseManager};
   * @param properties propriedades a serem usadas no filto
   * @return {@link DataProvider}
   */
  public static <T extends Serializable, S extends ListBaseManager<T> & CountBaseManager<T>> DataProvider<T, String> createBaseDataProviderFromService(S service,
                                                                                                                                                       Set<String> properties) {
    return createBaseDataProviderFromCallBacks(createFetchCallbackFromService(service),
                                               createCountCallbackFromService(service),
                                               properties);
  }

  /**
   * Cria um {@link DataProvider}
   *
   * @param <T>           Tipo de dado.
   * @param <S>           Tipo do serviço
   * @param fetchFunction função de busca
   * @param countFunction função de contagem
   * @param properties    propriedades a serem usadas no filto
   * @return {@link DataProvider}
   */
  public static <T extends Serializable, S extends ListBaseManager<T> & CountBaseManager<T>> DataProvider<T, String> createBaseDataProviderFromRequest(Function<SearchRequestDTO, Stream<T>> fetchFunction,
                                                                                                                                                       Function<SearchRequestDTO, Integer> countFunction,
                                                                                                                                                       Set<String> properties) {
    return createBaseDataProviderFromCallBacks(createFetchCallbackFromRequest(fetchFunction),
                                               createCountCallbackFromRequest(countFunction),
                                               properties);
  }

  /**
   * Cria um {@link CountCallback}
   *
   * @param <T>          Tipo de dado
   * @param countService {@link CountBaseManager}
   * @return {@link CountCallback}
   */
  public static <T extends Serializable> CountCallback<T, SearchRequestDTO> createCountCallbackFromService(CountBaseManager<T> countService) {
    return createCountCallbackFromRequest(request -> countService
        .count(request));
  }

  /**
   * Cria um {@link CountCallback}
   *
   * @param <T>             Tipo de dado
   * @param requestFunction {@link Function} que transforma o
   *                        {@link SearchRequestDTO} em inteiro
   * @return {@link CountCallback}
   */
  public static <T extends Serializable> CountCallback<T, SearchRequestDTO> createCountCallbackFromRequest(Function<SearchRequestDTO, Integer> requestFunction) {
    return query -> {
      try {
        int page = query.getPage();
        int size = query.getPageSize();
        SearchRequestDTO request = query.getFilter()
            .orElse(SearchRequestDTO.builder().build());

        request.setPage(page);
        request.setSize(size);

        return requestFunction.apply(request);
      } catch (Exception e) {
        ExceptionViewFactory.showError(e);
      }
      return 0;
    };
  }

  /**
   * Cria um {@link CountCallback}
   *
   * @param <T>           Tipo de dado
   * @param countSupplier {@link CountBaseManager}
   * @return {@link CountCallback}
   */
  public static <T extends Serializable> CountCallback<T, SearchRequestDTO> createCountCallbackFromSupplier(Supplier<Collection<T>> countSupplier) {
    return query -> {
      try {
        int page = query.getOffset();
        int size = query.getLimit();
        // filtering
        return (int) countSupplier.get().stream().skip(page).limit(size)
            .count();
      } catch (Exception e) {
        ExceptionViewFactory.showError(e);
      }
      return 0;
    };
  }

  /**
   * Cria {@link DataProvider} a partir de {@link FetchCallback} e
   * {@link CountCallback}
   *
   * @param <T>           Tipo de dado
   * @param fetchCallback {@link FetchCallback}
   * @param countCallback {@link CountCallback}
   * @return {@link ConfigurableFilterDataProvider}
   */
  public static <T> DataProvider<T, SearchRequestDTO> createDataProviderFromCallBacks(FetchCallback<T, SearchRequestDTO> fetchCallback,
                                                                                      CountCallback<T, SearchRequestDTO> countCallback) {
    // callback
    return DataProvider.fromFilteringCallbacks(fetchCallback,
                                               countCallback);
  }

  /**
   * Cria um {@link ConfigurableFilterDataProvider}
   *
   * @param <T>     Tipo de dado.
   * @param <S>     Tipo do serviço
   * @param Service {@link DefaultBaseManager};
   * @return {@link ConfigurableFilterDataProvider}
   */
  public static <T extends Serializable, S extends ListBaseManager<T> & CountBaseManager<T>> DataProvider<T, SearchRequestDTO> createDataProviderFromService(S Service) {
    return createDataProviderFromCallBacks(createFetchCallbackFromService(Service),
                                           createCountCallbackFromService(Service));
  }

  /**
   * Cria um {@link ConfigurableFilterDataProvider}
   *
   * @param <T>      Tipo de dado.
   * @param supplier {@link DefaultBaseManager};
   * @return {@link DataProvider}
   */
  public static <T extends Serializable> DataProvider<T, SearchRequestDTO> createDataProviderFromSupplier(Supplier<Collection<T>> supplier) {
    return createDataProviderFromCallBacks(createFetchCallbackFromSupplier(supplier),
                                           createCountCallbackFromSupplier(supplier));
  }

  /**
   * Cria um {@link ConfigurableFilterDataProvider}
   *
   * @param <T>        Tipo de dado.
   * @param supplier   {@link DefaultBaseManager};
   * @param properties Propriedades a serem usadas no filto
   * @return {@link DataProvider}
   */
  public static <T extends Serializable> DataProvider<T, String> createDataProviderFromSupplier(Supplier<Collection<T>> supplier,
                                                                                                Set<String> properties) {
    // callback
    DataProvider<T, SearchRequestDTO> provider = createDataProviderFromSupplier(supplier);

    // com filtro configurável
    DataProvider<T, String> filteredProvider = provider
        .withConfigurableFilter((search, value) -> {
          SearchRequestDTO request = SearchRequestDTO.builder()
              .disjunction(false).build();
          properties.forEach(property -> {
            request.getFilters()
                .add(FilterRequestDTO.builder().key(property)
                    .operator(OperatorDTO.LIKE)
                    .fieldType(FieldTypeDTO.STRING).value(search).build());
          });
          return request;
        });
    return filteredProvider;
  }

  /**
   * Cria um {@link FetchCallback}
   *
   * @param <T>         Tipo de dado
   * @param listService {@link ListBaseManager}
   * @return {@link FetchCallback}
   */
  public static <T extends Serializable> FetchCallback<T, SearchRequestDTO> createFetchCallbackFromService(ListBaseManager<T> listService) {
    return createFetchCallbackFromRequest(request -> listService
        .findAll(request).stream());
  }

  /**
   * Cria um {@link FetchCallback}
   *
   * @param <T>             Tipo de dado
   * @param requestFunction {@link Function} que transorma
   *                        {@link SearchRequestDTO} em {@link Stream} de T
   * @return {@link FetchCallback}
   */
  public static <T extends Serializable> FetchCallback<T, SearchRequestDTO> createFetchCallbackFromRequest(Function<SearchRequestDTO, Stream<T>> requestFunction) {
    return query -> {
      try {
        int page = query.getPage();
        int size = query.getPageSize();
        SearchRequestDTO request = query.getFilter()
            .orElse(SearchRequestDTO.builder().build());
        try {
          request.toBuilder().sorts(Collections.emptyList());
        } catch (Exception e) {
          log.info(e.getLocalizedMessage(), e);
        }
        // sorting
        request.setPage(page);
        request.setSize(size);
        request.toBuilder().sorts(toSortRequest(query));

        // filtering
        return requestFunction.apply(request);
      } catch (Exception e) {
        ExceptionViewFactory.showError(e);
      }
      return Stream.empty();
    };
  }

  /**
   * Cria um {@link FetchCallback}
   *
   * @param <T>          Tipo de dado
   * @param listSupplier {@link CountBaseManager}
   * @return {@link CountCallback}
   */
  public static <T extends Serializable> FetchCallback<T, SearchRequestDTO> createFetchCallbackFromSupplier(Supplier<Collection<T>> listSupplier) {
    return query -> {
      try {
        int page = query.getOffset();
        int size = query.getLimit();
        var sortingComparator = query.getSortingComparator();
        var result = listSupplier.get().stream().skip(page).limit(size);
        if (sortingComparator.isPresent()) {
          return result.sorted(sortingComparator.get());
        }
        return result;
      } catch (Exception e) {
        ExceptionViewFactory.showError(e);
      }
      return Stream.empty();
    };
  }

  /**
   * Cria um {@link SortRequest}
   *
   * @param <T>   Tipo de dado
   * @param query {@link Query}
   * @return {@link List} de {@link SortRequest}
   */
  private static <T extends Serializable> List<SortRequestDTO> toSortRequest(Query<T, SearchRequestDTO> query) {
    List<QuerySortOrder> sortOrders = query.getSortOrders();
    if (sortOrders == null) {
      return Collections.emptyList();
    }
    return sortOrders.stream().map(order -> {
      return SortRequestDTO.builder().key(order.getSorted())
          .direction(SortDirection.ASCENDING.equals(order
              .getDirection()) ? SortDirectionDTO.ASC
                               : SortDirection.DESCENDING.equals(order
                                   .getDirection()) ? SortDirectionDTO.DESC
                                                    : null)
          .build();
    }).filter(order -> order.getDirection() != null)
        .collect(Collectors.toList());
  }

}
