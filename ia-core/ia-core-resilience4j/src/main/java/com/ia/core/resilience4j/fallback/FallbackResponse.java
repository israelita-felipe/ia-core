package com.ia.core.resilience4j.fallback;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Representa a resposta retornada por uma estratégia de fallback.
 *
 * <p>Encapsula o resultado da execução de um fallback, incluindo
 * indicadores de sucesso, código de erro, mensagem e dados de retorno.</p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder
public class FallbackResponse implements Serializable {
    private boolean success;
    private String errorCode;
    private String message;
    private boolean retryable;
    private Object data;
    private Throwable originalException;
}
