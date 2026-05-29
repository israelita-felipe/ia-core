# ADR 042: Use OAuth 2.0 for Refresh Token Flow

## Status
Accepted

## Context
Precisamos implementar suporte a refresh tokens no sistema de autenticação JWT para melhorar a segurança e usabilidade. O fluxo de refresh token permite que os usuários obtenham novos tokens de acesso sem precisar reinserir credenciais, mantendo a sessão ativa de forma segura.

## Decision
Vamos implementar o fluxo de refresh token conforme especificado no RFC 6749 (OAuth 2.0 Authorization Framework), incluindo:
- Endpoint POST /api/auth/refresh para troca de refresh token por novo access token
- Validação de refresh tokens com verificação de assinatura, expiração e revogação
- Lógica de rotação de tokens (geração de novo access token e novo refresh token)
- Suporte a header X-Refresh-Token ou cookie para transmissão segura
- Armazenamento seguro de refresh tokens (ex: Redis ou banco de dados)
- Seguimento das diretrizes de segurança do OWASP para refresh tokens

## Consequences
### Positivos
- Melhoria na experiência do usuário ao reduzir a necessidade de reautenticação frequente
- Aumento da segurança através de tokens de acesso de curta duração
- Conformidade com padrões industriais de autenticação
- Facilidade de implementação e manutenção devido ao uso de padrão estabelecido

### Negativos
- Complexidade adicional no sistema de autenticação
- Necessidade de armazenamento e gerenciamento de refresh tokens
- Overhead de validação adicional em cada requisição de refresh

## Implementation Plan
1. Criar endpoint POST /api/auth/refresh em AuthenticationBaseController
2. Adicionar método validateRefreshToken() em JwtCoreManager
3. Implementar lógica de rotação de tokens
4. Adicionar suporte a header X-Refresh-Token no CoreJwtAuthenticationFilter
5. Implementar armazenamento seguro de refresh tokens
6. Adicionar validações de nulidade, expiração e revogação
7. Seguir RFC 6749 e diretrizes OWASP para segurança

## Related Documents
- REFACTORING.md (itens 12 e 13 sobre refresh token)
- RFC 6749: OAuth 2.0 Authorization Framework
- OWASP Authentication Security Guidelines
