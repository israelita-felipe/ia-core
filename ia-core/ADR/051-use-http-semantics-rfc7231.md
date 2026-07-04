---
title: Use RFC 9110 HTTP Semantics for RESTful API Design
author: Israel
status: accepted
date: 2026-06-11
tags: [http, rfc9110, rfc9112, rest, api]
---

## Context

Os módulos REST do `ia-core` usam Spring Boot para expor APIs. A decisão anterior referenciava RFC 7231 para semântica HTTP. A série RFC 9110-9113, publicada em 2022, atualiza e substitui a série RFC 7230-7235. Para manter os ADRs alinhados com padrões vigentes, este ADR substitui a referência principal a RFC 7231 por RFC 9110 e RFC 9112.

## Decision

Usar RFC 9110 como referência primária para semântica HTTP em APIs REST do `ia-core`, incluindo métodos, status codes, cabeçalhos e autenticação HTTP. Usar RFC 9112 para HTTP/1.1 quando a sintaxe de mensagens e roteamento forem relevantes.

Diretrizes obrigatórias:

- `GET` para operações seguras e idempotentes de leitura.
- `POST` para criação ou processamento que altera estado.
- `PUT` para substituição completa de recurso.
- `PATCH` para atualização parcial quando suportado.
- `DELETE` para remoção.
- `200 OK`, `201 Created`, `202 Accepted`, `204 No Content`, `400 Bad Request`, `401 Unauthorized`, `403 Forbidden`, `404 Not Found`, `409 Conflict`, `422 Unprocessable Content` e `500 Internal Server Error` conforme o cenário.
- `Accept`, `Content-Type`, `Location`, `ETag`, `Last-Modified`, `If-Match`, `If-None-Match`, `Cache-Control` e `Link` conforme RFC 9110/RFC 9111/RFC 8288 quando aplicável.
- Respostas de erro estruturadas devem seguir RFC 9457 quando o erro for exposto via API HTTP.

## Details

### Alternativas Consideradas

- **Manter RFC 7231**: simples, mas desatualizado.
- **Adotar OpenAPI como única referência**: útil para contrato, mas não substitui RFC HTTP.
- **Adotar RFC 9110/RFC 9112**: padrão vigente e compatível com Spring MVC/Spring Boot.

### Critérios de Decisão

- Conformidade com padrões vigentes.
- Interoperabilidade com clientes HTTP.
- Clareza para desenvolvedores.
- Compatibilidade com Spring Boot e Spring MVC.

## Consequences

### Positivas

- ADR alinhado com HTTP moderno.
- Melhor interoperabilidade e previsibilidade das APIs.
- Redução de ambiguidade entre métodos, status e cabeçalhos.
- Base mais clara para testes de contrato e integração.

### Negativas

- Pode exigir revisão de controllers legados que documentavam RFC 7231.
- Equipes acostumadas à nomenclatura RFC 7230-7235 precisarão migrar referências documentais.

## Confirmation

- Revisar controllers REST para garantir métodos e status alinhados com RFC 9110.
- Atualizar documentação de API e testes de integração.
- Usar RFC 9457 para respostas de erro quando aplicável.
- Atualizar ADRs relacionados, como [`044-use-http-1-1-for-rest-communication.md`](044-use-http-1-1-for-rest-communication.md) e [`050-standardization-guidelines.md`](050-standardization-guidelines.md).

## References

1. RFC 9110 — HTTP Semantics: https://www.rfc-editor.org/rfc/rfc9110
2. RFC 9112 — HTTP/1.1: https://www.rfc-editor.org/rfc/rfc9112
3. RFC 9111 — HTTP Caching: https://www.rfc-editor.org/rfc/rfc9111
4. RFC 9113 — HTTP/2: https://www.rfc-editor.org/rfc/rfc9113
5. RFC 9457 — Problem Details for HTTP APIs: https://www.rfc-editor.org/rfc/rfc9457
6. RFC 8288 — Web Linking: https://www.rfc-editor.org/rfc/rfc8288
