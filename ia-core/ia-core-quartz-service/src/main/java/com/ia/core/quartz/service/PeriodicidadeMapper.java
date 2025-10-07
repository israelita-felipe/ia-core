package com.ia.core.quartz.service;

import org.mapstruct.Mapper;

import com.ia.core.quartz.model.periodicidade.Periodicidade;
import com.ia.core.quartz.service.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.service.mapper.BaseMapper;

/**
 * {@link Mapper} de {@link Periodicidade} para {@link PeriodicidadeDTO}
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring")
public interface PeriodicidadeMapper
  extends BaseMapper<Periodicidade, PeriodicidadeDTO> {

}
