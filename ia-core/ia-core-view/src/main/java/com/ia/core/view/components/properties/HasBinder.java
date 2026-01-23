package com.ia.core.view.components.properties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import com.ia.core.view.components.converters.StringToBigDecimalConverter;
import com.ia.core.view.components.converters.StringToIntegerConverter;
import com.ia.core.view.components.converters.StringToLongConverter;
import com.ia.core.view.manager.DefaultBaseManager;
import com.ia.core.view.utils.DataProviderFactory;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Binder.Binding;
import com.vaadin.flow.data.converter.Converter;

/**
 * Possui propriedade de realizar bind dos dados
 *
 * @author Israel Araújo
 * @param <T> Tipo do dado
 */
public interface HasBinder<T> {
  /**
   * Realiza binding de {@link ComboBox}
   *
   * @param <E>        Tipo do dado do {@link ComboBox}
   * @param combo      {@link ComboBox}
   * @param properties Propriedades para filtro
   * @param service    {@link DefaultBaseManager}
   */
  default <E extends Serializable> void bind(ComboBox<E> combo,
                                             Set<String> properties,
                                             DefaultBaseManager<E> service) {
    combo.setItems(DataProviderFactory
        .createBaseDataProviderFromManager(service, properties));

  }

  /**
   * Realiza o binder
   *
   * @param consumer {@link Consumer} do binder
   */
  default void bind(Consumer<Binder<T>> consumer) {
    consumer.accept(getBinder());
  }

  /**
   * Realiza binding em {@link ComboBox}
   *
   * @param <E>            Tipo do dado
   * @param property       propriedade
   * @param combo          {@link ComboBox}
   * @param labelGenerator {@link ItemLabelGenerator}
   * @return {@link Binding}
   */
  default <E> Binding<?, E> bind(String property, ComboBox<E> combo,
                                 ItemLabelGenerator<E> labelGenerator) {
    AtomicReference<Binding<?, E>> thread = new AtomicReference<>();
    this.bind(binder -> {
      thread.set(binder.forField(combo).bind(parseProperty(property)));
      combo.setItemLabelGenerator(labelGenerator);
    });

    return thread.get();

  }

  /**
   * Realiza o binding
   *
   * @param <E>            tipo do dado
   * @param property       propriedade
   * @param combo          {@link ComboBox}
   * @param labelGenerator {@link ItemLabelGenerator}
   * @param properties     propriedades de filtros
   * @param service        {@link DefaultBaseManager}
   * @return {@link Binding}
   */
  default <E extends Serializable> Binding<?, E> bind(String property,
                                                      ComboBox<E> combo,
                                                      ItemLabelGenerator<E> labelGenerator,
                                                      Set<String> properties,
                                                      DefaultBaseManager<E> service) {
    AtomicReference<Binding<?, E>> thread = new AtomicReference<>();
    this.bind(binder -> {
      thread.set(binder.forField(combo).bind(parseProperty(property)));
      combo.setItemLabelGenerator(labelGenerator);
      combo.setItems(DataProviderFactory
          .createBaseDataProviderFromManager(service, properties));
    });

    return thread.get();

  }

  /**
   * Realiza binding
   *
   * @param <E>        Tipo do dado
   * @param property   propriedade do binding
   * @param combo      {@link ComboBox}
   * @param properties propriedade de filtros
   * @param service    {@link DefaultBaseManager}
   * @return {@link Binding}
   */
  default <E extends Serializable> Binding<?, E> bind(String property,
                                                      ComboBox<E> combo,
                                                      Set<String> properties,
                                                      DefaultBaseManager<E> service) {
    AtomicReference<Binding<?, E>> thread = new AtomicReference<>();
    this.bind(binder -> {
      thread.set(binder.forField(combo).bind(parseProperty(property)));
      combo.setItems(DataProviderFactory
          .createBaseDataProviderFromManager(service, properties));
    });

    return thread.get();

  }

