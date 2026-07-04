package com.ia.core.llm.service.skill;

import com.ia.core.llm.model.skill.Skill;
import com.ia.core.llm.model.skill.SkillTipo;
import com.ia.core.service.repository.BaseEntityRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para acesso a dados de Skill.
 * <p>
 * Fornece métodos específicos para buscar e manipular skills no banco de dados.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface SkillRepository
  extends BaseEntityRepository<Skill> {

  /**
   * Busca uma skill pelo identificador único.
   *
   * @param identificador identificador da skill (ex: ONTOLOGY_BUILDER)
   * @return Optional com a Skill encontrada
   */
  Optional<Skill> findByIdentificador(String identificador);

  /**
   * Lista todas as skills ativas.
   *
   * @return lista de skills ativas
   */
  List<Skill> findByAtivoTrue();

  /**
   * Lista todas as skills de um tipo específico.
   *
   * @param tipo tipo da skill
   * @return lista de skills do tipo especificado
   */
  List<Skill> findByTipo(SkillTipo tipo);
}
