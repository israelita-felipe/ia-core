package com.ia.core.llm.service.model.ontologia;

import com.ia.core.llm.model.ontologia.Ontologia;
import com.ia.core.llm.model.ontologia.OntologyFormat;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * DTO para representar uma ontologia OWL.
 * <p>
 * Usado para transferência de dados entre camadas no contexto de agentes guiados por ontologias.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OntologiaDTO extends AbstractBaseEntityDTO<Ontologia> {

  /**
   * IRI (Internationalized Resource Identifier) da ontologia.
   */
  @NotBlank(message = OntologiaTranslator.VALIDATION.IRI_REQUIRED)
  @Size(max = 500, message = OntologiaTranslator.VALIDATION.IRI_SIZE)
  private String iri;

  /**
   * Nome da ontologia.
   */
  @NotBlank(message = OntologiaTranslator.VALIDATION.NOME_REQUIRED)
  @Size(max = 200, message = OntologiaTranslator.VALIDATION.NOME_SIZE)
  private String nome;

  /**
   * Descrição da ontologia.
   */
  @Size(max = 2000)
  private String descricao;

  /**
   * Versão da ontologia.
   */
  @Size(max = 50)
  private String versao;

  /**
   * Prefixo padrão para classes e propriedades.
   */
  @Size(max = 100)
  private String prefixo;

  /**
   * Namespace da ontologia.
   */
  @Size(max = 500)
  private String namespace;

  /**
   * Formato da ontologia (OWL, RDF, Turtle, Manchester).
   */
  private OntologyFormat formato;

  /**
   * Conteúdo da ontologia em formato serializado.
   */
  @Lob
  private String conteudo;

  /**
   * Indica se a ontologia está consistente (validada pelo reasoner).
   */
  @Default
  private boolean consistente = true;

  /**
   * Data e hora da última modificação.
   */
  private LocalDateTime ultimaModificacao;

  /**
   * Data e hora da criação.
   */
  private LocalDateTime dataCriacao;

  /**
   * Estatísticas da ontologia.
   */
  private EstatisticasOntologiaDTO estatisticas;

  @Override
  public void setVersion(Long version) {
    super.setVersion(version);
  }

  @Override
  public OntologiaDTO cloneObject() {
    log.debug("Clonando OntologiaDTO: iri={}", iri);
    return toBuilder().build();
  }

  /**
   * Constantes dos campos do DTO para uso type-safe.
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS extends AbstractBaseEntityDTO.CAMPOS {
    public static final String IRI = "iri";
    public static final String NOME = "nome";
    public static final String DESCRICAO = "descricao";
    public static final String VERSAO = "versao";
    public static final String PREFIXO = "prefixo";
    public static final String NAMESPACE = "namespace";
    public static final String FORMATO = "formato";
    public static final String CONTEUDO = "conteudo";
    public static final String CONSISTENTE = "consistente";
    public static final String ULTIMA_MODIFICACAO = "ultimaModificacao";
    public static final String DATA_CRIACAO = "dataCriacao";
    public static final String ESTATISTICAS = "estatisticas";
    public static final String PROPERTY_CHANGE_SUPPORT = "propertyChangeSupport";

    public static java.util.Set<String> values() {
      return java.util.Set.of(
          IRI, NOME, DESCRICAO, VERSAO, PREFIXO, NAMESPACE, FORMATO,
          CONTEUDO, CONSISTENTE, ULTIMA_MODIFICACAO, DATA_CRIACAO,
          ESTATISTICAS, PROPERTY_CHANGE_SUPPORT
      );
    }
  }
}
