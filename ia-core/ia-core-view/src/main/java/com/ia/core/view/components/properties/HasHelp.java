package com.ia.core.view.components.properties;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.html.Span;

/**
 * Possui ajuda
 *
 * @author Israel Araújo
 */
public interface HasHelp
  extends HasElement {

  /**
   * Cria o conteúdo de ajuda
   *
   * @param help Texto de ajuda
   * @return {@link Component}
   */
  default Component createHelpComponentFromText(String help) {
    return new Span(help);
  }

  /**
   * Indica se a ajuda está visível
   *
   * @return <code>false</code> por padrão.
   */
  default boolean isHelpVisible() {
    return false;
  }

  /**
   * Atribui ajuda a um componente
   *
   * @param hasHelper componente que possui ajuda
   * @param help      componente de ajuda
   */
  default void setHelp(Component hasHelper, Component help) {
    getElement().getChildren()
        .filter(child -> "helper".equals(child.getAttribute("slot")))
        .findAny().ifPresent(getElement()::removeChild);

    if (help != null) {
      help.getElement().setAttribute("slot", "helper");
      getElement().appendChild(help.getElement());
    }
    help.setVisible(isHelpVisible());
  }

  /**
   * Atribui ajuda a um componente
   *
   * @param hasHelper component que possui ajuda
   * @param help      texto de ajuda
   */
  default void setHelp(Component hasHelper, String help) {
    setHelp(hasHelper, createHelpComponentFromText(help));
  }
}
