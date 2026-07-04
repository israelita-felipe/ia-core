# FlywayExecutionTranslator - Casos de Teste da Camada de Mapper

## Visão Geral
Casos de teste para a classe `FlywayExecutionTranslator`, verificando conversão entre entidade e DTO.

## Conformidade com ADR-012
- **Classe de Teste Base**: BaseAPITest
- **Referências CDU**: N/A (domínio Flyway)
- **Tipo de Teste**: Testes unitários
- **Padrões de Teste**: AssertJ, padrão AAA, Mockito

## Cenários de Teste

### 1. Conversão de Entidade para DTO
**Dado**: Uma entidade FlywayExecution
**Quando**: Convertida para DTO
**Então**: Deve manter todos os campos corretamente

#### 1.1 Conversão bem-sucedida
- **Dado**: Uma entidade FlywayExecution com campos preenchidos
- **Quando**: toDTO(entity) é chamado
- **Então**: Deve retornar DTO com todos os campos mapeados
- **Casos de Borda**: Nenhum

#### 1.2 Conversão com campos nulos
- **Dado**: Uma entidade FlywayExecution com campos nulos
- **Quando**: toDTO(entity) é chamado
- **Então**: Deve tratar campos nulos adequadamente
- **Casos de Borda**: Nenhum

### 2. Conversão de DTO para Entidade
**Dado**: Um DTO FlywayExecutionDTO
**Quando**: Convertido para entidade
**Então**: Deve manter a integridade dos dados

#### 2.1 Conversão bem-sucedida
- **Dado**: Um DTO FlywayExecutionDTO com campos preenchidos
- **Quando**: toEntity(dto) é chamado
- **Então**: Deve retornar entidade com todos os campos mapeados
- **Casos de Borda**: Nenhum

#### 2.2 Conversão com campos nulos
- **Dado**: Um DTO FlywayExecutionDTO com campos nulos
- **Quando**: toEntity(dto) é chamado
- **Então**: Deve tratar campos nulos adequadamente
- **Casos de Borda**: Nenhum

### 3. Mapeamento de Timestamp
**Dado**: Uma entidade ou DTO com timestamp
**Quando**: Convertida
**Então**: Deve manter o timestamp corretamente

#### 3.1 Mapeamento de timestamp válido
- **Dado**: Uma entidade com timestamp válido
- **Quando**: Convertida para DTO
- **Então**: Deve manter o timestamp
- **Casos de Borda**: Nenhum

#### 3.2 Mapeamento de timestamp nulo
- **Dado**: Uma entidade com timestamp nulo
- **Quando**: Convertida para DTO
- **Então**: Deve tratar timestamp nulo adequadamente
- **Casos de Borda**: Nenhum

## Fluxo do Teste
1. Criar instância de FlywayExecutionTranslator
2. Testar conversão de entidade para DTO (sucesso, campos nulos)
3. Testar conversão de DTO para entidade (sucesso, campos nulos)
4. Testar mapeamento de timestamp (válido, nulo)
5. Verificar casos de borda (valores nulos)
