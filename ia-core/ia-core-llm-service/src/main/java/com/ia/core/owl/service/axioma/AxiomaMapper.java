package com.ia.core.owl.service.axioma;

import com.ia.core.owl.model.axiom.Axioma;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * {@link Mapper} de {@link Axioma} para {@link AxiomaDTO}
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa o mapeamento de dados para axioma.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a AxiomaMapper
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface AxiomaMapper
  extends BaseEntityMapper<Axioma, AxiomaDTO> {

}
