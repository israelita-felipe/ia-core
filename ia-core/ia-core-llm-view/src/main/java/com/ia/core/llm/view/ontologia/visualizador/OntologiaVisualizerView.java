package com.ia.core.llm.view.ontologia.visualizador;

import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Pre;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * View para visualização de ontologias.
 * <p>
 * Exibe axiomas e estrutura da ontologia de forma legível.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Route("ontologia-visualizador")
public class OntologiaVisualizerView extends VerticalLayout {

  public OntologiaVisualizerView() {
    H2 title = new H2("Visualizador de Ontologia");
    add(title);
  }

  public void setOntologia(OntologiaDTO ontologia) {
    removeAll();

    H2 title = new H2("Ontologia: " + ontologia.getNome());
    add(title);

    Div info = new Div();
    info.setText("IRI: " + ontologia.getIri() +
                " | Versão: " + ontologia.getVersao() +
                " | Consistente: " + ontologia.isConsistente());
    add(info);

    Pre axiomas = new Pre();
    axiomas.setText(String.join("\n", ontologia.getAxiomas()));
    add(axiomas);
  }
}
