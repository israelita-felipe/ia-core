package com.ia.core.service.configuracao;

import com.ia.core.model.configuracao.ConfiguracaoSistema;
import com.ia.core.service.configuracao.dto.ConfiguracaoSistemaDTO;
import com.ia.core.service.mapper.BaseEntityMapper;

/**
 * Mapper genericamente tipado para ConfiguracaoSistema.
 * <p>
 * Segue o padrão AttachmentMapper - os módulos devem estender esta interface
 * e adicionar a anotação @Mapper com componentModel = "spring".
 *
 * @param <T> tipo da entidade de configuração
 * @param <D> tipo do DTO de configuração
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface ConfiguracaoMapper<T extends ConfiguracaoSistema, D extends ConfiguracaoSistemaDTO<T>>
    extends BaseEntityMapper<T, D> {

}