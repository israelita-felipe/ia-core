package com.ia.core.view.components.properties;

import java.util.Arrays;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.data.provider.DataProvider;

/**
 * Propriedade de criação de {@link ComboBox}
 *
 * @author Israel Araújo
 */
public interface HasComboBoxCreator
  extends HasHelp {
  /**
   * Cria uma {@link ComboBox}
   *
   * @param <E>   Tipo de dado da {@link ComboBox}
   * @param label título da combo
   * @param help  Texto de ajuda da {@link ComboBox}
   * @return {@link ComboBox}
   */
  default <E> ComboBox<E> createComboBox(String label, String help) {
    ComboBox<E> comboBox = new ComboBox<>(label);
    setHelp(comboBox, help);
    comboBox.setWidthFull();
    return comboBox;
  }

  /**
   * Cria uma {@link ComboBox}
   *
   * @param <E>          Tipo de dado da {@link ComboBox}
   * @param label        título da combo
   * @param help         Texto de ajuda da {@link ComboBox}
   * @param dataProvider {@link DataProvider}
   * @return {@link ComboBox}
   */
  default <E> ComboBox<E> createComboBox(String label, String help,
                                         DataProvider<E, String> dataProvider) {
    ComboBox<E> comboBox = new ComboBox<>(label);
    setHelp(comboBox, help);
    comboBox.setItems(dataProvider);
    comboBox.setWidthFull();
    return comboBox;
  }

  /**
   * Cria uma {@link ComboBox}
   *
   * @param <E>            Tipo de dado da {@link ComboBox}
   * @param label          título da combo
   * @param help           Texto de ajuda da {@link ComboBox}
   * @param dataProvider   {@link DataProvider}
   * @param labelGenerator Gerador de label dos dados
   * @return {@link ComboBox}
   */
  default <E> ComboBox<E> createComboBox(String label, String help,
                                         DataProvider<E, String> dataProvider,
                                         ItemLabelGenerator<E> labelGenerator) {
    ComboBox<E> comboBox = new ComboBox<>(label);
    setHelp(comboBox, help);
    comboBox.setItemLabelGenerator(labelGenerator);
    comboBox.setItems(dataProvider);
    comboBox.setWidthFull();
    return comboBox;
  }

  /**
   * Cria uma {@link ComboBox}
   *
   * @param <E>   Tipo de dado da {@link ComboBox}
   * @param label título
   * @param help  texto de ajuda
   * @param type  tipo da classe do {@link Enum}
   * @return {@link ComboBox} de {@link Enum}
   */
  default <E extends Enum<?>> ComboBox<E> createEnumComboBox(String label,
                                                             String help,
                                                             Class<E> type) {
    ComboBox<E> combo = new ComboBox<>(label, Arrays
        .asList(type.getEnumConstants()));
    combo.setWidthFull();
    setHelp(combo, help);
    return combo;
  }

  /**
   * Cria uma {@link ComboBox}
   *
   * @param <E>            Tipo de dado da {@link ComboBox}
   * @param label          título
   * @param help           texto de ajuda
   * @param type           tipo da classe do {@link Enum}
   * @param labelGenerator Gerador de label dos dados
   * @return {@link ComboBox} de {@link Enum}
   */
  default <E extends Enum<?>> ComboBox<E> createEnumComboBox(String label,
                                                             String help,
                                                             Class<E> type,
                                                             ItemLabelGenerator<E> labelGenerator) {
    ComboBox<E> combo = new ComboBox<>(label, Arrays
        .asList(type.getEnumConstants()));
    combo.setItemLabelGenerator(labelGenerator);
    combo.setWidthFull();
    setHelp(combo, help);
    return combo;
  }
}
