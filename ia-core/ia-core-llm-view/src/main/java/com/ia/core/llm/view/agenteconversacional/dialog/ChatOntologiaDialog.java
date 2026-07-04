package com.ia.core.llm.view.agenteconversacional.dialog;

import com.ia.core.llm.service.model.agente.ContextConversacaoDTO;
import com.ia.core.llm.service.model.agente.RespostaAgente;
import com.ia.core.llm.view.agenteconversacional.AgenteConversacionalManager;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Pre;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.extern.slf4j.Slf4j;

/**
 * Dialog para chat com agente conversacional guiado por ontologia.
 * <p>
 * Permite conversação interativa com validação ontológica em tempo real.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public class ChatOntologiaDialog extends Dialog {

  private final AgenteConversacionalManager manager;
  private TextField mensagemField;
  private Pre respostaPre;
  private Pre axiomasPre;
  private ContextConversacaoDTO contextoAtual;

  public ChatOntologiaDialog(AgenteConversacionalManager manager) {
    this.manager = manager;

    H2 title = new H2("Chat com Agente Ontológico");

    mensagemField = new TextField("Mensagem");
    mensagemField.setWidthFull();

    Button enviarButton = new Button("Enviar");
    enviarButton.addClickListener(e -> enviarMensagem());

    HorizontalLayout inputLayout = new HorizontalLayout(mensagemField, enviarButton);
    inputLayout.setWidthFull();
    inputLayout.setFlexGrow(1, mensagemField);

    respostaPre = new Pre();
    respostaPre.setText("Resposta do agente aparecerá aqui");
    respostaPre.setWidthFull();

    axiomasPre = new Pre();
    axiomasPre.setText("Axiomas extraídos aparecerão aqui");
    axiomasPre.setWidthFull();

    VerticalLayout layout = new VerticalLayout(title, inputLayout, respostaPre, axiomasPre);
    layout.setSizeFull();
    layout.setAlignItems(FlexComponent.Alignment.STRETCH);

    add(layout);
    setSizeFull();
  }

  public void setContexto(ContextConversacaoDTO contexto) {
    this.contextoAtual = contexto;
  }

  private void enviarMensagem() {
    String mensagem = mensagemField.getValue();
    if (mensagem == null || mensagem.isEmpty()) {
      return;
    }

    log.info("Enviando mensagem: {}", mensagem);

    // Na implementação completa, chamaria o serviço REST
    RespostaAgente resposta = RespostaAgente.builder()
        .agentResponse("Resposta simulada")
        .build();

    respostaPre.setText(resposta.getAgentResponse());
    axiomasPre.setText(resposta.getExtractedAxioms().toString());

    mensagemField.clear();
  }
}
