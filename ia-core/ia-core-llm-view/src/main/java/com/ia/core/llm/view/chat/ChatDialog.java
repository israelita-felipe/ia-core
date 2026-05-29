package com.ia.core.llm.view.chat;

import com.ia.core.llm.service.model.chat.ChatRequestTranslator;
import com.ia.core.llm.service.model.chat.ChatTranslator;
import com.ia.core.llm.service.model.prompt.PromptDTO;
import com.ia.core.view.components.dialog.DialogHeaderBar;
import com.ia.core.view.components.properties.*;
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

import java.util.Locale;
import java.util.Objects;
/**
 * Componente de interface visual para chat dialog.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a ChatDialog
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class ChatDialog
  extends Dialog
  implements HasErrorHandle, HasComboBoxCreator, HasTextAreaCreator,
  HasTextFieldCreator, HasTranslator, HasCheckBoxCreator,
  HasBinder<ChatDialogViewModel> {

  private TextArea responseField;
  private TextArea askField;
  private FlexLayout askLayout;
  private ComboBox<PromptDTO> promptField;
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
          .forField(createPromptField($(ChatRequestTranslator.REQUEST),
                                      $(ChatRequestTranslator.HELP.REQUEST),
                                      DataProviderFactory
                                          .createBaseDataProviderFromManager(getViewModel()
                                              .getConfig()
                                              .getPromptService(), PromptDTO.propertyFilters()),
                                      PromptDTO::getTitulo))
          .withConverter(createPromptDTOToIdConverter())
          .bind(parseProperty("promptId"));
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
  protected Converter<PromptDTO, Long> createPromptDTOToIdConverter() {
    return new Converter<PromptDTO, Long>() {

      private static final InheritableThreadLocal<PromptDTO> ctx = new InheritableThreadLocal<>();

      @Override
      public Result<Long> convertToModel(PromptDTO value, ValueContext context) {
        ctx.set(value);
        if (value == null) {
          return Result.ok(null);
        }
        return Result.ok(value.getId());
      }

      @Override
      public PromptDTO convertToPresentation(Long value, ValueContext context) {
        if (value == null) {
          return null;
        }
        PromptDTO promptDTO = ctx.get();
        if (promptDTO == null) {
          return null;
        }
        if (Objects.equals(value, promptDTO.getId())) {
          return promptDTO;
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
  private ComboBox<PromptDTO> createPromptField(String label,
                                                String help,
                                                DataProvider<PromptDTO, String> dataProvider,
                                                ItemLabelGenerator<PromptDTO> labelGenerator) {
    promptField = createComboBox(label, help, dataProvider, labelGenerator);
    askLayout.add(promptField);
    promptField.setVisible(false);
    return promptField;
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
      this.promptField.clear();
      this.promptField.setVisible(value);
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
