package com.ia.core.quartz.view.job.form;

import com.ia.core.quartz.service.model.job.dto.QuartzJobDTO;
import com.ia.core.quartz.view.job.QuartzJobManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import lombok.Getter;

/**
 * Configuração do ViewModel para o formulário de Jobs do Quartz.
 *
 * @author Israel Araújo
 */
public class QuartzJobFormViewModelConfig
  extends FormViewModelConfig<QuartzJobDTO> {

  @Getter
  private final QuartzJobManager quartzJobManager;

  /**
   * @param readOnly         Indicativo de somente leitura
   * @param quartzJobManager Manager de Jobs do Quartz
   */
  public QuartzJobFormViewModelConfig(boolean readOnly,
                                      QuartzJobManager quartzJobManager) {
    super(readOnly);
    this.quartzJobManager = quartzJobManager;
  }
}
