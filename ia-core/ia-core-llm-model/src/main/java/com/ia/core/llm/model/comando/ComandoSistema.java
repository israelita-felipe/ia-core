package com.ia.core.llm.model.comando;

import com.ia.core.llm.model.LLMModel;
import com.ia.core.llm.model.template.Template;
import com.ia.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

/**
 * Classe que representa a entidade de domínio comando sistema.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a ComandoSistema
 * dentro do sistema.
 *
 * @author Israel Araújo
 * @since 1.0
 */
@Entity
@Table(name = ComandoSistema.TABLE_NAME,
       schema = ComandoSistema.SCHEMA_NAME)
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = false)
public class ComandoSistema
  extends BaseEntity {
  /** Serial UID */
  private static final long serialVersionUID = 5644976387280082125L;

  /** NOME DA TABELA */
  public static final String TABLE_NAME = LLMModel.TABLE_PREFIX
      + "COMANDO_SISTEMA";

  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = LLMModel.SCHEMA;

  /**
   * Finalidade do comando
   */
  @Default
  @Column(name = "finalidade")
  @Enumerated
  private FinalidadeComandoEnum finalidade = FinalidadeComandoEnum.RESPOSTA_TEXTUAL;

  /**
   * Título do comando
   */
  @Column(name = "titulo", unique = true, nullable = false)
  private String titulo;

  /**
   * Comando a ser executado, expresso em linguagem natural
   */
  @Lob
  @Column(name = "comando")
  private String comando;

  /**
   * Template para executar o comando
   */
  @ManyToOne(targetEntity = Template.class)
  @JoinColumn(name = "template", referencedColumnName = "id")
  private Template template;

}
