package com.ia.core.llm.model.ontologia;

import com.ia.core.llm.model.LLMModel;
import com.ia.core.llm.model.ontologia.converters.OntologyFormatConverter;
import com.ia.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Entidade para representar uma ontologia OWL.
 * <p>
 * Armazena metadados e conteúdo de ontologias OWL utilizadas em conversações
 * com agentes guiados por ontologias.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Entity
@Table(name = "ONTOLOGIA", schema = LLMModel.SCHEMA)
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Ontologia extends BaseEntity {

  private static final long serialVersionUID = 1L;

  /**
   * IRI (Internationalized Resource Identifier) da ontologia.
   */
  @Column(name = "iri", length = 500, nullable = false)
  private String iri;

  /**
   * Nome da ontologia.
   */
  @Column(name = "nome", length = 200, nullable = false)
  private String nome;

  /**
   * Descrição da ontologia.
   */
  @Column(name = "descricao", length = 2000)
  private String descricao;

  /**
   * Versão da ontologia.
   */
  @Column(name = "versao", length = 50)
  private String versao;

  /**
   * Prefixo padrão para classes e propriedades.
   */
  @Column(name = "prefixo", length = 100)
  private String prefixo;

  /**
   * Namespace da ontologia.
   */
  @Column(name = "namespace", length = 500)
  private String namespace;

  /**
   * Formato da ontologia (OWL, RDF, Turtle, Manchester).
   */
  @Column(name = "formato", length = 20)
  @Convert(converter = OntologyFormatConverter.class)
  private OntologyFormat formato;

  /**
   * Conteúdo da ontologia em formato serializado.
   */
  @Lob
  @Column(name = "conteudo")
  private String conteudo;

  /**
   * Indica se a ontologia está consistente (validada pelo reasoner).
   */
  @Column(name = "consistente")
  @Default
  private boolean consistente = true;

  /**
   * Data e hora da última modificação.
   */
  @Column(name = "ultima_modificacao")
  private LocalDateTime ultimaModificacao;

  /**
   * Data e hora da criação.
   */
  @Column(name = "data_criacao")
  private LocalDateTime dataCriacao;
}
