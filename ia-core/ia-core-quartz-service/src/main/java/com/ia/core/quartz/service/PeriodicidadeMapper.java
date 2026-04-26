package com.ia.core.quartz.service;

import com.ia.core.quartz.model.periodicidade.Periodicidade;
import com.ia.core.quartz.service.model.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * {@link Mapper} de {@link Periodicidade} para {@link PeriodicidadeDTO}
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring")
public interface PeriodicidadeMapper
  extends BaseEntityMapper<Periodicidade, PeriodicidadeDTO> {

}