  /**
   * Realiza o binding
   *
   * @param <E>      Tipo do dado
   * @param property propriedade do binding
   * @param field    {@link HasValue}
   * @return {@link Binding}
   */
  default <E> Binding<?, E> bind(String property, HasValue<?, E> field) {
    AtomicReference<Binding<?, E>> thread = new AtomicReference<>();
    this.bind(binder -> {
      thread.set(binder.forField(field).bind(parseProperty(property)));
    });

    return thread.get();

  }

  /**
   * Realiza o binding
   *
   * @param <E>      Tipo do dado
   * @param property propriedade do binding
   * @param field    {@link HasValue}
   * @param readOnly indicativo de somente leitura
   * @return {@link Binding}
   */
  default <E> Binding<?, E> bind(String property, HasValue<?, E> field,
                                 boolean readOnly) {
    AtomicReference<Binding<?, E>> thread = new AtomicReference<>();
    this.bind(binder -> {
      if (readOnly) {
        thread.set(binder.forField(field)
            .bindReadOnly(parseProperty(property)));
      } else {
        thread.set(binder.forField(field).bind(parseProperty(property)));
      }
    });

    return thread.get();

  }

  /**
   * Realiza o binding
   *
   * @param <E>      Tipo do dado
   * @param property propriedade do binding
   * @param field    {@link HasValue}
   * @return {@link Binding}
   */
  default Binding<?, ?> bindWithConverter(String property,
                                          HasValue<?, ?> field,
                                          Converter<?, ?> converter) {
    AtomicReference<Binding<?, ?>> thread = new AtomicReference<>();
    this.bind(binder -> {
      thread.set(binder.forField(field).withConverter((Converter) converter)
          .bind(parseProperty(property)));
    });

    return thread.get();

  }

  /**
   * Realiza o binding em campos decimais
   *
   * @param property propriedades
   * @param field    {@link HasValue}
   * @return {@link Binding}
   */
  default Binding<?, BigDecimal> bindDecimal(String property,
                                             HasValue<?, String> field) {
    AtomicReference<Binding<?, BigDecimal>> thread = new AtomicReference<>();
    this.bind(binder -> {
      thread.set(binder.forField(field).withNullRepresentation("")
          .withConverter(new StringToBigDecimalConverter())
          .bind(parseProperty(property)));
    });

    return thread.get();
  }

  /**
   * Realiza o binding em campos inteiros
   *
   * @param property propriedade
   * @param field    {@link HasValue}
   * @return {@link Binding}
   */
  default Binding<?, Integer> bindInteger(String property,
                                          HasValue<?, String> field) {
    AtomicReference<Binding<?, Integer>> thread = new AtomicReference<>();
    this.bind(binder -> {
      thread.set(binder.forField(field).withNullRepresentation("")
          .withConverter(new StringToIntegerConverter())
          .bind(parseProperty(property)));
    });

    return thread.get();

  }

  /**
   * Realiza o binding em campos inteiros
   *
   * @param property propriedade
   * @param field    {@link HasValue}
   * @return {@link Binding}
   */
  default Binding<?, Long> bindLong(String property,
                                    HasValue<?, String> field) {
    AtomicReference<Binding<?, Long>> thread = new AtomicReference<>();
    this.bind(binder -> {
      thread.set(binder.forField(field).withNullRepresentation("")
          .withConverter(new StringToLongConverter())
          .bind(parseProperty(property)));
    });

    return thread.get();

  }

  /**
   * @return {@link Binder}
   */
  Binder<T> getBinder();

  /**
   * @return Prefixo utilizado ao realizar binding com o a propriedade
   */
  default String getModelPrefix() {
    return "";
  }

  /**
   * @param property Realiza o perser da propriedade realizando procedimentos no
   *                 nome da propriedade a ser realizado o binding.
   * @return concatenação de {@link #getModelPrefix()} com a property caso o
   *         prefixo não seja vazio. Caso contrário retorna a propriedade.
   */
  default String parseProperty(String property) {
    var prefix = getModelPrefix();
    if (!"".equals(prefix)) {
      return String.format("%s.%s", prefix, property);
    }
    return property;
  }
}
