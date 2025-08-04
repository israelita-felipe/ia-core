package com.ia.core.view.components.textArea;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import com.wontlost.ckeditor.Config;
import com.wontlost.ckeditor.Constants.EditorType;
import com.wontlost.ckeditor.Constants.Language;
import com.wontlost.ckeditor.Constants.Toolbar;
import com.wontlost.ckeditor.VaadinCKEditor;
import com.wontlost.ckeditor.VaadinCKEditorBuilder;

/**
 * Implementação padrão de um editor de texto "rico".
 */
@SuppressWarnings("javadoc")
@Tag("rich-text-area")
public class RichTextArea
  extends VerticalLayout
  implements
  HasValue<ComponentValueChangeEvent<CustomField<String>, String>, String> {
  /** Serial UID */
  private static final long serialVersionUID = 7496198572171688592L;
  /** Delegate do editor */
  private final VaadinCKEditor delegate;

  /**
   * @param label Título do campo
   */
  public RichTextArea(String label) {
    super();
    this.delegate = createDelegate(label);
    setSizeFull();
    setMinHeight(getDefaultHeight());
    setHeight(100, Unit.PERCENTAGE);
  }

  /**
   * @return
   */
  protected String getDefaultHeight() {
    return "200px";
  }

  /**
   * @param label Label do editor
   * @return {@link VaadinCKEditor}
   */
  protected VaadinCKEditor createDelegate(String label) {
    var delegate = new VaadinCKEditorBuilder().with(builder -> {
      builder.editorType = getEditorType();
      builder.config = getConfiguration();
      builder.height = getDefaultHeight();
    }).createVaadinCKEditor();
    delegate.getStyle().setMargin("0");
    delegate.setLabel(label);
    delegate.setMinHeight(getDefaultHeight());
    delegate.setHeight(100, Unit.PERCENTAGE);
    add(delegate);
    return delegate;
  }

  /**
   * @param width
   * @param unit
   * @see com.vaadin.flow.component.HasSize#setWidth(float,
   *      com.vaadin.flow.component.Unit)
   */
  @Override
  public void setWidth(float width, Unit unit) {
    super.setWidth(width, unit);
    if (delegate != null) {
      delegate.setWidth(width, unit);
    }
  }

  /**
   * @param height
   * @param unit
   * @see com.vaadin.flow.component.HasSize#setHeight(float,
   *      com.vaadin.flow.component.Unit)
   */
  @Override
  public void setHeight(float height, Unit unit) {
    super.setHeight(height, unit);
    if (delegate != null) {
      delegate.setHeight(height, unit);
    }
  }

  /**
   * @param editorWidth
   * @see com.wontlost.ckeditor.VaadinCKEditor#setWidth(java.lang.String)
   */
  @Override
  public void setWidth(String editorWidth) {
    super.setWidth(editorWidth);
    if (delegate != null) {
      delegate.setWidth(editorWidth);
    }
  }

  /**
   * @param editorHeight
   * @see com.wontlost.ckeditor.VaadinCKEditor#setHeight(java.lang.String)
   */
  @Override
  public void setHeight(String editorHeight) {
    super.setHeight(editorHeight);
    if (delegate != null) {
      delegate.setHeight(editorHeight);
    }
  }

  /**
   * @return {@link EditorType}
   */
  protected EditorType getEditorType() {
    return EditorType.CLASSIC;
  }

  /**
   * @return {@link Config}
   */
  protected Config getConfiguration() {
    Config config = new Config();
    config.setUILanguage(getLanguage());
    config.setEditorToolBarObject(getToolbars(), true);
    return config;
  }

  /**
   * @return {@link Language}
   */
  protected Language getLanguage() {
    return Language.pt_br;
  }

  /**
   * @return Array de {@link Toolbar} para ser configurado no editor
   */
  protected Toolbar[] getToolbars() {
    return Toolbar.values();
  }

  @Override
  public void setReadOnly(boolean readOnly) {
    this.delegate.setReadOnly(readOnly);
    setHideToolbar(readOnly);
  }

  /**
   * @return
   * @see com.wontlost.ckeditor.HasConfig#getConfig()
   */
  public Config getConfig() {
    return delegate.getConfig();
  }

  /**
   * @return
   * @see com.wontlost.ckeditor.VaadinCKEditor#getValue()
   */
  @Override
  public String getValue() {
    return delegate.getValue();
  }

  /**
   * @param value
   * @see com.wontlost.ckeditor.VaadinCKEditor#setValue(java.lang.String)
   */
  @Override
  public void setValue(String value) {
    delegate.setValue(value);
  }

  /**
   * @param hideToolbar
   * @see com.wontlost.ckeditor.VaadinCKEditor#setHideToolbar(boolean)
   */
  public void setHideToolbar(boolean hideToolbar) {
    delegate.setHideToolbar(hideToolbar);
  }

  /**
   * @see com.wontlost.ckeditor.VaadinCKEditor#clear()
   */
  @Override
  public void clear() {
    delegate.clear();
  }

  /**
   * @param config
   * @see com.wontlost.ckeditor.VaadinCKEditor#setConfig(com.wontlost.ckeditor.Config)
   */
  public void setConfig(Config config) {
    delegate.setConfig(config);
  }

  /**
   * @return
   * @see com.vaadin.flow.component.AbstractField#isEmpty()
   */
  @Override
  public boolean isEmpty() {
    return delegate.isEmpty();
  }

  /**
   * @return
   * @see com.vaadin.flow.component.AbstractField#getEmptyValue()
   */
  @Override
  public String getEmptyValue() {
    return delegate.getEmptyValue();
  }

  @Override
  public Registration addValueChangeListener(ValueChangeListener<? super ComponentValueChangeEvent<CustomField<String>, String>> listener) {
    return delegate.addValueChangeListener(listener);
  }

  @Override
  public boolean isReadOnly() {
    return delegate.isReadOnly();
  }

  @Override
  public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
    delegate.setRequiredIndicatorVisible(requiredIndicatorVisible);
  }

  @Override
  public boolean isRequiredIndicatorVisible() {
    return delegate.isRequiredIndicatorVisible();
  }

}
