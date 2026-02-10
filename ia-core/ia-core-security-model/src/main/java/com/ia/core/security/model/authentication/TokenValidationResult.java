package com.ia.core.security.model.authentication;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Resultado de validação de token JWT.
 * <p>
 * Este é um Value Object que representa o resultado de uma operação
 * de validação de token. Ele é imutável e auto-validável.
 * </p>
 * <p>
 * Exemplos de uso:
 * <pre>{@code
 * TokenValidationResult result = tokenValidator.validate(token);
 *
 * if (result.isValid()) {
 *     // Token é válido, prosseguir
 * } else {
 *     // Tratar erro
 *     log.warn("Token inválido: {}", result.getErrorMessage());
 * }
 * }</pre>
 * </p>
 *
 * @author Israel Araújo
 * @version 1.0.0
 * @since 1.0.0
 */
public final class TokenValidationResult {

    /**
     * Indica se a validação foi bem-sucedida.
     */
    private final boolean valid;

    /**
     * Mensagem de erro, presente apenas se a validação falhou.
     */
    private final String errorMessage;

    /**
     * Código de erro específico.
     */
    private final String errorCode;

    /**
     * Detalhes adicionais sobre o erro.
     */
    private final Map<String, Object> details;

    /**
     * Construtor privado para garantir imutabilidade.
     */
    private TokenValidationResult(boolean valid, String errorMessage, String errorCode, Map<String, Object> details) {
        this.valid = valid;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.details = details != null ? Map.copyOf(details) : Map.of();
    }

    /**
     * Cria um resultado de validação bem-sucedida.
     *
     * @return resultado válido
     */
    public static TokenValidationResult success() {
        return new TokenValidationResult(true, null, null, null);
    }

    /**
     * Cria um resultado de validação falhada.
     *
     * @param errorMessage mensagem de erro
     * @return resultado inválido
     */
    public static TokenValidationResult failure(String errorMessage) {
        return new TokenValidationResult(false, errorMessage, "VALIDATION_FAILED", null);
    }

    /**
     * Cria um resultado de validação falhada com código de erro.
     *
     * @param errorMessage mensagem de erro
     * @param errorCode código de erro
     * @return resultado inválido
     */
    public static TokenValidationResult failure(String errorMessage, String errorCode) {
        return new TokenValidationResult(false, errorMessage, errorCode, null);
    }

    /**
     * Cria um resultado de validação falhada com detalhes.
     *
     * @param errorMessage mensagem de erro
     * @param errorCode código de erro
     * @param details detalhes adicionais
     * @return resultado inválido
     */
    public static TokenValidationResult failure(String errorMessage, String errorCode, Map<String, Object> details) {
        return new TokenValidationResult(false, errorMessage, errorCode, details);
    }

    /**
     * Factory method para token expirado.
     *
     * @param expirationTime momento de expiração
     * @return resultado de expiração
     */
    public static TokenValidationResult expired(java.time.Instant expirationTime) {
        return new TokenValidationResult(
            false,
            "Token expirado em: " + expirationTime,
            "TOKEN_EXPIRED",
            Map.of("expirationTime", expirationTime.toString())
        );
    }

    /**
     * Factory method para token malformado.
     *
     * @param reason motivo da malformação
     * @return resultado de malformação
     */
    public static TokenValidationResult malformed(String reason) {
        return new TokenValidationResult(
            false,
            "Token malformado: " + reason,
            "TOKEN_MALFORMED",
            Map.of("reason", reason)
        );
    }

    /**
     * Factory method para assinatura inválida.
     *
     * @return resultado de assinatura inválida
     */
    public static TokenValidationResult invalidSignature() {
        return new TokenValidationResult(
            false,
            "Assinatura do token inválida",
            "TOKEN_INVALID_SIGNATURE",
            null
        );
    }

    /**
     * Verifica se a validação foi bem-sucedida.
     *
     * @return true se válido
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Verifica se a validação falhou.
     *
     * @return true se inválido
     */
    public boolean isInvalid() {
        return !valid;
    }

    /**
     * Retorna a mensagem de erro, se disponível.
     *
     * @return Optional com a mensagem de erro
     */
    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }

    /**
     * Retorna o código de erro.
     *
     * @return código de erro ou null
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Retorna detalhes adicionais sobre o erro.
     *
     * @return mapa de detalhes (imutável)
     */
    public Map<String, Object> getDetails() {
        return details;
    }

    /**
     * Obtém um detalhe específico.
     *
     * @param key chave do detalhe
     * @param <T> tipo esperado
     * @return Optional com o valor
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> getDetail(String key) {
        return Optional.ofNullable((T) details.get(key));
    }

    /**
     * Combina este resultado com outro.
     * Se ambos forem inválidos, concatena as mensagens.
     *
     * @param other outro resultado
     * @return resultado combinado
     */
    public TokenValidationResult combine(TokenValidationResult other) {
        if (this.valid) {
            return other;
        }
        if (other.valid) {
            return this;
        }
        // Ambos inválidos - combinar mensagens
        String combinedMessage = this.errorMessage + "; " + other.errorMessage;
        return new TokenValidationResult(false, combinedMessage, this.errorCode, this.details);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenValidationResult that = (TokenValidationResult) o;
        return valid == that.valid &&
               Objects.equals(errorMessage, that.errorMessage) &&
               Objects.equals(errorCode, that.errorCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valid, errorMessage, errorCode);
    }

    @Override
    public String toString() {
        if (valid) {
            return "TokenValidationResult[valid=true]";
        }
        return "TokenValidationResult[valid=false][error=" + errorMessage + "]";
    }
}
