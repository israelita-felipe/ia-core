package com.ia.core.llm.view.chat;

import java.util.Locale;
import java.util.Objects;

import com.ia.core.llm.service.model.chat.ChatRequestTranslator;
import com.ia.core.llm.service.model.chat.ChatTranslator;
import com.ia.core.llm.service.model.comando.ComandoSistemaDTO;
import com.ia.core.view.components.dialog.DialogHeaderBar;
import com.ia.core.view.components.properties.HasBinder;
import com.ia.core.view.components.properties.HasCheckBoxCreator;
import com.ia.core.view.components.properties.HasComboBoxCreator;
import com.ia.core.view.components.properties.HasTextAreaCreator;
import com.ia.core.view.components.properties.HasTextFieldCreator;
import com.ia.core.view.components.properties.HasTranslator;
import com.ia.core.view.properties.HasErrorHandle;
import com.ia.core.view.utils.DataProviderFactory;
import com.ia.core.view.utils.Size;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.data.provider.DataProvider;

import lombok.Getter;

/**
 * @author Israel Araújo
 */
public class ChatDialog
  extends Dialog
  implements HasErrorHandle, HasComboBoxCreator, HasTextAreaCreator,
  HasTextFieldCreator, HasTranslator, HasCheckBoxCreator,
  HasBinder<ChatDialogViewModel> {

  private TextArea responseField;
  private TextArea askField;
  private FlexLayout askLayout;
  private ComboBox<ComandoSistemaDTO> finalidadeField;
  @Getter
  private Binder<ChatDialogViewModel> binder;

  /**
   *
   */
  public ChatDialog(ChatDialogViewModel viewModel, Size width,
                    Size height) {
    super();
    this.binder = new Binder<>(ChatDialogViewModel.class, true);
    this.binder.setBean(viewModel);
    createLayout();
    setWidth(width.getSize(), width.getUnit());
    setHeight(height.getSize(), height.getUnit());
  }

  /**
   * @param layout
   */
  protected void createAsk() {
    createUseTemplateAsk();
    createAskLayout();
    bind(binder -> {
      binder
          .forField(createFinalidadeField($(ChatRequestTranslator.REQUEST),
                                          $(ChatRequestTranslator.HELP.REQUEST),
                                          DataProviderFactory
                                              .createBaseDataProviderFromManager(getViewModel()
                                                  .getConfig()
                                                  .getComandoSistemaService(), ComandoSistemaDTO.propertyFilters()),
                                          ComandoSistemaDTO::getTitulo))
          .withConverter(createComandoSistemaDTOToUUIDConverter())
          .bind(parseProperty("comandoSistemaID"));
    });
    bind("request", createAskField($(ChatRequestTranslator.REQUEST),
                                   $(ChatRequestTranslator.HELP.REQUEST)));
    createAskButton();
  }

  /**
   * @return
   */
  private Button createAskButton() {
    Button button = new Button(VaadinIcon.COMMENT.create(), onClick -> {
      try {
        this.responseField.setValue(getViewModel().ask());
        this.askField.clear();
      } catch (Exception e) {
        handleError(e);
      }
    });
    askLayout.add(button);
    return button;
  }

  /**
   * @return
   */
  private TextArea createAskField(String label, String help) {
    askField = new TextArea();
    askField.setWidth("inherit");
    askField.setMaxHeight(20, Unit.VH);
    askLayout.add(askField);
    return askField;
  }

  /**
   *
   */
  protected void createAskLayout() {
    askLayout = new FlexLayout();
    askLayout.getStyle().set("gap", "8px");
    askLayout.setWidthFull();
    getFooter().add(askLayout);
  }

  /**
   * @return
   */
  protected Converter<ComandoSistemaDTO, Long> createComandoSistemaDTOToUUIDConverter() {
    return new Converter<ComandoSistemaDTO, Long>() {

      private static final InheritableThreadLocal<ComandoSistemaDTO> ctx = new InheritableThreadLocal<>();

      @Override
      public Result<Long> convertToModel(ComandoSistemaDTO value,
                                         ValueContext context) {
        ctx.set(value);
        if (value == null) {
          return Result.ok(null);
        }
        return Result.ok(value.getId());
      }

      @Override
      public ComandoSistemaDTO convertToPresentation(Long value,
                                                     ValueContext context) {
        if (value == null) {
          return null;
        }
        ComandoSistemaDTO comandoSistemaDTO = ctx.get();
        if (comandoSistemaDTO == null) {
          return null;
        }
        if (Objects.equals(value, comandoSistemaDTO.getId())) {
          return comandoSistemaDTO;
        }
        return null;
      }
    };
  }

  /**
   * @param $
   * @param $2
   * @param dataProvider
   * @param object
   * @return
   */
  private ComboBox<ComandoSistemaDTO> createFinalidadeField(String label,
                                                            String help,
                                                            DataProvider<ComandoSistemaDTO, String> dataProvider,
                                                            ItemLabelGenerator<ComandoSistemaDTO> labelGenerator) {
    finalidadeField = createComboBox(label, help, dataProvider,
                                     labelGenerator);
    askLayout.add(finalidadeField);
    finalidadeField.setVisible(false);
    return finalidadeField;
  }

  /**
   * Cria o layout da aplicação
   */
  protected void createLayout() {
    setCloseOnEsc(true);
    setCloseOnOutsideClick(false);
    setResizable(true);
    setDraggable(true);
    DialogHeaderBar.addTo(this);
    createResponse();
    createAsk();
  }

  /**
   * @param layout
   */
  protected void createResponse() {
    add(createResponseField());
  }

  /**
   * @return
   */
  private TextArea createResponseField() {
    responseField = new TextArea();
    responseField.setSizeFull();
    responseField.setReadOnly(true);
    return responseField;
  }

  /**
   *
   */
  protected Checkbox createUseTemplateAsk() {
    var field = createCheckBoxField($(ChatTranslator.TEMPLATE_CHAT),
                                    $(ChatTranslator.TEMPLATE_CHAT));
    field.addValueChangeListener(onChange -> {
      var value = onChange.getValue();
      this.askField.clear();
      this.askField.setVisible(!value);
      this.finalidadeField.clear();
      this.finalidadeField.setVisible(value);
    });
    getFooter().add(field);
    return field;
  }

  @Override
  public Locale getLocale() {
    return super.getLocale();
  }

  private ChatDialogViewModel getViewModel() {
    return getBinder().getBean();
  }

  private void setViewModel(ChatDialogViewModel viewModel) {
    getBinder().setBean(viewModel);
  }

  @Override
  public String getModelPrefix() {
    return "model";
  }
}
