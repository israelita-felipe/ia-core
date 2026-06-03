package com.ia.core.llm.view.dialog;

import com.ia.core.llm.service.model.validacao.ExplicacaoInconsistencia;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Dialog para exibição e explicação de inconsistências em ontologias.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public class InconsistenciaDialog extends Dialog {

  private final TextArea explicacaoArea;
  private final TextArea axiomasArea;
  private final TextArea sugestoesArea;
  private final Button corrigirButton;
  private final Button fecharButton;
  private final H3 titulo;
  private final Span gravidadeLabel;

  private ExplicacaoInconsistencia explicacaoAtual;

  public InconsistenciaDialog() {
    setSizeFull();
    setWidth("700px");
    setHeight("500px");

    // Header
    titulo = new H3("Inconsistência Detectada");

    gravidadeLabel = new Span();
    gravidadeLabel.getElement().getStyle().set("font-weight", "bold");

    HorizontalLayout headerLayout = new HorizontalLayout(titulo, gravidadeLabel);
    headerLayout.setWidthFull();
    headerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

    // Explicação em linguagem natural
    explicacaoArea = new TextArea("Explicação");
    explicacaoArea.setReadOnly(true);
    explicacaoArea.setWidthFull();
    explicacaoArea.setHeight("100px");

    // Axiomas causadores
    axiomasArea = new TextArea("Axiomas Causadores");
    axiomasArea.setReadOnly(true);
    axiomasArea.setWidthFull();
    axiomasArea.setHeight("120px");

    // Sugestões de correção
    sugestoesArea = new TextArea("Sugestões de Correção");
    sugestoesArea.setReadOnly(true);
    sugestoesArea.setWidthFull();
    sugestoesArea.setHeight("100px");

    // Botões
    corrigirButton = new Button("Corrigir Automaticamente", VaadinIcon.WRENCH.create());
    corrigirButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    corrigirButton.addClickListener(e -> corrigirInconsistencia());

    fecharButton = new Button("Fechar", VaadinIcon.CLOSE.create());
    fecharButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    fecharButton.addClickListener(e -> close());

    HorizontalLayout buttonLayout = new HorizontalLayout(corrigirButton, fecharButton);
    buttonLayout.setWidthFull();
    buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

    // Layout principal
    VerticalLayout layout = new VerticalLayout(headerLayout, explicacaoArea,
                                               axiomasArea, sugestoesArea, buttonLayout);
    layout.setSizeFull();
    layout.setPadding(true);
    layout.setSpacing(true);

    add(layout);
  }

  /**
   * Define a explicação da inconsistência a ser exibida.
   */
  public void setExplicacao(ExplicacaoInconsistencia explicacao) {
    this.explicacaoAtual = explicacao;

    if (explicacao != null) {
      gravidadeLabel.setText("Gravidade: " + explicacao.getGravidade());
      gravidadeLabel.getElement().getStyle().set("color", getCorGravidade(explicacao.getGravidade()));

      explicacaoArea.setValue(explicacao.getExplicacaoNatural());

      List<String> axiomas = explicacao.getAxiomasCausadores();
      axiomasArea.setValue(String.join("\n", axiomas));

      List<String> sugestoes = explicacao.getSugestoesCorrecao();
      sugestoesArea.setValue(String.join("\n", sugestoes));
    }
  }

  /**
   * Retorna a cor baseada na gravidade.
   */
  private String getCorGravidade(String gravidade) {
    if (gravidade == null) return "var(--lumo-body-text-color)";

    return switch (gravidade.toUpperCase()) {
      case "ERROR" -> "var(--lumo-error-color)";
      case "WARNING" -> "var(--lumo-warning-color)";
      case "INFO" -> "var(--lumo-primary-color)";
      default -> "var(--lumo-body-text-color)";
    };
  }

  /**
   * Tenta corrigir a inconsistência automaticamente.
   */
  private void corrigirInconsistencia() {
    if (explicacaoAtual == null) {
      return;
    }

    log.info("Iniciando correção automática de inconsistência");

    // Na implementação completa, chamaria o serviço REST para correção
    // Por enquanto, apenas exibe uma mensagem
    sugestoesArea.setValue(sugestoesArea.getValue() + "\n\n[Correção automática iniciada...]");
  }

  /**
   * Define o título do dialog.
   */
  public void setTitulo(String titulo) {
    this.titulo.setText(titulo);
  }

  /**
   * Limpa os campos.
   */
  public void limpar() {
    explicacaoAtual = null;
    gravidadeLabel.setText("");
    explicacaoArea.clear();
    axiomasArea.clear();
    sugestoesArea.clear();
  }
}
