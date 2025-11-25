package com.ia.core.view.properties;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.ThemableLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;

/**
 * Contrato para criação de {@link TabSheet} e {@link Tab}
 *
 * @author Israel Araújo
 */
public interface HasTabSheetCreator {
  /**
   * Cria uma {@link Tab}
   *
   * @param icon ícone
   * @return {@link Tab}
   */
  default Tab createTab(Icon icon) {
    Tab tab = new Tab();
    tab.add(icon);
    return tab;
  }

  /**
   * Cria uma {@link Tab}
   *
   * @param icon  ícone
   * @param label título da tab
   * @return {@link Tab}
   */
  default Tab createTab(Icon icon, String label) {
    Tab tab = new Tab();
    tab.add(icon, new NativeLabel(label));
    return tab;
  }

  /**
   * Cria uma {@link Tab}
   *
   * @param icon  ícone
   * @param label título da tab
   * @return {@link Tab}
   */
  default Tab createTab(String label) {
    Tab tab = new Tab();
    tab.add(new NativeLabel(label));
    return tab;
  }

  /**
   * Cria uma {@link Tab} em uma {@link TabSheet}
   *
   * @param tabSheet  {@link TabSheet} onde será criada
   * @param icon      ícone
   * @param component conteúdo da tab
   * @return {@link Tab}
   */
  default Tab createTab(TabSheet tabSheet, Icon icon, Component component) {
    Tab tab = createTab(icon);
    if (ThemableLayout.class.isInstance(component)) {
      ((ThemableLayout) component).setMargin(false);
    }
    tabSheet.add(tab, component);
    return tab;
  }

  /**
   * Cria uma {@link Tab} em uma {@link TabSheet}
   *
   * @param tabSheet  {@link TabSheet} onde será criada
   * @param icon      ícone
   * @param label     título
   * @param component conteúdo da tab
   * @return {@link Tab}
   */
  default Tab createTab(TabSheet tabSheet, Icon icon, String label,
                        Component component) {
    Tab tab = createTab(icon, label);
    if (ThemableLayout.class.isInstance(component)) {
      ((ThemableLayout) component).setPadding(false);
      ((ThemableLayout) component).setMargin(false);
    }
    tabSheet.add(tab, component);
    return tab;
  }

  /**
   * Cria uma {@link Tab} em uma {@link TabSheet}
   *
   * @param tabSheet  {@link TabSheet} onde será criada
   * @param icon      ícone
   * @param label     título
   * @param component conteúdo da tab
   * @return {@link Tab}
   */
  default Tab createTab(TabSheet tabSheet, String label,
                        Component component) {
    Tab tab = createTab(label);
    if (ThemableLayout.class.isInstance(component)) {
      ((ThemableLayout) component).setPadding(false);
      ((ThemableLayout) component).setMargin(false);
    }
    tabSheet.add(tab, component);
    return tab;
  }

  /**
   * @return {@link TabSheet}
   */
  default TabSheet createTabSheet() {
    TabSheet tab = new TabSheet();
    return tab;
  }
}
