package com.ia.core.communication.service.mensagem;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.ia.core.communication.model.Mensagem;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Repository para Mensagem.
 * <p>
 * Define as operações de acesso a dados para a entidade Mensagem, utilizando
 * JPQL para consultas específicas.
 *
 * @author Israel Araújo
 */
@NoRepositoryBean
public interface MensagemRepository
  extends BaseEntityRepository<Mensagem> {
  /**
   * Busca uma mensagem pelo ID externo do WhatsApp.
   *
   * @param idExterno ID externo da mensagem
   * @return Optional contendo a mensagem se encontrada
   */
  @Query("SELECT m FROM Mensagem m WHERE m.idExterno = :idExterno")
  Optional<Mensagem> findByIdExterno(@Param("idExterno") String idExterno);
}
