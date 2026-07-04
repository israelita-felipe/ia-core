# ADR 043: Use JWT Tokens for Authentication

## Status
Accepted

## Context
O sistema utiliza autenticação baseada em tokens para APIs REST. Precisamos padronizar o uso de JSON Web Tokens (JWT) conforme RFC 7519 para garantir interoperabilidade e segurança.

## Decision
Vamos utilizar JWT (JSON Web Tokens) conforme especificado no RFC 7519 para todos os fluxos de autenticação no sistema, incluindo:
- Tokens de acesso (access tokens) com curta duração
- Tokens de atualização (refresh tokens) conforme ADR 042
- Validação de assinatura usando algoritmos seguros (HS256, RS256, ES256)
- Claims padronizados (iss, sub, aud, exp, nbf, iat, jti)
- Criptografia opcional quando necessário (JWE)
- Envio de access tokens via `Authorization: Bearer <token>` conforme RFC 6750
- Perfil de access token JWT conforme RFC 9068 quando o sistema for interoperar com OAuth 2.0

## Consequences
### Positivos
- Padronização amplamente adotada na indústria
- Suporte nativo em diversas linguagens e frameworks
- Facilidade de validação e inspeção de tokens
- Extensibilidade através de claims customizados
- Integração com bibliotecas estabelecidas (jjwt, nimbus-jose-jwt)

### Negativos
- Tamanho maior do token comparado a tokens opacos
- Necessidade de gerenciamento seguro de chaves de assinatura
- Overhead de processamento para validação de assinatura

## Implementation Plan
1. Padronizar a estrutura de JWT em JwtCoreManager
2. Utilizar algoritmo HS256 com chave segura armazenada em variável de ambiente; considerar RS256/ES256 quando houver múltiplos consumidores ou validadores de tokens
3. Incluir claims essenciais: iss (issuer), sub (subject), exp (expiration), iat (issued at), jti (token id)
4. Implementar validação rigorosa de assinatura e claims
5. Suporte a refresh tokens conforme ADR 042
6. Enviar access tokens via `Authorization: Bearer <token>` conforme RFC 6750
7. Aplicar RFC 9068 quando o access token JWT precisar interoperar com OAuth 2.0
8. Considerar uso de JWE para criptografia quando necessário

## Related Documents
- REFACTORING.md (itens 12, 13, 15, 16, 17 sobre JWT e refresh token)
- RFC 7515: JSON Web Signature (JWS)
- RFC 7516: JSON Web Encryption (JWE)
- RFC 7517: JSON Web Key (JWK)
- RFC 7518: JSON Web Algorithms (JWA)
- RFC 7519: JSON Web Token (JWT)
- RFC 6750: OAuth 2.0 Bearer Token Usage
- RFC 9068: JWT Profile for OAuth 2.0 Access Tokens
- ADR 042: Use OAuth 2.0 for Refresh Token Flow
- ADR 028: Use JWT for Stateless Authentication (existente)
