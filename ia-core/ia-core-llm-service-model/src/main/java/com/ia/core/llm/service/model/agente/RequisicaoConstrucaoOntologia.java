package com.ia.core.llm.service.model.agente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO para requisição de construção de ontologia pelo agente construtor.
 * <p>
 * Contém o corpus de texto e configurações para a construção autônoma.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequisicaoConstrucaoOntologia {

  /**
   * Domínio da ontologia (ex: biblioteca, biologia, medicina).
   */
  @NotBlank(message = "O domínio é obrigatório")
  @Size(max = 200)
  private String domain;

  /**
   * Corpus de texto em linguagem natural para extração.
   */
  @NotBlank(message = "O corpus é obrigatório")
  private String corpus;

  /**
   * IRI desejada para a ontologia.
   */
  @Size(max = 500)
  private String targetIri;

  /**
   * Nome desejado para a ontologia.
   */
  @Size(max = 200)
  private String targetName;

  /**
   * Construtores OWL 2 DL desejados (ex: SubClassOf, ObjectPropertyDomain).
   */
  @Builder.Default
  private List<String> desiredConstructors = new ArrayList<>();

  /**
   * Número máximo de iterações de refinamento.
   */
  @NotNull
  private Integer maxIterations;

  /**
   * Nível de detalhe desejado (BASIC, INTERMEDIATE, ADVANCED).
   */
  @Size(max = 20)
  private String detailLevel;

  /**
   * Indica se deve usar todos os construtores OWL 2 DL.
   */
  @Builder.Default
  private boolean useAllConstructors = false;

  /**
   * Idioma da ontologia (pt-BR, en-US, etc).
   */
  @Size(max = 10)
  private String language;

  /**
   * Constantes dos campos do DTO para uso type-safe.
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS {
    public static final String DOMAIN = "domain";
    public static final String CORPUS = "corpus";
    public static final String TARGET_IRI = "targetIri";
    public static final String TARGET_NAME = "targetName";
    public static final String DESIRED_CONSTRUCTORS = "desiredConstructors";
    public static final String MAX_ITERATIONS = "maxIterations";
    public static final String DETAIL_LEVEL = "detailLevel";
    public static final String USE_ALL_CONSTRUCTORS = "useAllConstructors";
    public static final String LANGUAGE = "language";

    public static java.util.Set<String> values() {
      return java.util.Set.of(
          DOMAIN, CORPUS, TARGET_IRI, TARGET_NAME, DESIRED_CONSTRUCTORS,
          MAX_ITERATIONS, DETAIL_LEVEL, USE_ALL_CONSTRUCTORS, LANGUAGE
      );
    }
  }
}
