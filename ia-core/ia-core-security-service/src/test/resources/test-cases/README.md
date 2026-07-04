# Casos de Teste Security por Classe e Camada

Este diretório contém casos de teste documentados para DTOs e modelos dos módulos `ia-core-security-*`, organizados por camada da stack:

- Model
- Repository
- Mapper
- ServiceModel
- Service
- API/REST
- View/Client

## Matriz de cobertura

| Classe | Domínio | CDU | Casos presentes | Lacunas |
|--------|---------|-----|-----------------|---------|
| AuthenticationResponse | Modelo Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 1/1 | Completo |
| Functionality | Modelo Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 1/1 | Completo |
| Operation | Modelo Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 1/1 | Completo |
| JwtAuthenticationResponseDTO | Manter Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 7/7 | Completo |
| LogOperationDTO | Manter Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 7/7 | Completo |
| LogOperationDetails | Manter Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 7/7 | Completo |
| LogOperationSearchRequest | Manter Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 7/7 | Completo |
| OperationItemDetails | Manter Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 7/7 | Completo |
| PrivilegeDTO | Manter Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 7/7 | Completo |
| PrivilegeOperationContextDTO | Manter Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 7/7 | Completo |
| PrivilegeOperationDTO | Manter Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 7/7 | Completo |
| PrivilegeSearchRequest | Manter Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 7/7 | Completo |
| RoleDTO | Manter Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 7/7 | Completo |
| RolePrivilegeDTO | Manter Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 7/7 | Completo |
| RoleSearchRequest | Manter Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 7/7 | Completo |
| UserDTO | Manter Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 7/7 | Completo |
| UserPasswordChangeDTO | Manter Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 7/7 | Completo |
| UserPasswordResetDTO | Manter Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 7/7 | Completo |
| UserPrivilegeDTO | Manter Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 7/7 | Completo |
| UserRoleDTO | Manter Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 7/7 | Completo |
| UserSearchRequest | Manter Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 7/7 | Completo |
| AuthenticationRequest | Modelo Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 1/1 | Completo |
| JwtToken | Modelo Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 1/1 | Completo |
| TokenValidationResult | Modelo Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 1/1 | Completo |
| OperationEnum | Modelo Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 1/1 | Completo |
| LogOperation | Modelo Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 1/1 | Completo |
| Privilege | Modelo Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 1/1 | Completo |
| PrivilegeOperation | Modelo Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 1/1 | Completo |
| PrivilegeOperationContext | Modelo Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 1/1 | Completo |
| PrivilegeType | Modelo Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 1/1 | Completo |
| Role | Modelo Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 1/1 | Completo |
| RolePrivilege | Modelo Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 1/1 | Completo |
| User | Modelo Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 1/1 | Completo |
| UserPrivilege | Modelo Security | [CDU/Manter-Security: Manter Security](../../../../CDU/Manter-Security/README.md) | 1/1 | Completo |

## Padrão de nomenclatura

Os arquivos seguem o padrão:

```text
<NomeClasse>-<Camada>-Layer.md
```

Exemplo: `UserDTO-ServiceModel-Layer.md`.

## Aderência a ADRs

Todos os casos devem conter a seção `## Aderência a ADRs`, com metadados, matriz de conformidade, critérios de aceitação, evidências esperadas e referências ADR.

## Referências

- [ADR-012 - Padrões de Teste Automatizado](../../../../ADR/012-testing-patterns.md)
- [ADR-050 - Diretrizes Gerais de Padronização](../../../../ADR/050-standardization-guidelines.md)
- [ADR-052 - MADR e Linguagem Normativa](../../../../ADR/052-adopt-madr-and-rfc-normative-language-for-adrs.md)
