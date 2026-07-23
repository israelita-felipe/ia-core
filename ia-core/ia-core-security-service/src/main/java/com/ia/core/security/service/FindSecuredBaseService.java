package com.ia.core.security.service;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.service.FindBaseService;
import com.ia.core.service.annotations.TransactionalReadOnly;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.repository.BaseEntityRepository;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Interface base para serviços seguros de busca de entidades com controle de autorização.
 * <p>
 * Esta interface estende {@link BaseSecuredService} adicionando funcionalidades específicas
 * para busca de entidades com verificação de autorização. Implementa a lógica de verificação
 * de permissões de leitura e enriquecimento de contexto com informações da entidade.
 * <p>
 * Principais responsabilidades:
 * <ul>
 *   <li>Verificação de autorização para operações de leitura</li>
 *   <li>Enriquecimento do contexto de autorização com dados da entidade</li>
 *   <li>Registro de funcionalidades do serviço</li>
 *   <li>Integração com o gerenciador de autorizações</li>
 * </ul>
 *
 * @param <T> tipo da entidade que estende {@link BaseEntity}
 * @param <D> tipo do DTO que estende {@link DTO}
 * @author Israel Araújo
 * @see BaseSecuredService
 * @see FindBaseService
 * @see BaseEntityRepository
 * @since 1.0.0
 */
public interface FindSecuredBaseService<T extends Serializable, D extends DTO<?>>
    extends BaseSecuredService<T, D>, FindBaseService<T, D> {

    @TransactionalReadOnly
    @Override
    default D find(Long id) {
        return FindBaseService.super.find(id);
    }

    /**
     * Verifica se o usuário atual possui autorização para ler a entidade especificada.
     * <p>
     * Delega ao gerenciador de autorizações a verificação de permissão de leitura
     * para a entidade identificada pelo ID fornecido. Esta verificação considera
     * as políticas de segurança, contextos e permissões configuradas para o serviço.
     *
     * @param id o identificador único da entidade a ser verificada (pode ser null)
     * @return true se o usuário possuir autorização para ler a entidade, false caso contrário
     */
    @Override
    default boolean canFind(Long id) {
        return getAuthorizationManager().canRead(this, id);
    }

    /**
     * Obtém o mapa de valores de contexto para o objeto fornecido, enriquecido com
     * informações da entidade quando aplicável.
     * <p>
     * Este método estende a implementação base adicionando o identificador da entidade
     * ao mapa de contexto quando o objeto fornecido é um {@link Long}. O identificador
     * é armazenado sob a chave {@link AbstractBaseEntityDTO.CAMPOS#ID}, permitindo que
     * o sistema de autorização correlacione o contexto com a entidade específica.
     *
     * @param object o objeto para o qual os valores de contexto devem ser obtidos
     *               (pode ser um {@link Long} representando o ID da entidade)
     * @return mapa contendo os pares chave-valor dos contextos, nunca null. Inclui
     * o identificador da entidade quando object é um {@link Long}
     */
    @Override
    default Map<String, String> getContextValue(Object object) {
        Map<String, String> contextMap = BaseSecuredService.super.getContextValue(object);
        if (Long.class.isInstance(object)) {
            contextMap.put(AbstractBaseEntityDTO.CAMPOS.ID,
                Objects.toString(object));
        }
        return contextMap;
    }

    /**
     * Registra as funcionalidades do serviço no gerenciador de funcionalidades.
     * <p>
     * Este método é invocado durante a inicialização do sistema para registrar
     * as funcionalidades associadas a este serviço. Adiciona a funcionalidade
     * principal do serviço ao gerenciador, permitindo o controle de acesso
     * baseado em funcionalidades.
     *
     * @param functionalityManager o gerenciador de funcionalidades responsável
     *                             pelo registro (não pode ser null)
     * @return conjunto contendo a funcionalidade registrada, nunca null
     * @throws NullPointerException se functionalityManager for null
     */
    @Override
    default Set<Functionality> registryFunctionalities(FunctionalityManager functionalityManager) {
        return Set.of(functionalityManager.addFunctionality(this));
    }
}
