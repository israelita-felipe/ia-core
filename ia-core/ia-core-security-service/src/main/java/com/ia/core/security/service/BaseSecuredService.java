package com.ia.core.security.service;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.service.authorization.HasAuthorizationManager;
import com.ia.core.security.service.model.authorization.HasContext;
import com.ia.core.security.service.model.functionality.HasFunctionality;
import com.ia.core.service.BaseService;
import com.ia.core.service.dto.DTO;

import java.io.Serializable;

/**
 * Interface base para serviços seguros com controle de autorização e contexto.
 * <p>
 * Esta interface estende {@link BaseService} adicionando funcionalidades de
 * segurança, autorização e gerenciamento de contexto. Serviços que implementam
 * esta interface possuem controle de acesso baseado em funcionalidades,
 * contextos de autorização e permissões granulares.
 * <p>
 * Principais responsabilidades:
 * <ul>
 *   <li>Gerenciamento de funcionalidades do sistema</li>
 *   <li>Controle de contexto de autorização</li>
 *   <li>Registro de operações e auditoria</li>
 *   <li>Gerenciamento de autorizações</li>
 * </ul>
 *
 * @param <T> tipo da entidade que estende {@link BaseEntity}
 * @param <D> tipo do DTO que estende {@link DTO}
 * @author Israel Araújo
 * @see BaseService
 * @see HasFunctionality
 * @see HasContext
 * @see HasLogOperation
 * @see HasAuthorizationManager
 * @since 1.0.0
 */
public interface BaseSecuredService<T extends Serializable, D extends DTO<?>>
    extends BaseService<T, D>, HasFunctionality, HasContext, HasLogOperation,
    HasAuthorizationManager {

    @Override
    default String getContextName() {
        return getFunctionalityTypeName();
    }
}
