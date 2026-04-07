package com.ia.core.security.service.privilege;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * @author Israel Araújo
 */
public interface PrivilegeRepository
  extends BaseEntityRepository<Privilege> {

  /**
   * Verifica se existe um privilégio com mesmo nome
   *
   * @param name nome
   * @return <code>true</code> se existir privilégio com mesmo nome.
   */
  @Query("select count(p) > 0 from Privilege p where p.name = :name")
  boolean existsByName(@Param("name") String name);

}
