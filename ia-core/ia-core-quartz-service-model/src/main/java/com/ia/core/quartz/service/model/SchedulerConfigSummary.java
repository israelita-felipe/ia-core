package com.ia.core.quartz.service.model;

import com.ia.core.model.projection.EntityProjection;

/**
 * Projection para visualização resumida de SchedulerConfig.
 * <p>
 * Usada em listas e selects onde não é necessário carregar todos os dados
 * da entidade, apenas os campos essenciais para exibição.
 * </p>
 *
 * @author Israel Araújo
 */
public interface SchedulerConfigSummary extends EntityProjection {

    /**
     * ID único do SchedulerConfig.
     */
    Long getId();

    /**
     * Nome da classe do job.
     */
    String getJobClassName();

    /**
     * Status ativo/inativo.
     */
    Boolean getAtivo();
}
