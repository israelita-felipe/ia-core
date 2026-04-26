package com.ia.core.service.repository;

import com.ia.core.model.BaseEntity;
import com.ia.core.model.projection.EntityProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Repositório base da camada de serviço.
 * <p>
 * Fornece métodos utilitários para operações básicas de persistência.
 * Para projeções específicas, utilize métodos customizados com @Query
 * diretamente nos repositories específicos.
 * </p>
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado que extende de {@link BaseEntity}.
 * @see BaseEntity
 * @see EntityProjection
 */
@NoRepositoryBean
public interface BaseEntityRepository<T extends BaseEntity>
  extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

}
