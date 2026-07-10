package com.ia.core.llm.service.model.agente;

import com.ia.core.service.dto.DTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * DTO para requisição de construção de ontologia pelo agente construtor.
 * <p>
 * Contém o corpus de texto e configurações para a construção autônoma.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see AgenteConstrutorTranslator
 */
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RequisicaoConstrucaoOntologiaDTO implements DTO<String> {

    /** Serial UID */
    private static final long serialVersionUID = -574920123456789123L;

    /**
     * Domínio da ontologia (ex: biblioteca, biologia, medicina).
     */
    @NotBlank(message = AgenteConstrutorTranslator.VALIDATION.DOMAIN_REQUIRED)
    @Size(max = 200, message = AgenteConstrutorTranslator.VALIDATION.DOMAIN_SIZE)
    private String domain;

    /**
     * Corpus de texto em linguagem natural para extração.
     */
    @NotBlank(message = AgenteConstrutorTranslator.VALIDATION.CORPUS_REQUIRED)
    private String corpus;

    /**
     * IRI desejada para a ontologia.
     */
    @Size(max = 500, message = AgenteConstrutorTranslator.VALIDATION.TARGET_IRI_SIZE)
    private String targetIri;

    /**
     * Nome desejado para a ontologia.
     */
    @Size(max = 200, message = AgenteConstrutorTranslator.VALIDATION.TARGET_NAME_SIZE)
    private String targetName;

    /**
     * Construtores OWL 2 DL desejados (ex: SubClassOf, ObjectPropertyDomain).
     */
    @Builder.Default
    private List<String> desiredConstructors = new ArrayList<>();

    /**
     * Número máximo de iterações de refinamento.
     */
    @NotNull(message = AgenteConstrutorTranslator.VALIDATION.MAX_ITERATIONS_REQUIRED)
    private Integer maxIterations;

    /**
     * Nível de detalhe desejado (BASIC, INTERMEDIATE, ADVANCED).
     */
    @Size(max = 20, message = AgenteConstrutorTranslator.VALIDATION.DETAIL_LEVEL_SIZE)
    private String detailLevel;

    /**
     * Indica se deve usar todos os construtores OWL 2 DL.
     */
    @Builder.Default
    private boolean useAllConstructors = false;

    /**
     * Idioma da ontologia (pt-BR, en-US, etc).
     */
    @Size(max = 10, message = AgenteConstrutorTranslator.VALIDATION.LANGUAGE_SIZE)
    private String language;

    /**
     * Cria uma cópia superficial (clone) deste DTO.
     *
     * @return nova instância com os mesmos valores
     */
    @Override
    public RequisicaoConstrucaoOntologiaDTO cloneObject() {
        return toBuilder()
            .desiredConstructors(new ArrayList<>(desiredConstructors))
            .build();
    }

    /**
     * Retorna uma representação em string deste objeto.
     *
     * @return string contendo o domínio
     */
    @Override
    public String toString() {
        return String.format("RequisicaoConstrucaoOntologiaDTO{domain=%s}", domain);
    }

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

        public static Set<String> values() {
            return Collections.unmodifiableSet(Set.of(
                DOMAIN, CORPUS, TARGET_IRI, TARGET_NAME, DESIRED_CONSTRUCTORS,
                MAX_ITERATIONS, DETAIL_LEVEL, USE_ALL_CONSTRUCTORS, LANGUAGE
            ));
        }
    }
}