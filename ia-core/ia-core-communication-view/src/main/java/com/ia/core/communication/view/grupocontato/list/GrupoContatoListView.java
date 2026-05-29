package com.ia.core.communication.view.grupocontato.list;

import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
/**
 * Interface visual para lista de grupos de contatos.
 * <p>
 * Responsável por gerenciar a exibição de grupos de contatos em formato de lista,
 * incluindo configuração de colunas para nome, descrição e status ativo.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */

public class GrupoContatoListView extends ListView<GrupoContatoDTO> {

  public GrupoContatoListView(IListViewModel<GrupoContatoDTO> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn(GrupoContatoDTO.CAMPOS.NOME);
    addColumn(GrupoContatoDTO.CAMPOS.DESCRICAO);
    addColumn(GrupoContatoDTO.CAMPOS.ATIVO);
  }
}
