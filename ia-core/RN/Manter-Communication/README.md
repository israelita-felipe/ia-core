# Regras de Negócio - Módulo Communication

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Comunicação do ia-core-apps.

## Entidades

### ContatoMensagem
Representa um contato para envio de mensagens.

#### Regras Implementadas

##### CTR_001 - ContatoTelefoneValidoRule
- **Nome**: Contato Telefone Válido
- **Descrição**: Garante que o telefone do contato tenha o formato correto e quantidade de dígitos adequada
- **Critérios**:
  - Telefone é obrigatório
  - Deve conter entre 10 e 20 dígitos (após remoção de caracteres não numéricos)
- **Severidade**: ERRO
- **Localização**: `ia-core-communication-service/src/main/java/com/ia/core/communication/service/contatomensagem/rules/ContatoTelefoneValidoRule.java`

### Mensagem
Representa uma mensagem a ser enviada para um destinatário.

#### Regras Implementadas

##### MSG_001 - MensagemCanalValidoRule
- **Nome**: Mensagem Canal Válido
- **Descrição**: Verifica se o canal de envio da mensagem é válido
- **Critérios**:
  - Canal deve ser um dos tipos válidos (WHATSAPP, SMS, EMAIL, TELEGRAM, WEBHOOK)
- **Severidade**: ERRO
- **Localização**: `ia-core-communication-service/src/main/java/com/ia/core/communication/service/mensagem/rules/MensagemCanalValidoRule.java`

#### Regras a Implementar

##### MSG_002 - MensagemDestinatarioObrigatorioRule
- **Nome**: Mensagem Destinatário Obrigatório
- **Descrição**: Garante que a mensagem tenha um destinatário definido
- **Critérios**:
  - Telefone do destinatário é obrigatório para canais SMS e WhatsApp
  - E-mail do destinatário é obrigatório para canal EMAIL
- **Severidade**: ERRO

##### MSG_003 - MensagemCorpoObrigatorioRule
- **Nome**: Mensagem Corpo Obrigatório
- **Descrição**: Garante que a mensagem tenha conteúdo
- **Critérios**:
  - Corpo da mensagem não pode estar vazio ou em branco
  - Tamanho mínimo de 1 caractere
  - Tamanho máximo de 5000 caracteres
- **Severidade**: ERRO

##### MSG_004 - AgendamentoValidoRule
- **Nome**: Agendamento de Mensagem Válido
- **Descrição**: Valida data/hora de agendamento de mensagens
- **Critérios**:
  - Data de agendamento deve ser futura
  - Não permite agendamento com mais de 1 ano de antecedência
- **Severidade**: ERRO

### GrupoContato
Representa um grupo de contatos para envio de mensagens.

#### Regras a Implementar

##### GRP_001 - GrupoNomeUnicoRule
- **Nome**: Grupo Nome Único
- **Descrição**: Garante que o nome do grupo seja único por organização
- **Critérios**:
  - Nome do grupo não pode ser duplicado
  - Comparação case-insensitive
- **Severidade**: ERRO

##### GRP_002 - GrupoContatoMinimoRule
- **Nome**: Grupo Contato Mínimo
- **Descrição**: Garante que o grupo tenha no mínimo um contato
- **Critérios**:
  - Grupo deve ter pelo menos 1 contato associado
- **Severidade**: AVISO

### ModeloMensagem
Modelo de mensagem pré-definido para reutilização.

#### Regras a Implementar

##### MDL_001 - ModeloNomeUnicoRule
- **Nome**: Modelo Nome Único
- **Descrição**: Garante que o nome do modelo seja único
- **Critérios**:
  - Nome do modelo não pode ser duplicado por tipo de canal
- **Severidade**: ERRO

##### MDL_002 - ModeloVariavelValidaRule
- **Nome**: Modelo Variável Válida
- **Descrição**: Valida variáveis utilizadas no corpo do modelo
- **Critérios**:
  - Variáveis devem seguir o padrão {{nome_variavel}}
  - Variáveis declaradas no cabeçalho devem ser utilizadas no corpo
- **Severidade**: ERRO

## Validadores

Cada entidade possui um validador específico que orquestra as regras:

- `ContatoMensagemValidator` - Orquestra regras de ContatoMensagem
- `MensagemValidator` - Orquestra regras de Mensagem
- `GrupoContatoValidator` - Orquestra regras de GrupoContato
- `ModeloMensagemValidator` - Orquestra regras de ModeloMensagem

## Padrão de Implementação

As regras de negócio seguem o padrão `BusinessRule<T>` do módulo ia-core-service:

```java
public class MinhaRegra implements BusinessRule<MeuDTO> {
    @Override
    public String getCode() {
        return "COD_001";
    }

    @Override
    public String getName() {
        return "Minha Regra";
    }

    @Override
    public String getDescription() {
        return "Descrição da regra";
    }

    @Override
    public void validate(MeuDTO entity, ValidationResult result) {
        // Lógica de validação
    }
}
```

## Referências

- ADR 005 - Use Domain Events
- ADR 011 - Exception Handling Patterns
- Service Base: `com.ia.core.service.rules.BusinessRule`