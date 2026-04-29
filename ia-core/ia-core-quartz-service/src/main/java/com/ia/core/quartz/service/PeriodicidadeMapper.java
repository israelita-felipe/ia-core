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
/**
 * Classe que representa o mapeamento de dados para periodicidade.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PeriodicidadeMapper
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface PeriodicidadeMapper
  extends BaseEntityMapper<Periodicidade, PeriodicidadeDTO> {

}
