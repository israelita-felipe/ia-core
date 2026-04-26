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
 *用法示例:
 * <pre>
 * public class MeuJob extends AbstractJob {
 *     \&#64;Override
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
public abstract class AbstractJob
  extends QuartzJobBean {

  /**
   * Consumer para inicialização customizada do contexto de execução.
   * <p>
   * Permite que cada job defina sua própria lógica de preparação
   * do contexto antes da execução.
   *
   * @since 1.0.0
   */
  @Getter
  private Consumer<JobExecutionContext> contextInitializer = context -> {
  };

  /**
   * Método interno do Quartz que executa a lógica do job.
   * <p>
   * Este método é chamado pelo framework Quartz. Ele inicializa o contexto
   * e delega a execução para a lógica específica do job.
   *
   * @param context Contexto de execução do Quartz contendo informações do job e trigger
   * @throws JobExecutionException se ocorrer erro durante a execução do job
   * @since 1.0.0
   */
  @Override
  protected void executeInternal(JobExecutionContext context)
    throws JobExecutionException {
    initContext(context);
  }

  /**
   * Define um consumidor customizado para inicialização do contexto.
   * <p>
   * Permite que o job defina preparações específicas que devem ser
   * executadas antes de cada execução.
   *
   * @param contextInitializer Consumer que será executado para inicializar o contexto
   * @since 1.0.0
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
    this.contextInitializer.accept(context);
  }
}
