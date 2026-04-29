package com.ia.core.quartz.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.function.Consumer;

/**
 * Classe abstrata base para jobs do Quartz.
 * <p>
 * Fornece uma base para implementação de jobs usando Spring Quartz,
 * com suporte a inicialização de contexto customizável. Esta classe
 * abstrai a complexidade do ciclo de vida do Quartz e permite que
 * subclasses foquem apenas na lógica de negócio.
 * <p>
 * Exemplo de uso:
 * <pre>
 * public class MeuJob extends AbstractJob {
 *     &#64;Override
 *     protected void executeInternal(JobExecutionContext context)
 *             throws JobExecutionException {
 *         // Lógica do job
 *     }
 * }
 * </pre>
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see QuartzJobBean
 * @see org.quartz.Job
 */
@Slf4j
@NoArgsConstructor
public abstract class AbstractJob extends QuartzJobBean {

  /**
   * Inicializador de contexto para jobs.
   */
  @Getter
  private Consumer<JobExecutionContext> contextInitializer;

  /**
   * Inicializa o contexto do job com um consumer customizado.
   *
   * @param contextInitializer consumer para inicialização do contexto
   */
  public void initContext(Consumer<JobExecutionContext> contextInitializer) {
    this.contextInitializer = contextInitializer;
  }

  /**
   * Executa a inicialização do contexto usando o consumer configurado.
   *
   * @param context Contexto de execução do Quartz
   * @since 1.0.0
   */
  public void initContext(JobExecutionContext context) {
    if (this.contextInitializer != null) {
      this.contextInitializer.accept(context);
    }
  }

  @Override
  protected void executeInternal(JobExecutionContext context)
    throws JobExecutionException {
    initContext(context);
    executeJob(context);
  }

  /**
   * Método abstrato para implementação da lógica do job.
   *
   * @param context contexto de execução do Quartz
   * @throws JobExecutionException se ocorrer um erro na execução
   */
  protected abstract void executeJob(JobExecutionContext context)
    throws JobExecutionException;
}
