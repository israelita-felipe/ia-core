package com.ia.core.service.repository;

import com.ia.core.model.projection.EntityProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.Serializable;

/**
 * Repositório base da camada de serviço.
 * <p>
 * Fornece métodos utilitários para operações básicas de persistência.
 * Para projeções específicas, utilize métodos customizados com @Query
 * diretamente nos repositories específicos.
 * </p>
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado que extende de {@link com.ia.core.model.BaseEntity}.
 * @see com.ia.core.model.BaseEntity
 * @see EntityProjection
 */

/**
 * Classe que representa o acesso a dados de base entity.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a BaseEntityRepository
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@NoRepositoryBean
public interface BaseEntityRepository<T extends Serializable>
    extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

    /**
     * Registra uma sincronização de transação que será executada após o commit.
     *
     * @param afterCommit ação a ser executada após o commit da transação.
     */
    default void registerTransactionSynchronization(Runnable afterCommit) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                if (TransactionSynchronizationManager.isSynchronizationActive()) {
                    TransactionSynchronizationManager
                        .registerSynchronization(new TransactionSynchronization() {
                            @Override
                            public void afterCommit() {
                                afterCommit.run();

                            }
                        });
                } else {
                    afterCommit.run();
                }
            }
        });
    }

}
