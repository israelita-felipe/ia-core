package com.ia.core.communication.service.model.mensagem.dto;

import com.ia.core.communication.model.mensagem.Mensagem;
import com.ia.core.communication.model.mensagem.StatusMensagem;
import com.ia.core.communication.model.mensagem.TipoCanal;
import com.ia.core.communication.service.model.modelomensagem.dto.HasVariavel;
import com.ia.core.communication.service.model.modelomensagem.dto.Variavel;
import com.ia.core.communication.service.model.modelomensagem.dto.VariavelTemplate;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * DTO para a entidade Mensagem.
 * <p>
 * Esta classe é utilizada para transferir dados de mensagens entre as camadas
 * de apresentação e serviço, contendo informações sobre destinatário, conteúdo,
 * canal de comunicação, status e histórico de entrega.
 * </p>
 *
 * @author Israel Araújo
 * @see Mensagem
 * @see TipoCanal
 * @see StatusMensagem
 * @see AbstractBaseEntityDTO
 * @since 1.0.0
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de uma mensagem de comunicação")
public class MensagemDTO
    extends AbstractBaseEntityDTO<Mensagem>
    implements HasVariavel {

    /** Serial UID para controle de versão da serialização. */
    private static final long serialVersionUID = 1L;

    /**
     * Telefone do destinatário.
     * <p>
     * Número de telefone no formato internacional ou nacional, utilizado para
     * identificar o destinatário da mensagem. Campo obrigatório.
     * </p>
     * <p>
     * Exemplos de formato:
     * <ul>
     *   <li>{@code +5511999999999} - formato internacional</li>
     *   <li>{@code 11999999999} - formato nacional</li>
     * </ul>
     * </p>
     */
    @NotBlank(message = MensagemTranslator.VALIDATION.TELEFONE_DESTINATARIO_NOT_BLANK)
    @Size(max = 20, message = MensagemTranslator.VALIDATION.TELEFONE_DESTINATARIO_SIZE)
    @Schema(description = "Telefone do destinatário",
            example = "+5511999999999",
            required = true)
    private String telefoneDestinatario;

    /**
     * Nome do destinatário.
     * <p>
     * Nome ou identificador do destinatário da mensagem. Campo opcional,
     * utilizado para personalização do conteúdo.
     * </p>
     */
    @Size(max = 200, message = MensagemTranslator.VALIDATION.NOME_DESTINATARIO_SIZE)
    @Schema(description = "Nome do destinatário", example = "João Silva")
    private String nomeDestinatario;

    /**
     * Corpo da mensagem.
     * <p>
     * Conteúdo textual da mensagem a ser enviada. Campo obrigatório.
     * </p>
     */
    @NotBlank(message = MensagemTranslator.VALIDATION.CORPO_MENSAGEM_NOT_BLANK)
    @Schema(description = "Corpo da mensagem",
            example = "Olá, esta é uma mensagem de teste",
            required = true)
    private String corpoMensagem;

    /**
     * Tipo do canal de comunicação.
     * <p>
     * Define o canal através do qual a mensagem será enviada.
     * Valores possíveis:
     * <ul>
     *   <li>{@link TipoCanal#WHATSAPP} - WhatsApp</li>
     *   <li>{@link TipoCanal#SMS} - SMS</li>
     *   <li>{@link TipoCanal#EMAIL} - E-mail</li>
     * </ul>
     * Campo obrigatório.
     * </p>
     */
    @NotNull(message = MensagemTranslator.VALIDATION.TIPO_CANAL_NOT_NULL)
    @Schema(description = "Tipo do canal de comunicação",
            example = "WHATSAPP",
            required = true)
    private TipoCanal tipoCanal;

    /**
     * Status da mensagem.
     * <p>
     * Representa o estado atual da mensagem no pipeline de envio.
     * Valores possíveis:
     * <ul>
     *   <li>{@link StatusMensagem#PENDENTE} - aguardando envio</li>
     *   <li>{@link StatusMensagem#ENVIADA} - enviada ao provedor</li>
     *   <li>{@link StatusMensagem#ENTREGUE} - entregue ao destinatário</li>
     *   <li>{@link StatusMensagem#FALHA} - falha no envio</li>
     * </ul>
     * Campo obrigatório.
     * </p>
     */
    @NotNull(message = MensagemTranslator.VALIDATION.STATUS_MENSAGEM_NOT_NULL)
    @Schema(description = "Status da mensagem", example = "ENVIADA", required = true)
    private StatusMensagem statusMensagem;

    /**
     * ID externo da mensagem no sistema do provedor.
     * <p>
     * Identificador único atribuído pelo provedor de mensagens. Campo opcional,
     * utilizado para rastreamento e reconciliação.
     * </p>
     */
    @Size(max = 100, message = MensagemTranslator.VALIDATION.ID_EXTERNO_SIZE)
    @Schema(description = "ID externo da mensagem", example = "msg-12345")
    private String idExterno;

    /**
     * Data e hora de envio da mensagem.
     */
    @Schema(description = "Data de envio da mensagem", example = "2024-01-15T10:30:00")
    private LocalDateTime dataEnvio;

    /**
     * Data e hora de entrega da mensagem ao destinatário.
     */
    @Schema(description = "Data de entrega da mensagem", example = "2024-01-15T10:31:00")
    private LocalDateTime dataEntrega;

    /**
     * Data e hora de leitura da mensagem pelo destinatário.
     */
    @Schema(description = "Data de leitura da mensagem", example = "2024-01-15T10:35:00")
    private LocalDateTime dataLeitura;

    /**
     * Motivo da falha no envio.
     * <p>
     * Descrição do erro ocorrido durante o envio, quando status é FALHA.
     * Campo opcional.
     * </p>
     */
    @Size(max = 500, message = MensagemTranslator.VALIDATION.MOTIVO_FALHA_SIZE)
    @Schema(description = "Motivo da falha no envio", example = "Número inválido")
    private String motivoFalha;

    /**
     * Retorna uma requisição de busca padrão para mensagens.
     *
     * @return instância de {@link SearchRequestDTO} configurada para busca de mensagens
     */
    public static SearchRequestDTO getSearchRequest() {
        return new MensagemSearchRequestDTO();
    }

    /**
     * Retorna o conjunto de propriedades disponíveis para filtragem.
     *
     * @return conjunto de strings representando as propriedades que podem ser usadas como filtros
     */
    public static Set<String> propertyFilters() {
        return getSearchRequest().propertyFilters();
    }

    /**
     * Cria uma cópia superficial (clone) deste objeto DTO.
     *
     * @return novo objeto {@link MensagemDTO} com os mesmos valores de atributos
     */
    @Override
    public MensagemDTO cloneObject() {
        return toBuilder().build();
    }

    /**
     * Cria uma cópia deste objeto DTO utilizando a implementação da classe pai.
     *
     * @return cópia do objeto {@link MensagemDTO}
     */
    @Override
    public MensagemDTO copyObject() {
        return (MensagemDTO) super.copyObject();
    }

    /**
     * Verifica a igualdade entre este objeto e outro objeto.
     * <p>
     * Duas mensagens são consideradas iguais se possuírem o mesmo identificador.
     * Se o identificador for nulo, utiliza a comparação de referência.
     * </p>
     *
     * @param obj o objeto a ser comparado
     * @return true se os objetos forem iguais, false caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (id == null) {
            return this == obj;
        }
        if (!(getClass().isInstance(obj))) {
            return false;
        }
        MensagemDTO other = (MensagemDTO) obj;
        return Objects.equals(id, other.id);
    }

    /**
     * Calcula o código hash para este objeto.
     * <p>
     * O código hash é baseado no identificador da mensagem, se disponível.
     * </p>
     *
     * @return código hash calculado
     */
    @Override
    public int hashCode() {
        if (id != null) {
            return Objects.hash(id);
        }
        return super.hashCode();
    }

    /**
     * Retorna uma representação em string deste objeto.
     *
     * @return string contendo o telefone e tipo de canal
     */
    @Override
    public String toString() {
        return String.format("%s -> %s", telefoneDestinatario, tipoCanal);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retorna as variáveis disponíveis para substituição no template,
     * mapeando os campos deste DTO para as variáveis correspondentes.
     * </p>
     */
    @Override
    public Map<Variavel, Object> getContext() {
        return Map.of(
            VariavelTemplate.TELEFONE, telefoneDestinatario,
            VariavelTemplate.NOME, nomeDestinatario,
            VariavelTemplate.CORPO_MENSAGEM, corpoMensagem,
            VariavelTemplate.DATA_ENVIO, dataEnvio,
            VariavelTemplate.STATUS, statusMensagem
        );
    }

    /**
     * Constantes para nomes dos campos deste DTO, conforme ADR-040.
     */
    @SuppressWarnings("javadoc")
    public static class CAMPOS extends com.ia.core.service.dto.entity.AbstractBaseEntityDTO.CAMPOS {

        /** Telefone do destinatário. */
        public static final String TELEFONE_DESTINATARIO = "telefoneDestinatario";

        /** Nome do destinatário. */
        public static final String NOME_DESTINATARIO = "nomeDestinatario";

        /** Corpo da mensagem. */
        public static final String CORPO_MENSAGEM = "corpoMensagem";

        /** Tipo do canal de comunicação. */
        public static final String TIPO_CANAL = "tipoCanal";

        /** Status da mensagem. */
        public static final String STATUS_MENSAGEM = "statusMensagem";

        /** ID externo da mensagem. */
        public static final String ID_EXTERNO = "idExterno";

        /** Data de envio da mensagem. */
        public static final String DATA_ENVIO = "dataEnvio";

        /** Data de entrega da mensagem. */
        public static final String DATA_ENTREGA = "dataEntrega";

        /** Data de leitura da mensagem. */
        public static final String DATA_LEITURA = "dataLeitura";

        /** Motivo da falha no envio. */
        public static final String MOTIVO_FALHA = "motivoFalha";

        /**
         * Retorna todos os nomes de campos deste DTO incluindo os da superclasse.
         *
         * @return conjunto imutável de strings com os nomes dos campos
         */
        public static Set<String> values() {
            var baseValues = com.ia.core.service.dto.entity.AbstractBaseEntityDTO.CAMPOS.values();
            var currentValues = Set.of(TELEFONE_DESTINATARIO, NOME_DESTINATARIO, CORPO_MENSAGEM,
                TIPO_CANAL, STATUS_MENSAGEM, ID_EXTERNO, DATA_ENVIO, DATA_ENTREGA, DATA_LEITURA, MOTIVO_FALHA);
            var allValues = new java.util.HashSet<String>();
            allValues.addAll(baseValues);
            allValues.addAll(currentValues);
            return java.util.Collections.unmodifiableSet(allValues);
        }
    }
}