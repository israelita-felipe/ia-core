package com.ia.core.view.components.properties;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Criadores de campos de texto
 *
 * @author Israel Araújo
 */
public interface HasTextFieldCreator
  extends HasHelp {

  /**
   * Cria um campo de valor inteiro
   *
   * @param label Label do campo
   * @param help  Texto de ajuda
   * @return {@link TextField}
   */
  default TextField createInteiroTextField(String label, String help) {
    TextField field = createTextField(label, help);
    field.setAllowedCharPattern("[0-9]");
    field.setWidthFull();
    return field;
  }

  /**
   * Cria um campo de valor numerico
   *
   * @param label Label do campo
   * @param help  Texto de ajuda
   * @return {@link TextField}
   */
  default TextField createNumeroTextField(String label, String help) {
    TextField field = createTextField(label, help);
    field.setAllowedCharPattern("[0-9,]");
    field.setWidthFull();
    return field;
  }

  /**
   * Cria um campo de texto.
   *
   * @param label Label do {@link TextField}
   * @param help  Texto de ajuda
   * @return {@link TextField}
   */
  default TextField createTextField(String label, String help) {
    TextField field = new TextField(label);
    field.setWidthFull();
    setHelp(field, help);
    return field;
  }

  /**
   * Cria um campo de valor monetário
   *
   * @param label Label do campo
   * @param help  Texto de ajuda
   * @return {@link TextField}
   */
  default TextField createValorMonetarioTextField(String label,
                                                  String help) {
    TextField field = createTextField(label, help);
    field.setAllowedCharPattern("[0-9,]");
    field.setPrefixComponent(new Span("R$"));
    field.setWidthFull();
    return field;
  }

}
