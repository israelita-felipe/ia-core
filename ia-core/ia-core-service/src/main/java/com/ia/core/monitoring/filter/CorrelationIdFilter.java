package com.ia.core.monitoring.filter;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro para geração e propagação de Correlation ID.
 * 
 * Este filtro garante que cada requisição HTTP tenha um ID único
 * que será propagado através de todas as camadas da aplicação,
 * permitindo rastreamento completo através de logs.
 * 
 * O Correlation ID é:
 * - Gerado se não presente no header x-correlation-id
 * - Armazenado no MDC (Mapped Diagnostic Context) para disponibilidade nos logs
 * - Adicionado ao response header para rastreamento externo
 * 
 * @author Israel Araújo
 */
@Component
@Order(1)
public class CorrelationIdFilter extends OncePerRequestFilter {

    public static final String CORRELATION_ID_HEADER = "x-correlation-id";
    public static final String CORRELATION_ID_MDC_KEY = "correlationId";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String correlationId = getOrGenerateCorrelationId(request);

        try {
            MDC.put(CORRELATION_ID_MDC_KEY, correlationId);
            response.addHeader(CORRELATION_ID_HEADER, correlationId);

            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(CORRELATION_ID_MDC_KEY);
        }
    }

    /**
     * Obtém o Correlation ID do header ou gera um novo.
     *
     * @param request a requisição HTTP
     * @return o Correlation ID (existente ou gerado)
     */
    private String getOrGenerateCorrelationId(HttpServletRequest request) {
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);

        if (StringUtils.hasText(correlationId)) {
            return correlationId;
        }

        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        // Não aplicar filtro em endpoints de actuator health
        return path.startsWith("/actuator/health");
    }
}
