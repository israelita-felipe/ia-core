package com.ia.core.view.components.form;

import java.io.Serializable;

import com.ia.core.view.components.IView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.properties.HasBinder;
import com.ia.core.view.components.properties.HasCheckBoxCreator;
import com.ia.core.view.components.properties.HasComboBoxCreator;
import com.ia.core.view.components.properties.HasDateTimeCreator;
import com.ia.core.view.components.properties.HasId;
import com.ia.core.view.components.properties.HasPasswordCreator;
import com.ia.core.view.components.properties.HasTextAreaCreator;
import com.ia.core.view.components.properties.HasTextFieldCreator;
import com.ia.core.view.components.properties.HasUploaderCreator;
import com.ia.core.view.properties.AutoCastable;
import com.ia.core.view.properties.HasTabSheetCreator;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;

/**
 * Contrato que define o comportamento de um formulário
 *
 * @author Israel Araújo
 * @param <T> Tipo do objeto do formulário
 */
public interface IFormView<T extends Serializable>
  extends AutoCastable, HasComponents, HasDateTimeCreator,
  HasComboBoxCreator, HasTextFieldCreator, HasTextAreaCreator,
  HasCheckBoxCreator, HasId, HasPasswordCreator, HasUploaderCreator,
  HasTabSheetCreator, HasBinder<IFormViewModel<T>>, IView<T> {

  /**
   * Enum que representa os tamanhos de tela
   */
  public static enum ScreenSize {
    /** Extra pequeno */
    XSM(1, Unit.PIXELS),
    /** Pequeno */
    SM(480, Unit.PIXELS),
    /** Médio */
    MD(640, Unit.PIXELS),
    /** Largo */
    LG(720, Unit.PIXELS),
    /** Extra largo */
    XLG(1080, Unit.PIXELS);

    /** Largura */
    private final int width;
    /** Unidade de menida */
    private final Unit unit;

    /**
     * @param width largura
     * @param unit  unidade de menida
     */
    private ScreenSize(int width, Unit unit) {
      this.width = width;
      this.unit = unit;
    }

    /**
     * @return String contendo {width}{unit}. Onde {width} é a largura, e {unit}
     *         é a unidade de medida do tamanho.
     */
    public String getWidthValue() {
      return String.format("%s%s", width, unit.getSymbol());
    }
  }

  @Override
  default IFormViewModel<T> getViewModel() {
    return getBinder().getBean();
  }

  /**
   * @return Número máximo de colunas
   */
  default int maxColumnSize() {
    return 12;
  }

  /**
   * Atualiza os campos
   */
  default void refreshFields() {
    getBinder().refreshFields();
  }

  /**
   * Atualiza o campo
   *
   * @param property propriedade a ser atualizada
   */
  default void refreshField(String property) {
    getBinder().getBinding(parseProperty(property)).ifPresent(binding -> {
      binding.read(getViewModel());
    });
  }

  /**
   * Atribui a visibilidade de forma segura
   *
   * @param component {@link Component}, pode ser <code>null</code>
   * @param visible   indicativo de visibilidade
   */
  default void setVisible(Component component, boolean visible) {
    if (component != null) {
      component.setVisible(visible);
    }
  }

  /**
   * Cria uma {@link ResponsiveStep}
   *
   * @param size {@link ScreenSize}
   * @param qtd  quantidade de colunas
   * @return {@link ResponsiveStep}
   */
  default ResponsiveStep step(ScreenSize size, int qtd) {
    return new ResponsiveStep(size.getWidthValue(), qtd);
  }

}
