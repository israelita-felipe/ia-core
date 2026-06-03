package com.ia.core.llm.view.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import lombok.extern.slf4j.Slf4j;

/**
 * Dialog de chat para interação com agentes guiados por ontologia.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public class ChatDialog extends Dialog {

  private final TextArea chatArea;
  private final TextArea mensagemField;
  private final Button enviarButton;
  private final Button fecharButton;
  private final H3 titulo;

  private StringBuilder historicoChat = new StringBuilder();

  public ChatDialog() {
    setSizeFull();
    setWidth("800px");
    setHeight("600px");

    // Header
    titulo = new H3("Chat com Agente");

    // Área de chat (somente leitura)
    chatArea = new TextArea("Conversação");
    chatArea.setReadOnly(true);
    chatArea.setWidthFull();
    chatArea.setHeight("400px");
    chatArea.setValue("Bem-vindo ao chat com o agente guiado por ontologia.\n" +
                     "Digite sua mensagem abaixo para começar.\n\n");

    // Campo de entrada de mensagem
    mensagemField = new TextArea("Mensagem");
    mensagemField.setPlaceholder("Digite sua mensagem aqui...");
    mensagemField.setWidthFull();
    mensagemField.setHeight("80px");

    // Botões
    enviarButton = new Button("Enviar", VaadinIcon.PAPERPLANE.create());
    enviarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    enviarButton.addClickListener(e -> enviarMensagem());

    fecharButton = new Button("Fechar", VaadinIcon.CLOSE.create());
    fecharButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    fecharButton.addClickListener(e -> close());

    HorizontalLayout buttonLayout = new HorizontalLayout(enviarButton, fecharButton);
    buttonLayout.setWidthFull();
    buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

    // Layout principal
    VerticalLayout layout = new VerticalLayout(titulo, chatArea, mensagemField, buttonLayout);
    layout.setSizeFull();
    layout.setPadding(true);
    layout.setSpacing(true);
    layout.setFlexGrow(1, chatArea);

    add(layout);
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

    // Adiciona mensagem do usuário ao histórico
    adicionarMensagemUsuario(mensagem);

    // Limpa o campo
    mensagemField.clear();

    // Na implementação completa, chamaria o serviço REST
    // Por enquanto, simula uma resposta
    simularRespostaAgente(mensagem);
  }

  /**
   * Adiciona mensagem do usuário ao chat.
   */
  private void adicionarMensagemUsuario(String mensagem) {
    historicoChat.append("Você: ").append(mensagem).append("\n");
    chatArea.setValue(historicoChat.toString());
  }

  /**
   * Adiciona resposta do agente ao chat.
   */
  private void adicionarRespostaAgente(String resposta) {
    historicoChat.append("Agente: ").append(resposta).append("\n\n");
    chatArea.setValue(historicoChat.toString());
  }

  /**
   * Simula resposta do agente (placeholder).
   */
  private void simularRespostaAgente(String mensagem) {
    // Na implementação completa, chamaria o serviço REST
    String resposta = "Entendi sua mensagem. Processando axiomas OWL...\n" +
                     "Axioma(s) adicionado(s) à ontologia com sucesso.";

    adicionarRespostaAgente(resposta);
  }

  /**
   * Define o título do dialog.
   */
  public void setTitulo(String titulo) {
    this.titulo.setText(titulo);
  }

  /**
   * Limpa o histórico do chat.
   */
  public void limparChat() {
    historicoChat = new StringBuilder();
    historicoChat.append("Chat limpo. Digite sua mensagem abaixo.\n\n");
    chatArea.setValue(historicoChat.toString());
  }

  /**
   * Adiciona uma mensagem programática ao chat.
   */
  public void adicionarMensagem(String mensagem, boolean isUsuario) {
    if (isUsuario) {
      adicionarMensagemUsuario(mensagem);
    } else {
      adicionarRespostaAgente(mensagem);
    }
  }
}
