package com.ia.core.quartz.service;

import org.quartz.ScheduleBuilder;

import com.ia.core.quartz.service.model.periodicidade.dto.PeriodicidadeDTO;

public class PeriodicidadeScheduleBuilder
  extends ScheduleBuilder<PeriodicidadeTrigger> {

  private final PeriodicidadeDTO periodicidade;

  public PeriodicidadeScheduleBuilder(PeriodicidadeDTO periodicidade) {
    this.periodicidade = periodicidade;
  }

  @Override
  protected PeriodicidadeTrigger build() {
    return new PeriodicidadeTrigger(periodicidade);
  }
}
