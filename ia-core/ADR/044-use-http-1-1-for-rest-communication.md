# ADR 044: Use HTTP/1.1 for REST Communication

## Status
Accepted

## Context
Os módulos REST do sistema precisam seguir padrões estabelecidos para comunicação HTTP. Precisamos garantir conformidade com as especificações HTTP/1.1 para interoperabilidade e confiabilidade.

## Decision
Vamos seguir as especificações HTTP atualmente vigentes para toda comunicação REST no sistema, incluindo:
- Formatação correta de mensagens HTTP/1.1 (RFC 9112, que substitui RFC 7230)
- Semântica de métodos, status e cabeçalhos (RFC 9110, que substitui RFC 7231, RFC 7232 e RFC 7233)
- Cache (RFC 9111, que substitui RFC 7234)
- Autenticação HTTP (RFC 9110, que substitui RFC 7235)
- HTTP/2 como transporte compatível quando aplicável (RFC 9113)

## Consequences
### Positivos
- Interoperabilidade com clientes e servidores HTTP padrão
- Conformidade com especificações amplamente adotadas
- Melhor tratamento de erros e códigos de status
- Suporte a recursos como cache, compressão e conexões persistentes

### Negativos
- Overhead de processamento comparado a HTTP/2 ou HTTP/3
- Necessidade de implementação cuidadosa para evitar vulnerabilidades

## Implementation Plan
1. Garantir que todos os endpoints REST respeitem os métodos HTTP corretos (GET, POST, PUT, DELETE, PATCH, etc.)
2. Implementar tratamento adequado de códigos de status HTTP
3. Utilizar cabeçalhos HTTP padrão para cache, content-type, autorização, etc.
4. Garantir conformidade com RFC 9110, RFC 9111, RFC 9112 e RFC 9113 em todas as comunicações REST
5. Validar mensagens de entrada e saída conforme especificações HTTP
6. Tratar RFC 7230-7235 como referências históricas/obsoletas quando aparecerem em documentação legada

## Related Documents
- REFACTORING.md (seção 8 sobre padrões RFC relevantes)
- RFC 9110: HTTP Semantics
- RFC 9111: HTTP Caching
- RFC 9112: HTTP/1.1
- RFC 9113: HTTP/2
- RFC 7230-7235: referências históricas substituídas por RFC 9110-9113
