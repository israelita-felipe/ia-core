package com.ia.core.communication.service.grupocontato;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import com.ia.core.communication.model.GrupoContato;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Repository para GrupoContato.
 * <p>
 * Define as operações de acesso a dados para a entidade GrupoContato,
 * utilizando JPQL para consultas específicas.
 *
 * @author Israel Araújo
 */
@NoRepositoryBean
public interface GrupoContatoRepository
  extends BaseEntityRepository<GrupoContato> {
  /**
   * Busca grupos de contato que estão ativos.
   *
   * @return lista de grupos ativos
   */
  @Query("SELECT g FROM GrupoContato g WHERE g.ativo = true")
  java.util.List<GrupoContato> findByAtivoTrue();
}
