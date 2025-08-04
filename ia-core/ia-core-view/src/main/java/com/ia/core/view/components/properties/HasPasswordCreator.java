package com.ia.core.view.components.properties;

import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Propriedade de criar um objeto
 *
 * @author Israel Araújo
 */
public interface HasPasswordCreator
  extends HasHelp {

  /**
   * Cria um campo de senha.
   *
   * @param label Label do {@link TextField}
   * @param help  Texto de ajuda
   * @return {@link TextField}
   */
  default PasswordField createPasswordField(String label, String help) {
    PasswordField field = new PasswordField(label);
    field.setWidthFull();
    setHelp(field, help);
    return field;
  }

}
