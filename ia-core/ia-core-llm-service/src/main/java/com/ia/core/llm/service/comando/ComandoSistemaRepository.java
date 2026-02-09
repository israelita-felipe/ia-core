package com.ia.core.llm.service.comando;

import com.ia.core.llm.model.comando.ComandoSistema;
import com.ia.core.service.repository.BaseEntityRepository;

import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

/**
 * Repository para entidade ComandoSistema.
 * Implementa EntityGraph para evitar N+1 queries.
 * 
 * @author Israel Araújo
 */
public interface ComandoSistemaRepository
  extends BaseEntityRepository<ComandoSistema> {

  /**
   * Busca comando por ID com template carregado.
   * Evita N+1 query ao carregar relacionamento.
   */
  @EntityGraph("ComandoSistema.withTemplate")
  Optional<ComandoSistema> findByIdWithTemplate(Long id);

  /**
   * Busca comando por título com template carregado.
   */
  @EntityGraph("ComandoSistema.withTemplate")
  Optional<ComandoSistema> findByTitulo(String titulo);

  /**
   * Lista todos os comandos com templates carregados.
   */
  @EntityGraph("ComandoSistema.withTemplate")
  List<ComandoSistema> findAllWithTemplate();

}
