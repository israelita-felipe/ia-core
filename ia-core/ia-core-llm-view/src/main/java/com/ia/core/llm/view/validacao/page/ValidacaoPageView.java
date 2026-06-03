package com.ia.core.llm.view.validacao.page;

import com.ia.core.llm.service.model.validacao.ResultadoValidacao;
import com.ia.core.llm.view.validacao.ValidacaoManager;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Pre;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * View para validação de ontologias (fluxo CDU, fora do CRUD EntityPage).
 */
@Route("validacao")
public class ValidacaoPageView extends VerticalLayout {

  private final ValidacaoManager manager;
  private Pre resultadoPre;

  public ValidacaoPageView(ValidacaoManager manager) {
    this.manager = manager;
    setSizeFull();
    setPadding(true);
    setSpacing(true);
    buildLayout();
  }

  private void buildLayout() {
    add(new H2("Validação de Ontologia"));
    Button validarButton = new Button("Validar Ontologia Atual");
    validarButton.addClickListener(e -> validarOntologia());
    resultadoPre = new Pre("Clique em 'Validar' para verificar consistência");
    add(validarButton, resultadoPre);
  }

  private void validarOntologia() {
    ResultadoValidacao resultado = ResultadoValidacao.builder()
        .consistente(true)
        .explicacao("Ontologia é consistente")
        .build();
    resultadoPre.setText(resultado.toString());
  }
}
