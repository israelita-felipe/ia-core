package com.ia.core.llm.view.conversacao;

import com.ia.core.llm.service.model.agente.ContextoConversacao;
import com.ia.core.llm.service.model.agente.TurnoConversacao;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import lombok.extern.slf4j.Slf4j;

/**
 * View para gerenciamento de sessões de conversação com agentes guiados por ontologia.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public class SessaoConversacaoView extends VerticalLayout {

  private final Grid<TurnoConversacao> historicoGrid;
  private final TextArea mensagemField;
  private final Button enviarButton;
  private final Button encerrarButton;
  private final H3 titulo;
  private final TextField sessionIdField;
  private final TextField dominioField;
  private final TextField statusField;

  private ContextoConversacao contextoAtual;

  public SessaoConversacaoView() {
    setSizeFull();
    setPadding(true);
    setSpacing(true);

    // Header
    titulo = new H3("Sessão de Conversação");

    sessionIdField = new TextField("ID da Sessão");
    sessionIdField.setReadOnly(true);
    sessionIdField.setWidthFull();

    dominioField = new TextField("Domínio");
    dominioField.setReadOnly(true);
    dominioField.setWidthFull();

    statusField = new TextField("Status");
    statusField.setReadOnly(true);
    statusField.setWidthFull();

    HorizontalLayout infoLayout = new HorizontalLayout(sessionIdField, dominioField, statusField);
    infoLayout.setWidthFull();
    infoLayout.setFlexGrow(1, sessionIdField, dominioField, statusField);

    // Histórico da conversação
    historicoGrid = new Grid<>(TurnoConversacao.class, false);
    historicoGrid.addColumn(TurnoConversacao::getNumeroTurno).setHeader("Turno");
    historicoGrid.addColumn(TurnoConversacao::getMensagemUsuario).setHeader("Mensagem do Usuário");
    historicoGrid.addColumn(TurnoConversacao::getRespostaAgente).setHeader("Resposta do Agente");
    historicoGrid.addColumn(TurnoConversacao::getConstrutorIdentificado).setHeader("Construtor");
    historicoGrid.addColumn(TurnoConversacao::getTimestamp).setHeader("Timestamp");
    historicoGrid.setSizeFull();
    historicoGrid.setMaxHeight("400px");

    // Área de entrada de mensagem
    mensagemField = new TextArea("Mensagem");
    mensagemField.setPlaceholder("Digite sua mensagem aqui...");
    mensagemField.setWidthFull();
    mensagemField.setMinHeight("80px");

    enviarButton = new Button("Enviar", VaadinIcon.PAPERPLANE.create());
    enviarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    enviarButton.addClickListener(e -> enviarMensagem());

    encerrarButton = new Button("Encerrar Sessão", VaadinIcon.CLOSE.create());
    encerrarButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
    encerrarButton.addClickListener(e -> encerrarSessao());

    HorizontalLayout buttonLayout = new HorizontalLayout(enviarButton, encerrarButton);
    buttonLayout.setWidthFull();
    buttonLayout.setJustifyContentMode(JustifyContentMode.END);

    // Layout principal
    add(titulo, infoLayout, historicoGrid, mensagemField, buttonLayout);
    setFlexGrow(1, historicoGrid);
  }

  /**
   * Define o contexto da conversação atual.
   */
  public void setContexto(ContextoConversacao contexto) {
    this.contextoAtual = contexto;

    sessionIdField.setValue(contexto.getSessionId());
    dominioField.setValue(contexto.getDominio());
    statusField.setValue(contexto.isOntologiaConsistente() ? "Consistente" : "Inconsistente");

    atualizarHistorico();
  }

  /**
   * Atualiza o grid de histórico.
   */
  private void atualizarHistorico() {
    if (contextoAtual != null) {
      historicoGrid.setItems(contextoAtual.getHistorico());
    }
  }

  /**
   * Envia uma mensagem para o agente.
   */
  private void enviarMensagem() {
    String mensagem = mensagemField.getValue();
    if (mensagem == null || mensagem.trim().isEmpty()) {
      return;
    }

    log.info("Enviando mensagem: {}", mensagem);

    // Na implementação completa, chamaria o serviço REST
    // Por enquanto, apenas limpa o campo
    mensagemField.clear();

    // Simula atualização do histórico
    // Na implementação completa, atualizaria com a resposta do agente
  }

  /**
   * Encerra a sessão atual.
   */
  private void encerrarSessao() {
    log.info("Encerrando sessão: {}", contextoAtual != null ? contextoAtual.getSessionId() : "null");

    // Na implementação completa, chamaria o serviço REST para encerrar
    // Por enquanto, apenas limpa a view
    contextoAtual = null;
    sessionIdField.clear();
    dominioField.clear();
    statusField.clear();
    historicoGrid.setItems();
  }

  /**
   * Habilita ou desabilita os controles.
   */
  public void setEnabled(boolean enabled) {
    mensagemField.setEnabled(enabled);
    enviarButton.setEnabled(enabled);
    encerrarButton.setEnabled(enabled);
  }
}
