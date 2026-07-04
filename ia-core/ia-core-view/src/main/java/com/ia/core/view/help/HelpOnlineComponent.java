package com.ia.core.view.help;

import com.ia.core.view.components.properties.HasHelp;
import com.ia.core.view.components.properties.HasTranslator;
import com.ia.core.view.help.dialog.HelpDialogViewFactory;
import com.ia.core.view.help.documentation.FlexmarkHelpRenderer;
import com.ia.core.view.help.documentation.HelpDocumentationGenerator;
import com.ia.core.view.help.screenshot.HelpScreenshotComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Componente para exibir ajuda online contextual.
 *
 * <p>Este componente adiciona um botão de ajuda (?) que, ao ser clicado,
 * abre um diálogo com informações detalhadas sobre o campo ou view.
 *
 * <p>O componente usa a interface {@link HasHelp} para obter o conteúdo
 * de ajuda do componente associado e gera documentação recursiva com
 * screenshots dos elementos.
 *
 * <p>Atributos de acessibilidade implementados conforme WCAG 2.2:
 * <ul>
 *   <li>aria-label: Descreve o propósito do botão</li>
 *   <li>aria-expanded: Reflete o estado aberto/fechado do diálogo</li>
 * </ul>
 *
 * @author Israel Araújo
 */
public class HelpOnlineComponent extends Button implements HasTranslator {

  private final FlexmarkHelpRenderer renderer;
  private final HasHelp hasHelp;
  private final HelpDocumentationGenerator documentationGenerator;

  /**
   * Cria um componente de ajuda online para o componente especificado.
   *
   * @param hasHelp componente que possui ajuda
   */
  public HelpOnlineComponent(HasHelp hasHelp) {
    this(hasHelp, null);
  }

  /**
   * Cria um componente de ajuda online para o componente especificado.
   *
   * @param hasHelp componente que possui ajuda
   * @param customHelpText texto de ajuda customizado (sobrescreve o do componente)
   */
  public HelpOnlineComponent(HasHelp hasHelp, String customHelpText) {
    super(new Icon(VaadinIcon.QUESTION_CIRCLE));
    this.hasHelp = hasHelp;
    this.renderer = new FlexmarkHelpRenderer();
    this.documentationGenerator = new HelpDocumentationGenerator();
    addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
    getElement().setAttribute("aria-label", "Ajuda");
    // WCAG 2.2: aria-expanded reflete o estado do diálogo
    getElement().setAttribute("aria-expanded", "false");
    setClassName("help-button");

    addClickListener(event -> showHelpDialog(hasHelp, customHelpText));

    getElement().addEventListener("mouseover", event -> showHelpComponents());
    getElement().addEventListener("mouseout", event -> hideHelpComponents());
    setVisible(false);
  }

  /**
   * Exibe o diálogo de ajuda com conteúdo Markdown renderizado de forma segura.
   *
   * <p>Fluxo:
   * 1. Coleta todos os componentes HasHelp recursivamente
   * 2. Captura screenshots de cada componente
   * 3. Gera Markdown com conteúdo e screenshots
   * 4. Renderiza para HTML seguro
   * 5. Abre diálogo
   *
   * @param hasHelp componente raiz que possui ajuda
   * @param customHelpText texto de ajuda customizado (opcional)
   */
  private void showHelpDialog(HasHelp hasHelp, String customHelpText) {
    // 1. Coleta todos os HasHelp recursivamente
    List<HasHelp> allHasHelps = new ArrayList<>();
    collectHasHelpsRecursive(hasHelp, allHasHelps);

    // 2. Captura screenshots de todos os componentes
    Map<HasHelp, String> screenshots = new HashMap<>();
    AtomicInteger pendingCaptures = new AtomicInteger(allHasHelps.size());

    if (allHasHelps.isEmpty()) {
      // Sem componentes, mostra diálogo vazio
      String title = hasHelp.getHelpTitle() != null ? hasHelp.getHelpTitle() : "Ajuda";
      String markdown = customHelpText != null ? customHelpText : documentationGenerator.generate(hasHelp);
      String safeHtml = renderer.render(markdown);
      HelpDialogViewFactory.show(title, safeHtml);
      return;
    }

    for (HasHelp help : allHasHelps) {
      // Usa o próprio componente HasHelp como target para screenshot
      Component target = (Component) help;
      HelpScreenshotComponent.capture(target, bytes -> {
        if (bytes != null) {
          screenshots.put(help, bytes);
        }
        if (pendingCaptures.decrementAndGet() == 0) {
          // 3. Gera Markdown com conteúdo e screenshots
          String markdown = buildRecursiveMarkdown(hasHelp, customHelpText, screenshots);

          // 4. Renderiza para HTML seguro
          String safeHtml = renderer.render(markdown);

          // 5. Abre diálogo
          String title = hasHelp.getHelpTitle() != null ? hasHelp.getHelpTitle() : "Ajuda";
          UI.getCurrent().access(() -> HelpDialogViewFactory.show(title, safeHtml));
        }
      });
    }
  }


