package com.ia.core.quartz.service;

import org.quartz.ScheduleBuilder;

import com.ia.core.quartz.service.model.periodicidade.dto.PeriodicidadeDTO;

/**
 * Construtor de schedule específico para periodicidades.
 * <p>
 * Esta classe estende o {@link org.quartz.ScheduleBuilder} do Quartz para
 * permitir a criação de triggers baseados em objetos {@link PeriodicidadeDTO}.
 * O schedule builder é responsável por construir a estratégia de agendamento
 * que será usada pelo trigger.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see PeriodicidadeTrigger
 * @see PeriodicidadeDTO
 * @see org.quartz.ScheduleBuilder
 */
public class PeriodicidadeScheduleBuilder
  extends ScheduleBuilder<PeriodicidadeTrigger> {

  /**
   * Periodicidade usada para construir o schedule.
   */
  private final PeriodicidadeDTO periodicidade;

  /**
   * Construtor que recebe a periodicidade para construção do schedule.
   *
   * @param periodicidade Objeto contendo as informações de periodicidade
   *                       (não pode ser {@code null})
   * @since 1.0.0
   */
  public PeriodicidadeScheduleBuilder(PeriodicidadeDTO periodicidade) {
    this.periodicidade = periodicidade;
  }

  /**
   * Constrói o trigger de periodicidade.
   *
   * @return Novo {@link PeriodicidadeTrigger} configurado com base na periodicidade
   * @since 1.0.0
   */
  @Override
  protected PeriodicidadeTrigger build() {
    return new PeriodicidadeTrigger(periodicidade);
  }
}
