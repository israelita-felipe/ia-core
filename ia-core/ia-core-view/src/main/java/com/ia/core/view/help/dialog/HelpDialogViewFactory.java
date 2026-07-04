package com.ia.core.view.help.dialog;

import com.ia.core.view.components.dialog.DialogHeaderBar;
import com.ia.core.view.components.properties.HasTranslator;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.UI;

/**
 * Fábrica para diálogos de ajuda online.
 *
 * <p>Este factory cria diálogos com tamanho padrão 980px x 75vh e header bar
 * contendo botão de maximizar/restore, exibindo conteúdo HTML.
 *
 * <p>Atributos de acessibilidade implementados conforme WCAG 2.2:
 * <ul>
 *   <li>aria-live="polite": Anuncia mudanças de estado para screen readers</li>
 *   <li>prefers-reduced-motion: Suporte a redução de movimento</li>
 *   <li>prefers-contrast: high: Suporte a modo de alto contraste</li>
 * </ul>
 *
 * @author Israel Araújo
 */
public class HelpDialogViewFactory implements HasTranslator {

  /** Instância singleton */
  private static HelpDialogViewFactory instance = null;

  /**
   * Captura o singleton desta classe
   *
   * @return {@link #instance}
   */
  private static HelpDialogViewFactory get() {
    if (instance == null) {
      instance = new HelpDialogViewFactory();
    }
    return instance;
  }

  /**
   * Exibe o diálogo de ajuda com conteúdo HTML.
   *
   * @param title título do diálogo
   * @param htmlContent conteúdo HTML
   */
  public static void show(String title, String htmlContent) {
    get().showDialog(title, htmlContent);
  }

  /**
   * Construtor privado
   */
  private HelpDialogViewFactory() {
  }

  /**
   * Cria e exibe o diálogo de ajuda.
   *
   * <p>Implementa acessibilidade WCAG 2.2:
   * <ul>
   *   <li>aria-live="polite" no conteúdo para screen readers</li>
   *   <li>aria-modal="true" para indicar diálogo modal</li>
   *   <li>role="dialog" para semântica apropriada</li>
   * </ul>
   *
   * <p>Configura tamanho padrão 980px x 75vh e habilita maximização via
   * {@link DialogHeaderBar}.
   *
   * @param title título do diálogo
   * @param htmlContent conteúdo HTML
   */
  private void showDialog(String title, String htmlContent) {

    Dialog dialog = new Dialog();
    dialog.setHeaderTitle(title);

    // Configura tamanho padrão: 980px x 75vh
    dialog.setWidth("980px");
    dialog.setHeight("75vh");

    // WCAG 2.2: Configura atributos de acessibilidade do diálogo
    dialog.getElement().setAttribute("aria-modal", "true");
    dialog.getElement().setAttribute("role", "dialog");

    // WCAG 2.2: Live region para screen readers anunciar mudanças
    Html content = new Html(String.format("<div aria-live=\"polite\" aria-atomic=\"true\">%s</div>", htmlContent));
    dialog.add(content);

    // Adiciona header bar com botão de maximizar
    DialogHeaderBar headerBar = DialogHeaderBar.addTo(dialog);
    headerBar.setCaption(title);

    // WCAG 2.2: Atualiza aria-expanded do botão de ajuda quando diálogo abre/fecha
    dialog.addOpenedChangeListener(event -> {
      if (event.isOpened()) {
        // Notifica o HelpOnlineComponent que o diálogo foi aberto
        UI.getCurrent().getPage().executeJs(
            "document.querySelector('.help-button')?.setAttribute('aria-expanded', 'true')");
      } else {
        // Notifica o HelpOnlineComponent que o diálogo foi fechado
        UI.getCurrent().getPage().executeJs(
            "document.querySelector('.help-button')?.setAttribute('aria-expanded', 'false')");
      }
    });

    dialog.open();
  }
}
