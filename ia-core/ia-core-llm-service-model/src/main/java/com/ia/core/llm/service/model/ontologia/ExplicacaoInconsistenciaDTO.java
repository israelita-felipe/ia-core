package com.ia.core.llm.service.model.ontologia;

import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * DTO para explicação de inconsistência em linguagem natural.
 * <p>
 * Converte mensagens técnicas do reasoner em explicações compreensíveis para usuários.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ExplicacaoInconsistenciaDTO implements DTO<String> {

    /** Serial UID */
    private static final long serialVersionUID = 374920123456789123L;

    /**
     * Mensagem técnica original do reasoner.
     */
    private String mensagemTecnica;

    /**
     * Explicação em linguagem natural (português).
     */
    private String explicacaoNatural;

    /**
     * Lista de axiomas que causam a inconsistência.
     */
    @Builder.Default
    private List<String> axiomasCausadores = new ArrayList<>();

    /**
     * Tipo de inconsistência (ex: classe insatisfatível, cardinalidade conflitante).
     */
    private String tipoInconsistencia;

    /**
     * Sugestões de correção.
     */
    @Builder.Default
    private List<String> sugestoesCorrecao = new ArrayList<>();

    /**
     * Gravidade da inconsistência (ERROR, WARNING, INFO).
     */
    private String gravidade;

    /**
     * Cria uma cópia superficial (clone) deste DTO.
     *
     * @return nova instância com os mesmos valores
     */
    @Override
    public ExplicacaoInconsistenciaDTO cloneObject() {
        return toBuilder()
            .axiomasCausadores(new ArrayList<>(axiomasCausadores))
            .sugestoesCorrecao(new ArrayList<>(sugestoesCorrecao))
            .build();
    }

    /**
     * Retorna uma representação em string deste objeto.
     *
     * @return string contendo o tipo de inconsistência
     */
    @Override
    public String toString() {
        return String.format("ExplicacaoInconsistenciaDTO{tipoInconsistencia=%s}", tipoInconsistencia);
    }

    /**
     * Constantes dos campos do DTO para uso type-safe.
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS {
        public static final String MENSAGEM_TECNICA = "mensagemTecnica";
        public static final String EXPLICACAO_NATURAL = "explicacaoNatural";
        public static final String AXIOMAS_CAUSADORES = "axiomasCausadores";
        public static final String TIPO_INCONSISTENCIA = "tipoInconsistencia";
        public static final String SUGESTOES_CORRECAO = "sugestoesCorrecao";
        public static final String GRAVIDADE = "gravidade";

        public static Set<String> values() {
            return Collections.unmodifiableSet(Set.of(MENSAGEM_TECNICA, EXPLICACAO_NATURAL, AXIOMAS_CAUSADORES,
                TIPO_INCONSISTENCIA, SUGESTOES_CORRECAO, GRAVIDADE));
        }
    }
}