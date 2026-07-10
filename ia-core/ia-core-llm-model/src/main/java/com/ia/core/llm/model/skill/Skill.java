package com.ia.core.llm.model.skill;

import com.ia.core.llm.model.LLMModel;
import com.ia.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * Entidade que representa uma Skill (habilidade especializada).
 * <p>
 * Skills são implementações com métodos @Tool que podem ser fornecidas ao ChatModel.
 * Definem habilidades especializadas que um agente pode ter.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see SkillTipo
 */
@Entity
@Table(name = Skill.TABLE_NAME, schema = Skill.SCHEMA_NAME)
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = false)
public class Skill extends BaseEntity {

    /** Serial UID */
    private static final long serialVersionUID = 1234567890123456789L;

    /** NOME DA TABELA */
    public static final String TABLE_NAME = LLMModel.TABLE_PREFIX + "SKILL";

    /** NOME DO SCHEMA */
    public static final String SCHEMA_NAME = LLMModel.SCHEMA;

    /**
     * Identificador único da skill (ex: ONTOLOGY_BUILDER, KNOWLEDGE_EXTRACTION).
     */
    @Column(name = "identificador", unique = true, nullable = false, length = 100)
    private String identificador;

    /**
     * Nome apresentável da skill na UI.
     */
    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    /**
     * Descrição do propósito da skill.
     */
    @Column(name = "descricao", length = 1000)
    private String descricao;

    /**
     * Tipo da skill (ex: ONTOLOGY_BUILDER, KNOWLEDGE_EXTRACTION, REASONING, OUTRA).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 50)
    private SkillTipo tipo;

    /**
     * Indica se a skill está disponível para uso.
     */
    @Default
    @Column(name = "ativo")
    private Boolean ativo = true;

    /**
     * Módulo ou pacote fonte.
     * Usado para identificar a origem da skill.
     */
    @Column(name = "modulo_origem", length = 200)
    private String moduloOrigem;

    /**
     * Gera ID se ausente antes de persistir.
     */
    @PrePersist
    protected void onCreate() {
        super.generateIdIfAbsent();
    }
}
