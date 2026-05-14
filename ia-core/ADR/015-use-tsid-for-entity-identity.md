# ADR-015: Usar TSID para Identidade de Entidades

## Status

✅ Aceito

## Contexto

O projeto precisa de uma estratégia de geração de IDs que:
- Garanta unicidade em ambientes distribuídos
- Permita ordenação temporal dos registros
- Evite conflitos em migrações de dados
- Não dependa de auto-increment do banco de dados
- Seja seguro para uso em URLs e APIs públicas

## Decisão

Usar **TSID (Time-Sorted Unique Identifier)** como estratégia de geração de IDs para todas as entidades do domínio.

## Detalhes

### O que é TSID

TSID é um identificador de 64 bits composto por:
- **Componente temporal (42 bits)**: milissegundos desde 2020-01-01 (epoch customizado)
- **Componente aleatório (22 bits)**: bits gerados por gerador seguro

### Configuração no BaseEntity

```java
@MappedSuperclass
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity
  implements Serializable, HasVersion<Long>, Comparable<BaseEntity> {

  @Id
  private Long id;

  @PrePersist
  public void generateIdIfAbsent() {
    if (this.id == null) {
      this.id = TSID.Factory.getTsid4096().toLong();
    }
  }
}
```

### Variantes de Factory

| Factory | Nós Suportados | IDs/ms por Nó | Bits do Nó | Bits do Contador |
|---------|----------------|---------------|------------|------------------|
| `getTsid()` | 1024 | 4096 | 10 | 12 |
| `getTsid256()` | 256 | 16384 | 8 | 14 |
| `getTsid1024()` | 1024 | 4096 | 10 | 12 |
| `getTsid4096()` | 4096 | 1024 | 12 | 10 |

### Uso no Projeto

O projeto utiliza `getTsid4096()` para suportar até 4096 nós em ambiente distribuído:

```java
@PrePersist
public void generateIdIfAbsent() {
  if (this.id == null) {
    this.id = TSID.Factory.getTsid4096().toLong();
  }
}
```

### Configuração via Propriedades

```bash
# Definir identificador do nó (evita colisões)
export TSID_NODE=1

# Ou via propriedade do sistema
-Dtsid.node=1
```

### Alternativas Consideradas

| Alternativa | Prós | Contras |
|-------------|------|---------|
| Auto-increment | Simples, nativo do DB | Não distribuído, previsível |
| UUID v4 | Distribuído, seguro | Não ordenável, 128 bits |
| UUID v7 | Ordenável, distribuído | Mais complexo |
| **TSID** | Ordenável, distribuído, 64 bits | Requer configuração do nó |
| Snowflake ID | Ordenável, distribuído | Depende de configuração manual |

## Consequências

### Positivas
- ✅ IDs ordenáveis temporalmente (ótimo para índices e paginação)
- ✅ Suporte nativo a ambientes distribuídos
- ✅ Tamanho compacto (64 bits vs 128 bits do UUID)
- ✅ Não previsível externamente (componente aleatório)
- ✅ Sem dependência de auto-increment do banco
- ✅ Facilita migrações e merges de dados

### Negativas
- ❌ Requer configuração do nó para evitar colisões
- ❌ IDs maiores que auto-increment (Long vs Integer)
- ❌ Não legível por humanos (ex: 281474976710656)

## Status de Implementação

✅ **COMPLETO**

- [`TSID.java`](../../ia-core-model/src/main/java/com/ia/core/model/TSID.java) implementado
- Integrado em [`BaseEntity`](../../ia-core-model/src/main/java/com/ia/core/model/BaseEntity.java)
- Usado em todas as entidades do projeto

## Data

2024-03-10

## Revisores

- Team Lead
- Architect

## Referências

1. **TSID Library (Fabio Lima)**
   - URL: https://github.com/f4b6a3/tsid-creator
   - Biblioteca base utilizada (MIT License)

2. **Snowflake ID (Twitter)**
   - URL: https://blog.twitter.com/engineering/en_us/a/2010/announcing-snowflake
   - Conceito original de IDs ordenáveis distribuídos

3. **UUID v7 Draft**
   - URL: https://datatracker.ietf.org/doc/html/draft-peabody-dispatch-new-uuid-format
   - Alternativa moderna com ordenação temporal

4. **Baeldung - UUID vs TSID**
   - URL: https://www.baeldung.com/java-uuid
   - Comparação de estratégias de identificação
