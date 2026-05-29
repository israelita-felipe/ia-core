# ADR 043: Use JWT Tokens for Authentication

## Status
Accepted

## Context
O sistema utiliza autenticação baseada em tokens para APIs REST. Precisamos padronizar o uso de JSON Web Tokens (JWT) conforme RFC 7519 para garantir interoperabilidade e segurança.

## Decision
Vamos utilizar JWT (JSON Web Tokens) conforme especificado no RFC 7519 para todos os fluxos de autenticação no sistema, incluindo:
- Tokens de acesso (access tokens) com curta duração
- Tokens de atualização (refresh tokens) conforme ADR 042
- Validação de assinatura usando algoritmos seguros (HS256, RS256)
- Claims padronizados (iss, sub, aud, exp, nbf, iat, jti)
- Criptografia opcional quando necessário (JWE)

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
2. Utilizar algoritmo HS256 com chave segura armazenada em variável de ambiente
3. Incluir claims essenciais: iss (issuer), sub (subject), exp (expiration), iat (issued at)
4. Implementar validação rigorosa de assinatura e claims
5. Suporte a refresh tokens conforme ADR 042
6. Considerar uso de JWE para criptografia quando necessário

## Related Documents
- REFACTORING.md (itens 12, 13, 15, 16, 17 sobre JWT e refresh token)
- RFC 7519: JSON Web Token (JWT)
- ADR 042: Use OAuth 2.0 for Refresh Token Flow
- ADR 028: Use JWT for Stateless Authentication (existente)
