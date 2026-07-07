# Caso de Teste: SenhaMinimaCaracteresRN003

## Descrição
Caso de teste para a regra de negócio USER_RN003 - Senha mínima de 8 caracteres.

## Classe Testada
`com.ia.core.security.service.user.rules.validation.SenhaMinimaCaracteresRN003`

## Objetivo
Validar que a senha do usuário tem no mínimo 8 caracteres conforme definido na RN001 do CDU015-Manter-Security.

## Cenários

### Cenário 1: Cenário feliz - senha válida (8+ caracteres)
**Given**: UserDTO com senha de 8 ou mais caracteres.
**When**: Regra valida o DTO.
**Then**: Nenhum erro é adicionado ao resultado.

### Cenário 2: Senha inválida (menos de 8 caracteres)
**Given**: UserDTO com senha de 7 caracteres.
**When**: Regra valida o DTO.
**Then**: Erro é adicionado para campo password.

### Cenário 3: Senha nula
**Given**: UserDTO com senha nula.
**When**: Regra valida o DTO.
**Then**: Nenhum erro é adicionado (regra não aplicável).

### Cenário 4: UserDTO nulo
**Given**: UserDTO nulo.
**When**: Regra valida o DTO.
**Then**: Nenhum erro é adicionado (regra não aplicável).

## Referências
- [CDU015-Manter-Security](../../../../CDU/CDU015-Manter-Security/README.md)
- [ADR-018 - Business Rule Chain Pattern](../../../../ADR/018-use-business-rule-chain-pattern.md)
