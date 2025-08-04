package com.ia.core.view.components.filter;

import java.util.Collection;

import com.ia.core.service.dto.filter.FieldTypeDTO;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.view.components.IView;
import com.ia.core.view.components.filter.viewModel.IFilterRequestViewModel;
import com.ia.core.view.components.properties.HasBinder;
import com.ia.core.view.components.properties.HasCheckBoxCreator;
import com.ia.core.view.components.properties.HasComboBoxCreator;
import com.ia.core.view.components.properties.HasDateTimeCreator;
import com.ia.core.view.components.properties.HasId;
import com.ia.core.view.components.properties.HasTextFieldCreator;
import com.ia.core.view.components.properties.HasTranslator;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;

/**
 * Inteface que define o contrato de uma View para filtro
 *
 * @author Israel Araújo
 */
@Tag("filter-request-view")
public interface IFilterRequestView
  extends HasId, HasComboBoxCreator, HasTextFieldCreator,
  HasCheckBoxCreator, HasDateTimeCreator, HasTranslator,
  IView<FilterRequestDTO>, HasBinder<IFilterRequestViewModel> {

  /**
   * Cria o botão de deletar
   *
   * @param action ação do botão
   */
  void createDeleteButton(ComponentEventListener<ClickEvent<Button>> action);

  /**
   * Cria a combobox dos campos
   *
   * @param label  Título dos campos
   * @param campos Propriedades
   * @return {@link ComboBox}
   */
  ComboBox<FilterProperty> createFieldsComboBox(String label,
                                                Collection<FilterProperty> campos);

  /**
   * Cria o {@link ComboBox} de operadores
   *
   * @param label      título
   * @param operadores Coleção de operadores utilizados
   * @return {@link ComboBox}
   */
  ComboBox<OperatorDTO> createOperatorsComboBox(String label,
                                                Collection<OperatorDTO> operadores);

  /**
   * Cria o campo de falor
   *
   * @param label Título
   * @param type  {@link FieldTypeDTO} do tipo de dado da propriedade a ser
   *              filtrada
   * @return {@link HasValue}
   */
  HasValue<?, ?> createValorField(String label, FieldTypeDTO type);

  /***
   * Cria o campo de negação do filtro
   *
   * @param label Título do campo
   * @return {@link Checkbox}
   */
  Checkbox createNegateField(String label);

  @Override
  IFilterRequestViewModel getViewModel();

  /**
   * @param action {@link Runnable} contendo ação de deletar
   */
  void setDeleteAction(Runnable action);

  /**
   * @param key {@link FilterProperty}
   */
  void setKey(FilterProperty key);

}
