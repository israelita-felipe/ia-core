package com.ia.core.llm.view.agenteconstrutor.page;

import com.ia.core.llm.service.model.agente.RequisicaoConstrucaoOntologiaDTO;
import com.ia.core.llm.service.model.agente.ResultadoConstrucaoOntologiaDTO;
import com.ia.core.llm.view.agenteconstrutor.AgenteConstrutorManager;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Pre;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

/**
 * View para construção autônoma de ontologias (fluxo CDU, fora do CRUD EntityPage).
 */
@Route("construtor-ontologia")
public class ConstrutorOntologiaPageView extends VerticalLayout {

  private final AgenteConstrutorManager manager;
  private TextField dominioField;
  private TextArea corpusField;
  private TextField targetIriField;
  private TextField targetNameField;
  private ProgressBar progressBar;
  private Pre resultadoPre;
  private String jobIdAtual;

  public ConstrutorOntologiaPageView(AgenteConstrutorManager manager) {
    this.manager = manager;
    setSizeFull();
    setPadding(true);
    setSpacing(true);
    buildLayout();
  }

  private void buildLayout() {
    add(new H2("Construtor de Ontologias"));
    dominioField = new TextField("Domínio");
    corpusField = new TextArea("Corpus de Texto");
    corpusField.setWidthFull();
    targetIriField = new TextField("IRI Alvo (opcional)");
    targetNameField = new TextField("Nome da Ontologia (opcional)");
    Button iniciarButton = new Button("Iniciar Construção");
    iniciarButton.addClickListener(e -> iniciarConstrucao());
    progressBar = new ProgressBar();
    progressBar.setVisible(false);
    resultadoPre = new Pre("Resultado aparecerá aqui");
    resultadoPre.setWidthFull();
    add(dominioField, corpusField, targetIriField, targetNameField, iniciarButton, progressBar, resultadoPre);
  }

  private void iniciarConstrucao() {
    String dominio = dominioField.getValue();
    String corpus = corpusField.getValue();
    if (dominio == null || dominio.isBlank() || corpus == null || corpus.isBlank()) {
      resultadoPre.setText("Preencha o domínio e o corpus");
      return;
    }
    RequisicaoConstrucaoOntologiaDTO requisicao = RequisicaoConstrucaoOntologiaDTO.builder()
        .domain(dominio)
        .corpus(corpus)
        .targetIri(targetIriField.getValue())
        .targetName(targetNameField.getValue())
        .maxIterations(10)
        .build();
    ResultadoConstrucaoOntologiaDTO resultado = ResultadoConstrucaoOntologiaDTO.builder()
        .jobId("job-" + System.currentTimeMillis())
        .status("QUEUED")
        .build();
    jobIdAtual = resultado.getJobId();
    progressBar.setVisible(true);
    resultadoPre.setText("Job iniciado: " + jobIdAtual + " (requisição: " + requisicao.getDomain() + ")");
  }
}
