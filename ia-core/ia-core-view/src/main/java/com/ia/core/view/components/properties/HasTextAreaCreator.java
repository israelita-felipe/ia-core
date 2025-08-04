package com.ia.core.view.components.properties;

import com.ia.core.view.components.textArea.RichTextArea;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Criador de área de texto
 *
 * @author Israel Araújo
 */
public interface HasTextAreaCreator
  extends HasHelp {

  /**
   * Cria um {@link RichTextArea}
   *
   * @param label Título do campo
   * @param help  Texto de ajuda
   * @return {@link RichTextArea}
   */
  default RichTextArea createRichTextArea(String label, String help) {
    RichTextArea field = new RichTextArea(label);
    setHelp(field, help);
    return field;
  }

  /**
   * Cria um campo de texto.
   *
   * @param label Label do {@link TextField}
   * @param help  Texto de ajuda
   * @return {@link TextField}
   */
  default TextArea createTextArea(String label, String help) {
    TextArea field = new TextArea(label);
    field.setWidthFull();
    setHelp(field, help);
    return field;
  }

}
