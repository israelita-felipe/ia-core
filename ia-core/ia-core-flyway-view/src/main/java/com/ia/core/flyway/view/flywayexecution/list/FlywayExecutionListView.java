package com.ia.core.flyway.view.flywayexecution.list;

import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

/**
 * ListView para exibir a lista de execuções do Flyway.
 * <p>
 * Exibe as colunas: versão, descrição, tipo, script, usuário, data,
 * tempo de execução e status (sucesso/falha).
 * </p>
 *
 * @author Israel Araújo
 */
public class FlywayExecutionListView extends ListView<FlywayExecutionDTO> {

	public FlywayExecutionListView(IListViewModel<FlywayExecutionDTO> viewModel) {
		super(viewModel);
	}

	@Override
	protected void createColumns() {
		addColumn(execution -> execution.getId())
			.setHeader("Rank")
			.setSortable(true)
			.setWidth("80px");

		addColumn(FlywayExecutionDTO::getMigrationVersion)
			.setHeader("Versão")
			.setSortable(true)
			.setWidth("100px");

		addColumn(FlywayExecutionDTO::getDescription)
			.setHeader("Descrição")
			.setSortable(true)
			.setFlexGrow(1);

		addColumn(FlywayExecutionDTO::getType)
			.setHeader("Tipo")
			.setWidth("100px");

		addColumn(FlywayExecutionDTO::getScript)
			.setHeader("Script")
			.setWidth("200px");

		addColumn(FlywayExecutionDTO::getInstalledBy)
			.setHeader("Executado por")
			.setWidth("120px");

		addColumn(FlywayExecutionDTO::getInstalledOn)
			.setHeader("Data")
			.setWidth("150px");

		addColumn(FlywayExecutionDTO::getExecutionTime)
			.setHeader("Tempo (ms)")
			.setWidth("100px");

		addColumn(execution -> execution.getSuccess() ? "✓ Sucesso" : "✗ Falha")
			.setHeader("Status")
			.setWidth("100px")
			.setSortable(true);
	}
}