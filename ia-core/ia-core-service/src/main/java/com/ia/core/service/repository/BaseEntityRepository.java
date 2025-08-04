package com.ia.core.service.repository;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.ia.core.model.BaseEntity;

/**
 * Repositório base da camada de serviço.
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado que extende de {@link BaseEntity}.
 * @see BaseEntity
 */
@NoRepositoryBean
public interface BaseEntityRepository<T extends Serializable>
  extends JpaRepository<T, UUID>, JpaSpecificationExecutor<T> {

}
