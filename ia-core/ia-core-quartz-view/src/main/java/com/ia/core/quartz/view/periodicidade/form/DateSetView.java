package com.ia.core.quartz.view.periodicidade.form;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * Campo customizado para seleção de múltiplas datas. Compatível com Binder.
 *
 * @author Israel Araújo
 */
@Tag("date-set-field")
public class DateSetView
  extends CustomField<Set<LocalDate>> {

  private static final long serialVersionUID = 1L;

  private final HorizontalLayout container = new HorizontalLayout();
  private final DatePicker datePicker = new DatePicker();

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter
      .ofPattern("dd/MM/yyyy");

  private Set<LocalDate> dates;

  public DateSetView() {
    configureLayout();
    configureDatePicker();
    super.add(container);
  }

  private void configureLayout() {
    container.setSpacing(true);
    container.setPadding(false);
    container.setWidthFull();
  }

  private void configureDatePicker() {
    datePicker.setWidthFull();

    datePicker.addValueChangeListener(event -> {
      if (event.getValue() != null && !isReadOnly()) {

        if (dates != null) {
          dates.add(event.getValue());
          updateChips();
          // Notifica o CustomField que o valor mudou
          updateValue();
        }

        datePicker.setValue(null);
      }
    });

    super.add(datePicker);
  }

  // ==========================================================
  // MÉTODOS OBRIGATÓRIOS DO CustomField
  // ==========================================================

  @Override
  protected Set<LocalDate> generateModelValue() {
    return dates;
  }

  @Override
  protected void setPresentationValue(Set<LocalDate> newPresentationValue) {
    dates = newPresentationValue;
    updateChips();
  }

  // ==========================================================
  // UI
  // ==========================================================

  private void updateChips() {

    container.removeAll();

    if (dates != null) {
      for (LocalDate date : dates.stream().sorted()
          .collect(Collectors.toList())) {
        add((Component) createChip(date));
      }
    }
  }

  private Span createChip(LocalDate date) {

    Span chip = new Span();
    chip.getElement()
        .setAttribute("style",
                      "display: inline-flex; align-items: center; background: #e0e0e0;"
                          + "border-radius: 4px; padding: 4px 8px; margin: 2px;");

    Span dateLabel = new Span(date.format(FORMATTER));
    chip.add(dateLabel);

    if (!isReadOnly()) {
      Button removeButton = new Button(VaadinIcon.CLOSE.create());
      removeButton.getElement()
          .setAttribute("style", "margin-left: 4px; padding: 0 4px;");
      removeButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

      removeButton.addClickListener(e -> {
        if (dates != null) {
          dates.remove(date);
          updateChips();
          updateValue();
        }
      });

      chip.add(removeButton);
    }

    return chip;
  }

  // ==========================================================
  // READ ONLY
  // ==========================================================

  @Override
  public void setReadOnly(boolean readOnly) {
    getElement().setProperty("readonly", readOnly);
    datePicker.setReadOnly(readOnly);
    updateChips();
  }

  // ==========================================================
  // LABEL (delegado ao DatePicker)
  // ==========================================================

  @Override
  public void setLabel(String label) {
    datePicker.setLabel(label);
  }

  @Override
  public String getLabel() {
    return datePicker.getLabel();
  }

  @Override
  public Locale getLocale() {
    return super.getLocale();
  }

  @Override
  public void remove(Component... components) {
    this.container.remove(components);
  }

  @Override
  public void add(Component... components) {
    add(this.container, components);
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
}
