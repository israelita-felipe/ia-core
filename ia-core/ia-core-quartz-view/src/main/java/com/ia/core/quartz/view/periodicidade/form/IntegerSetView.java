package com.ia.core.quartz.view.periodicidade.form;

import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

/**
 * Campo customizado para seleção de múltiplos inteiros. Compatível com Binder.
 * Utiliza um TextField para entrada de números inteiros.
 *
 * @author Israel Araújo
 */
@Tag("integer-set-field")
public class IntegerSetView
  extends CustomField<Set<Integer>> {

  private static final long serialVersionUID = 1L;

  private final FlexLayout container = new FlexLayout();
  private final TextField numberField = new TextField();

  private Integer minValue;
  private Integer maxValue;

  private Set<Integer> numbers;

  public IntegerSetView() {
    configureLayout();
    configureNumberField();
    super.add(container);
  }

  /**
   * Construtor com conjunto inicial de inteiros.
   *
   * @param initialValues Conjunto inicial de inteiros
   */
  public IntegerSetView(Set<Integer> initialValues) {
    this();
    if (initialValues != null) {
      this.numbers = new TreeSet<>(initialValues);
      setValue(this.numbers);
    }
  }

  private void configureLayout() {
    // container.setSpacing(true);
    // container.setPadding(false);
    container.setFlexWrap(FlexWrap.WRAP);
    container.setWidthFull();
  }

  private void configureNumberField() {
    numberField.setWidthFull();
    numberField.setPlaceholder("Digite um número e pressione Enter");
    numberField.setPattern("\\d*");
    numberField.setValueChangeMode(ValueChangeMode.LAZY);
    numberField.setValueChangeTimeout(300);

    numberField.addKeyDownListener(e -> {
      if (e.getKey().getKeys().contains("Enter") && !isReadOnly()) {
        String textValue = numberField.getValue();

        if (textValue != null && !textValue.trim().isEmpty()) {
          try {
            int number = Integer.parseInt(textValue.trim());

            // Valida mínimo
            if (minValue != null && number < minValue) {
              numberField.setErrorMessage("Valor mínimo: " + minValue);
              numberField.setInvalid(true);
              return;
            }

            // Valida máximo
            if (maxValue != null && number > maxValue) {
              numberField.setErrorMessage("Valor máximo: " + maxValue);
              numberField.setInvalid(true);
              return;
            }

            numberField.setInvalid(false);

            if (numbers == null) {
              numbers = new TreeSet<>();
            }

            if (!numbers.contains(number)) {
              numbers.add(number);
              updateChips();
              // Notifica o CustomField que o valor mudou
              updateValue();
            }

            numberField.clear();
          } catch (NumberFormatException ex) {
            // Ignora valores inválidos
            numberField.clear();
          }
        }
      }
    });

    super.add(numberField);
  }

  // ==========================================================
  // MÉTODOS OBRIGATÓRIOS DO CustomField
  // ==========================================================

  @Override
  protected Set<Integer> generateModelValue() {
    return numbers;
  }

  @Override
  protected void setPresentationValue(Set<Integer> newPresentationValue) {
    numbers = newPresentationValue != null ? new TreeSet<>(newPresentationValue)
                                           : null;
    updateChips();
  }

  // ==========================================================
  // UI
  // ==========================================================

  private void updateChips() {

    container.removeAll();

    if (numbers != null) {
      for (Integer number : numbers.stream().sorted()
          .collect(Collectors.toList())) {
        add((Component) createChip(number));
      }
    }
  }

  private Span createChip(Integer number) {

    Span chip = new Span();
    chip.getElement()
        .setAttribute("style",
                      "display: inline-flex; align-items: center; background: #e0e0e0;"
                          + "border-radius: 4px; padding: 4px 8px; margin: 2px;");

    Span numberLabel = new Span(String.valueOf(number));
    chip.add(numberLabel);

    if (!isReadOnly()) {
      Button removeButton = new Button(VaadinIcon.CLOSE.create());
      removeButton.getElement()
          .setAttribute("style", "margin-left: 4px; padding: 0 4px;");
      removeButton.addThemeVariants(ButtonVariant.LUMO_SMALL);

      removeButton.addClickListener(e -> {
        if (numbers != null) {
          numbers.remove(number);
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
    numberField.setReadOnly(readOnly);
    updateChips();
  }

  // ==========================================================
  // LABEL (delegado ao TextField)
  // ==========================================================

  @Override
  public void setLabel(String label) {
    numberField.setLabel(label);
  }

  @Override
  public String getLabel() {
    return numberField.getLabel();
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

  // ==========================================================
  // MIN/MAX
  // ==========================================================

  /**
   * Define o valor mínimo permitido.
   *
   * @param minValue Valor mínimo
   */
  public void setMinValue(Integer minValue) {
    this.minValue = minValue;
  }

  /**
   * Define o valor máximo permitido.
   *
   * @param maxValue Valor máximo
   */
  public void setMaxValue(Integer maxValue) {
    this.maxValue = maxValue;
  }

  /**
   * Define os limites mínimo e máximo.
   *
   * @param minValue Valor mínimo
   * @param maxValue Valor máximo
   */
  public void setRange(Integer minValue, Integer maxValue) {
    this.minValue = minValue;
    this.maxValue = maxValue;
  }

  /**
   * Retorna o valor mínimo permitido.
   *
   * @return Valor mínimo
   */
  public Integer getMinValue() {
    return minValue;
  }

  /**
   * Retorna o valor máximo permitido.
   *
   * @return Valor máximo
   */
  public Integer getMaxValue() {
    return maxValue;
  }
}
