# CDU - Filtragem Dinâmica

## 1. Metadados
- **Nome do CDU**: Filtragem Dinâmica
- **Versão**: 1.0
- **Data**: 2026-06-18
- **Autor**: IA Core
- **Status**: Em Revisão

## 2. Descrição do Caso de Uso

### 2.1. Descrição Breve
O caso de uso "Filtragem Dinâmica" define o padrão para construção de filtros dinâmicos em consultas de dados, permitindo que usuários especifiquem critérios de busca flexíveis através de DTOs especializados. O padrão suporta operadores de comparação, composição de múltiplos filtros, ordenação e paginação.

### 2.2. Objetivos
- Definir estrutura padrão para filtros dinâmicos
- Suportar múltiplos operadores de comparação (igual, diferente, like, in, maior, menor, etc.)
- Permitir composição de filtros com AND/OR
- Suportar ordenação dinâmica de resultados
- Fornecer paginação configurável
- Habilitar filtros de contexto (filtros aplicados automaticamente pelo sistema)

### 2.3. Escopo
**Incluído**:
- OperatorDTO: Enum de operadores de comparação
- FilterRequestDTO: DTO para representar um filtro individual
- SearchRequestDTO: DTO para encapsular requisições de busca complexas
- Construção de Predicates através de reflexão
- Suporte a múltiplos filtros com lógica AND/OR
- Ordenação dinâmica
- Paginação configurável

**Excluído**:
- Validação de valores de filtros (delegado para camada de serviço)
- Execução de consultas (delegado para repositórios)
- Tratamento de erros de sintaxe (delegado para validação)

## 3. Atores

| Ator | Descrição | Tipo |
|------|------------|------|
| Desenvolvedor | Desenvolvedor que usa o padrão para construir filtros | Primário |
| Usuário Final | Usuário que especifica critérios de busca | Secundário |
| Sistema | Sistema que aplica filtros em consultas | Sistema |

## 4. Pré-condições

### 4.1. Para Criar Filtro
- DTO de entidade deve estar disponível
- Campos filtráveis devem estar definidos
- Operadores suportados devem ser conhecidos

### 4.2. Para Aplicar Filtro
- Requisição de busca deve estar configurada
- Repositório ou serviço deve suportar aplicação de filtros
- Valores de filtros devem ser válidos

## 5. Pós-condições

### 5.1. Após Aplicação Bem-Sucedida
- Filtros são aplicados à consulta
- Resultados são filtrados conforme critérios especificados
- Ordenação é aplicada conforme especificado
- Paginação é respeitada

### 5.2. Após Aplicação com Erros
- Erros de validação são reportados
- Consulta pode ser executada sem filtros problemáticos
- Mensagens de erro são claras e acionáveis

## 6. Fluxo Principal

### 6.1. Criação de Filtro Simples
**Given**: Usuário deseja filtrar por campo específico
**When**: Desenvolvedor cria FilterRequestDTO com key, operator e value
**Then**: Filtro está pronto para ser aplicado à consulta

### 6.2. Criação de Filtro com Operador LIKE
**Given**: Usuário deseja busca por texto parcial
**When**: Desenvolvedor usa OperatorDTO.LIKE com valor contendo wildcard
**Then**: Filtro realiza busca por correspondência parcial

### 6.3. Criação de Filtro com Operador IN
**Given**: Usuário deseja filtrar por múltiplos valores
**When**: Desenvolvedor usa OperatorDTO.IN com lista de valores
**Then**: Filtro verifica se campo está na lista especificada

### 6.4. Criação de Requisição de Busca Complexa
**Given**: Usuário deseja buscar com múltiplos critérios
**When**: Desenvolvedor cria SearchRequestDTO com múltiplos filtros
**Then**: Requisição contém todos os filtros configurados

### 6.5. Aplicação de Ordenação
**Given**: Usuário deseja resultados ordenados
**When**: Desenvolvedor adiciona sort à SearchRequestDTO
**Then**: Resultados são ordenados conforme especificado

### 6.6. Aplicação de Paginação
**Given**: Usuário deseja resultados paginados
**When**: Desenvolvedor configura page e size na SearchRequestDTO
**Then**: Resultados são paginados conforme especificado

## 7. Fluxos Alternativos

### 7.1. Filtro com Contexto
**Given**: Sistema precisa aplicar filtros automáticos
**When**: Desenvolvedor adiciona contextFilters à SearchRequestDTO
**Then**: Filtros de contexto são aplicados automaticamente

### 7.2. Filtro com Disjunção
**Given**: Usuário deseja busca com lógica OR
**When**: Desenvolvedor configura disjunction=true na SearchRequestDTO
**Then**: Filtros são aplicados com lógica OR em vez de AND

### 7.3. Filtro Negado
**Given**: Usuário deseja negar um filtro específico
**When**: Desenvolvedor configura negate=true no FilterRequestDTO
**Then**: Filtro é aplicado com lógica de negação

## 8. Fluxos de Exceção

### 8.1. Operador Não Suportado
**Given**: Usuário especifica operador inválido
**When**: Sistema tenta aplicar filtro
**Then**: Erro é reportado indicando operador não suportado

