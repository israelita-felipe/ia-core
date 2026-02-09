package com.ia.core.llm.service.template;

import com.ia.core.llm.model.template.Template;
import com.ia.core.service.repository.BaseEntityRepository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository para entidade Template.
 * Implementa EntityGraph para evitar N+1 queries.
 * 
 * @author Israel Araújo
 */
public interface TemplateRepository
  extends BaseEntityRepository<Template> {

  /**
   * Busca template por ID com parametros carregados.
   * Evita N+1 query ao carregar relacionamentos.
   */
  @EntityGraph("Template.withParameters")
  Optional<Template> findByIdWithParametros(Long id);

  /**
   * Busca template por título com parametros carregados.
   */
  @EntityGraph("Template.withParameters")
  Optional<Template> findByTitulo(String titulo);

  /**
   * Lista todos os templates com parametros carregados.
   */
  @EntityGraph("Template.withParameters")
  List<Template> findAllWithParametros();

}
