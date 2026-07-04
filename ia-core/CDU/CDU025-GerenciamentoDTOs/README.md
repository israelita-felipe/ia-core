# CDU - Gerenciamento de DTOs

## 1. Metadados
- **Nome do CDU**: Gerenciamento de DTOs
- **Versão**: 1.0
- **Data**: 2026-06-18
- **Autor**: IA Core
- **Status**: Em Revisão

## 2. Descrição do Caso de Uso

### 2.1. Descrição Breve
O caso de uso "Gerenciamento de DTOs" define o padrão para criação e uso de Data Transfer Objects (DTOs) no sistema ia-core, incluindo interfaces base, classes abstratas e implementações concretas com suporte a clonagem, PropertyChangeSupport e CAMPOS para acesso type-safe a nomes de campos.

### 2.2. Objetivos
- Definir interface base DTO para todos os DTOs do sistema
- Fornecer classe abstrata AbstractDTO com PropertyChangeSupport
- Implementar suporte a clonagem de DTOs
- Definir padrão CAMPOS para acesso type-safe a nomes de campos
- Suportar DTOs de entidades com BaseEntityDTO

### 2.3. Escopo
**Incluído**:
- Interface DTO com método cloneObject()
- Classe abstrata AbstractDTO com PropertyChangeSupport
- Interface BaseEntityDTO para DTOs de entidades
- Padrão CAMPOS para acesso type-safe
- Suporte a clonagem e cópia de objetos

**Excluído**:
- Implementação específica de DTOs de domínio (cada módulo define seus próprios DTOs)
- Lógica de negócio específica (deve estar em Services)
- Validação de campos (deve usar Jakarta Validation)

## 3. Atores

| Ator | Descrição | Tipo |
|------|------------|------|
| Desenvolvedor | Desenvolvedor que cria e usa DTOs | Primário |
| Sistema | Sistema que gerencia DTOs | Sistema |

## 4. Pré-condições

### 4.1. Para Criar DTO
- Desenvolvedor deve conhecer o padrão de DTOs do ia-core
- Entidade ou modelo correspondente deve existir (para BaseEntityDTO)

### 4.2. Para Clonar DTO
- DTO deve implementar interface DTO
- Método cloneObject() deve estar implementado

## 5. Pós-condições

### 5.1. Após Criar DTO
- DTO deve implementar interface DTO ou BaseEntityDTO
- DTO deve ter classe interna CAMPOS com constantes de campos
- DTO deve ter método cloneObject() implementado

### 5.2. Após Clonar DTO
- Novo DTO deve ser independente do original
- PropertyChangeSupport deve ser inicializado corretamente

## 6. Fluxo Principal

### 6.1. Criação de DTO Básico
**Given**: Desenvolvedor precisa criar um DTO para transferência de dados
**When**: Desenvolvedor cria classe DTO implementando interface DTO
**Then**: DTO deve ter método cloneObject() e classe interna CAMPOS

### 6.2. Criação de DTO de Entidade
**Given**: Desenvolvedor precisa criar DTO para uma entidade JPA
**When**: Desenvolvedor cria classe DTO implementando BaseEntityDTO
**Then**: DTO deve ter método getId() e getVersion()

### 6.3. Clonagem de DTO
**Given**: Instância de DTO existente
**When**: Método cloneObject() é chamado
**Then**: Nova instância independente é retornada com os mesmos valores

### 6.4. Cópia de DTO
**Given**: Instância de DTO existente
**When**: Método copyObject() é chamado
**Then**: Nova instância é retornada (usando cloneObject() por padrão)

## 7. Fluxos Alternativos

### 7.1. DTO com PropertyChangeSupport
**Given**: Desenvolvedor precisa notificar mudanças de propriedades
**When**: DTO estende AbstractDTO
**Then**: PropertyChangeSupport é automaticamente incluído

### 7.2. DTO com Builder
**Given**: Desenvolvedor precisa criar DTO com builder
**When**: DTO usa anotação @SuperBuilder do Lombok
**Then**: Builder é gerado automaticamente com suporte a toBuilder()

