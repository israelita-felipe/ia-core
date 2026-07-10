package com.ia.core.communication.service.model.enviomensagem.dto;

import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * DTO para resposta de envio de mensagem em massa.
 * <p>
 * Representa os dados de transferência para resposta de envio de mensagens,
 * incluindo total de enviados, total de falhas, mensagens de falha, data e status geral.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EnvioMensagemResponseDTO implements DTO<Serializable> {

    /** Serial UID */
    private static final long serialVersionUID = 1L;

    private int totalEnviados;

    private int totalFalhas;

    private List<String> mensagensFalhas;

    private LocalDateTime dataEnvio;

    private String statusGeral;

    public static EnvioMensagemResponseDTO sucesso(int total) {
        return EnvioMensagemResponseDTO.builder()
            .totalEnviados(total)
            .totalFalhas(0)
            .dataEnvio(LocalDateTime.now())
            .statusGeral("SUCESSO")
            .build();
    }

    public static EnvioMensagemResponseDTO parcial(int enviados, int falhas, List<String> falhasList) {
        return EnvioMensagemResponseDTO.builder()
            .totalEnviados(enviados)
            .totalFalhas(falhas)
            .mensagensFalhas(falhasList)
            .dataEnvio(LocalDateTime.now())
            .statusGeral("PARCIAL")
            .build();
    }

    public static EnvioMensagemResponseDTO comFalhas(int enviados, List<String> falhasList) {
        return EnvioMensagemResponseDTO.builder()
            .totalEnviados(enviados)
            .totalFalhas(falhasList.size())
            .mensagensFalhas(falhasList)
            .dataEnvio(LocalDateTime.now())
            .statusGeral("PARCIAL")
            .build();
    }

    @Override
    public EnvioMensagemResponseDTO cloneObject() {
        return toBuilder().mensagensFalhas(mensagensFalhas != null ? List.copyOf(mensagensFalhas) : null).build();
    }

    @Override
    public String toString() {
        return String.format("EnvioMensagemResponseDTO{totalEnviados=%d, totalFalhas=%d, status=%s}", totalEnviados, totalFalhas, statusGeral);
    }

    @SuppressWarnings("javadoc")
    public static class CAMPOS {
        public static final String TOTAL_ENVIADOS = "totalEnviados";
        public static final String TOTAL_FALHAS = "totalFalhas";
        public static final String MENSAGENS_FALHAS = "mensagensFalhas";
        public static final String DATA_ENVIO = "dataEnvio";
        public static final String STATUS_GERAL = "statusGeral";

        public static Set<String> values() {
            return Collections.unmodifiableSet(Set.of(TOTAL_ENVIADOS, TOTAL_FALHAS, MENSAGENS_FALHAS, DATA_ENVIO, STATUS_GERAL));
        }
    }
}