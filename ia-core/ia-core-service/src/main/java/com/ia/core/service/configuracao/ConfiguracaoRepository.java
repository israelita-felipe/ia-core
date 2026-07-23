package com.ia.core.service.configuracao;

import com.ia.core.model.configuracao.ConfiguracaoSistema;
import com.ia.core.service.repository.BaseEntityRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Repositório genericamente tipado para ConfiguracaoSistema.
 * <p>
 * Fornece métodos de acesso a dados para configurações do sistema.
 *
 * @param <T> tipo da entidade de configuração
 * @author Israel Araújo
 * @since 1.0.0
 */
@NoRepositoryBean
public interface ConfiguracaoRepository<T extends ConfiguracaoSistema> extends BaseEntityRepository<T> {

    /**
     * Busca todas as configurações de um módulo específico.
     *
     * @param modulo nome do módulo
     * @return lista de configurações do módulo
     */
    List<T> findByModulo(String modulo);

    /**
     * Busca todos os módulos distintos que possuem configurações.
     *
     * @return lista de nomes de módulos únicos
     */
    List<String> findAllModulos();

    List<T> findByChave(String chave);
}
