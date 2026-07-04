# Regras de Negócio - Módulo Security

## Visão Geral
Este documento define as regras de negócio para o módulo de Segurança do ia-core-apps.

## Referência
- **CDU**: CDU015-Manter-Security
- **Service**: ia-core-security-service
- **Módulo**: ia-core-security-model

## Entidades

### Usuario
Representa um usuário do sistema com autenticação e autorização.

#### Regras Implementadas

##### SEC_001 - UsuarioSenhaForteRule
- **Nome**: Usuário Senha Forte
- **Descrição**: Garante que a senha do usuário atenda aos critérios de segurança
- **Critérios**:
  - Mínimo 8 caracteres
  - Pelo menos 1 letra maiúscula
  - Pelo menos 1 letra minúscula
  - Pelo menos 1 número
  - Pelo menos 1 caractere especial (!@#$%^&*())
- **Severidade**: ERRO
- **Referência CDU**: RN001

##### SEC_002 - UsuarioEmailUnicoRule
- **Nome**: Usuário Email Único
- **Descrição**: Garante que o e-mail seja único no sistema
- **Critérios**:
  - E-mail é obrigatório
  - Não pode ser duplicado
  - Comparação case-insensitive
- **Severidade**: ERRO
- **Referência CDU**: RN002

##### SEC_003 - UsuarioLoginUnicoRule
- **Nome**: Usuário Login Único
- **Descrição**: Garante que o login seja único no sistema
- **Critérios**:
  - Login é obrigatório
  - Não pode ser duplicado
  - Tamanho entre 3 e 50 caracteres
- **Severidade**: ERRO
- **Referência CDU**: RN003

##### SEC_004 - UsuarioBloqueioSenhaRule
- **Nome**: Usuário Bloqueio por Senha Incorreta
- **Descrição**: Bloqueia usuário após múltiplas tentativas de senha incorreta
- **Critérios**:
  - Bloqueio após 5 tentativas incorretas
  - Duração do bloqueio: 30 minutos
- **Severidade**: ERRO
- **Referência CDU**: RN004

### Permissao
Define permissões de acesso a funcionalidades.

#### Regras Implementadas

##### SEC_005 - PermissaoMinimaRule
- **Nome**: Permissão Mínima
- **Descrição**: Garante que papéis tenham ao menos uma permissão associada
- **Critérios**:
  - Papel deve ter pelo menos 1 permissão associada
  - Mostrar aviso se papel não tiver permissões
- **Severidade**: AVISO
- **Referência CDU**: RN005

### Papel (Role)
Agrupa permissões para atribuição a usuários.

#### Regras Implementadas

##### SEC_006 - PapelProtegidoRule
- **Nome**: Papel Sistema Protegido
- **Descrição**: Protege papéis do sistema de modificações indevidas
- **Critérios**:
  - Papéis ADMIN e USER não podem ser excluídos
  - Papéis sistema não podem ter permissões removidas
- **Severidade**: ERRO
- **Referência CDU**: RN006

## Validadores

- `UsuarioValidator` - Orquestra regras de Usuario
- `PermissaoValidator` - Orquestra regras de Permissao
- `PapelValidator` - Orquestra regras de Papel
- `SessaoValidator` - Orquestra regras de Sessão

## Padrão de Implementação

As regras de negócio seguem o padrão `BusinessRule<T>` do módulo ia-core-service:

```java
public class UsuarioSenhaForteRule implements BusinessRule<UsuarioDTO> {
    private static final String CODE = "SEC_001";
    
    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() {
        return "Usuário Senha Forte";
    }

    @Override
    public void validate(UsuarioDTO entity, ValidationResult result) {
        String senha = entity.getSenha();
        if (senha == null || senha.length() < 8) {
            result.addError("senha", "Senha deve ter mínimo 8 caracteres");
        }
        // ... outras validações
    }
}
```

## Referências

- ADR-053: Usar CDU para Documentação de Casos de Uso
- ADR-011: Exception Handling Patterns
- Service Base: `com.ia.core.service.rules.BusinessRule`