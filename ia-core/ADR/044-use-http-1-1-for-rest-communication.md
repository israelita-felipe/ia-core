# ADR 044: Use HTTP/1.1 for REST Communication

## Status
Accepted

## Context
Os módulos REST do sistema precisam seguir padrões estabelecidos para comunicação HTTP. Precisamos garantir conformidade com as especificações HTTP/1.1 para interoperabilidade e confiabilidade.

## Decision
Vamos seguir as especificações do RFC 7230-7235 (HTTP/1.1) para toda comunicação REST no sistema, incluindo:
- Formatação correta de mensagens HTTP (RFC 7230)
- Semântica de métodos HTTP (RFC 7231)
- Semântica de cabeçalhos (RFC 7232)
- Semântica de payload (RFC 7233)
- Autenticação (RFC 7235)

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
4. Garantir conformidade com RFC 7230-7235 em todas as comunicações REST
5. Validar mensagens de entrada e saída conforme especificações HTTP

## Related Documents
- REFACTORING.md (seção 8 sobre padrões RFC relevantes)
- RFC 7230: Hypertext Transfer Protocol (HTTP/1.1): Message Syntax and Routing
- RFC 7231: Hypertext Transfer Protocol (HTTP/1.1): Semantics and Content
- RFC 7232: Hypertext Transfer Protocol (HTTP/1.1): Conditional Requests
- RFC 7233: Hypertext Transfer Protocol (HTTP/1.1): Range Requests
- RFC 7234: Hypertext Transfer Protocol (HTTP/1.1): Caching
- RFC 7235: Hypertext Transfer Protocol (HTTP/1.1): Authentication
