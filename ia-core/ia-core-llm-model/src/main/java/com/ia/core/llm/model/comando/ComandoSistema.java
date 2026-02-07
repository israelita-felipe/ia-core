package com.ia.core.llm.model.comando;

import com.ia.core.llm.model.LLMModel;
import com.ia.core.llm.model.template.Template;
import com.ia.core.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Classe que representa um comando de sistema
 *
 * @author Israel Araújo
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

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ComandoDeSistema [");
    if (finalidade != null) {
      builder.append("finalidade=");
      builder.append(finalidade);
      builder.append(", ");
    }
    if (titulo != null) {
      builder.append("titulo=");
      builder.append(titulo);
      builder.append(", ");
    }
    if (comando != null) {
      builder.append("comando=");
      builder.append(comando);
      builder.append(", ");
    }
    if (template != null) {
      builder.append("template=");
      builder.append(template);
    }
    builder.append("]");
    return builder.toString();
  }

}
