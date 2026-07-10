package com.ia.core.llm.service.model.agente;

import com.ia.core.llm.model.agente.ContextoConversacao;
import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

/**
 * DTO para contexto de conversação com agente guiado por ontologia.
 * <p>
 * Mantém o estado da conversa, ontologia incremental e histórico de interações.
 *
 * @author Israel Araújo
 * @see ContextoConversacao
 * @see AgenteConversacionalTranslator
 * @since 1.0.0
 */
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ContextConversacaoDTO extends AbstractBaseEntityDTO<ContextoConversacao> {

    /**
     * Serial UID
     */
    private static final long serialVersionUID = 374920123456789123L;

    /**
     * ID único da sessão de conversação.
     */
    private String sessionId;

    /**
     * ID do usuário.
     */
    private String userId;

    /**
     * Domínio da conversação (ex: biologia, biblioteca, medicina).
     */
    private String dominio;

    /**
     * Ontologia construída incrementalmente durante a conversa.
     */
    private OntologiaDTO ontologia;

    /**
     * Data e hora de início da sessão.
     */
    private LocalDateTime dataInicio;

    /**
     * Data e hora da última atividade.
     */
    private LocalDateTime ultimaAtividade;

    /**
     * Número total de axiomas extraídos até o momento.
     */
    private int totalAxiomasExtraidos;

    /**
     * Indica se a ontologia atual está consistente.
     */
    private boolean ontologiaConsistente;

    /**
     * Número de inconsistências corrigidas durante a conversa.
     */
    private int inconsistenciasCorrigidas;

    /**
     * Cria uma cópia superficial (clone) deste DTO.
     *
     * @return nova instância com os mesmos valores
     */
    @Override
    public ContextConversacaoDTO cloneObject() {
        return toBuilder().build();
    }

    /**
     * Retorna uma representação em string deste objeto.
     *
     * @return string contendo sessionId e domínio
     */
    @Override
    public String toString() {
        return String.format("ContextConversacaoDTO{sessionId=%s, dominio=%s}", sessionId, dominio);
    }

    /**
     * Constantes dos campos do DTO para uso type-safe.
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS {
        public static final String SESSION_ID = "sessionId";
        public static final String USER_ID = "userId";
        public static final String DOMINIO = "dominio";
        public static final String ONTOLOGIA = "ontologia";
        public static final String DATA_INICIO = "dataInicio";
        public static final String ULTIMA_ATIVIDADE = "ultimaAtividade";
        public static final String TOTAL_AXIOMAS_EXTRAIDOS = "totalAxiomasExtraidos";
        public static final String ONTOLOGIA_CONSISTENTE = "ontologiaConsistente";
        public static final String INCONSISTENCIAS_CORRIGIDAS = "inconsistenciasCorrigidas";

        public static Set<String> values() {
            return Collections.unmodifiableSet(Set.of(SESSION_ID, USER_ID, DOMINIO, ONTOLOGIA,
                DATA_INICIO, ULTIMA_ATIVIDADE, TOTAL_AXIOMAS_EXTRAIDOS,
                ONTOLOGIA_CONSISTENTE, INCONSISTENCIAS_CORRIGIDAS));
        }
    }
}
