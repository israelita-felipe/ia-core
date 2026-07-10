package com.ia.core.llm.service.model.ferramenta;

import com.ia.core.service.dto.DTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * DTO para requisição de execução de ferramenta OWL 2 DL.
 * <p>
 * Contém a descrição em linguagem natural e contexto ontológico.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RequisicaoFerramentaDTO implements DTO<Serializable> {

    /** Serial UID */
    private static final long serialVersionUID = -374920123456789123L;

    /**
     * Descrição em linguagem natural para converter em axioma OWL.
     */
    @NotBlank(message = FerramentaOWLTranslator.VALIDATION.DESCRICAO_REQUIRED)
    private String descricaoNatureza;

    /**
     * Contexto ontológico atual (ontologia em Manchester Syntax).
     */
    private String contextoOntologia;

    /**
     * Construtor OWL específico a usar (opcional, se não especificado o agente decide).
     */
    @Size(max = 50, message = FerramentaOWLTranslator.VALIDATION.CONSTRUTOR_SIZE)
    private String construtor;

    /**
     * Parâmetros adicionais específicos da ferramenta.
     */
    private Map<String, Object> parametrosAdicionais;

    /**
     * Cria uma cópia superficial (clone) deste DTO.
     *
     * @return nova instância com os mesmos valores
     */
    @Override
    public RequisicaoFerramentaDTO cloneObject() {
        return toBuilder().build();
    }

    /**
     * Retorna uma representação em string deste objeto.
     *
     * @return string contendo a descrição
     */
    @Override
    public String toString() {
        return String.format("RequisicaoFerramentaDTO{descricaoNatureza=%s}", descricaoNatureza);
    }

    /**
     * Constantes dos campos do DTO para uso type-safe.
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS {
        public static final String DESCRICAO_NATUREZA = "descricaoNatureza";
        public static final String CONTEXTO_ONTOLOGIA = "contextoOntologia";
        public static final String CONSTRUTOR = "construtor";
        public static final String PARAMETROS_ADICIONAIS = "parametrosAdicionais";

        public static Set<String> values() {
            return Collections.unmodifiableSet(Set.of(
                DESCRICAO_NATUREZA, CONTEXTO_ONTOLOGIA, CONSTRUTOR, PARAMETROS_ADICIONAIS
            ));
        }
    }
}