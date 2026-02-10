package com.ia.core.security.service.token;

import java.util.Collection;
import java.util.Date;

import com.ia.core.security.model.authentication.JwtToken;
import com.ia.core.security.model.authentication.TokenValidationResult;

/**
 * Serviço para operações com tokens JWT.
 * <p>
 * Esta interface define as operações básicas para geração,
 * validação e extração de informações de tokens JWT.
 * </p>
 *
 * @author Israel Araújo
 * @version 1.0.0
 * @since 1.0.0
 */
public interface TokenService {

    /**
     * Gera um token JWT para o usuário especificado.
     *
     * @param userCode código do usuário
     * @param roles papéis do usuário
     * @param functionalities funcionalidades do usuário
     * @return token JWT gerado
     */
    JwtToken generateToken(String userCode, Collection<String> roles, Collection<String> functionalities);

    /**
     * Gera um token JWT com data de expiração customizada.
     *
     * @param userCode código do usuário
     * @param roles papéis do usuário
     * @param functionalities funcionalidades do usuário
     * @param expirationDate data de expiração
     * @return token JWT gerado
     */
    JwtToken generateToken(String userCode, Collection<String> roles,
                           Collection<String> functionalities, Date expirationDate);

    /**
     * Valida um token JWT.
     *
     * @param token token a ser validado
     * @return resultado da validação
     */
    TokenValidationResult validateToken(JwtToken token);

    /**
     * Extrai o código do usuário de um token.
     *
     * @param token token JWT
     * @return código do usuário
     */
    String getUserCodeFromToken(JwtToken token);

    /**
     * Extrai o nome do usuário de um token.
     *
     * @param token token JWT
     * @return nome do usuário
     */
    String getUserNameFromToken(JwtToken token);

    /**
     * Extrai os papéis do usuário de um token.
     *
     * @param token token JWT
     * @return coleção de papéis
     */
    Collection<String> getRolesFromToken(JwtToken token);

    /**
     * Extrai as funcionalidades do usuário de um token.
     *
     * @param token token JWT
     * @return coleção de funcionalidades
     */
    Collection<String> getFunctionalitiesFromToken(JwtToken token);

    /**
     * Verifica se um token está expirado.
     *
     * @param token token JWT
     * @return true se expirado
     */
    boolean isTokenExpired(JwtToken token);

    /**
     * Renova um token expirado (dentro do período de graça).
     *
     * @param expiredToken token expirado
     * @return novo token válido
     */
    JwtToken refreshToken(JwtToken expiredToken);
}
