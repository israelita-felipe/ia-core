# ADR-001: Usar MapStruct para Mapeamento DTO-Entidade

## Status

✅ Aceito

## Contexto

O projeto precisa de uma forma eficiente e type-safe de mapear entre entidades JPA e DTOs para a camada de apresentação.

## Decisão

Usar **MapStruct** como biblioteca de mapeamento objeto-objeto.

## Detalhes

### Alternativas Consideradas

| Alternativa | Prós | Contras |
|-------------|------|---------|
| MapStruct | Type-safe, compile-time validation, rápido | Requer annotation processor |
| ModelMapper | API simples | Runtime overhead, não type-safe |
| Manual | Controle total | Boilerplate, erro-prone |
| Dozer | Mature | Deprecated, lento |

### Critérios de Decisão

1. **Type Safety** - Erros em tempo de compilação
2. **Performance** - Zero overhead em runtime
3. **Manutenibilidade** - Código gerado legível
4. **Integração Spring** - Suporte nativo a Spring

## Implementação

```java
@Mapper(componentModel = "spring", uses = { EnderecoMapper.class })
public interface PessoaMapper {
    
    @Mapping(target = "enderecos", source = "enderecos")
    @Mapping(target = "contatos", source = "contatos")
    PessoaDTO toDTO(Pessoa pessoa);
    
    @Mapping(target = "enderecos", source = "enderecos")
    @Mapping(target = "contatos", source = "contatos")
    Pessoa toEntity(PessoaDTO dto);
}
```

## Consequências

### Positivas

- ✅ Type-safe, erros detectados em compilação
- ✅ Performance superior (código gerado)
- ✅ Suporte a Spring (`componentModel = "spring"`)
- ✅ Mapeamento bidirecional
- ✅ Suporte a mapeamentos aninhados (`uses`)

### Negativas

- ❌ Requer annotation processor no build
- ❌ curva de aprendizado inicial

## Status de Implementação

✅ **COMPLETO**

- MapStruct configurado em todos os módulos
- 40+ mappers implementados
- Documentação em [`CODING_STANDARDS.md`](../CODING_STANDARDS.md)

## Data

2024-01-15

## Revisores

- Team Lead
- Architect
