package com.ia.core.rest.control;

import com.ia.core.service.BaseService;
import com.ia.core.service.dto.DTO;

import java.io.Serializable;

/**
 * Interface base para um controller.
 *
 * @param <T> Tipo do modelo.
 * @param <D> Tipo de dado {@link DTO}
 * @author Israel Araújo
 */
public interface BaseController<T extends Serializable, D extends DTO<?>> {
    // Constante para o nome do esquema de segurança JWT
    String TOKEN_AUTENTICACAO = "Token de Autenticação";

    /**
     * Serviço do controlador {@link BaseController}.
     *
     * @param <R> Tipo do serviço: {@link BaseService}
     * @return Um {@link BaseService} de <R>.
     */
    <R extends BaseService<?, D>> R getService();
}
