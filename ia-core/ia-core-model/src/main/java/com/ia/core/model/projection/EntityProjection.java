package com.ia.core.model.projection;

import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryProxyPostProcessor;

import com.ia.core.model.BaseEntity;

/**
 * Marker interface para projeções JPA.
 * <p>
 * Spring Data JPA suporta projeções através de interfaces ou classes.
 * Esta interface serve como base para todas as projeções do projeto.
 * </p>
 * <p>
 * Exemplo de uso:
 * <pre>
 * public interface EventoSummary extends EntityProjection {
 *     Long getId();
 *     String getTitulo();
 * }
 * </pre>
 * </p>
 *
 * @author Israel Araújo
 */
public interface EntityProjection {
    
    /**
     * Marker method para identificar que é uma projection.
     * Não possui implementação - serve apenas para type checking.
     */
    default boolean isProjection() {
        return true;
    }
}
