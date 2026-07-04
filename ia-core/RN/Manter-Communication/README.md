# Regras de Negócio - Módulo Communication

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Comunicação do ia-core-apps.

## Referência
- **CDU**: CDU003-Manter-Communication
- **Service**: ia-core-communication-service
- **Módulo**: ia-core-communication-model, ia-core-communication-service

## Entidades

### Mensagem
Representa uma mensagem a ser enviada para um destinatário.

#### Regras Implementadas

##### COM_001 - TituloMensagemValidoRule
- **Nome**: Título da Mensagem Válido
- **Descrição**: Garante que o título da mensagem seja válido
- **Critérios**:
  - Título é obrigatório
  - Deve ter entre 3 e 200 caracteres
- **Severidade**: ERRO
- **Referência CDU**: RN001

##### COM_002 - ConteudoMensagemValidoRule
- **Nome**: Conteúdo da Mensagem Válido
- **Descrição**: Garante que o conteúdo da mensagem não seja vazio
- **Critérios**:
  - Conteúdo não pode estar vazio ou em branco
- **Severidade**: ERRO
- **Referência CDU**: RN002

##### COM_003 - TipoCanalValidoRule
- **Nome**: Tipo de Canal Válido
- **Descrição**: Verifica se o tipo de canal da mensagem é válido
- **Critérios**:
  - Canal deve ser um dos tipos válidos (EMAIL, TELEFONE, WHATSAPP)
- **Severidade**: ERRO
- **Referência CDU**: RN003

### ModeloMensagem
Modelo de mensagem pré-definido para reutilização.

#### Regras Implementadas

##### COM_004 - VariavelModeloFormatadaRule
- **Nome**: Variável do Modelo Formatada
- **Descrição**: Valida que variáveis do modelo sigam o formato correto
- **Critérios**:
  - Variáveis devem seguir o padrão {{nome_variavel}}
- **Severidade**: ERRO
- **Referência CDU**: RN005

### GrupoContato
Representa um grupo de contatos para envio de mensagens.

#### Regras Implementadas

##### COM_005 - GrupoContatoMinimoRule
- **Nome**: Grupo Contato Mínimo
- **Descrição**: Garante que o grupo tenha no mínimo um contato associado
- **Critérios**:
  - Grupo deve ter pelo menos 1 contato associado
- **Severidade**: AVISO
- **Referência CDU**: RN004

### EnvioMensagem
Representa o envio de mensagens.

#### Regras Implementadas

##### COM_006 - EnvioAssincronoRule
- **Nome**: Envio Assíncrono
- **Descrição**: Garante que o envio de mensagens seja assíncrono
- **Critérios**:
  - O envio de mensagens é realizado de forma assíncrona
  - Sistema retorna imediatamente após enfileirar
- **Severidade**: INFO
- **Referência CDU**: RN006

##### COM_007 - HistoricoEnvioMantidoRule
- **Nome**: Histórico de Envio Mantido
- **Descrição**: Garante que o histórico de envios seja mantido
- **Critérios**:
  - Cada envio é registrado no histórico
  - Total de enviados e falhas são contabilizados
- **Severidade**: INFO
- **Referência CDU**: RN007

### ContatoMensagem
Representa um contato para envio de mensagens.

#### Regras Implementadas

##### COM_008 - TelefoneContatoValidoRule
- **Nome**: Telefone do Contato Válido
- **Descrição**: Garante que o telefone do contato tenha o formato correto
- **Critérios**:
  - Telefone é obrigatório para canais SMS e WhatsApp
  - Deve conter entre 10 e 20 dígitos
- **Severidade**: ERRO
- **Referência CDU**: (regra implícita)

## Validadores

Cada entidade possui um validador específico que orquestra as regras:

- `MensagemValidator` - Orquestra regras de Mensagem
- `ModeloMensagemValidator` - Orquestra regras de ModeloMensagem
- `GrupoContatoValidator` - Orquestra regras de GrupoContato
- `ContatoMensagemValidator` - Orquestra regras de ContatoMensagem

## Padrão de Implementação

As regras de negócio seguem o padrão `BusinessRule<T>` do módulo ia-core-service:

```java
public class MinhaRegra implements BusinessRule<MeuDTO> {
    private static final String CODE = "COM_001";
    
    @Override
    public String getCode() {
        return CODE;
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

- ADR-053: Usar CDU para Documentação de Casos de Uso
- ADR-011: Exception Handling Patterns
- Service Base: `com.ia.core.service.rules.BusinessRule`