## 8. Fluxos de Exceção

### 8.1. CloneObject Não Implementado
**Given**: DTO não implementa método cloneObject()
**When**: Método cloneObject() é chamado
**Then**: Exceção AbstractMethodError é lançada

### 8.2. DTO Nulo
**Given**: Referência nula a DTO
**When**: Método copyObject() é chamado
**Then**: NullPointerException é lançada

## 9. Regras de Negócio

| ID | Regra | Descrição |
|----|-------|-----------|
| RN001 | PadraoCampos | Todo DTO deve ter classe interna CAMPOS com constantes String para cada campo |
| RN002 | CloneObrigatorio | Todo DTO deve implementar método cloneObject() retornando nova instância |
| RN003 | ImutabilidadeClonagem | DTO clonado deve ser independente do original (deep clone quando necessário) |
| RN004 | PropertyChangeSupport | AbstractDTO fornece PropertyChangeSupport automaticamente |
| RN005 | BaseEntityDTO | DTOs de entidades devem implementar BaseEntityDTO com getId() e getVersion() |

## 10. Estrutura de Dados

### 10.1. DTO
```java
public interface DTO<T extends Serializable> extends Serializable {
    DTO<T> cloneObject();
    default <R extends DTO<T>> R copyObject() {
        return (R) cloneObject();
    }
}
```

### 10.2. AbstractDTO
```java
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public abstract class AbstractDTO<T extends Serializable>
    implements DTO<T>, HasPropertyChangeSupport {
    private static final long serialVersionUID = -5587338226555597583L;
    @Transient
    @JsonIgnore
    protected transient final PropertyChangeSupport propertyChangeSupport =
        new PropertyChangeSupport(this);
}
```

### 10.3. BaseEntityDTO
```java
public interface BaseEntityDTO<T extends BaseEntity>
    extends DTO<T>, HasVersion<Long> {
    Long getId();
}
```

### 10.4. Exemplo de DTO Concreto
```java
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class PessoaDTO extends AbstractDTO<Pessoa> {
    public static final class CAMPOS {
        public static final String NOME = "nome";
        public static final String CPF = "cpf";
    }

    private String nome;
    private String cpf;

    @Override
    public PessoaDTO cloneObject() {
        return toBuilder().build();
    }
}
```

## 11. Contratos de Interface

### 11.1. Interface DTO
- **cloneObject()**: Retorna nova instância do DTO com os mesmos valores
- **copyObject()**: Retorna cópia covariante do DTO (padrão usa cloneObject())

### 11.2. Interface BaseEntityDTO
- **getId()**: Retorna ID da entidade
- **getVersion()**: Retorna versão para controle de concorrência (herdado de HasVersion)

## 12. Requisitos Especiais

### 12.1. Performance
- Clonagem deve ser eficiente (usar builder quando possível)
- PropertyChangeSupport deve ser transient para não ser serializado

### 12.2. Serialização
- Todos os DTOs devem implementar Serializable
- PropertyChangeSupport deve ser marcado como transient

### 12.3. Imutabilidade
- DTOs devem ser imutáveis quando possível (usar @Value em vez de @Data)
- Quando mutável, usar PropertyChangeSupport para notificar mudanças

## 13. Pontos de Extensão

### 13.1. Validadores Personalizados
- DTOs podem ter validadores Jakarta Validation
- Validações podem usar CAMPOS para acesso type-safe

### 13.2. Conversores
- DTOs podem ter conversores customizados para campos específicos
- Conversores podem usar PropertyChangeSupport para notificar mudanças

## 14. Referências

- [ADR-010: Padrões de Nomenclatura](/home/israel/git/ia-core-apps/ia-core/ADR/010-nomenclature-standards.md)
- [ADR-012: Testing Patterns](/home/israel/git/ia-core-apps/ia-core/ADR/012-testing-patterns.md)
- [ADR-040: DTO CAMPOS Pattern](/home/israel/git/ia-core-apps/ia-core/ADR/040-dto-campos-pattern.md)
