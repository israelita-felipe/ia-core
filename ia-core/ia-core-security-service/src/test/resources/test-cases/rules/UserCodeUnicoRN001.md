# Caso de Teste: UserCodeUnicoRN001

## Descrição
Caso de teste para a regra de negócio USER_RN001 - Código de usuário único.

## Classe Testada
`com.ia.core.security.service.user.rules.UserCodeUnicoRN001`

## Objetivo
Validar que o código de usuário é único no sistema conforme definido na RN002 do CDU015-Manter-Security.

## Cenários

### Cenário 1: Cenário feliz - userCode válido
**Given**: UserDTO com userCode não nulo e não vazio.
**When**: Regra valida o DTO.
**Then**: Nenhum erro é adicionado ao resultado.

### Cenário 2: userCode nulo
**Given**: UserDTO com userCode nulo.
**When**: Regra valida o DTO.
**Then**: Nenhum erro é adicionado (regra não aplicável).

### Cenário 3: UserDTO nulo
**Given**: UserDTO nulo.
**When**: Regra valida o DTO.
**Then**: Nenhum erro é adicionado (regra não aplicável).

## Referências
- [CDU015-Manter-Security](../../../../CDU/CDU015-Manter-Security/README.md)
- [ADR-018 - Business Rule Chain Pattern](../../../../ADR/018-use-business-rule-chain-pattern.md)
