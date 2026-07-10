package com.ia.core.communication.service.model.enviomensagem.dto;

import com.ia.core.communication.model.mensagem.TipoCanal;
import com.ia.core.communication.service.model.modelomensagem.dto.HasVariavel;
import com.ia.core.communication.service.model.modelomensagem.dto.Variavel;
import com.ia.core.communication.service.model.modelomensagem.dto.VariavelTemplate;
import com.ia.core.service.dto.DTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * DTO para requisição de envio de mensagem em massa.
 * <p>
 * Esta classe é utilizada para transferir dados de envio de mensagens entre as camadas
 * de apresentação e serviço, contendo informações sobre canal, conteúdo, destinatários
 * e agendamento.
 * </p>
 *
 * @author Israel Araújo
 * @see TipoCanal
 * @since 1.0.0
 */
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EnvioMensagemRequestDTO implements HasVariavel, DTO<Serializable> {

    /**
     * Tipo do canal de comunicação.
     * <p>
     * Define o canal através do qual as mensagens serão enviadas.
     * Valores possíveis: {@link TipoCanal#WHATSAPP}, {@link TipoCanal#SMS}, {@link TipoCanal#EMAIL}.
     * Campo obrigatório.
     * </p>
     */
    @NotNull(message = EnvioMensagemTranslator.VALIDATION.TIPO_CANAL_NOT_NULL)
    private TipoCanal tipoCanal;

    /**
     * Corpo da mensagem.
     * <p>
     * Conteúdo textual da mensagem a ser enviada. Campo obrigatório.
     * </p>
     */
    @NotBlank(message = EnvioMensagemTranslator.VALIDATION.CORPO_MENSAGEM_NOT_BLANK)
    private String corpoMensagem;

    /**
     * ID do modelo de mensagem a ser utilizado.
     * <p>
     * Identificador do modelo pré-cadastrado. Opcional, utilizado quando
     * se deseja usar um template existente.
     * </p>
     */
    private Long modeloMensagemId;

    /**
     * Parâmetros para substituição no template.
     * <p>
     * Mapa de chave-valor para substituição de placeholders no template.
     * </p>
     */
    private Map<String, String> parametrosTemplate;

    /**
     * Lista de telefones dos destinatários.
     * <p>
     * Números de telefone para envio individual. Alternativa a gruposContatoIds.
     * </p>
     */
    private List<String> telefones;

    /**
     * IDs dos grupos de contatos.
     * <p>
     * Identificadores de grupos pré-cadastrados. Alternativa a lista de telefones.
     * </p>
     */
    private List<Long> gruposContatoIds;

    /**
     * Indica se o envio está agendado.
     * <p>
     * Valor padrão é false. Quando true, dataAgendamento deve ser informado.
     * </p>
     */
    @Builder.Default
    private Boolean agendado = false;

    /**
     * Data e hora do agendamento.
     * <p>
     * Quando preenchida, o envio será realizado nesta data/hora.
     * </p>
     */
    private LocalDateTime dataAgendamento;

    /**
     * Retorna as variáveis disponíveis para substituição no template.
     *
     * @return mapa contendo variáveis e seus valores
     */
    @Override
    public Map<Variavel, Object> getContext() {
        return Map.of(
            VariavelTemplate.TIPO_CANAL, tipoCanal,
            VariavelTemplate.CORPO_MENSAGEM, corpoMensagem
        );
    }

    public EnvioMensagemRequestDTO cloneObject() {
        return toBuilder().build();
    }

    /**
     * Retorna a representação em string deste objeto.
     *
     * @return string formatada com tipo de canal e quantidade de telefones
     */
    @Override
    public String toString() {
        return String.format("EnvioMensagemRequestDTO{tipoCanal=%s, telefones=%s}",
            tipoCanal, telefones != null ? telefones.size() : 0);
    }

    /**
     * Constantes para nomes dos campos deste DTO, conforme ADR-040.
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS {
        public static final String TIPO_CANAL = "tipoCanal";
        public static final String CORPO_MENSAGEM = "corpoMensagem";
        public static final String MODELO_MENSAGEM_ID = "modeloMensagemId";
        public static final String PARAMETROS_TEMPLATE = "parametrosTemplate";
        public static final String TELEFONES = "telefones";
        public static final String GRUPOS_CONTATO_IDS = "gruposContatoIds";
        public static final String AGENDADO = "agendado";
        public static final String DATA_AGENDAMENTO = "dataAgendamento";

        /**
         * Retorna todos os nomes de campos deste DTO.
         *
         * @return conjunto imutável de strings com os nomes dos campos
         */
        public static Set<String> values() {
            return Collections.unmodifiableSet(Set.of(TIPO_CANAL, CORPO_MENSAGEM, MODELO_MENSAGEM_ID,
                PARAMETROS_TEMPLATE, TELEFONES, GRUPOS_CONTATO_IDS, AGENDADO, DATA_AGENDAMENTO));
        }
    }
}
