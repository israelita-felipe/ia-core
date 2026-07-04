# GrupoContatoDTO - Casos de Teste da Camada de API

## Visão Geral
Casos de teste para a classe `GrupoContatoDTO`, verificando campos de transferência de dados de grupos de contato.

## Conformidade com ADR-012
- **Classe de Teste Base**: BaseAPITest
- **Referências CDU**: N/A (domínio de comunicação)
- **Tipo de Teste**: Testes unitários
- **Padrões de Teste**: AssertJ, padrão AAA, Mockito

## Cenários de Teste

### 1. Inicialização do Construtor
**Dado**: Uma instância de GrupoContatoDTO
**Quando**: O DTO é instanciado
**Então**: Deve inicializar com valores padrão

#### 1.1 Construtor padrão
- **Dado**: Sem parâmetros
- **Quando**: new GrupoContatoDTO() é chamado
- **Então**: Deve criar instância com valores padrão dos campos
- **Casos de Borda**: Nenhum

#### 1.2 Construtor com builder
- **Dado**: Parâmetros do builder
- **Quando**: Builder é utilizado
- **Então**: Deve criar instância com valores fornecidos
- **Casos de Borda**: Parâmetros nulos

### 2. Validação de Campos Obrigatórios
**Dado**: Uma GrupoContatoDTO
**Quando**: Campos obrigatórios são validados
**Então**: Deve validar restrições dos campos

#### 2.1 Campos obrigatórios presentes
- **Dado**: Uma GrupoContatoDTO com campos obrigatórios
- **Quando**: Validação é realizada
- **Então**: Deve passar na validação
- **Casos de Borda**: Nenhum

#### 2.2 Campos obrigatórios ausentes
- **Dado**: Uma GrupoContatoDTO sem campos obrigatórios
- **Quando**: Validação é realizada
- **Então**: Deve falhar na validação
- **Casos de Borda**: Nome nulo, campos obrigatórios faltando

### 3. Verificação de Constantes CAMPOS
**Dado**: Uma GrupoContatoDTO
**Quando**: Constantes CAMPOS são verificadas
**Então**: Deve conter todos os nomes de campos

#### 3.1 Verificação de CAMPOS
- **Dado**: Uma GrupoContatoDTO
- **Quando**: CAMPOS é acessado
- **Então**: Deve retornar array com nomes de campos
- **Casos de Borda**: Nenhum

#### 3.2 Busca de campo por nome
- **Dado**: Uma GrupoContatoDTO
- **Quando**: Campo é buscado por nome
- **Então**: Deve retornar índice do campo
- **Casos de Borda**: Campo não encontrado

### 4. Teste de Métodos de Busca por Campos
**Dado**: Uma GrupoContatoDTO
**Quando**: Métodos de busca por campos são chamados
**Então**: Deve retornar valores corretos

#### 4.1 Busca por campo existente
- **Dado**: Uma GrupoContatoDTO com campos preenchidos
- **Quando**: Campo é buscado
- **Então**: Deve retornar valor do campo
- **Casos de Borda**: Nenhum

#### 4.2 Busca por campo inexistente
- **Dado**: Uma GrupoContatoDTO
- **Quando**: Campo inexistente é buscado
- **Então**: Deve retornar nulo ou lançar exceção
- **Casos de Borda**: Campo não encontrado

## Fluxo do Teste
1. Criar instância de GrupoContatoDTO
2. Testar inicialização do construtor (padrão, builder)
3. Testar validação de campos obrigatórios (presentes, ausentes)
4. Testar verificação de constantes CAMPOS
5. Testar métodos de busca por campos (existente, inexistente)
6. Verificar casos de borda (valores nulos, campos não encontrados)
