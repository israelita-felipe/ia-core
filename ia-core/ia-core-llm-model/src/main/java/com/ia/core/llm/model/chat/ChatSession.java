package com.ia.core.llm.model.chat;

import com.ia.core.llm.model.LLMModel;
import com.ia.core.llm.model.agente.Agente;
import com.ia.core.llm.model.agente.ContextoConversacao;
import com.ia.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Sessão de chat com agentes LLM.
 * <p>
 * Representa uma conversação interativa entre um usuário e um agente,
 * mantendo o contexto da conversa através de chat memory.
 * Integrado com ContextoConversacao para gerenciamento de ontologia de contexto.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Entity
@Table(name = ChatSession.TABLE_NAME, schema = ChatSession.SCHEMA_NAME)
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = false)
public class ChatSession
    extends BaseEntity {

    public static final String TABLE_NAME = LLMModel.TABLE_PREFIX + "CHAT_SESSION";
    public static final String SCHEMA_NAME = LLMModel.SCHEMA;
    private static final long serialVersionUID = 5644976387280082130L;
    /**
     * Identificador único da sessão (UUID).
     */
    @Column(name = "session_id", unique = true, nullable = false, length = 100)
    private String sessionId;

    /**
     * Título da sessão.
     */
    @Column(name = "titulo", length = 200)
    private String titulo;

    /**
     * Data e hora de início da sessão.
     */
    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;

    /**
     * Data e hora de fim da sessão.
     */
    @Column(name = "data_fim")
    private LocalDateTime dataFim;

    /**
     * Status da sessão.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Default
    private ChatSessionStatus status = ChatSessionStatus.ATIVA;

    /**
     * Agente associado à sessão.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agente_id", nullable = false)
    private Agente agente;

    /**
     * ID do usuário que iniciou a sessão.
     */
    @Column(name = "usuario_id", length = 100)
    private String usuarioId;

    /**
     * Contexto de conversação associado à sessão (ontologia incremental).
     * <p>
     * Integrado de ContextoConversacao para gerenciamento de contexto e ontologia.
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "contexto_conversacao_id")
    private ContextoConversacao contextoConversacao;


}
