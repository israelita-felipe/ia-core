package com.ia.core.view.client;

import com.ia.core.service.dto.DTO;
import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;

/**
 * Interface base para clientes do tipo delete.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 */
public interface DeleteBaseClient<D extends Serializable>
  extends BaseClient<D> {

  /**
   * @param id Id do objeto.
   */
  @DeleteMapping("/{id}")
  @Resilient(ResilienceProfile.INTERNAL_SERVICE)
  void delete(@PathVariable("id") Long id);

}
