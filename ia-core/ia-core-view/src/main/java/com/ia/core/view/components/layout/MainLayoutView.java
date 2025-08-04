package com.ia.core.view.components.layout;

import java.util.Locale;

import com.ia.core.view.components.dialog.exception.ExceptionViewFactory;
import com.ia.core.view.components.layout.menu.AbstractMenuLayoutView;
import com.ia.core.view.components.layout.menu.AbstractMenuLayoutViewModel;
import com.ia.core.view.components.properties.HasConfirmDialog;
import com.ia.core.view.components.properties.HasMessageNotification;
import com.ia.core.view.components.properties.HasTranslator;
import com.ia.core.view.properties.HasErrorHandle;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.router.RouterLayout;

import lombok.Getter;

/**
 * Layout principal de aplicações.
 *
 * @author Israel Araújo
 */
@SuppressWarnings("serial")
public abstract class MainLayoutView
  extends AppLayout
  implements RouterLayout, HasConfirmDialog, HasMessageNotification,
  HasErrorHandle, HasTranslator {
  /** View model do layout */
  @Getter
  private MainLayoutViewModel viewModel;

  /**
   * Construtor padrão.
   *
   * @param viewModel View Model do layout
   */
  public MainLayoutView(MainLayoutViewModel viewModel) {
    this.viewModel = viewModel;
    init();
    createLayout();
    setPrimarySection(Section.DRAWER);
  }

  /**
   *
   */
  protected void init() {

  }

  /**
   * Cria o layout principal
   */
  public void createLayout() {
    createMenu(getViewModel().getMenuViewModel());
  }

  /**
   * Cria o menu principal
   *
   * @param menuViewModel view model do menu
   * @return {@link AbstractMenuLayoutView}
   */
  public AbstractMenuLayoutView createMenu(AbstractMenuLayoutViewModel menuViewModel) {
    AbstractMenuLayoutView menuView = createMenuView(menuViewModel);
    addToDrawer(menuView);
    return menuView;
  }

  /**
   * Cria o menu
   *
   * @param menuViewModel {@link AbstractMenuLayoutViewModel} da view
   * @return {@link AbstractMenuLayoutView}
   */
  protected AbstractMenuLayoutView createMenuView(AbstractMenuLayoutViewModel menuViewModel) {
    return new AbstractMenuLayoutView(menuViewModel);
  }

  /**
   * Cria a barra de navegação
   *
   * @param title Título da barra
   */
  public void createNavbar(String title) {
    addToNavbar(true, createToggle(), createTitle(title));
  }

  /**
   * Cria o título da barra de navegação
   *
   * @param title Texto do título
   * @return {@link Component}
   */
  public Component createTitle(String title) {
    H2 titulo = new H2(title);
    titulo.setWidthFull();
    return titulo;
  }

  /**
   * @return {@link DrawerToggle}
   */
  protected DrawerToggle createToggle() {
    return new DrawerToggle();
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