  /**
   * Coleta todos os componentes HasHelp recursivamente.
   *
   * @param hasHelp componente raiz
   * @param result lista acumuladora de HasHelp
   */
  private void collectHasHelpsRecursive(HasHelp hasHelp, List<HasHelp> result) {
    result.add(hasHelp);

    // Campos com ajuda
    Map<Component, Component> helpFields = hasHelp.getHelpFields();
    for (Component helpComponent : helpFields.values()) {
      if (helpComponent instanceof HasHelp) {
        collectHasHelpsRecursive((HasHelp) helpComponent, result);
      }
    }
  }


  /**
   * Gera Markdown recursivo com conteúdo e screenshots.
   *
   * @param hasHelp componente raiz
   * @param customHelpText texto customizado (opcional)
   * @param screenshots mapa de HasHelp -> imagem
   * @return Markdown completo
   */
  private String buildRecursiveMarkdown(HasHelp hasHelp, String customHelpText, Map<HasHelp, String> screenshots) {
    StringBuilder md = new StringBuilder();

    if (customHelpText != null) {
      md.append(customHelpText).append("\n\n");
    } else {
      buildHasHelpMarkdown(md, hasHelp, screenshots);
    }

    return md.toString();
  }

  /**
   * Gera Markdown para um HasHelp e seus filhos recursivamente.
   *
   * @param md StringBuilder acumulador
   * @param hasHelp componente atual
   * @param screenshots mapa de screenshots
   */
  private void buildHasHelpMarkdown(StringBuilder md, HasHelp hasHelp, Map<HasHelp, String> screenshots) {
    String screenshot = screenshots.get(hasHelp);
    if (screenshot != null) {
      md.append("![")
          .append(hasHelp.getHelpTitle())
          .append("](data:image/png;base64,")
          .append(screenshot)
          .append(")\n\n");
    }
    md.append(documentationGenerator.generate(hasHelp)).append("\n\n");
  }

  /**
   * Exibe todos os componentes de ajuda associados.
   */
  private void showHelpComponents() {
    java.util.Map<Component, Component> helpFields = hasHelp.getHelpFields();
    for (Component helpComponent : helpFields.values()) {
      helpComponent.setVisible(true);
    }
  }

  /**
   * Oculta todos os componentes de ajuda associados.
   */
  private void hideHelpComponents() {
    java.util.Map<Component, Component> helpFields = hasHelp.getHelpFields();
    for (Component helpComponent : helpFields.values()) {
      helpComponent.setVisible(false);
    }
  }

  /**
   * Gera o texto de ajuda como Markdown sem abrir o diálogo.
   * Método útil para geração de documentação ou exportação.
   *
   * @return Markdown com conteúdo de ajuda
   */
  public String generateHelpText() {
    return generateHelpText(null);
  }

  /**
   * Gera o texto de ajuda como Markdown sem abrir o diálogo.
   * Método útil para geração de documentação ou exportação.
   *
   * @param customHelpText texto de ajuda customizado (opcional)
   * @return Markdown com conteúdo de ajuda
   */
  public String generateHelpText(String customHelpText) {
    // Coleta todos os HasHelp recursivamente
    List<HasHelp> allHasHelps = new ArrayList<>();
    collectHasHelpsRecursive(hasHelp, allHasHelps);

    if (allHasHelps.isEmpty()) {
      // Sem componentes, retorna markdown simples
      if (customHelpText != null) {
        return customHelpText;
      }
      return documentationGenerator.generate(hasHelp);
    }

    // Gera Markdown sem screenshots (apenas texto)
    return buildRecursiveMarkdown(hasHelp, customHelpText, new HashMap<>());
  }

  /**
   * Gera o texto de ajuda como Markdown com screenshots sem abrir o diálogo.
   * Método útil para geração de documentação com imagens.
   *
   * @param consumer callback que recebe o markdown quando pronto
   */
  public void generateHelpTextWithScreenshots(java.util.function.Consumer<String> consumer) {
    generateHelpTextWithScreenshots(null, consumer);
  }

  /**
   * Gera o texto de ajuda como Markdown com screenshots sem abrir o diálogo.
   * Método útil para geração de documentação com imagens.
   *
   * @param customHelpText texto de ajuda customizado (opcional)
   * @param consumer callback que recebe o markdown quando pronto
   */
  public void generateHelpTextWithScreenshots(String customHelpText, java.util.function.Consumer<String> consumer) {
    // Coleta todos os HasHelp recursivamente
    List<HasHelp> allHasHelps = new ArrayList<>();
    collectHasHelpsRecursive(hasHelp, allHasHelps);

    if (allHasHelps.isEmpty()) {
      // Sem componentes, retorna markdown simples
      String markdown = customHelpText != null ? customHelpText : documentationGenerator.generate(hasHelp);
      consumer.accept(markdown);
      return;
    }

    // Captura screenshots de todos os componentes
    Map<HasHelp, String> screenshots = new HashMap<>();
    AtomicInteger pendingCaptures = new AtomicInteger(allHasHelps.size());

    for (HasHelp help : allHasHelps) {
      Component target = (Component) help;
      HelpScreenshotComponent.capture(target, bytes -> {
        if (bytes != null) {
          screenshots.put(help, bytes);
        }
        if (pendingCaptures.decrementAndGet() == 0) {
          // Gera Markdown com conteúdo e screenshots
          String markdown = buildRecursiveMarkdown(hasHelp, customHelpText, screenshots);
          consumer.accept(markdown);
        }
      });
    }
  }

    @Override
    public Locale getLocale() {
        return HasTranslator.super.getLocale();
    }
}
