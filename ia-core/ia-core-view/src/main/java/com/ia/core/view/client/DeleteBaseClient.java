package com.ia.core.view.client;

import java.io.Serializable;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ia.core.service.dto.DTO;

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
  void delete(@PathVariable("id") Long id);

}
