package com.ia.core.llm.service.model.ferramenta;

import com.ia.core.service.usecase.CrudUseCase;

import java.util.List;

/**
 * Interface de Use Case para Ferramenta.
 * <p>
 * Define operações CRUD e métodos específicos para ferramentas.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface FerramentaUseCase
  extends CrudUseCase<FerramentaDTO> {

  void syncFromDiscovery();

  List<FerramentaDTO> listAvailable();
}
