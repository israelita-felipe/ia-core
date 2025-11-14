package com.ia.core.owl.service.axioma;

import org.mapstruct.Mapper;

import com.ia.core.owl.model.axiom.Axioma;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import com.ia.core.service.mapper.BaseEntityMapper;

/**
 * {@link Mapper} de {@link Axioma} para {@link AxiomaDTO}
 *
 * @author Israel Araújo
 */
@Mapper(componentModel = "spring")
public interface AxiomaMapper
  extends BaseEntityMapper<Axioma, AxiomaDTO> {

}
