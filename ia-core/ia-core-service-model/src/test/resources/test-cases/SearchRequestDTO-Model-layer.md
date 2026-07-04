# Casos de Teste - SearchRequestDTO (Model Layer)

## CDU Relacionado
CDU027-FiltragemDinamica

## Descrição
Casos de teste básicos para validar o padrão SearchRequestDTO no módulo ia-core-service-model.

## Cenários de Teste

### CT001 - Criação de Requisição de Busca Simples
**Descrição**: Validar criação com filtros básicos
**Entrada**: SearchRequestDTO com lista de filtros
**Resultado Esperado**: Requisição criada com filtros configurados
**Tipo**: Unitário
**Prioridade**: Alta

### CT002 - Configuração de Paginação
**Descrição**: Validar configuração de page e size
**Entrada**: SearchRequestDTO com page=0, size=10
**Resultado Esperado**: Paginação configurada corretamente
**Tipo**: Unitário
**Prioridade**: Alta

### CT003 - Adição de Filtros de Contexto
**Descrição**: Validar adição de contextFilters
**Entrada**: SearchRequestDTO com contextFilters configurados
**Resultado Esperado**: Filtros de contexto adicionados
**Tipo**: Unitário
**Prioridade**: Alta

### CT004 - Configuração de Disjunção
**Descrição**: Validar configuração de disjunction=true
**Entrada**: SearchRequestDTO com disjunction=true
**Resultado Esperado**: Lógica OR configurada
**Tipo**: Unitário
**Prioridade**: Média

### CT005 - Criação de Mapa de Filtros
**Descrição**: Validar método createFilters()
**Entrada**: Lista de FilterRequestDTO
**Resultado Esperado**: Map<String, Object> criado
**Tipo**: Unitário
**Prioridade**: Alta

### CT006 - Requisição sem Filtros
**Descrição**: Validar comportamento sem filtros
**Entrada**: SearchRequestDTO sem filtros
**Resultado Esperado**: Requisição criada sem exceções
**Tipo**: Unitário
**Prioridade**: Média

## Gaps de Cobertura
- Testes de integração com repositórios
- Testes de ordenação dinâmica
- Testes de performance com muitos filtros

## Referências
- CDU027-FiltragemDinamica
- ADR-012: Testing Patterns
