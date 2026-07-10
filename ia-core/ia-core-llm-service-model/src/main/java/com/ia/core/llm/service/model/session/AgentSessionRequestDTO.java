package com.ia.core.llm.service.model.session;

import com.ia.core.service.dto.DTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO para requisição de sessão de agente.
 * <p>
 * Contém a mensagem do usuário, ferramenta opcional e identificador de sessão.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see SessionTranslator
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AgentSessionRequestDTO implements DTO<Serializable> {

    /** Serial UID */
    private static final long serialVersionUID = 1L;

    /**
     * Mensagem do usuário.
     */
    @NotBlank(message = SessionTranslator.VALIDATION.USER_MESSAGE_NOT_BLANK)
    private String userMessage;

    /**
     * Identificador da ferramenta (opcional).
     */
    private Long ferramentaId;

    /**
     * Identificador da sessão.
     */
    private String sessionId;

    /**
     * Cria uma cópia superficial (clone) deste DTO.
     *
     * @return nova instância com os mesmos valores
     */
    @Override
    public AgentSessionRequestDTO cloneObject() {
        return toBuilder().build();
    }

    /**
     * Retorna uma representação em string deste objeto.
     *
     * @return string contendo o sessionId e userMessage
     */
    @Override
    public String toString() {
        return String.format("SessionRequest[sessionId=%s, message=%s]", sessionId, userMessage);
    }

    /**
     * Constantes para nomes dos campos deste DTO.
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS {
        /** Mensagem do usuário */
        public static final String USER_MESSAGE = "userMessage";

        /** Identificador da ferramenta */
        public static final String FERRAMENTA_ID = "ferramentaId";

        /** Identificador da sessão */
        public static final String SESSION_ID = "sessionId";

        /**
         * Retorna todos os nomes de campos deste DTO.
         *
         * @return conjunto com os nomes dos campos
         */
        public static Set<String> values() {
            return Set.of(USER_MESSAGE, FERRAMENTA_ID, SESSION_ID);
        }
    }
}
