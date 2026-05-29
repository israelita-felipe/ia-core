package com.ia.core.llm.view.ferramenta;

import com.ia.core.llm.service.model.ferramenta.FerramentaDTO;
import com.ia.core.view.manager.DefaultBaseManager;

/**
 * Manager Feign para CRUD de {@link FerramentaDTO}.
 * {@code syncFromDiscovery()} fica em {@link com.ia.core.llm.service.ferramenta.FerramentaService}.
 */
public class FerramentaManager
  extends DefaultBaseManager<FerramentaDTO> {

  public FerramentaManager(FerramentaManagerConfig config) {
    super(config);
  }
}
