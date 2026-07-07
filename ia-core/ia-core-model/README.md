# ia-core-model

## 📋 Descrição

Módulo base contendo entidades, modelos de domínio e utilitários compartilhados por todos os outros módulos do ia-core. Define as estruturas fundamentais e abstrações usadas em toda a aplicação.

## 🏗️ Estrutura

```
ia-core-model/
├── src/main/java/
│   └── com/ia/core/model/
│       ├── AbstractEntity.java       # Entidade base com auditoria
│       ├── domain/                   # Modelos de domínio
│       ├── util/                     # Utilitários compartilhados
│       └── filter/                   # Filtros dinâmicos
└── pom.xml
```

## 🔑 Responsabilidades

- **Entidades Base**: Define `AbstractEntity` com campos de auditoria (criação, modificação, versão)
- **TSID Generator**: Fornece identificadores distribuídos usando TSID
- **Filter Specifications**: Implementa padrão Specification para filtros dinâmicos em JPA
- **Utilitários Comuns**: Funções auxiliares reutilizáveis
- **DTOs Base**: Classes base para DTOs compartilhados

## 🛠️ Tecnologias Utilizadas

- Spring Boot
- Spring Data JPA
- Jakarta Persistence (JPA)
- Jakarta Validation
- Lombok (anotações de boilerplate)

## 📦 Dependências

- `spring-boot-starter`
- `spring-boot-starter-data-jpa` (opcional, herdado do parent)
- `lombok`

## 🔗 Relacionamentos

Este módulo é **base fundamental** para:
- `ia-core-service` - Herda entidades e modelos
- `ia-core-security-model` - Estende modelos de segurança
- `ia-core-llm-model` - Estende modelos para LLM
- Todos os outros módulos que usam entidades JPA

## 💡 Padrões Implementados

- **Repository Pattern**: Interface genérica para acesso a dados
- **Domain-Driven Design**: Entidades refletem conceitos de negócio
- **Auditing**: Rastreamento automático de criador e modificador
- **Base Class**: Herança de campos comuns (`AbstractEntity`)

## 🚀 Como Usar

### Herdar de AbstractEntity

```java
@Entity
@Table(name = "minha_tabela")
@Data
@Builder
public class MinhaEntidade extends AbstractEntity {
    @Column(nullable = false)
    private String nome;

    // ... mais atributos
}
```

### Usar Specification para Filtros

```java
// No repository
public interface MinhaRepository extends JpaRepository<MinhaEntidade, Long>,
    JpaSpecificationExecutor<MinhaEntidade> {}

// No serviço
List<MinhaEntidade> resultado = minhaRepository.findAll(
    Specification.where(/* suas condições */)
);
```

## 📖 Arquivos Importantes

- `pom.xml` - Configuração Maven
- `src/main/java/` - Código-fonte das entidades e utilitários

## 🧪 Testes

Os testes unitários estão em `src/test/java/` e cobrem:
- Criação e validação de entidades
- Especificações de filtro
- Utilitários gerais

**Tipos de Testes Aplicáveis** (conforme ADR-012):
- **Testes Unitários**: Entidades JPA, Specifications, Utilitários
- **Testes de Validação**: Jakarta Validation annotations
- **Testes de Conversão**: Mappers e Converters

**Cobertura Mínima**: 85% (conforme ADR-012)

## 📚 ADRs Relevantes

- **ADR-001**: Usar MapStruct para Mapeamento
- **ADR-002**: Usar Specification para Filtros
- **ADR-007**: Usar BaseEntity para Padronização
- **ADR-010**: Padrões de Nomenclatura
- **ADR-012**: Testing Patterns
- **ADR-015**: Usar TSID para Identidade
- **ADR-020**: Usar SuperBuilder para JPA

## 🤝 Contribuição

Ao adicionar novas entidades base:
1. Estenda `AbstractEntity`
2. Use Lombok `@Data`, `@Builder`
3. Adicione validações com `@Valid` e `@NotNull`
4. Documente campos com comentários JavaDoc

## 📝 Notas

- Este módulo não contém lógica de negócio complexa
- Deve permanecer estável para evitar quebras em dependentes
- Mudanças aqui afetam todos os outros módulos


