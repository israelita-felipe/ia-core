package com.ia.core.view.components.filter;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.FilterRequestTranslator;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.view.components.filter.viewModel.IFilterRequestViewModel;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Binder.Binding;
import com.vaadin.flow.data.converter.Converter;

import lombok.Getter;

/**
 * Implementação padrão de uma view para filter request
 *
 * @author Israel Araújo
 */

public class FilterRequestView
  extends CustomField<FilterRequestDTO>
  implements IFilterRequestView {
  /** Serial UUID */
  private static final long serialVersionUID = -3427074390627935138L;
  /** View model */
  @Getter
  private IFilterRequestViewModel viewModel;
  /** Campos de filtros */
  private ComboBox<FilterProperty> fields;
  /** Layouts dos campos */
  private FlexLayout fieldsWrapper;
  /** Campos de operadores */
  private ComboBox<OperatorDTO> operators;
  /** Layout dos operadores */
  private FlexLayout operatorsWrapper;
  /** Campo de valor */
  private HasValue<?, ?> valueField;
  /** Layout do valor */
  private FlexLayout valueWrapper;
  /** Botão de deleção */
  private Button deleteButton;
  /** Layout do botão de deleção */
  private FlexLayout deleteWrapper;
  /** Campo de negação do filtro */
  private Checkbox negate;
  /** Binder */
  @SuppressWarnings("rawtypes")
  @Getter
  private Binder binder;

  /**
   * Cria o filtro
   *
   * @param viewModel view model do filtro
   */
  public FilterRequestView(IFilterRequestViewModel viewModel) {
    super();
    binder = createBinder(viewModel);
    setId(createId());
    this.viewModel = viewModel;
    createMainLayout();
  }

  /**
   * Cria o binder
   *
   * @param viewModel {@link IFilterRequestViewModel}
   * @return {@link Binder}
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected Binder createBinder(IFilterRequestViewModel viewModel) {
    Binder binder = new Binder<>(viewModel.getClass(), true);
    binder.setBean(viewModel);
    binder.setReadOnly(viewModel.isReadOnly());
    return binder;
  }

  /**
   * Realiza o binding no campo
   */
  protected void bindCampo() {
    List<FilterProperty> items = viewModel.getFilterMap().keySet().stream()
        .distinct().toList();
    ComboBox<FilterProperty> combo = createFieldsComboBox($(FilterRequestTranslator.KEY),
                                                          items);
    combo.setItemLabelGenerator(item -> $(item.getLabel()));
    combo.addValueChangeListener(onChange -> {
      setKey(onChange.getValue());
      setOperations(onChange.getValue());
      setCampo(onChange.getValue().getProperty());
    });
  }

  /**
   * Atribui as operações dado uma propriedade selecionada
   *
   * @param value {@link FilterProperty} selecionada
   */
  private void setOperations(FilterProperty value) {
    if (value == null) {
      this.operators.clear();
    }
    if (this.operators != null) {
      this.operators.setItems(viewModel.getFilterMap().get(value).stream()
          .map(FilterRequestDTO::getOperator).distinct().toList());
    }
  }

  /**
   * Realiza o binding na operação
   */
  protected void bindOperacao() {
    ComboBox<OperatorDTO> combo = createOperatorsComboBox($(OperatorDTO.class
        .getCanonicalName()), viewModel.getFilterMap().values().stream()
            .flatMap(colecao -> colecao.stream())
            .map(item -> item.getOperator()).distinct().toList());
    combo.setItemLabelGenerator(item -> $(item.name()));
    bind(FilterRequestDTO.CAMPOS.OPERACAO, combo);
  }

  @Override
  public void createDeleteButton(ComponentEventListener<ClickEvent<Button>> action) {
    if (this.deleteButton != null) {
      this.deleteWrapper.remove(this.deleteButton);
    }
    this.deleteButton = new Button(VaadinIcon.TRASH.create(), action);
    this.deleteButton.setWidthFull();
    this.deleteWrapper

        .add(this.deleteButton);
  }

  /**
   * Cria o layout do botão deletar
   *
   * @param layout {@link FlexLayout} onde será criado
   */
  protected void createDeleteWrapper(FlexLayout layout) {
    this.deleteWrapper = new FlexLayout();
    layout.add(this.deleteWrapper);
  }

  @Override
  public ComboBox<FilterProperty> createFieldsComboBox(String label,
                                                       Collection<FilterProperty> campos) {
    this.fields = createComboBox(label, $(FilterRequestTranslator.KEY));
    this.fields.setItems(campos);
    this.fields.setWidthFull();
    this.fieldsWrapper.add(this.fields);
    return this.fields;
  }

  @Override
  public Checkbox createNegateField(String label) {
    this.negate = createCheckBoxField(label,
                                      $(FilterRequestTranslator.NEGATE));
    this.operatorsWrapper.add(this.negate);
    return this.negate;
  }

  /**
   * cria o layout dos campos
   *
   * @param layout {@link FlexLayout}
   */
  protected void createFieldsWrapper(FlexLayout layout) {
    this.fieldsWrapper = new FlexLayout();
    this.fieldsWrapper.setWidthFull();
    layout.add(this.fieldsWrapper);
  }

  /**
   * Cria o layout principal
   */
  protected void createMainLayout() {
    setWidthFull();
    FlexLayout layout = new FlexLayout();
    layout.setFlexGrow(1);
    layout.setAlignItems(Alignment.BASELINE);
    layout.setFlexWrap(FlexWrap.WRAP);
    createWrappers(layout);
    layout.getStyle().setPadding("var(--lumo-space-s)");
    layout.getStyle().setBorder("1px solid var(--lumo-contrast-20pct)");
    layout.getStyle().setBorderRadius("5px");
    add(layout);
    bindNegacao();
    bindCampo();
    bindOperacao();
  }

  /**
   * Realiza o bind no campo de negação
   */
  protected void bindNegacao() {
    bind("negate", createNegateField($(FilterRequestTranslator.NEGATE)));
  }

  @Override
  public ComboBox<OperatorDTO> createOperatorsComboBox(String label,
                                                       Collection<OperatorDTO> operadores) {
    this.operators = createComboBox(label,
                                    $(FilterRequestTranslator.OPERATOR));
    this.operators.setItems(operadores);
    this.operators.setItemLabelGenerator(item -> $(item.name()));
    this.operators.setWidthFull();
    this.operatorsWrapper.add(this.operators);
    return this.operators;
  }

  /**
   * Cria o layout dos operadores
   *
   * @param layout {@link FlexLayout}
   */
  protected void createOperatorsWrapper(FlexLayout layout) {
    this.operatorsWrapper = new FlexLayout();
    this.operatorsWrapper.setAlignItems(Alignment.BASELINE);
    layout.add(this.operatorsWrapper);
    this.operatorsWrapper.setWidthFull();
  }

  protected HasValue<?, ?> createValorField(FilterProperty key,
                                            String label, FieldType type) {
    if (this.valueField != null) {
      this.valueWrapper.remove((Component) this.valueField);
    }
    this.valueField = createValueFieldFromFieldType(key, label, type);
    if (this.valueField != null) {
      this.valueWrapper.setVisible(true);
      Component newValueField = (Component) this.valueField;
      ((HasSize) newValueField).setWidthFull();
      this.valueWrapper.add(newValueField);
    }
    return this.valueField;
  }

  /**
   * @param key
   * @param label
   * @param type
   */
  @Override
  public HasValue<?, ?> createValueFieldFromFieldType(FilterProperty key,
                                                      String label,
                                                      FieldType type) {
    switch (type) {
    case STRING:
      return createTextField(label, $(FilterRequestTranslator.TEXT_FILTER));
    case DOUBLE:
      return createNumeroTextField(label,
                                   $(FilterRequestTranslator.DOUBLE_FILTER));
    case INTEGER:
      return createNumeroTextField(label,
                                   $(FilterRequestTranslator.INTEGER_FILTER));
    case LONG:
      return createNumeroTextField(label,
                                   $(FilterRequestTranslator.LONG_FILTER));
    case BOOLEAN:
      return createCheckBoxField(label,
                                 $(FilterRequestTranslator.BOOLEAN_FILTER));
    case CHAR:
      return createTextField(label,
                             $(FilterRequestTranslator.CHARACTER_FLTER));
    case DATE:
      return createDateField(label, $(FilterRequestTranslator.DATE_FILTER));
    case TIME:
      return createTimeField(label, $(FilterRequestTranslator.TIME_FILTER));
    case DATE_TIME:
      return createDateTimePickerField(label,
                                       $(FilterRequestTranslator.DATE_TIME_FILTER));
    case ENUM:
      return createEnumComboBox(label,
                                $(FilterRequestTranslator.ENUM_FILTER),
                                getViewModel().getEnumType(),
                                value -> $(value.name()));
    default:
      return null;
    }
  }

  /**
   * Cria o layout do valor
   *
   * @param layout {@link FlexLayout}
   */
  protected void createValueWrapper(FlexLayout layout) {
    this.valueWrapper = new FlexLayout();
    this.valueWrapper.setVisible(false);
    this.valueWrapper.setWidthFull();
    layout.add(this.valueWrapper);
  }

  /**
   * Cria os leiautes
   *
   * @param layout {@link FlexLayout} onde será criado
   */
  protected void createWrappers(FlexLayout layout) {
    createDeleteWrapper(layout);
    createFieldsWrapper(layout);
    createOperatorsWrapper(layout);
    createValueWrapper(layout);
  }

  @Override
  protected FilterRequestDTO generateModelValue() {
    return this.viewModel.getModel();
  }

  @Override
  public Locale getLocale() {
    return super.getLocale();
  }

  /**
   * @param campo campo desejado
   */
  private void setCampo(String campo) {
    viewModel.getModel().setKey(campo);
  }

  @Override
  public void setDeleteAction(Runnable action) {
    createDeleteButton(click -> action.run());
  }

  @Override
  public void setKey(FilterProperty key) {
    if (viewModel.getModel() != null) {
      viewModel.getModel().setKey(key.getProperty());
      Collection<FilterRequestDTO> itens = viewModel.getFilterMap()
          .get(key);
      if (itens != null) {
        itens.stream().findAny().map(item -> item.getFieldType())
            .ifPresent(type -> {
              HasValue<?, ?> valorField = createValorField(key,
                                                           $(FilterRequestTranslator.VALUE),
                                                           type);
              bindValorField(key, valorField);
              setType(type);
            });
      }
    }
  }

  /**
   * @param valorField
   * @return
   */
  public Binding<?, ?> bindValorField(FilterProperty key,
                                      HasValue<?, ?> valorField) {
    Converter<?, ?> converter = valorFieldConverter(key);
    if (converter != null) {
      return bindWithConverter(FilterRequestDTO.CAMPOS.VALOR, valorField,
                               converter);
    }
    return bind(FilterRequestDTO.CAMPOS.VALOR, valorField);
  }

  public Converter<?, ?> valorFieldConverter(FilterProperty key) {
    return null;
  }

  @Override
  protected void setPresentationValue(FilterRequestDTO newPresentationValue) {
    this.viewModel.setModel(newPresentationValue);
  }

  /**
   * Atribui o tipo
   *
   * @param type {@link FieldType}
   */
  private void setType(FieldType type) {
    if (viewModel.getModel() != null) {
      viewModel.getModel().setFieldType(type);
    }
  }

  @Override
  public String getModelPrefix() {
    return getViewModel().getModelPrefix();
  }
}
