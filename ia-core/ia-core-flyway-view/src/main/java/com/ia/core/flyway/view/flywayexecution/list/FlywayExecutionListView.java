package com.ia.core.flyway.view.flywayexecution.list;

import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

/**
 * ListView para exibir a lista de execuções do Flyway.
 * <p>
 * Exibe as colunas: versão, descrição, tipo, script, usuário, data,
 * tempo de execução e status (sucesso/falha).
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class FlywayExecutionListView<T extends FlywayExecutionDTO<?>>
  extends ListView<T> {

  public FlywayExecutionListView(IListViewModel<T> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    addColumn((T execution) -> execution.getId())
      .setHeader("Rank")
      .setSortable(true)
      .setWidth("80px");

    addColumn((T execution) -> execution.getMigrationVersion())
      .setHeader("Versão")
      .setSortable(true)
      .setWidth("100px");

    addColumn((T execution) -> execution.getDescription())
      .setHeader("Descrição")
      .setSortable(true)
      .setFlexGrow(1);

    addColumn((T execution) -> execution.getType())
      .setHeader("Tipo")
      .setWidth("100px");

    addColumn((T execution) -> execution.getScript())
      .setHeader("Script")
      .setWidth("200px");

    addColumn((T execution) -> execution.getInstalledBy())
      .setHeader("Executado por")
      .setWidth("120px");

    addColumn((T execution) -> execution.getInstalledOn())
      .setHeader("Data")
      .setWidth("150px");

    addColumn((T execution) -> execution.getExecutionTime())
      .setHeader("Tempo (ms)")
      .setWidth("100px");

    addColumn((T execution) -> execution.getSuccess() ? "✓ Sucesso" : "✗ Falha")
      .setHeader("Status")
      .setWidth("100px")
      .setSortable(true);
  }
}
