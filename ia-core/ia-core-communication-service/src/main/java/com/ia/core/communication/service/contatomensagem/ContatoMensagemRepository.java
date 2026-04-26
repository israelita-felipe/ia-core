package com.ia.core.communication.service.contatomensagem;

import com.ia.core.communication.model.contato.ContatoMensagem;
import com.ia.core.service.repository.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository para ContatoMensagem.
 * <p>
 * Define as operações de acesso a dados para a entidade ContatoMensagem,
 * utilizando JPQL para consultas específicas.
 *
 * @author Israel Araújo
 */
@Repository
public interface ContatoMensagemRepository
  extends BaseEntityRepository<ContatoMensagem> {
  /**
   * Conta a quantidade de contatos por grupo.
   *
   * @param grupoContatoId ID do grupo de contato
   * @return quantidade de contatos no grupo
   */
  @Query("SELECT COUNT(c) FROM ContatoMensagem c WHERE c.grupoContato.id = :grupoContatoId")
  long countByGrupoContatoId(@Param("grupoContatoId") Long grupoContatoId);

  /**
   * Busca um contato pelo número de telefone.
   *
   * @param telefone número de telefone
   * @return Optional contendo o contato se encontrado
   */
  @Query("SELECT c FROM ContatoMensagem c WHERE c.telefone = :telefone")
  Optional<ContatoMensagem> findByTelefone(@Param("telefone") String telefone);
}
