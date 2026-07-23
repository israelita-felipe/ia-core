package com.ia.core.model.configuracao;

import com.ia.core.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entidade que armazena configurações do sistema.
 * <p>
 * Cada configuração possui uma chave única, valor, módulo e categoria para
 * organização. Permite configurações dinâmicas por módulo sem necessidade de redeploy.
 * <p>
 * Classes que implementam configurações específicas devem estender esta classe
 * e adicionar as anotações @Entity e @Table específicas para definir o nome
 * e esquema da tabela.
 *
 * @author Israel Araújo
 * @see TipoConfiguracao
 * @since 1.0.0
 */
@SuppressWarnings("serial")
@MappedSuperclass
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public abstract class ConfiguracaoSistema extends BaseEntity {

    /**
     * Chave única da configuração.
     */
    @Column(nullable = false, unique = true)
    private String chave;

    /**
     * Valor da configuração.
     */
    @Lob
    @Column(nullable = false)
    private String valor;

    /**
     * Módulo ao qual a configuração pertence.
     */
    @Column(nullable = false)
    private String modulo;

    /**
     * Categoria para agrupamento na UI (ex: "Geral", "Segurança", "Notificações").
     */
    @Column(nullable = false)
    private String categoria;

    /**
     * Descrição da configuração.
     */
    @Column
    private String descricao;

    /**
     * Tipo de dado da configuração.
     * <p>
     * Define como o valor deve ser interpretado e validado.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoConfiguracao tipo;
}
