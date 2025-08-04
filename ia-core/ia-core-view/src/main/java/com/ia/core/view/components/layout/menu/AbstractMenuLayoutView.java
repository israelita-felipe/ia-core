package com.ia.core.view.components.layout.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.ia.core.view.components.dialog.exception.ExceptionViewFactory;
import com.ia.core.view.components.properties.HasConfirmDialog;
import com.ia.core.view.components.properties.HasMessageNotification;
import com.ia.core.view.components.properties.HasTranslator;
import com.ia.core.view.properties.AutoCastable;
import com.ia.core.view.properties.HasErrorHandle;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.component.tabs.Tab;

import lombok.Getter;

/**
 * Abstração de um menu
 *
 * @author Israel Araújo
 */
public class AbstractMenuLayoutView
  extends VerticalLayout
  implements HasConfirmDialog, HasMessageNotification, HasErrorHandle,
  AutoCastable, HasTranslator {
  /** Serial UID */
  private static final long serialVersionUID = -9254967662726091L;
  /** Map dos menus */
  protected final Map<SideNav, List<SideNavItem>> menuMap = new HashMap<>();
  /** View Model do menu */
  @Getter
  private AbstractMenuLayoutViewModel viewModel;

  /**
   * Construtor padrão
   *
   * @param viewModel {@link AbstractMenuLayoutViewModel}
   */
  public AbstractMenuLayoutView(AbstractMenuLayoutViewModel viewModel) {
    super();
    menuMap.put(null, new ArrayList<>());
    this.viewModel = viewModel;
    createLayout();
  }

  /**
   * Adiciona um grupo
   *
   * @param nome Nome do grupo
   * @return {@link Tab} que representa o grupo.
   */
  protected SideNav addGroup(String nome) {
    SideNav tab = null;
    add(tab = criarGrupo(nome));
    menuMap.put(tab, new ArrayList<>());
    return tab;
  }

  /**
   * Adiciona uma tab
   *
   * @param parent    Tab parent
   * @param viewIcon  ícone.
   * @param viewName  label
   * @param viewClass classe de rota.
   * @return {@link Tab}
   */
  protected SideNavItem addItem(SideNav parent, VaadinIcon viewIcon,
                                String viewName,
                                Class<? extends Component> viewClass) {
    if (parent == null) {
      parent = addGroup("");
    }
    SideNavItem tab = null;
    parent.addItem(tab = createItem(viewIcon, viewName, viewClass));
    menuMap.get(parent).add(tab);
    checarVisibilidade(parent);
    return tab;
  }

  /**
   * Adiciona um separador na nav
   *
   * @param nav {@link SideNav} onde será posto o item
   */
  protected void addSeparator(SideNav nav) {
    nav.getElement().appendChild(new Hr().getElement());
  }

  /**
   * Checa a visibilidade de uma tab de grupo
   *
   * @param root {@link Tab}
   */
  protected void checarVisibilidade(SideNav root) {
    if (root != null) {
      if (menuMap.get(root).size() == 0) {
        root.getElement().removeFromParent();
      }
    }
  }

  /**
   * Cria um item de menu em uma {@link Tab}
   *
   * @param viewIcon ícone.
   * @param name     Título do item
   * @param type     classe de rota.
   * @return {@link SideNavItem}
   */
  public SideNavItem createItem(VaadinIcon viewIcon, String name,
                                Class<? extends Component> type) {
    return new SideNavItem(name, type, createMenuIcon(viewIcon));
  }

  /**
   * Cria o layout principal
   */
  public void createLayout() {

  }

  /**
   * Cria o ícone de menu
   *
   * @param viewIcon {@link VaadinIcon}
   * @return {@link Icon}
   */
  protected Icon createMenuIcon(VaadinIcon viewIcon) {
    Icon icon = viewIcon.create();
    icon.getStyle().set("padding", "0");
    return icon;
  }

  /**
   * Cria um {@link Tab} de grupo.
   *
   * @param nome {@link String} contendo o nome.
   * @return {@link Tab}
   */
  private SideNav criarGrupo(String nome) {
    SideNav group = new SideNav(nome);
    group.setCollapsible(nome != null && nome.length() != 0);
    group.setWidthFull();
    return group;
  }

  @Override
  public Locale getLocale() {
    return super.getLocale();
  }

  @Override
  public void handleError(Exception ex) {
    HasErrorHandle.super.handleError(ex);
    ExceptionViewFactory.showError(ex);
  }
}
