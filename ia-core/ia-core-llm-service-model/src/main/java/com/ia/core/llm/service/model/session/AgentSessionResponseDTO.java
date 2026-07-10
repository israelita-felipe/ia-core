package com.ia.core.llm.service.model.session;

import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO para resposta de sessão de agente.
 * <p>
 * Contém o identificador da sessão, mensagem e flag de confirmação pendente.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see SessionTranslator
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AgentSessionResponseDTO implements DTO<Serializable> {

    /** Serial UID */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador da sessão.
     */
    private String sessionId;

    /**
     * Mensagem de resposta.
     */
    private String message;

    /**
     * Indica se há confirmação pendente.
     */
    private boolean pendingConfirmation;

    /**
     * Cria uma cópia superficial (clone) deste DTO.
     *
     * @return nova instância com os mesmos valores
     */
    @Override
    public AgentSessionResponseDTO cloneObject() {
        return toBuilder().build();
    }

    /**
     * Retorna uma representação em string deste objeto.
     *
     * @return string contendo o sessionId
     */
    @Override
    public String toString() {
        return String.format("SessionResponse[sessionId=%s]", sessionId);
    }

    /**
     * Constantes para nomes dos campos deste DTO.
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS {
        /** Identificador da sessão */
        public static final String SESSION_ID = "sessionId";

        /** Mensagem de resposta */
        public static final String MESSAGE = "message";

        /** Flag de confirmação pendente */
        public static final String PENDING_CONFIRMATION = "pendingConfirmation";

        /**
         * Retorna todos os nomes de campos deste DTO.
         *
         * @return conjunto com os nomes dos campos
         */
        public static Set<String> values() {
            return Set.of(SESSION_ID, MESSAGE, PENDING_CONFIRMATION);
        }
    }
}
