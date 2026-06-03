package com.ia.core.llm.view.ferramenta;

import com.ia.core.llm.service.model.ferramenta.RequisicaoFerramenta;
import com.ia.core.llm.service.model.ferramenta.owl.FerramentaOwlInfoDTO;
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

import java.util.List;

/**
 * View para listagem e execução de ferramentas OWL 2 DL via REST ({@link FerramentasOwlClient}).
 */
@Slf4j
public class FerramentasView extends VerticalLayout {

  private final FerramentasOwlClient ferramentasOwlClient;
  private final Grid<FerramentaOwlInfoDTO> ferramentasGrid;
  private final TextField descricaoField;
  private final TextArea resultadoArea;
  private final Button executarButton;
  private final Button atualizarButton;

  private FerramentaOwlInfoDTO ferramentaSelecionada;

  public FerramentasView(FerramentasOwlClient ferramentasOwlClient) {
    this.ferramentasOwlClient = ferramentasOwlClient;
    setSizeFull();
    setPadding(true);
    setSpacing(true);

    H3 titulo = new H3("Ferramentas OWL 2 DL");
    ferramentasGrid = new Grid<>(FerramentaOwlInfoDTO.class, false);
    ferramentasGrid.addColumn(FerramentaOwlInfoDTO::getConstrutor).setHeader("Construtor");
    ferramentasGrid.addColumn(FerramentaOwlInfoDTO::getDescricao).setHeader("Descrição");
    ferramentasGrid.setSizeFull();
    ferramentasGrid.setMaxHeight("300px");
    executarButton = new Button("Executar Ferramenta", VaadinIcon.PLAY.create());
    executarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    executarButton.setEnabled(false);
    executarButton.addClickListener(e -> executarFerramenta());

    ferramentasGrid.addSelectionListener(e ->
        e.getFirstSelectedItem().ifPresent(tool -> {
          ferramentaSelecionada = tool;
          executarButton.setEnabled(true);
        }));

    atualizarButton = new Button("Atualizar", VaadinIcon.REFRESH.create());
    atualizarButton.addClickListener(e -> carregarFerramentas());

    HorizontalLayout gridHeaderLayout = new HorizontalLayout(titulo, atualizarButton);
    gridHeaderLayout.setWidthFull();
    gridHeaderLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

    descricaoField = new TextField("Descrição");
    descricaoField.setPlaceholder("Descreva o axioma que deseja gerar...");
    descricaoField.setWidthFull();
    HorizontalLayout inputLayout = new HorizontalLayout(descricaoField, executarButton);
    inputLayout.setWidthFull();
    inputLayout.setFlexGrow(1, descricaoField);

    resultadoArea = new TextArea("Resultado");
    resultadoArea.setReadOnly(true);
    resultadoArea.setWidthFull();
    resultadoArea.setHeight("200px");

    add(gridHeaderLayout, ferramentasGrid, inputLayout, resultadoArea);
    setFlexGrow(1, ferramentasGrid);
    carregarFerramentas();
  }

  private void carregarFerramentas() {
    List<FerramentaOwlInfoDTO> ferramentas = ferramentasOwlClient.listar();
    ferramentasGrid.setItems(ferramentas);
    log.info("Carregadas {} ferramentas OWL", ferramentas.size());
  }

  private void executarFerramenta() {
    if (ferramentaSelecionada == null) {
      return;
    }
    String descricao = descricaoField.getValue();
    if (descricao == null || descricao.isBlank()) {
      return;
    }
    var resultado = ferramentasOwlClient.executar(
        ferramentaSelecionada.getId(),
        RequisicaoFerramenta.builder().descricaoNatureza(descricao).build());
    resultadoArea.setValue(resultado.getExplicacao() + "\nAxiomas: " + resultado.getAxiomas());
  }
}
