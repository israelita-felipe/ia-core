package com.ia.core.security.service.token;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ia.core.security.model.authentication.JwtToken;
import com.ia.core.security.model.authentication.TokenValidationResult;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementação do serviço de tokens JWT.
 * <p>
 * Utiliza a biblioteca JJWT para geração e validação de tokens
 * seguindo o padrão JWT (RFC 7519).
 * </p>
 *
 * @author Israel Araújo
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Service
public class JwtTokenService implements TokenService {

    /**
     * Chave secreta para assinatura dos tokens.
     * Deve ser mantida segura e compartilhada apenas entre serviços que validam.
     */
    private final SecretKey secretKey;

    /**
     * Issuer do token.
     */
    private final String issuer;

    /**
     * Tempo de expiração em minutos.
     */
    private final int expirationMinutes;

    /**
     * Período de graça para renovação em minutos.
     */
    private final int refreshGracePeriodMinutes;

    /**
     * Construtor com injeção de dependências.
     *
     * @param jwtSecret segredo para assinatura (inyetado pelo Spring)
     * @param jwtIssuer issuer do token
     * @param jwtExpirationMinutes minutos até expiração
     * @param jwtRefreshGracePeriodMinutes minutos de graça para refresh
     */
    public JwtTokenService(
            @Value("${jwt.secret:change-me-in-production-use-a-secure-random-key}") String jwtSecret,
            @Value("${jwt.issuer:ia-core}") String jwtIssuer,
            @Value("${jwt.expiration-minutes:60}") int jwtExpirationMinutes,
            @Value("${jwt.refresh-grace-period-minutes:5}") int jwtRefreshGracePeriodMinutes) {

        // Garante que a chave tenha pelo menos 256 bits
        String paddedSecret = jwtSecret;
        while (paddedSecret.getBytes(StandardCharsets.UTF_8).length < 32) {
            paddedSecret = paddedSecret + jwtSecret;
        }
        this.secretKey = Keys.hmacShaKeyFor(paddedSecret.getBytes(StandardCharsets.UTF_8));
        this.issuer = jwtIssuer;
        this.expirationMinutes = jwtExpirationMinutes;
        this.refreshGracePeriodMinutes = jwtRefreshGracePeriodMinutes;

        log.info("JWT TokenService inicializado com issuer: {}, expiração: {} min",
                issuer, expirationMinutes);
    }

    @Override
    public JwtToken generateToken(String userCode, Collection<String> roles,
                                  Collection<String> functionalities) {
        return generateToken(userCode, roles, functionalities, calculateExpirationDate());
    }

    @Override
    public JwtToken generateToken(String userCode, Collection<String> roles,
                                  Collection<String> functionalities, Date expirationDate) {

        Date now = new Date();
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        claims.put("funcs", functionalities);
        claims.put("iss", issuer);

        String token = Jwts.builder()
                .claims(claims)
                .subject(userCode)
                .issuer(issuer)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();

        return JwtToken.withDates(token, now.toInstant(), expirationDate.toInstant());
    }

    @Override
    public TokenValidationResult validateToken(JwtToken token) {
        if (token == null) {
            return TokenValidationResult.failure("Token não pode ser nulo", "TOKEN_NULL");
        }

        try {
            Claims claims = parseToken(token.getValue());

            // Valida expiração
            if (claims.getExpiration().before(new Date())) {
                return TokenValidationResult.expired(claims.getExpiration().toInstant());
            }

            // Valida issuer
            if (!issuer.equals(claims.getIssuer())) {
                return TokenValidationResult.failure(
                    "Issuer inválido. Esperado: " + issuer + ", Encontrado: " + claims.getIssuer(),
                    "TOKEN_INVALID_ISSUER"
                );
            }

            return TokenValidationResult.success();

        } catch (ExpiredJwtException e) {
            log.warn("Token expirado: {}", e.getMessage());
            return TokenValidationResult.expired(e.getClaims().getExpiration().toInstant());

        } catch (MalformedJwtException e) {
            log.warn("Token malformado: {}", e.getMessage());
            return TokenValidationResult.malformed("Estrutura JWT inválida");

        } catch (SignatureException e) {
            log.error("Assinatura de token inválida: {}", e.getMessage());
            return TokenValidationResult.invalidSignature();

        } catch (UnsupportedJwtException e) {
            log.warn("Token não suportado: {}", e.getMessage());
            return TokenValidationResult.failure("Tipo de token não suportado", "TOKEN_UNSUPPORTED");

        } catch (JwtException e) {
            log.error("Erro na validação do token: {}", e.getMessage());
            return TokenValidationResult.failure("Token inválido: " + e.getMessage(), "TOKEN_INVALID");
        }
    }

    @Override
    public String getUserCodeFromToken(JwtToken token) {
        Claims claims = parseToken(token.getValue());
        return claims.getSubject();
    }

    @Override
    public String getUserNameFromToken(JwtToken token) {
        Claims claims = parseToken(token.getValue());
        return claims.get("userName", String.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<String> getRolesFromToken(JwtToken token) {
        Claims claims = parseToken(token.getValue());
        Object rolesObj = claims.get("roles");
        if (rolesObj instanceof List) {
            return (List<String>) rolesObj;
        }
        return List.of();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<String> getFunctionalitiesFromToken(JwtToken token) {
        Claims claims = parseToken(token.getValue());
        Object funcsObj = claims.get("funcs");
        if (funcsObj instanceof List) {
            return (List<String>) funcsObj;
        }
        return List.of();
    }

    @Override
    public boolean isTokenExpired(JwtToken token) {
        try {
            Claims claims = parseToken(token.getValue());
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (JwtException e) {
            log.warn("Erro ao verificar expiração: {}", e.getMessage());
            return true;
        }
    }

    @Override
    public JwtToken refreshToken(JwtToken expiredToken) {
        Claims claims;
        try {
            claims = parseToken(expiredToken.getValue());
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }

        Date expiration = claims.getExpiration();
        Date now = new Date();

        // Verifica se está dentro do período de graça
        long gracePeriodMs = TimeUnit.MINUTES.toMillis(refreshGracePeriodMinutes);
        if (now.getTime() - expiration.getTime() > gracePeriodMs) {
            throw new IllegalStateException(
                "Token expirado fora do período de graça. Expirou em: " + expiration
            );
        }

        @SuppressWarnings("unchecked")
        Collection<String> roles = (Collection<String>) claims.get("roles");
        @SuppressWarnings("unchecked")
        Collection<String> funcs = (Collection<String>) claims.get("funcs");

        return generateToken(
            claims.getSubject(),
            roles != null ? roles : List.of(),
            funcs != null ? funcs : List.of()
        );
    }

    /**
     * Faz o parse de um token e retorna os claims.
     *
     * @param token token JWT em string
     * @return claims do token
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Calcula a data de expiração baseada nas configurações.
     *
     * @return data de expiração
     */
    private Date calculateExpirationDate() {
        return new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(expirationMinutes));
    }
}
