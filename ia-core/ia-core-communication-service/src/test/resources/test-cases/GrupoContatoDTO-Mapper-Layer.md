# GrupoContatoMapper - Casos de Teste da Camada de Mapper

## Visão Geral
Casos de teste para a classe `GrupoContatoTranslator`, verificando conversão entre entidade e DTO.

## Conformidade com ADR-012
- **Classe de Teste Base**: BaseUnitTest
- **Referências CDU**: N/A (domínio de comunicação)
- **Tipo de Teste**: Testes unitários
- **Padrões de Teste**: AssertJ, padrão AAA, Mockito

## Cenários de Teste

### 1. Conversão de Entidade para DTO
**Dado**: Uma entidade GrupoContato
**Quando**: Convertida para DTO
**Então**: Deve manter todos os campos corretamente

#### 1.1 Conversão bem-sucedida
- **Dado**: Uma entidade GrupoContato com todos os campos
- **Quando**: toDTO(entity) é chamado
- **Então**: Deve retornar DTO com todos os campos mapeados
- **Casos de Borda**: Nenhum

#### 1.2 Conversão com campos nulos
- **Dado**: Uma entidade GrupoContato com campos nulos
- **Quando**: toDTO(entity) é chamado
- **Então**: Deve retornar DTO com campos nulos
- **Casos de Borda**: Nenhum

### 2. Conversão de DTO para Entidade
**Dado**: Um DTO GrupoContatoDTO
**Quando**: Convertido para entidade
**Então**: Deve manter a integridade dos dados

#### 2.1 Conversão bem-sucedida
- **Dado**: Um DTO GrupoContatoDTO com todos os campos
- **Quando**: toEntity(dto) é chamado
- **Então**: Deve retornar entidade com todos os campos mapeados
- **Casos de Borda**: Nenhum

#### 2.2 Conversão com campos nulos
- **Dado**: Um DTO GrupoContatoDTO com campos nulos
- **Quando**: toEntity(dto) é chamado
- **Então**: Deve retornar entidade com campos nulos
- **Casos de Borda**: Nenhum

### 3. Mapeamento de Lista de Contatos
**Dado**: Uma entidade GrupoContato com contatos
**Quando**: Convertida para DTO
**Então**: Deve mapear lista de contatos

#### 3.1 Mapeamento bem-sucedido
- **Dado**: Uma entidade GrupoContato com lista de contatos
- **Quando**: toDTO(entity) é chamado
- **Então**: Deve retornar DTO com lista de contatos mapeada
- **Casos de Borda**: Lista vazia

#### 3.2 Mapeamento com lista nula
- **Dado**: Uma entidade GrupoContato com lista de contatos nula
- **Quando**: toDTO(entity) é chamado
- **Então**: Deve retornar DTO com lista de contatos nula
- **Casos de Borda**: Nenhum

## Fluxo do Teste
1. Criar instância de GrupoContatoTranslator
2. Testar conversão de entidade para DTO (sucesso, campos nulos)
3. Testar conversão de DTO para entidade (sucesso, campos nulos)
4. Testar mapeamento de lista de contatos (sucesso, lista nula)
5. Verificar casos de borda (valores nulos, listas vazias)
