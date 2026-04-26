package com.ia.core.rest.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * Filtro para geração e propagação de Correlation ID.
 *
 * <p>Este filtro implementa a rastreabilidade de requisições conforme ADR-013:
 * - Gera um correlation ID único para cada requisição
 * - Propaga via MDC para disponibilidade em todos os logs
 * - Adiciona header x-correlation-id na resposta
 * - Suporta correlation ID existente via header (rastreamento distribuído)
 * </p>
 *
 * @author Israel Araújo
 * @see <a href="../../ADR/013-logging-monitoring-patterns.md">ADR-013 - Padrões de Logging e Monitoramento</a>
 * @version 1.0
 */
@Component
@Order(1)
public class CorrelationIdFilter extends OncePerRequestFilter {

    /**
     * Nome do header para correlation ID.
     */
    public static final String CORRELATION_ID_HEADER = "x-correlation-id";

    /**
     * Nome da chave MDC para correlation ID.
     */
    public static final String CORRELATION_ID_MDC_KEY = "correlationId";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException {

        String correlationId = getOrGenerateCorrelationId(request);

        // Disponibiliza no MDC para todos os logs da requisição
        MDC.put(CORRELATION_ID_MDC_KEY, correlationId);

        // Adiciona header na resposta
        response.addHeader(CORRELATION_ID_HEADER, correlationId);

        try {
            filterChain.doFilter(request, response);
        } finally {
            // Remove do MDC após finalizar a requisição
            MDC.remove(CORRELATION_ID_MDC_KEY);
        }
    }

    /**
     * Obtém correlation ID existente no header ou gera um novo.
     *
     * @param request requisição HTTP
     * @return correlation ID (existente ou gerado)
     */
    private String getOrGenerateCorrelationId(HttpServletRequest request) {
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);

        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }

        return correlationId;
    }
}
