package com.ia.core.rest.control;

import com.ia.core.service.BaseService;
import com.ia.core.service.dto.DTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * Classe abstrata base para um controlador.
 *
 * @param <T> Tipo do modelo.
 * @param <D> {@link DTO}
 * @author Israel Araújo
 */
@RequiredArgsConstructor
@Getter
public abstract class AbstractBaseController<T extends Serializable, D extends DTO<?>>
    implements BaseController<T, D> {

    /**
     * Serviço do controlador.
     */
    protected final BaseService<T, D> service;

}
