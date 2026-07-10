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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO para representar uma ontologia OWL.
 * <p>
 * Usado para transferência de dados entre camadas no contexto de agentes guiados por ontologias.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see Ontologia
 * @see OntologiaTranslator
 */
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class OntologiaDTO extends AbstractBaseEntityDTO<Ontologia> {

    /** Serial UID */
    private static final long serialVersionUID = -728374920123456789L;

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
    @Size(max = 1000, message = OntologiaTranslator.VALIDATION.DESCRICAO_SIZE)
    private String descricao;

    /**
     * Versão da ontologia.
     */
    @Size(max = 50, message = OntologiaTranslator.VALIDATION.VERSAO_SIZE)
    private String versao;

    /**
     * Prefixo padrão para classes e propriedades.
     */
    @Size(max = 100, message = OntologiaTranslator.VALIDATION.PREFIXO_SIZE)
    private String prefixo;

    /**
     * Namespace da ontologia.
     */
    @Size(max = 500, message = OntologiaTranslator.VALIDATION.NAMESPACE_SIZE)
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

    /**
     * Cria uma cópia superficial (clone) deste DTO.
     *
     * @return nova instância com os mesmos valores
     */
    @Override
    public OntologiaDTO cloneObject() {
        return toBuilder().build();
    }

    /**
     * Retorna uma representação em string deste objeto.
     *
     * @return string contendo o nome e IRI
     */
    @Override
    public String toString() {
        return String.format("OntologiaDTO{nome=%s, iri=%s}", nome, iri);
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

        public static Set<String> values() {
            var baseValues = AbstractBaseEntityDTO.CAMPOS.values();
            var currentValues = Set.of(IRI, NOME, DESCRICAO, VERSAO, PREFIXO, NAMESPACE, FORMATO,
                CONTEUDO, CONSISTENTE, ULTIMA_MODIFICACAO, DATA_CRIACAO, ESTATISTICAS);
            var allValues = new HashSet<String>();
            allValues.addAll(baseValues);
            allValues.addAll(currentValues);
            return Collections.unmodifiableSet(allValues);
        }
    }
}