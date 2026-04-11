package com.ia.core.view.components.properties;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.shared.HasAllowedCharPattern;

/**
 * Criadores de campos de seleção de cor.
 *
 * @author Israel Araújo
 */
public interface HasColorPickerCreator
  extends HasHelp {

  /**
   * Cria um campo de seleção de cor.
   *
   * @param label Label do campo
   * @param help  Texto de ajuda
   * @return {@link ColorPicker}
   */
  default ColorPicker createColorPicker(String label, String help) {
    ColorPicker picker = new ColorPicker(label);
    setHelp(picker, help);
    return picker;
  }

  /**
   * Componente de seleção de cor que combina um TextField para entrada de
   * código hexadecimal com uma pré-visualização da cor.
   */
    class ColorPicker
    extends CustomField<String> implements HasAllowedCharPattern {

    private final Input textField;

    public ColorPicker(String label) {
        setLabel(label);
      textField = new Input();
      textField.setType("color");
      textField.setPlaceholder("#FF5733");
      setAllowedCharPattern("[#0-9A-Fa-f]");
      textField.setWidthFull();
      add(textField);
    }

      public void setPlaceholder(String placeholder) {
      textField.setPlaceholder(placeholder);
    }

    @Override
    protected String generateModelValue() {
      return textField.getValue();
    }

    @Override
    protected void setPresentationValue(String newPresentationValue) {
      textField.setValue(newPresentationValue != null ? newPresentationValue
                                                      : "");
    }
  }
}
