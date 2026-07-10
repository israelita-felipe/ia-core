package com.ia.core.llm.service.model.ontologia;

import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 * DTO para feedback do raciocinador para o LLM.
 * <p>
 * Usado em loops iterativos LLM-Reasoner para auto-correção de axiomas.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRaciocinadorDTO implements DTO<String> {

    /** Serial UID */
    private static final long serialVersionUID = 274920123456789123L;

    /**
     * Indica se o axioma é válido.
     */
    private boolean axiomaValido;

    /**
     * Erro de sintaxe (se houver).
     */
    private String erroSintaxe;

    /**
     * Erro de consistência (se houver).
     */
    private String erroConsistencia;

    /**
     * Explicação detalhada do problema.
     */
    private String explicacao;

    /**
     * Sugestão de correção em linguagem natural.
     */
    private String sugestaoCorrecao;

    /**
     * Axioma corrigido (sugerido pelo reasoner ou LLM).
     */
    private String axiomaCorrigido;

    /**
     * Número da iteração atual.
     */
    private int iteracaoAtual;

    /**
     * Número máximo de iterações permitidas.
     */
    private int maxIteracoes;

    /**
     * Cria uma cópia superficial (clone) deste DTO.
     *
     * @return nova instância com os mesmos valores
     */
    @Override
    public FeedbackRaciocinadorDTO cloneObject() {
        return toBuilder().build();
    }

    /**
     * Retorna uma representação em string deste objeto.
     *
     * @return string contendo o status de validação
     */
    @Override
    public String toString() {
        return String.format("FeedbackRaciocinadorDTO{axiomaValido=%s}", axiomaValido);
    }

    /**
     * Constantes dos campos do DTO para uso type-safe.
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS {
        public static final String AXIOMA_VALIDO = "axiomaValido";
        public static final String ERRO_SINTAXE = "erroSintaxe";
        public static final String ERRO_CONSISTENCIA = "erroConsistencia";
        public static final String EXPLICACAO = "explicacao";
        public static final String SUGESTAO_CORRECAO = "sugestaoCorrecao";
        public static final String AXIOMA_CORRIGIDO = "axiomaCorrigido";
        public static final String ITERACAO_ATUAL = "iteracaoAtual";
        public static final String MAX_ITERACOES = "maxIteracoes";

        public static Set<String> values() {
            return Collections.unmodifiableSet(Set.of(AXIOMA_VALIDO, ERRO_SINTAXE, ERRO_CONSISTENCIA,
                EXPLICACAO, SUGESTAO_CORRECAO, AXIOMA_CORRIGIDO, ITERACAO_ATUAL, MAX_ITERACOES));
        }
    }
}