package com.ia.core.quartz.service;

import org.mapstruct.Mapper;

import com.ia.core.quartz.model.scheduler.SchedulerConfig;
import com.ia.core.quartz.service.model.scheduler.SchedulerConfigDTO;
import com.ia.core.service.mapper.BaseEntityMapper;

/**
 * {@link Mapper} de {@link SchedulerConfig} para {@link SchedulerConfigDTO}
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring", uses = { PeriodicidadeMapper.class })
public interface SchedulerConfigMapper
  extends BaseEntityMapper<SchedulerConfig, SchedulerConfigDTO> {

}
