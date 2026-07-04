# CDU003: Diálogos de Confirmação

## Metadados
- **Nome do CDU**: CDU003-DialogosConfirmacao
- **Versão**: 1.0
- **Data**: 2025-06-18
- **Autor**: IA Core
- **Status**: Em Revisão

## Descrição do Caso de Uso

### Descrição Breve
Este caso de uso descreve o uso de diálogos de confirmação no ia-core-view para solicitar confirmação do usuário antes de executar ações irreversíveis.

### Objetivos
- Fornecer diálogos de confirmação padronizados
- Internacionalizar textos dos diálogos
- Facilitar a criação de diálogos de confirmação
- Suportar ações de confirmação e cancelamento

### Escopo
- **Incluído**: ConfirmDialogViewFactory, InformationDialogViewFactory, ExceptionViewFactory
- **Excluído**: Implementação de diálogos customizados

## Atores

| Ator | Descrição | Tipo |
|------|------------|------|
| Usuário | Usuário que confirma ou cancela ações | Primário |
| Sistema | Aplicação Vaadin que exibe diálogos | Secundário |

## Pré-condições
- **Precondição 1**: O módulo ia-core-view deve estar configurado no classpath
- **Precondição 2**: O Vaadin ConfirmDialog deve estar disponível
- **Precondição 3**: O Translator deve estar configurado para internacionalização

## Pós-condições
- **Pós-condição de Sucesso**: O usuário confirma a ação e ela é executada
- **Pós-condição de Falha**: O usuário cancela a ação e ela não é executada

## Fluxo Principal (Basic Flow)

**Trigger**: O sistema solicita confirmação para uma ação

**Passos**:
1. **Dado** uma ação que requer confirmação
2. **Quando** o sistema chama ConfirmDialogViewFactory.show()
3. **Então** o diálogo é criado com título e mensagem
4. **E** os botões são internacionalizados [RN001]
5. **E** o diálogo é exibido ao usuário
6. **Quando** o usuário clica em "Sim"
7. **Então** a ação é executada
8. **E**: o diálogo é fechado
9. **Quando** o usuário clica em "Não"
10. **Então** a ação não é executada
11. **E**: o diálogo é fechado

## Fluxos Alternativos

**Fluxo Alternativo 1**: Diálogo de informação
1. **Dado** uma mensagem informativa
2. **Quando** o sistema chama InformationDialogViewFactory.show()
3. **Então** o diálogo de informação é exibido
4. **E**: o usuário fecha o diálogo

**Fluxo Alternativo 2**: Diálogo de exceção
1. **Dado** uma exceção ocorreu
2. **Quando** o sistema chama ExceptionViewFactory.show()
3. **Então** o diálogo de exceção é exibido
4. **E**: os detalhes da exceção são mostrados

## Fluxos de Exceção

**Fluxo de Exceção 1**: Ação falha
1. **Dado** uma ação que falha
2. **Quando** o usuário confirma a ação
3. **Então** a ação é executada
4. **E**: uma exceção é lançada
5. **E**: o ExceptionViewFactory exibe o erro

## Regras de Negócio

| ID | Regra de Negócio | Tipo | Aplicação |
|----|------------------|------|-----------|
| RN001 | Textos dos diálogos devem ser internacionalizados | Validação | Todos os diálogos |
| RN002 | Diálogos devem ter padrão singleton | Validação | Factory pattern |
| RN003 | Ações devem ser executadas apenas após confirmação | Validação | Execução de ações |

## Estrutura de Dados

```mermaid
erDiagram
    CONFIRM_DIALOG_VIEW_FACTORY {
        static instance
        show(action, title, message)
        showConfirm(action, title, message)
    }
    CONFIRM_DIALOG {
        String title
        String message
        String confirmText
        String cancelText
    }
```

## Contratos de Interface

**Classe ConfirmDialogViewFactory**:
| Método | Parâmetros | Retorno | Descrição |
|--------|------------|---------|------------|
| show | Runnable action, String title, String message | void | Exibe diálogo de confirmação |
| showConfirm | Runnable action, String title, String message | void | Cria e exibe diálogo |

## Requisitos Especiais
- **Performance**: Diálogos devem ser exibidos rapidamente (< 100ms)
- **Segurança**: Ações devem ser validadas antes de execução
- **Usabilidade**: Mensagens devem ser claras e internacionalizadas

## Pontos de Extensão
- **Extensão 1**: Adicionar diálogos customizados
- **Extensão 2**: Adicionar validações customizadas
- **Extensão 3**: Adicionar callbacks customizados

## Referências
- ADR-053: Usar CDU para Documentação de Casos de Uso
