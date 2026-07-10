package com.ia.core.llm.service.model.chat;

import com.ia.core.llm.model.chat.ChatSession;
import com.ia.core.llm.model.chat.ChatSessionStatus;
import com.ia.core.llm.service.model.agente.AgenteDTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import jakarta.validation.constraints.NotNull;
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
 * DTO para sessão de chat.
 * <p>
 * Representa uma sessão de conversação interativa entre usuário e agente LLM.
 *
 * @author Israel Araújo
 * @see ChatSession
 * @see ChatTranslator
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChatSessionDTO
    extends AbstractBaseEntityDTO<ChatSession> {

    /**
     * Serial UID
     */
    private static final long serialVersionUID = 574920123456789123L;
    /**
     * Identificador único da sessão (UUID).
     */
    @NotNull(message = ChatTranslator.VALIDATION.SESSION_ID_REQUIRED)
    @Size(min = 1, max = 100, message = ChatTranslator.VALIDATION.SESSION_ID_SIZE)
    private String sessionId;
    /**
     * Título da sessão.
     */
    @Size(max = 200, message = ChatTranslator.VALIDATION.TITULO_SIZE)
    private String titulo;
    /**
     * Data e hora de início da sessão.
     */
    @NotNull(message = ChatTranslator.VALIDATION.DATA_INICIO_REQUIRED)
    private LocalDateTime dataInicio;
    /**
     * Data e hora de fim da sessão.
     */
    private LocalDateTime dataFim;
    /**
     * Status da sessão.
     */
    @Default
    private ChatSessionStatus status = ChatSessionStatus.ATIVA;
    /**
     * Agente associado à sessão.
     */
    @NotNull(message = ChatTranslator.VALIDATION.AGENTE_REQUIRED)
    private AgenteDTO agente;
    /**
     * ID do usuário que iniciou a sessão.
     */
    @Size(max = 100, message = ChatTranslator.VALIDATION.USUARIO_ID_SIZE)
    private String usuarioId;

    /**
     * Retorna o request de pesquisa para este DTO.
     *
     * @return request de pesquisa
     */
    public static SearchRequestDTO getSearchRequest() {
        return new ChatSessionSearchRequest();
    }

    /**
     * Retorna os filtros de propriedade para pesquisa.
     *
     * @return conjunto de filtros
     */
    public static Set<String> propertyFilters() {
        return getSearchRequest().propertyFilters();
    }

    /**
     * Cria uma cópia superficial (clone) deste DTO.
     *
     * @return nova instância com os mesmos valores
     */
    @Override
    public ChatSessionDTO cloneObject() {
        return toBuilder().build();
    }

    /**
     * Retorna uma representação em string deste objeto.
     *
     * @return string contendo sessionId e título
     */
    @Override
    public String toString() {
        return String.format("ChatSessionDTO{sessionId=%s, titulo=%s}", sessionId, titulo);
    }


    /**
     * Constantes de campos para referência type-safe.
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS extends AbstractBaseEntityDTO.CAMPOS {
        public static final String SESSION_ID = "sessionId";
        public static final String TITULO = "titulo";
        public static final String DATA_INICIO = "dataInicio";
        public static final String DATA_FIM = "dataFim";
        public static final String STATUS = "status";
        public static final String AGENTE = "agente";
        public static final String USUARIO_ID = "usuarioId";

        public static Set<String> values() {
            var baseValues = AbstractBaseEntityDTO.CAMPOS.values();
            var currentValues = Set.of(SESSION_ID, TITULO, DATA_INICIO, DATA_FIM, STATUS, AGENTE, USUARIO_ID);
            var allValues = new HashSet<String>();
            allValues.addAll(baseValues);
            allValues.addAll(currentValues);
            return Collections.unmodifiableSet(allValues);
        }
    }
}
