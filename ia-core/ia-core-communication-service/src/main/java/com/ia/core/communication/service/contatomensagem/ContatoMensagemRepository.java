package com.ia.core.communication.service.contatomensagem;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.ia.core.communication.model.ContatoMensagem;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Repository para ContatoMensagem.
 * <p>
 * Define as operações de acesso a dados para a entidade ContatoMensagem,
 * utilizando JPQL para consultas específicas.
 *
 * @author Israel Araújo
 */
@NoRepositoryBean
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
