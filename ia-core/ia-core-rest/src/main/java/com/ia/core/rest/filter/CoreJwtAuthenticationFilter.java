package com.ia.core.rest.filter;

import java.util.Objects;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.ia.core.security.model.authentication.JwtToken;
import com.ia.core.security.model.exception.InvalidTokenException;
import com.ia.core.security.model.exception.UserNotFoundException;
import com.ia.core.security.service.token.TokenService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * Filtro de autenticação JWT para APIs REST.
 * <p>
 * Este filtro processa requisições HTTP para extrair e validar
 * tokens JWT do header Authorization.
 * </p>
 * <p>
 * Fluxo de autenticação:
 * <ol>
 * <li>Extrai token do header Authorization (formato: Bearer {token})</li>
 * <li>Valida o token usando TokenService</li>
 * <li>Carrega detalhes do usuário usando UserDetailsService</li>
 * <li>Cria objeto Authentication para contexto de segurança</li>
 * </ol>
 * </p>
 *
 * @author Israel Araújo
 * @version 2.0.0
 * @since 1.0.0
 */
@Slf4j
public class CoreJwtAuthenticationFilter
  extends OncePerRequestAuthenticationFilter {

    /**
     * Prefixo do header Authorization.
     */
    private static final String BEARER_PREFIX = "Bearer ";

    /**
     * Nome do header Authorization.
     */
    private static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * Serviço para operações com tokens JWT.
     */
    private final TokenService tokenService;

    /**
     * Serviço para carregar detalhes do usuário.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Construtor com dependências.
     *
     * @param tokenService serviço de tokens
     * @param userDetailsService serviço de detalhes do usuário
     */
    public CoreJwtAuthenticationFilter(TokenService tokenService,
                                       UserDetailsService userDetailsService) {
        this.tokenService = Objects.requireNonNull(tokenService, "TokenService cannot be null");
        this.userDetailsService = Objects.requireNonNull(userDetailsService, "UserDetailsService cannot be null");
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request)
        throws UserNotFoundException {

        JwtToken jwtToken = extractToken(request)
            .orElseThrow(() -> new InvalidTokenException("Token JWT não encontrado na requisição"));

        return getAuthenticationFromToken(jwtToken, request);
    }

    /**
     * Extrai o token JWT da requisição HTTP.
     *
     * @param request requisição HTTP
     * @return Optional contendo o token, ou vazio se não encontrado
     */
    private Optional<JwtToken> extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (Objects.nonNull(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            String tokenValue = bearerToken.substring(BEARER_PREFIX.length());
            return Optional.of(JwtToken.from(tokenValue));
        }

        return Optional.empty();
    }

    /**
     * Cria objeto Authentication a partir do token JWT.
     *
     * @param jwtToken token JWT validado
     * @param request requisição HTTP
     * @return objeto Authentication
     * @throws UserNotFoundException se o usuário não for encontrado
     */
    private Authentication getAuthenticationFromToken(JwtToken jwtToken, HttpServletRequest request)
        throws UserNotFoundException {

        // Valida o token
        var validationResult = tokenService.validateToken(jwtToken);
        if (validationResult.isInvalid()) {
            log.warn("Token JWT inválido: {}", validationResult.getErrorMessage().orElse("desconhecido"));
            throw new InvalidTokenException(validationResult.getErrorMessage().orElse("Token inválido"));
        }

        // Extrai o código do usuário
        String userCode = tokenService.getUserCodeFromToken(jwtToken);

        // Carrega detalhes do usuário
        UserDetails user = userDetailsService.loadUserByUsername(userCode);

        if (user == null) {
            log.warn("Usuário não encontrado para código: {}", userCode);
            throw new UserNotFoundException(userCode);
        }

        // Cria objeto de autenticação
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(jwtToken, null, user.getAuthorities());

        // Adiciona detalhes da requisição
        authentication.setDetails(new WebAuthenticationDetails(request));

        log.debug("Usuário autenticado: {} com {} autoridades",
                 userCode, user.getAuthorities().size());

        return authentication;
    }

    /**
     * Retorna o serviço de tokens.
     *
     * @return TokenService
     */
    protected TokenService getTokenService() {
        return tokenService;
    }

    /**
     * Retorna o serviço de detalhes do usuário.
     *
     * @return UserDetailsService
     */
    protected UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }
}
