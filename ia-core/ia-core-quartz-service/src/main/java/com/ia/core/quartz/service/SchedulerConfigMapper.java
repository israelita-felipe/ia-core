package com.ia.core.quartz.service;

import com.ia.core.quartz.model.scheduler.SchedulerConfig;
import com.ia.core.quartz.service.model.scheduler.dto.SchedulerConfigDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * {@link Mapper} de {@link SchedulerConfig} para {@link SchedulerConfigDTO}
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa o mapeamento de dados para scheduler config.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a SchedulerConfigMapper
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Mapper(componentModel = "spring", uses = { PeriodicidadeMapper.class })
public interface SchedulerConfigMapper
  extends BaseEntityMapper<SchedulerConfig, SchedulerConfigDTO> {

}
