package com.ia.core.view.help.documentation;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO contendo metadados de help de uma view.
 *
 * <p>Esta classe é usada para transportar informações de ajuda
 * entre o extrator e o gerador de Markdown.
 *
 * @author Israel Araújo
 */
public class HelpMetadata {

    private String viewName;
    private String title;
    private String description;
    private String route;
    private List<FieldMetadata> fields = new ArrayList<>();

    /**
     * @return nome da classe da view
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * @param viewName nome da classe da view
     */
    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    /**
     * @return título da ajuda
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title título da ajuda
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return descrição detalhada da view
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description descrição detalhada da view
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return rota da view (se houver)
     */
    public String getRoute() {
        return route;
    }

    /**
     * @param route rota da view
     */
    public void setRoute(String route) {
        this.route = route;
    }

    /**
     * @return lista de metadados de campos
     */
    public List<FieldMetadata> getFields() {
        return fields;
    }

    /**
     * @param fields lista de metadados de campos
     */
    public void setFields(List<FieldMetadata> fields) {
        this.fields = fields;
    }

    /**
     * Adiciona um campo à lista.
     *
     * @param field metadados do campo
     */
    public void addField(FieldMetadata field) {
        this.fields.add(field);
    }
}
