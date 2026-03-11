package com.ia.core.owl.service.model.axioma;

import java.util.List;

import com.ia.core.service.usecase.CrudUseCase;

/**
 * Interface de Use Case para Axioma OWL.
 * <p>
 * Define as operações específicas do domínio de axiomas OWL
 * conforme definido no caso de uso Manter-Axioma.
 *
 * @author Israel Araújo
 */
public interface AxiomaUseCase extends CrudUseCase<AxiomaDTO> {

  /**
   * Busca axiomas por classe.
   *
   * @param classeId ID da classe
   * @return lista de axiomas
   */
  List<AxiomaDTO> findByClasse(String classeId);

  /**
   * Busca axiomas por propriedade.
   *
   * @param propriedadeId ID da propriedade
   * @return lista de axiomas
   */
  List<AxiomaDTO> findByPropriedade(String propriedadeId);

  /**
   * Valida axiomas.
   *
   * @param axiomas lista de axiomas a validar
   * @return lista de axiomas válidos
   */
  List<AxiomaDTO> validar(List<AxiomaDTO> axiomas);
}
