package com.ia.core.view.help.documentation;

import com.vaadin.flow.component.Component;

/**
 * DTO contendo metadados de um campo de formulário.
 *
 * <p>Esta classe armazena informações sobre um campo individual,
 * incluindo label, tipo, texto de ajuda e outros metadados.
 *
 * @author Israel Araújo
 */
public class FieldMetadata {

    private String fieldName;
    private String fieldType;
    private String helpText;
    private String helperText;
    private boolean required;
    private boolean readOnly;
    private String placeholder;
    private String tooltip;

    /**
     * @return nome/label do campo
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param fieldName nome/label do campo
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return tipo do componente Vaadin
     */
    public String getFieldType() {
        return fieldType;
    }

    /**
     * @param fieldType tipo do componente Vaadin
     */
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * @return texto de ajuda (help online)
     */
    public String getHelpText() {
        return helpText;
    }

    /**
     * @param helpText texto de ajuda
     */
    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    /**
     * @return texto de ajuda inline (helper text)
     */
    public String getHelperText() {
        return helperText;
    }

    /**
     * @param helperText texto de ajuda inline
     */
    public void setHelperText(String helperText) {
        this.helperText = helperText;
    }

    /**
     * @return se o campo é obrigatório
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * @param required se o campo é obrigatório
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * @return se o campo é somente leitura
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * @param readOnly se o campo é somente leitura
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    /**
     * @return placeholder do campo
     */
    public String getPlaceholder() {
        return placeholder;
    }

    /**
     * @param placeholder placeholder do campo
     */
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    /**
     * @return tooltip do campo
     */
    public String getTooltip() {
        return tooltip;
    }

    /**
     * @param tooltip tooltip do campo
     */
    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * Cria FieldMetadata a partir de um componente.
     *
     * @param component componente Vaadin
     * @return FieldMetadata preenchido
     */
    public static FieldMetadata fromComponent(Component component) {
        FieldMetadata metadata = new FieldMetadata();
        metadata.setFieldType(component.getClass().getSimpleName());
        return metadata;
    }
}
