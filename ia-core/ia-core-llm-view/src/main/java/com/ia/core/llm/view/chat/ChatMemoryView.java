package com.ia.core.llm.view.chat;

import com.ia.core.llm.service.model.agente.TurnoConversacao;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * View para gerenciamento de memória de chat.
 * <p>
 * Permite visualizar, buscar e gerenciar o histórico de conversações.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
public class ChatMemoryView extends VerticalLayout {

  private final Grid<TurnoConversacao> memoriaGrid;
  private final Button limparButton;
  private final Button exportarButton;
  private final H3 titulo;

  private List<TurnoConversacao> memoria = new ArrayList<>();

  public ChatMemoryView() {
    setSizeFull();
    setPadding(true);
    setSpacing(true);

    // Header
    titulo = new H3("Memória de Chat");

    // Grid de memória
    memoriaGrid = new Grid<>(TurnoConversacao.class, false);
    memoriaGrid.addColumn(TurnoConversacao::getNumeroTurno).setHeader("Turno");
    memoriaGrid.addColumn(TurnoConversacao::getMensagemUsuario).setHeader("Mensagem do Usuário");
    memoriaGrid.addColumn(TurnoConversacao::getRespostaAgente).setHeader("Resposta do Agente");
    memoriaGrid.addColumn(TurnoConversacao::getConstrutorIdentificado).setHeader("Construtor");
    memoriaGrid.addColumn(TurnoConversacao::getTimestamp).setHeader("Timestamp");
    memoriaGrid.addColumn(TurnoConversacao::isValidacaoSucesso).setHeader("Validação");
    memoriaGrid.addColumn(TurnoConversacao::getIteracoesValidacao).setHeader("Iterações");
    memoriaGrid.setSizeFull();

    // Botões
    limparButton = new Button("Limpar Memória", VaadinIcon.TRASH.create());
    limparButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
    limparButton.addClickListener(e -> limparMemoria());

    exportarButton = new Button("Exportar", VaadinIcon.DOWNLOAD.create());
    exportarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    exportarButton.addClickListener(e -> exportarMemoria());

    HorizontalLayout buttonLayout = new HorizontalLayout(limparButton, exportarButton);
    buttonLayout.setWidthFull();
    buttonLayout.setJustifyContentMode(JustifyContentMode.END);

    // Layout principal
    add(titulo, memoriaGrid, buttonLayout);
    setFlexGrow(1, memoriaGrid);
  }

  /**
   * Adiciona um turno à memória.
   */
  public void adicionarTurno(TurnoConversacao turno) {
    memoria.add(turno);
    memoriaGrid.setItems(memoria);
    log.debug("Turno adicionado à memória: {}", turno.getNumeroTurno());
  }

  /**
   * Define a lista completa de memória.
   */
  public void setMemoria(List<TurnoConversacao> memoria) {
    this.memoria = new ArrayList<>(memoria);
    memoriaGrid.setItems(this.memoria);
    log.info("Memória atualizada com {} turnos", this.memoria.size());
  }

  /**
   * Obtém a memória atual.
   */
  public List<TurnoConversacao> getMemoria() {
    return new ArrayList<>(memoria);
  }

  /**
   * Limpa a memória.
   */
  private void limparMemoria() {
    memoria.clear();
    memoriaGrid.setItems(memoria);
    log.info("Memória limpa");
  }

  /**
   * Exporta a memória para formato texto.
   */
  private void exportarMemoria() {
    StringBuilder sb = new StringBuilder();
    sb.append("=== Memória de Chat ===\n\n");

    for (TurnoConversacao turno : memoria) {
      sb.append("Turno ").append(turno.getNumeroTurno()).append("\n");
      sb.append("Usuário: ").append(turno.getMensagemUsuario()).append("\n");
      sb.append("Agente: ").append(turno.getRespostaAgente()).append("\n");
      sb.append("Construtor: ").append(turno.getConstrutorIdentificado()).append("\n");
      sb.append("Timestamp: ").append(turno.getTimestamp()).append("\n");
      sb.append("Validação: ").append(turno.isValidacaoSucesso() ? "Sucesso" : "Falha").append("\n");
      sb.append("Iterações: ").append(turno.getIteracoesValidacao()).append("\n");
      sb.append("---\n\n");
    }

    // Na implementação completa, iniciaria download do arquivo
    log.info("Memória exportada: {} caracteres", sb.length());
  }

  /**
   * Busca na memória por texto.
   */
  public List<TurnoConversacao> buscar(String termo) {
    if (termo == null || termo.trim().isEmpty()) {
      return new ArrayList<>(memoria);
    }

    String termoLower = termo.toLowerCase();
    List<TurnoConversacao> resultados = new ArrayList<>();

    for (TurnoConversacao turno : memoria) {
      if (turno.getMensagemUsuario().toLowerCase().contains(termoLower) ||
          turno.getRespostaAgente().toLowerCase().contains(termoLower)) {
        resultados.add(turno);
      }
    }

    log.debug("Busca por '{}' retornou {} resultados", termo, resultados.size());
    return resultados;
  }

  /**
   * Obtém estatísticas da memória.
   */
  public String obterEstatisticas() {
    int totalTurnos = memoria.size();
    long validacoesSucesso = memoria.stream().filter(TurnoConversacao::isValidacaoSucesso).count();
    long validacoesFalha = totalTurnos - validacoesSucesso;
    int totalIteracoes = memoria.stream().mapToInt(TurnoConversacao::getIteracoesValidacao).sum();

    return String.format("Total: %d turnos | Sucesso: %d | Falha: %d | Iterações: %d",
                        totalTurnos, validacoesSucesso, validacoesFalha, totalIteracoes);
  }
}
