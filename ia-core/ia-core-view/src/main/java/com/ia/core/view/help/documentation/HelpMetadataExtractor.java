package com.ia.core.view.help.documentation;

import com.ia.core.view.components.properties.HasHelp;
import com.vaadin.flow.component.*;
import com.vaadin.flow.router.Route;

import java.util.Map;

/**
 * Extrai metadados de help de um componente HasHelp.
 *
 * <p>Este extrator percorre recursivamente a árvore de componentes
 * e coleta informações de ajuda, labels, tipos e outros metadados.
 *
 * @author Israel Araújo
 */
public class HelpMetadataExtractor {

    /**
     * Extrai metadados completos de um componente HasHelp.
     *
     * @param hasHelp componente para extrair metadados
     * @return metadados de help
     */
    public HelpMetadata extract(HasHelp hasHelp) {
        HelpMetadata metadata = new HelpMetadata();

        // Informações básicas do componente
        metadata.setViewName(hasHelp.getClass().getSimpleName());
        metadata.setTitle(hasHelp.getHelpTitle());
        metadata.setDescription(hasHelp.getHelpDescription());

        // Extrai rota se houver
        Route route = hasHelp.getClass().getAnnotation(Route.class);
        if (route != null) {
            metadata.setRoute(route.value());
        }

        // Extrai campos com ajuda
        Map<Component, Component> helpFields = hasHelp.getHelpFields();
        helpFields.forEach((field, helpComponent) -> {
            FieldMetadata fieldMeta = extractFieldMetadata(field, helpComponent);
            metadata.addField(fieldMeta);
        });

        return metadata;
    }

    /**
     * Extrai metadados de um campo específico.
     *
     * @param field componente do campo
     * @param helpComponent componente de ajuda
     * @return metadados do campo
     */
    private FieldMetadata extractFieldMetadata(Component field, Component helpComponent) {
        FieldMetadata fieldMeta = new FieldMetadata();

        // Nome/label do campo
        fieldMeta.setFieldName(extractLabel(field));

        // Tipo do componente
        fieldMeta.setFieldType(field.getClass().getSimpleName());

        // Texto de ajuda (do slot helper)
        fieldMeta.setHelpText(extractHelpText(helpComponent));

        // Helper text inline (se disponível)
        if (field instanceof HasHelper) {
            String helper = ((HasHelper) field).getHelperText();
            fieldMeta.setHelperText(helper);
        }

        // Placeholder (se disponível)
        if (field instanceof HasPlaceholder) {
            String placeholder = ((HasPlaceholder) field).getPlaceholder();
            fieldMeta.setPlaceholder(placeholder);
        }

        // Estado do campo (se disponível)
        if (field instanceof Focusable) {
            // Focusable tem isReadOnly() mas é protected, usamos getReadOnly() se existir
            // ou verificamos via elemento
            fieldMeta.setReadOnly(field.getElement().hasAttribute("readonly"));
        }

        return fieldMeta;
    }

    /**
     * Extrai o label de um componente.
     *
     * @param component componente para extrair o rótulo
     * @return o rótulo do componente ou "Campo" se não encontrado
     */
    private String extractLabel(Component component) {
        if (component == null) {
            return "Campo";
        }

        // Tenta obter o label de componentes comuns do Vaadin
        if (component instanceof HasLabel) {
            String label = ((HasLabel) component).getLabel();
            if (label != null && !label.isEmpty()) {
                return label;
            }
        }

        // Tenta obter o texto do elemento
        String text = component.getElement().getText();
        return text != null && !text.isEmpty() ? text : "Campo";
    }

    /**
     * Extrai o texto de ajuda de um componente.
     *
     * @param component componente de ajuda
     * @return o texto de ajuda ou string vazia se não encontrado
     */
    private String extractHelpText(Component component) {
        if (component == null) {
            return "";
        }

        // Tenta obter o texto do elemento
        String text = component.getElement().getText();
        return text != null ? text : "";
    }
}
