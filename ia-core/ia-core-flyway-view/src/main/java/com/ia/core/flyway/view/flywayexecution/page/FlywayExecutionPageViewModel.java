package com.ia.core.flyway.view.flywayexecution.page;

import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO;
import com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionSearchRequest;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.page.viewModel.PageViewModel;
import lombok.extern.slf4j.Slf4j;

/**
 * ViewModel para a página de execuções do Flyway.
 * <p>
 * Como esta é uma tabela de histórico de migrations (apenas leitura), este
 * ViewModel fornece métodos específicos para listar todas as execuções, apenas
 * as bem-sucedidas ou apenas as falhadas.
 * </p>
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa o modelo de dados para a view de flyway execution page.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a FlywayExecutionPageViewModel
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Slf4j
public class FlywayExecutionPageViewModel
  extends PageViewModel<FlywayExecutionDTO> {

  public FlywayExecutionPageViewModel(FlywayExecutionPageViewModelConfig config) {
    super(config);
  }

  @Override
  protected SearchRequestDTO createSearchRequest() {
    return new FlywayExecutionSearchRequest();
  }

  @Override
  public IFormViewModel<FlywayExecutionDTO> createFormViewModel(FormViewModelConfig<FlywayExecutionDTO> config) {
    // Tabela de histórico de migrations é apenas leitura - não há formulário
    throw new UnsupportedOperationException("Operação não suportada: a tabela flyway_schema_history é gerenciada automaticamente pelo Flyway");
  }

  @Override
  public Long getId(FlywayExecutionDTO object) {
    return object != null ? object.getId() : null;
  }

  @Override
  public FlywayExecutionDTO createNewObject() {
    return new FlywayExecutionDTO();
  }

  @Override
  public Class<FlywayExecutionDTO> getType() {
    return FlywayExecutionDTO.class;
  }

  @Override
  public FlywayExecutionPageViewModelConfig getConfig() {
    return super.getConfig().cast();
  }

  @Override
  public FlywayExecutionDTO cloneObject(FlywayExecutionDTO object) {
    // Como não há métodos de clone no DTO, retorna uma cópia manual
    if (object == null) {
      return null;
    }
    FlywayExecutionDTO copy = new FlywayExecutionDTO();
    copy.setId(object.getId());
    copy.setVersion(object.getVersion());
    copy.setDescription(object.getDescription());
    copy.setType(object.getType());
    copy.setScript(object.getScript());
    copy.setChecksum(object.getChecksum());
    copy.setInstalledBy(object.getInstalledBy());
    copy.setInstalledOn(object.getInstalledOn());
    copy.setExecutionTime(object.getExecutionTime());
    copy.setSuccess(object.getSuccess());
    return copy;
  }

  @Override
  public FlywayExecutionDTO copyObject(FlywayExecutionDTO object) {
    FlywayExecutionDTO copy = cloneObject(object);
    if (copy != null) {
      copy.setId(null);
    }
    return copy;
  }

}
