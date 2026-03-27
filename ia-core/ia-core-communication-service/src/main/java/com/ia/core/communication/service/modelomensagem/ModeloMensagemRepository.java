package com.ia.core.communication.service.modelomensagem;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.ia.core.communication.model.ModeloMensagem;
import com.ia.core.communication.model.TipoCanal;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Repository para ModeloMensagem.
 * <p>
 * Define as operações de acesso a dados para a entidade ModeloMensagem,
 * utilizando JPQL para consultas específicas.
 *
 * @author Israel Araújo
 */
@NoRepositoryBean
public interface ModeloMensagemRepository
  extends BaseEntityRepository<ModeloMensagem> {
  /**
   * Busca modelos de mensagem que estão ativos.
   *
   * @return lista de modelos ativos
   */
  @Query("SELECT m FROM ModeloMensagem m WHERE m.ativo = true")
  List<ModeloMensagem> findByAtivoTrue();

  /**
   * Busca modelos de mensagem por tipo de canal.
   *
   * @param tipoCanal tipo do canal de comunicação
   * @return lista de modelos para o canal especificado
   */
  @Query("SELECT m FROM ModeloMensagem m WHERE m.tipoCanal = :tipoCanal")
  List<ModeloMensagem> findByTipoCanal(@Param("tipoCanal") TipoCanal tipoCanal);
}
