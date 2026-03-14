package com.ia.core.quartz.view.job.page;

import java.util.Collection;
import java.util.List;

import com.ia.core.quartz.service.model.job.QuartzJobDTO;
import com.ia.core.quartz.view.job.QuartzJobManager;
import com.ia.core.quartz.view.job.form.QuartzJobFormView;
import com.ia.core.quartz.view.job.list.QuartzJobListView;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.PageView;
import com.ia.core.view.components.page.viewModel.IPageViewModel.PageAction;
import com.vaadin.flow.component.icon.VaadinIcon;

/**
 * Página principal para visualização de Jobs do Quartz.
 *
 * @author Israel Araújo
 */
public class QuartzJobPageView
  extends PageView<QuartzJobDTO> {
  /** Serial UUID */
  private static final long serialVersionUID = -2796746737472741061L;
  /** Rota */
  public static final String ROUTE = "quartz-job";

  /**
   * @param viewModel ViewModel da página
   */
  public QuartzJobPageView(QuartzJobPageViewModel viewModel) {
    super(viewModel);
  }

  @Override
  public IFormView<QuartzJobDTO> createFormView(IFormViewModel<QuartzJobDTO> formViewModel) {
    return new QuartzJobFormView(formViewModel);
  }

  @Override
  public IListView<QuartzJobDTO> createListView(IListViewModel<QuartzJobDTO> listViewModel) {
    return new QuartzJobListView(listViewModel);
  }

  @Override
  public Collection<PageAction<QuartzJobDTO>> createDefaultPageActions() {
    List<PageAction<QuartzJobDTO>> actions = new java.util.ArrayList<>();
    actions.add(createViewAction());
    actions.add(createPauseJobAction());
    actions.add(createResumeJobAction());
    actions.add(createTriggerJobAction());
    actions.add(createDeleteJobAction());
    actions.add(createPrintAction());
    return actions;
  }

  /**
   * Cria a ação de pausar o job.
   *
   * @return PageAction para pausar job
   */
  public PageAction<QuartzJobDTO> createPauseJobAction() {
    return PageAction.<QuartzJobDTO> builder().icon(VaadinIcon.PAUSE)
        .enableFunction(this::canPauseJob)
        .action(() -> pauseJob(getViewModel().getSelected())).build();
  }

  /**
   * Cria a ação de resumir o job.
   *
   * @return PageAction para resumir job
   */
  public PageAction<QuartzJobDTO> createResumeJobAction() {
    return PageAction.<QuartzJobDTO> builder().icon(VaadinIcon.AUTOMATION)
        .enableFunction(this::canResumeJob)
        .action(() -> resumeJob(getViewModel().getSelected())).build();
  }

  /**
   * Cria a ação de Disparar o job.
   *
   * @return PageAction para Disparar job
   */
  public PageAction<QuartzJobDTO> createTriggerJobAction() {
    return PageAction.<QuartzJobDTO> builder().icon(VaadinIcon.PLAY)
        .enableFunction(this::canTriggerJob)
        .action(() -> triggerJob(getViewModel().getSelected())).build();
  }

  /**
   * Cria a ação de excluir o job.
   *
   * @return PageAction para excluir job
   */
  public PageAction<QuartzJobDTO> createDeleteJobAction() {
    return PageAction.<QuartzJobDTO> builder().icon(VaadinIcon.TRASH)
        .enableFunction(this::canDeleteJob)
        .action(() -> deleteJob(getViewModel().getSelected())).build();
  }

  /**
   * Verifica se o job pode ser pausado.
   *
   * @param job Job a ser verificado
   * @return true se puder ser pausado
   */
  protected boolean canPauseJob(QuartzJobDTO job) {
    return job != null && !"PAUSED".equals(job.getJobState());
  }

  /**
   * Pausa o job selecionado.
   *
   * @param job Job a ser pausado
   */
  protected void pauseJob(QuartzJobDTO job) {
    if (job != null && job.getJobName() != null
        && job.getJobGroup() != null) {
      getQuartzJobManager().pauseJob(job.getJobName(), job.getJobGroup());
      refreshAll();
    }
  }

  /**
   * Verifica se o job pode ser resumido.
   *
   * @param job Job a ser verificado
   * @return true se puder ser resumido
   */
  protected boolean canResumeJob(QuartzJobDTO job) {
    return job != null && "PAUSED".equals(job.getJobState());
  }

  /**
   * Resume o job selecionado.
   *
   * @param job Job a ser resumido
   */
  protected void resumeJob(QuartzJobDTO job) {
    if (job != null && job.getJobName() != null
        && job.getJobGroup() != null) {
      getQuartzJobManager().resumeJob(job.getJobName(), job.getJobGroup());
      refreshAll();
    }
  }

  /**
   * Verifica se o job pode ser disparado.
   *
   * @param job Job a ser verificado
   * @return true se puder ser disparado
   */
  protected boolean canTriggerJob(QuartzJobDTO job) {
    return job != null;
  }

  /**
   * Dispara o job selecionado.
   *
   * @param job Job a ser disparado
   */
  protected void triggerJob(QuartzJobDTO job) {
    if (job != null && job.getJobName() != null
        && job.getJobGroup() != null) {
      getQuartzJobManager().triggerJob(job.getJobName(), job.getJobGroup());
    }
  }

  /**
   * Verifica se o job pode ser excluído.
   *
   * @param job Job a ser verificado
   * @return true se puder ser excluído
   */
  protected boolean canDeleteJob(QuartzJobDTO job) {
    return job != null;
  }

  /**
   * Exclui o job selecionado.
   *
   * @param job Job a ser excluído
   */
  protected void deleteJob(QuartzJobDTO job) {
    if (job != null && job.getJobName() != null
        && job.getJobGroup() != null) {
      getQuartzJobManager().deleteJob(job.getJobName(), job.getJobGroup());
      refreshAll();
    }
  }

  @Override
  public QuartzJobPageViewModel getViewModel() {
    return super.getViewModel().cast();
  }

  /**
   * Obtém o Manager de Jobs do Quartz.
   *
   * @return QuartzJobManager
   */
  protected QuartzJobManager getQuartzJobManager() {
    return getViewModel().getConfig().getQuartzJobManager();
  }

}
