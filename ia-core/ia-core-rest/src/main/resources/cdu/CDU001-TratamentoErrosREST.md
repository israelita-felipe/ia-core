# CDU001: Tratamento de Erros REST

## Metadados
- **Nome do CDU**: CDU001-TratamentoErrosREST
- **Versão**: 1.0
- **Data**: 2025-06-17
- **Autor**: IA Core
- **Status**: Em Revisão

## Descrição do Caso de Uso

### Descrição Breve
Este caso de uso descreve como o sistema trata erros na camada REST, fornecendo respostas padronizadas e consistentes para o cliente através do ErrorResponse e códigos de erro padronizados.

### Objetivos
- Fornecer respostas de erro padronizadas para o cliente
- Facilitar o tratamento de erros no cliente com estrutura consistente
- Suportar diferentes tipos de erro (validação, autenticação, autorização, servidor)
- Permitir rastreamento de erros através de trace ID

### Escopo
- **Incluído**: Geração de ErrorResponse, códigos de erro padronizados, tratamento de exceções
- **Excluído**: Implementação interna de filtros de autenticação

## Atores

| Ator | Descrição | Tipo |
|------|------------|------|
| Cliente REST | Aplicação cliente que consome a API REST | Primário |
| Sistema | Aplicação Spring Boot que processa requisições REST | Secundário |

## Pré-condições
- **Precondição 1**: O módulo ia-core-rest deve estar configurado no classpath
- **Precondição 2**: O CoreRestControllerAdvice deve estar configurado como @ControllerAdvice
- **Precondição 3**: O sistema deve ter um mecanismo de geração de trace ID

## Pós-condições
- **Pós-condição de Sucesso**: O cliente recebe uma resposta de erro padronizada com todas as informações necessárias
- **Pós-condição de Falha**: O cliente recebe uma resposta de erro genérica se o tratamento falhar

## Fluxo Principal (Basic Flow)

**Trigger**: Uma exceção é lançada durante o processamento de uma requisição REST

**Passos**:
1. **Dado** uma requisição REST em processamento
2. **Quando** uma exceção é lançada
3. **Então** o CoreRestControllerAdvice intercepta a exceção
4. **E** o sistema identifica o tipo de exceção
5. **Quando** é uma exceção de validação [RN001]
6. **Então** o sistema cria ErrorResponse com detalhes de validação
7. **Quando** é uma exceção de autenticação [RN002]
8. **Então** o sistema cria ErrorResponse com código AUTHENTICATION_ERROR
9. **Quando** é uma exceção de autorização [RN003]
10. **Então** o sistema cria ErrorResponse com código ACCESS_DENIED
11. **Quando** é uma exceção de recurso não encontrado [RN004]
12. **Então** o sistema cria ErrorResponse com código ENTITY_NOT_FOUND
13. **Quando** é uma exceção genérica de servidor
14. **Então** o sistema cria ErrorResponse com código INTERNAL_ERROR
15. **E** o sistema inclui o trace ID da requisição
16. **E** o sistema retorna a resposta com status HTTP apropriado

## Fluxos Alternativos

**Fluxo Alternativo 1**: Erro com detalhes de campo
1. **Dado** uma exceção de validação com erros de campo
2. **Quando** o CoreRestControllerAdvice intercepta a exceção
3. **Então** o sistema cria ErrorResponse com fieldErrors
4. **E** o sistema inclui mapa de campo para mensagens de erro

## Fluxos de Exceção

**Fluxo de Exceção 1**: Falha ao gerar ErrorResponse
1. **Dado** uma exceção não tratada
2. **Quando** o sistema falha ao gerar ErrorResponse
3. **Então** o sistema retorna resposta de erro genérica com status 500

## Regras de Negócio

| ID | Regra de Negócio | Tipo | Aplicação |
|----|------------------|------|-----------|
| RN001 | Erros de validação devem retornar status 400 | Validação | Tratamento de erros |
| RN002 | Erros de autenticação devem retornar status 401 | Validação | Tratamento de erros |
| RN003 | Erros de autorização devem retornar status 403 | Validação | Tratamento de erros |
| RN004 | Recursos não encontrados devem retornar status 404 | Validação | Tratamento de erros |
| RN005 | Erros de servidor devem retornar status 500 | Validação | Tratamento de erros |

## Estrutura de Dados

```mermaid
erDiagram
    ERROR_RESPONSE ||--o{ ERROR_DETAIL : contains
    ERROR_RESPONSE {
        Instant timestamp
        int status
        string errorCode
        string message
        string path
        string traceId
        List[ErrorDetail] details
        Map[string, Set[string]] fieldErrors
        string exception
    }
    ERROR_DETAIL {
        string code
        string message
        string field
        object rejectedValue
    }
```

## Contratos de Interface

**Interface REST**:
| Método | Endpoint | Descrição | Status |
|--------|----------|------------|--------|
| GET | /api/${api.version}/** | Qualquer endpoint | 200, 400, 401, 403, 404, 500 |

## Requisitos Especiais
- **Performance**: O tratamento de erro deve ser rápido (< 10ms)
- **Segurança**: Não expor stack trace em produção
- **Usabilidade**: Mensagens de erro devem ser claras e acionáveis
- **Conformidade**: Deve seguir RFC 7231 para códigos de status HTTP

## Pontos de Extensão
- **Extensão 1**: Adicionar novos códigos de erro específicos do domínio
- **Extensão 2**: Integração com sistemas de monitoramento de erros

## Referências
- ADR-011: Exception Handling Patterns
- ADR-053: Usar CDU para Documentação de Casos de Uso
- RFC 7231: Hypertext Transfer Protocol (HTTP/1.1): Semantics and Content
