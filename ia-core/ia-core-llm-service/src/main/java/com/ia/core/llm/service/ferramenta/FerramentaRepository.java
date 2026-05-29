package com.ia.core.llm.service.ferramenta;

import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.service.repository.BaseEntityRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para acesso a dados de Ferramenta.
 * <p>
 * Fornece métodos específicos para buscar e manipular ferramentas no banco de dados.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public interface FerramentaRepository
  extends BaseEntityRepository<Ferramenta> {

  Optional<Ferramenta> findByIdentificador(String identificador);

  List<Ferramenta> findByAtivoTrue();
}
