package com.ia.core.llm.service.model.ferramenta;

import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * DTO para resultado de execução de ferramenta OWL 2 DL.
 * <p>
 * Contém os axiomas gerados, status de consistência e informações sobre o processo.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoFerramentaDTO implements DTO<Serializable> {

    /** Serial UID */
    private static final long serialVersionUID = -274920123456789123L;

    /**
     * Axiomas gerados pela ferramenta.
     */
    @Builder.Default
    private List<AxiomaDTO> axiomas = new ArrayList<>();

    /**
     * Indica se os axiomas são consistentes com a ontologia.
     */
    private boolean consistente;

    /**
     * Explicação do resultado em linguagem natural.
     */
    private String explicacao;

    /**
     * Número de iterações usadas (se houve loop LLM-Reasoner).
     */
    private int iteracoesUsadas;

    /**
     * Tempo de processamento em milissegundos.
     */
    private long tempoProcessamentoMs;

    /**
     * Construtor OWL utilizado.
     */
    private String construtorUtilizado;

    /**
     * Erro ocorrido (se houve falha).
     */
    private String erro;

    /**
     * Cria uma cópia superficial (clone) deste DTO.
     *
     * @return nova instância com os mesmos valores
     */
    @Override
    public ResultadoFerramentaDTO cloneObject() {
        return toBuilder()
            .axiomas(new ArrayList<>(axiomas))
            .build();
    }

    /**
     * Retorna uma representação em string deste objeto.
     *
     * @return string contendo o status de consistência
     */
    @Override
    public String toString() {
        return String.format("ResultadoFerramentaDTO{consistente=%s}", consistente);
    }

    /**
     * Constantes dos campos do DTO para uso type-safe.
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS {
        public static final String AXIOMAS = "axiomas";
        public static final String CONSISTENTE = "consistente";
        public static final String EXPLICACAO = "explicacao";
        public static final String ITERACOES_USADAS = "iteracoesUsadas";
        public static final String TEMPO_PROCESSAMENTO_MS = "tempoProcessamentoMs";
        public static final String CONSTRUTOR_UTILIZADO = "construtorUtilizado";
        public static final String ERRO = "erro";

        public static Set<String> values() {
            return Collections.unmodifiableSet(Set.of(AXIOMAS, CONSISTENTE, EXPLICACAO, ITERACOES_USADAS,
                TEMPO_PROCESSAMENTO_MS, CONSTRUTOR_UTILIZADO, ERRO));
        }
    }
}