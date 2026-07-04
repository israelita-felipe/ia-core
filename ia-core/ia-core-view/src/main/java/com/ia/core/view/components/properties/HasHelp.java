package com.ia.core.view.components.properties;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.dom.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * Interface para componentes que possuem ajuda contextual.
 *
 * <p>Esta interface fornece métodos para exibir ajuda em dois níveis:
 * <ul>
 *   <li><b>Helper text</b>: Texto curto sempre visível abaixo do campo (padrão Vaadin)</li>
 *   <li><b>Help online</b>: Conteúdo detalhado acessível via diálogo ou tooltip</li>
 * </ul>
 *
 * <p>O helper text usa o slot "helper" do Vaadin, que é o padrão recomendado
 * pela documentação para fornecer informações adicionais sobre campos.
 *
 * @author Israel Araújo
 */
public interface HasHelp
  extends HasElement {

  /**
   * Cria o conteúdo de ajuda a partir de texto simples.
   *
   * @param help Texto de ajuda
   * @return {@link Component} com o texto de ajuda
   */
  default Component createHelpComponentFromText(String help) {
    return new Span(help);
  }

  /**
   * Indica se a ajuda está visível.
   *
   * @return <code>false</code> por padrão
   */
  default boolean isHelpVisible() {
    return false;
  }

  /**
   * Atribui ajuda a um componente usando o slot "helper" do Vaadin.
   *
   * <p>Este método remove qualquer ajuda existente antes de adicionar a nova.
   *
   * @param hasHelper componente que possui ajuda
   * @param help      componente de ajuda
   */
  default void setHelp(Component hasHelper, Component help) {
    // Remove ajuda existente do DOM
    hasHelper.getElement().getChildren()
      .filter(child -> "helper".equals(child.getAttribute("slot")))
      .findAny().ifPresent(hasHelper.getElement()::removeChild);

    if (help != null) {
      help.getElement().setAttribute("slot", "helper");
      hasHelper.getElement().appendChild(help.getElement());
      help.setVisible(isHelpVisible());
    }
  }

  /**
   * Atribui ajuda a um componente usando texto simples.
   *
   * @param hasHelper component que possui ajuda
   * @param help      texto de ajuda
   */
  default void setHelp(Component hasHelper, String help) {
    setHelp(hasHelper, createHelpComponentFromText(help));
  }

  /**
   * Retorna o texto de ajuda curto para exibição inline.
   *
   * <p>Este texto é usado para helper text sempre visível abaixo do campo.
   *
   * @return texto de ajuda curto, ou null se não definido
   */
  default String getHelpText() {
    return null;
  }

  /**
   * Retorna o título da ajuda para exibição em diálogo.
   *
   * @return título da ajuda, ou null se não definido
   */
  default String getHelpTitle() {
    return null;
  }

  /**
   * Retorna a descrição detalhada da ajuda.
   *
   * <p>Este texto é usado para help online em diálogo.
   *
   * @return descrição detalhada, ou null se não definido
   */
  default String getHelpDescription() {
    return null;
  }

  /**
   * Retorna mapa de componentes com seus componentes de ajuda.
   *
   * <p>Este método percorre recursivamente todos os elementos filhos do elemento raiz
   * e recupera os componentes de ajuda mapeados através do slot "helper".
   * <p>
   * As chaves são os componentes que receberam ajuda e os valores são os
   * componentes de ajuda associados.
   *
   * @return mapa de componente -> componente de ajuda, ou coleção vazia se não definido
   */
  default Map<Component, Component> getHelpFields() {
    Map<Component, Component> helpMap = new HashMap<>();
    collectHelpFieldsRecursive(getElement(), helpMap);
    return helpMap;
  }

  /**
   * Percorre recursivamente os elementos coletando os pares componente-ajuda.
   *
   * @param element elemento atual sendo percorrido
   * @param helpMap mapa acumulador de componentes de ajuda
   */
  private void collectHelpFieldsRecursive(Element element, Map<Component, Component> helpMap) {
    element.getChildren()
      .forEach(childElement -> {
        // Primeiro, verifica se este elemento tem um filho com slot "helper"
        childElement.getChildren()
          .filter(helpElement -> "helper".equals(helpElement.getAttribute("slot")))
          .findFirst()
          .ifPresent(helpElement -> {
            // Encontrou um elemento de ajuda - adiciona ao mapa
            childElement.getComponent().ifPresent(parentComponent -> {
              helpElement.getComponent().ifPresent(helpComponent -> {
                helpMap.put(parentComponent, helpComponent);
              });
            });
          });

        // Percorre recursivamente para encontrar ajuda em elementos aninhados
        collectHelpFieldsRecursive(childElement, helpMap);
      });
  }
}
