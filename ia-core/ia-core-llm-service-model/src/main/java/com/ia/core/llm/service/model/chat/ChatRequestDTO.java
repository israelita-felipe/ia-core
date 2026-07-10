package com.ia.core.llm.service.model.chat;

import com.ia.core.service.dto.DTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.Set;

/**
 * DTO para requisição de chat.
 * <p>
 * Contém o prompt opcional, texto da requisição e validações.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see ChatRequestTranslator
 */
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequestDTO implements DTO<String> {

    /** Serial UID */
    private static final long serialVersionUID = 474920123456789123L;

    private Long promptId;

    @NotNull(message = ChatTranslator.VALIDATION.REQUEST_REQUIRED)
    @Size(min = 1, max = 5000, message = ChatTranslator.VALIDATION.REQUEST_SIZE)
    private String request;

    private String text;

    private String sessionId;

    /**
     * Cria uma cópia superficial (clone) deste DTO.
     *
     * @return nova instância com os mesmos valores
     */
    @Override
    public ChatRequestDTO cloneObject() {
        return toBuilder().build();
    }

    /**
     * Retorna uma representação em string deste objeto.
     *
     * @return string contendo o request
     */
    @Override
    public String toString() {
        return String.format("ChatRequestDTO{request=%s}", request);
    }

    /**
     * Constantes dos campos do DTO para uso type-safe.
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS {
        public static final String PROMPT_ID = "promptId";
        public static final String REQUEST = "request";
        public static final String TEXT = "text";
        public static final String SESSION_ID = "sessionId";

        public static Set<String> values() {
            return Collections.unmodifiableSet(Set.of(PROMPT_ID, REQUEST, TEXT, SESSION_ID));
        }
    }
}