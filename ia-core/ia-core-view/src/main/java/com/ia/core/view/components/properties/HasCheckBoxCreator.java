package com.ia.core.view.components.properties;

import java.util.Arrays;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;

/**
 * Criador de checkbox
 *
 * @author Israel Araújo
 */
public interface HasCheckBoxCreator
  extends HasHelp {

  /**
   * Cria um checkbox
   *
   * @param label Label do campo
   * @param help  Texto de ajuda
   * @return {@link Checkbox}
   */
  default Checkbox createCheckBoxField(String label, String help) {
    Checkbox field = new Checkbox(label);
    setHelp(field, help);
    return field;
  }

  /**
   * Cria um {@link CheckboxGroup}
   *
   * @param <E>   Tipo de dado representado por um {@link Enum}
   * @param label título
   * @param help  texto de ajuda
   * @param type  Tipo do {@link Enum}
   * @return {@link CheckboxGroup}
   */
  default <E extends Enum<?>> CheckboxGroup<E> createEnumCheckBox(String label,
                                                                  String help,
                                                                  Class<E> type) {
    CheckboxGroup<E> checkGroup = new CheckboxGroup<>(label, Arrays
        .asList(type.getEnumConstants()));
    setHelp(checkGroup, help);
    return checkGroup;
  }

  /**
   * Cria um {@link CheckboxGroup}
   *
   * @param <E>            Tipo de dado representado por um {@link Enum}
   * @param label          título
   * @param help           texto de ajuda
   * @param type           Tipo do {@link Enum}
   * @param labelGenerator Gerador de titulos de dados
   * @return {@link CheckboxGroup}
   */
  default <E extends Enum<?>> CheckboxGroup<E> createEnumCheckBox(String label,
                                                                  String help,
                                                                  Class<E> type,
                                                                  ItemLabelGenerator<E> labelGenerator) {
    CheckboxGroup<E> checkGroup = new CheckboxGroup<>(label, Arrays
        .asList(type.getEnumConstants()));
    checkGroup.setItemLabelGenerator(labelGenerator);
    setHelp(checkGroup, help);
    return checkGroup;
  }
}
