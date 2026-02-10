package com.ia.core.security.model.authentication;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * Wrapper type-safe para tokens JWT.
 * <p>
 * Esta classe garante que apenas tokens válidos são aceitos em operações sensíveis,
 * evitando o uso de strings genéricas que podem ser mal interpretadas.
 * </p>
 * <p>
 * Exemplo de uso:
 * <pre>{@code
 * JwtToken token = JwtToken.from("eyJhbGciOiJIUzI1NiJ9...");
 * boolean expired = token.isExpired();
 * }</pre>
 * </p>
 *
 * @author Israel Araújo
 * @version 1.0.0
 * @since 1.0.0
 */
public final class JwtToken implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String value;
    private final Instant issuedAt;
    private final Instant expiration;

    /**
     * Tempo padrão de expiração em segundos (1 hora).
     */
    public static final long DEFAULT_EXPIRATION_SECONDS = 3600L;

    /**
     * Cria um JwtToken a partir de uma string raw do token.
     *
     * @param tokenValue string do token JWT (não nula)
     * @return JwtToken instância encapsulada
     * @throws IllegalArgumentException se o token for nulo ou vazio
     */
    public static JwtToken from(String tokenValue) {
        validateTokenValue(tokenValue);
        return new JwtToken(tokenValue, Instant.now(), Instant.now().plusSeconds(DEFAULT_EXPIRATION_SECONDS));
    }

    /**
     * Cria um JwtToken a partir de um token existente com claims extraídas.
     *
     * @param tokenValue string do token JWT
     * @param issuedAt momento de emissão do token
     * @param expiration momento de expiração do token
     * @return JwtToken com metadados
     * @throws IllegalArgumentException se o token for nulo ou vazio
     */
    public static JwtToken withDates(String tokenValue, Instant issuedAt, Instant expiration) {
        validateTokenValue(tokenValue);
        Objects.requireNonNull(issuedAt, "IssuedAt cannot be null");
        Objects.requireNonNull(expiration, "Expiration cannot be null");
        return new JwtToken(tokenValue, issuedAt, expiration);
    }

    /**
     * Construtor privado para garantir imutabilidade.
     *
     * @param value valor do token
     * @param issuedAt momento de emissão
     * @param expiration momento de expiração
     */
    private JwtToken(String value, Instant issuedAt, Instant expiration) {
        this.value = value;
        this.issuedAt = issuedAt;
        this.expiration = expiration;
    }

    /**
     * Valida o valor do token.
     *
     * @param tokenValue valor a ser validado
     * @throws IllegalArgumentException se o valor for nulo ou vazio
     */
    private static void validateTokenValue(String tokenValue) {
        if (tokenValue == null || tokenValue.isBlank()) {
            throw new IllegalArgumentException("Token JWT não pode ser nulo ou vazio");
        }
    }

    /**
     * Retorna o valor raw do token JWT.
     *
     * @return string do token
     */
    public String getValue() {
        return value;
    }

    /**
     * Retorna o momento de emissão do token.
     *
     * @return Instant de emissão
     */
    public Instant getIssuedAt() {
        return issuedAt;
    }

    /**
     * Retorna o momento de expiração do token.
     *
     * @return Instant de expiração
     */
    public Instant getExpiration() {
        return expiration;
    }

    /**
     * Verifica se o token está expirado considerando o momento atual.
     *
     * @return true se o token estiver expirado
     */
    public boolean isExpired() {
        return Instant.now().isAfter(expiration);
    }

    /**
     * Verifica se o token está prestes a expirar (dentro de tolerance segundos).
     *
     * @param toleranceSeconds tolerância em segundos
     * @return true se o token expirará dentro da tolerância
     */
    public boolean isExpiringSoon(long toleranceSeconds) {
        return Instant.now().plusSeconds(toleranceSeconds).isAfter(expiration);
    }

    /**
     * Retorna o tempo restante até a expiração em segundos.
     *
     * @return segundos até expiração, ou 0 se já expirado
     */
    public long getSecondsUntilExpiration() {
        Instant now = Instant.now();
        if (now.isAfter(expiration)) {
            return 0;
        }
        return expiration.getEpochSecond() - now.getEpochSecond();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JwtToken jwtToken = (JwtToken) o;
        return Objects.equals(value, jwtToken.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        int prefixLength = Math.min(10, value.length());
        return "JwtToken[prefix=" + value.substring(0, prefixLength) + "...][expired=" + isExpired() + "]";
    }
}
