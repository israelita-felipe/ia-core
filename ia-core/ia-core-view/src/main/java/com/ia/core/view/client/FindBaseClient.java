package com.ia.core.view.client;

import com.ia.core.service.dto.DTO;
import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;

/**
 * Interface base para clientes do tipo find.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 */
public interface FindBaseClient<D extends Serializable>
  extends BaseClient<D> {

  /**
   * @param id Id do objeto.
   * @return {@link DTO}
   */
  @GetMapping("/{id}")
  @Resilient(ResilienceProfile.INTERNAL_SERVICE)
  D find(@PathVariable("id") Long id);

}
