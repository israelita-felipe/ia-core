package com.ia.core.view.components.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.data.binder.Binder;

import elemental.json.JsonArray;

/**
 * Implementação padrão de um formulário
 *
 * @author Israel Araújo
 * @param <T> Tipo do objeto do formulário
 */
@Tag("form-view")
public abstract class FormView<T extends Serializable>
  extends CustomField<T>
  implements IFormView<T>, com.ia.core.view.components.properties.HasDateTimeCreator {
  /** Serial UID */
  private static final long serialVersionUID = -4513191796912403800L;

  /** Layout principal */
  private FormLayout layout = createFormLayout();
  /** Binder */
  @SuppressWarnings("rawtypes")
  private Binder binder;

  /**
   * Construtor padrão.
   *
   * @param viewModel {@link IFormViewModel}
   */
  public FormView(IFormViewModel<T> viewModel) {
    this.binder = createBinder(viewModel);
    setId(createId());
    super.add(layout);
    setSizeFull();
    createLayout();
    viewModel.addPropertyChangeListener(onChange -> {
      refreshFields();
    });
    refreshFields();
  }

  @Override
  public void add(Component... components) {
    add(this.layout, components);
  }

  /**
   * Adiciona o componente no {@link #layout} com o colspan indicado
   *
   * @param component {@link Component} a ser adicionado
   * @param colspan   colspan desejado
   */
  public void add(Component component, int colspan) {
    add(this.layout, component, colspan);
  }

  /**
   * Adiciona o componente no {@link #layout} com o colspan indicado
   *
   * @param container {@link FormLayout} container onde será adicionado
   * @param component {@link Component} a ser adicionado
   * @param colspan   colspan desejado
   */
  public void add(FormLayout container, Component component, int colspan) {
    container.add(component, colspan);
  }

  /**
   * Adiciona os componentes no {@link HasComponents} de container
   *
   * @param container  {@link HasComponents}
   * @param components Componentes a serem adicionados no container
   */
  public void add(HasComponents container, Component... components) {
    container.add(components);
  }

  /**
   * Cria o binder do view model
   *
   * @param viewModel {@link IFormViewModel}
   * @return {@link Binder}
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected Binder<?> createBinder(IFormViewModel<T> viewModel) {
    var type = viewModel.getClass();
    Binder binder = new Binder<>(type, true);
    binder.setBean(viewModel);
    binder.setReadOnly(viewModel.isReadOnly());
    return binder;
  }

  /**
   * @return {@link FormLayout} que será utilizado para composição deste
   *         componente
   */
  protected FormLayout createFormLayout() {
    FormLayout formLayout = new FormLayout();
    formLayout.setResponsiveSteps(createResponsiveSteps());
    formLayout.setSizeFull();
    return formLayout;
  }

  /**
   * Cria o layout principal
   */
  public void createLayout() {
  }

  /**
   * @return coleção de {@link ResponsiveStep} baseados no tamanho da tela.
   */
  protected List<ResponsiveStep> createResponsiveSteps() {
    return Arrays
        .asList(step(ScreenSize.XSM, xsm()), step(ScreenSize.SM, sm()),
                step(ScreenSize.MD, md()), step(ScreenSize.LG, lg()),
                step(ScreenSize.XLG, xlg()));
  }

  @Override
  protected T generateModelValue() {
    return getViewModel().getModel();
  }

  @SuppressWarnings("unchecked")
  @Override
  public Binder<IFormViewModel<T>> getBinder() {
    return this.binder;
  }

  @Override
  public Locale getLocale() {
    return super.getLocale();
  }

  /**
   * Get the list of {@link ResponsiveStep}s used to configure this layout.
   *
   * @see ResponsiveStep
   * @return the list of {@link ResponsiveStep}s used to configure this layout
   */
  public List<ResponsiveStep> getResponsiveSteps() {
    JsonArray stepsJsonArray = (JsonArray) getElement()
        .getPropertyRaw("responsiveSteps");
    if (stepsJsonArray == null) {
      return Collections.emptyList();
    }
    List<ResponsiveStep> steps = new ArrayList<>();
    for (int i = 0; i < stepsJsonArray.length(); i++) {
      steps.add(stepsJsonArray.get(i));
    }
    return steps;
  }

  /**
   * @return quantidade de colunas para tamanho largo
   */
  protected int lg() {
    return 6;
  }

  /**
   * @return quantidade de colunas para tamanho médio
   */
  protected int md() {
    return 6;
  }

  @Override
  public void refreshFields() {
    var readOnly = getViewModel().isReadOnly();
    setReadOnly(readOnly);
    var binder = getBinder();
    binder.setReadOnly(readOnly);
    binder.refreshFields();
  }

  @Override
  public void remove(Component... components) {
    this.layout.remove(components);
  }

  @Override
  protected void setPresentationValue(T newPresentationValue) {
    getViewModel().setModel(newPresentationValue);
  }

  /**
   * @return quantidade de colunas para tamanho pequeno
   */
  protected int sm() {
    return 6;
  }

  /**
   * @return quantidade de colunas para tamanho extra largo
   */
  protected int xlg() {
    return 6;
  }

  /**
   * @return quantidade de colunas para tamanho extra pequeno
   */
  protected int xsm() {
    return 1;
  }

  @Override
  public String getModelPrefix() {
    return getViewModel().getModelPrefix();
  }
}
