package com.ia.core.llm.service.model.session;

import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO para confirmação de ação de agente.
 * <p>
 * Utilizado para confirmar ou cancelar ações que requerem aprovação do usuário.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see SessionTranslator
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AgentConfirmationDTO implements DTO<Serializable> {

    /** Serial UID */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador da sessão.
     */
    private String sessionId;

    /**
     * Indica se a ação foi confirmada.
     */
    private boolean confirmed;

    /**
     * Mensagem adicional do usuário.
     */
    private String userMessage;

    /**
     * Cria uma cópia superficial (clone) deste DTO.
     *
     * @return nova instância com os mesmos valores
     */
    @Override
    public AgentConfirmationDTO cloneObject() {
        return toBuilder().build();
    }

    /**
     * Retorna uma representação em string deste objeto.
     *
     * @return string contendo o sessionId
     */
    @Override
    public String toString() {
        return String.format("Confirmation[sessionId=%s]", sessionId);
    }

    /**
     * Constantes para nomes dos campos deste DTO.
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS {
        /** Identificador da sessão */
        public static final String SESSION_ID = "sessionId";

        /** Flag de confirmação */
        public static final String CONFIRMED = "confirmed";

        /** Mensagem do usuário */
        public static final String USER_MESSAGE = "userMessage";

        /**
         * Retorna todos os nomes de campos deste DTO.
         *
         * @return conjunto com os nomes dos campos
         */
        public static Set<String> values() {
            return Set.of(SESSION_ID, CONFIRMED, USER_MESSAGE);
        }
    }
}