### 8.2. Campo Inexistente
**Given**: Usuário especifica campo que não existe
**When**: Sistema tenta acessar campo via reflexão
**Then**: Erro é reportado indicando campo inexistente

### 8.3. Tipo de Valor Incorreto
**Given**: Usuário especifica valor com tipo incorreto
**When**: Sistema tenta converter valor
**Then**: Erro de conversão é reportado

## 9. Regras de Negócio

| ID | Regra | Descrição |
|----|-------|-----------|
| RN001 | OperadorObrigatório | Todo filtro deve ter um operador especificado |
| RN002 | KeyObrigatoria | Todo filtro deve ter uma key (nome do campo) |
| RN003 | ValorObrigatorio | Filtros de comparação requerem valor (exceto IS_NULL/IS_NOT_NULL) |
| RN004 | TipoCampo | fieldType deve corresponder ao tipo do campo na entidade |
| RN005 | PaginaçãoDefault | Se page/size não especificados, usar valores padrão |
| RN006 | OrdenacaoDefault | Se sort não especificado, usar ordenação padrão |
| RN007 | ContextoPrioritario | Filtros de contexto têm prioridade sobre filtros do usuário |

## 10. Estrutura de Dados

### 10.1. OperatorDTO
```java
public enum OperatorDTO {
    EQUAL, NOT_EQUAL, LIKE, NOT_LIKE, IN, NOT_IN,
    GREATER_THAN, GREATER_THAN_OR_EQUAL, LESS_THAN, LESS_THAN_OR_EQUAL,
    IS_NULL, IS_NOT_NULL, BETWEEN, NOT_BETWEEN;

    public Predicate build(Object value, String fieldName, Class<?> fieldType);
}
```

### 10.2. FilterRequestDTO
```java
public class FilterRequestDTO {
    private String key;           // Nome do campo
    private OperatorDTO operator; // Operador de comparação
    private String fieldType;     // Tipo do campo
    private Object value;         // Valor do filtro
    private boolean negate;       // Se filtro deve ser negado

    public static class CAMPOS {
        public static final String KEY = "key";
        public static final String OPERATOR = "operator";
        public static final String FIELD_TYPE = "fieldType";
        public static final String VALUE = "value";
        public static final String NEGATE = "negate";
    }
}
```

### 10.3. SearchRequestDTO
```java
public class SearchRequestDTO {
    private List<FilterRequestDTO> filters;       // Filtros do usuário
    private List<FilterRequestDTO> contextFilters; // Filtros de contexto
    private List<Sort> sorts;                     // Ordenação
    private Integer page;                         // Número da página
    private Integer size;                         // Tamanho da página
    private boolean disjunction;                  // Lógica OR em vez de AND

    public static Map<String, Object> createFilters(List<FilterRequestDTO> filters);
}
```

## 11. Contratos de Interface

### 11.1. OperatorDTO.build()
- **Parâmetros**: value (Object), fieldName (String), fieldType (Class<?>)
- **Retorno**: Predicate
- **Comportamento**: Constrói Predicate para aplicar filtro usando reflexão

### 11.2. FilterRequestDTO
- **key**: Nome do campo a ser filtrado
- **operator**: Operador de comparação a ser aplicado
- **fieldType**: Tipo do campo para conversão correta
- **value**: Valor a ser comparado
- **negate**: Se true, inverte o resultado do filtro

### 11.3. SearchRequestDTO
- **filters**: Lista de filtros especificados pelo usuário
- **contextFilters**: Lista de filtros aplicados automaticamente pelo sistema
- **sorts**: Lista de critérios de ordenação
- **page**: Número da página (0-indexed)
- **size**: Tamanho da página
- **disjunction**: Se true, usa lógica OR entre filtros

## 12. Requisitos Especiais

### 12.1. Performance
- Construção de Predicates deve ser eficiente
- Reflexão deve ser cacheada quando possível
- Índices de banco de dados devem ser considerados

### 12.2. Segurança
- Campos sensíveis não devem ser expostos para filtragem
- Valores de filtros devem ser sanitizados contra SQL injection
- Validação de tipos deve ser rigorosa

### 12.3. Internacionalização
- Mensagens de erro devem ser internacionalizadas
- Nomes de campos podem ter labels internacionalizados

## 13. Pontos de Extensão

### 13.1. Operadores Customizados
- Módulos podem adicionar operadores específicos ao OperatorDTO
- Operadores customizados devem seguir o padrão de build()

### 13.2. Validadores de Filtro
- Módulos podem adicionar validação específica para filtros
- Validadores podem verificar regras de negócio específicas

### 13.3. Conversores de Valor
- Módulos podem adicionar conversores customizados para tipos específicos
- Conversores podem lidar com formatos complexos (data, moeda, etc.)

## 14. Referências

- [ADR-040: DTO CAMPOS Pattern](/home/israel/git/ia-core-apps/ia-core/ADR/040-dto-campos-pattern.md)
- [ADR-012: Testing Patterns](/home/israel/git/ia-core-apps/ia-core/ADR/012-testing-patterns.md)
