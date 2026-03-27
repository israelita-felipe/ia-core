# Regras de Negócio - Módulo Security

## Visão Geral
Este documento define as regras de negócio para o módulo de Segurança do ia-core-apps.

## Entidades

### Usuario
Representa um usuário do sistema com autenticação e autorização.

#### Regras a Implementar

##### USR_001 - UsuarioSenhaForteRule
- **Nome**: Usuário Senha Forte
- **Descrição**: Garante que a senha do usuário atenda aos critérios de segurança
- **Critérios**:
  - Mínimo 8 caracteres
  - Pelo menos 1 letra maiúscula
  - Pelo menos 1 letra minúscula
  - Pelo menos 1 número
  - Pelo menos 1 caractere especial (!@#$%^&*())
- **Severidade**: ERRO

##### USR_002 - UsuarioEmailUnicoRule
- **Nome**: Usuário Email Único
- **Descrição**: Garante que o e-mail seja único no sistema
- **Critérios**:
  - E-mail não pode ser duplicado
  - Comparação case-insensitive
- **Severidade**: ERRO

##### USR_003 - UsuarioLoginUnicoRule
- **Nome**: Usuário Login Único
- **Descrição**: Garante que o login seja único no sistema
- **Critérios**:
  - Login não pode ser duplicado
  - Tamanho entre 3 e 50 caracteres
- **Severidade**: ERRO

##### USR_004 - UsuarioAtivoInativoRule
- **Nome**: Usuário Ativo/Inativo
- **Descrição**: Controla status do usuário
- **Critérios**:
  - Usuários inativos não podem fazer login
  - Usuários inativos não recebem notificações
  - Não é possível inativar o último usuário admin
- **Severidade**: ERRO

##### USR_005 - UsuarioBloqueioSenhaRule
- **Nome**: Usuário Bloqueio por Senha Incorreta
- **Descrição**: Bloqueia usuário após múltiplas tentativas de senha incorreta
- **Critérios**:
  - Bloqueio após 5 tentativas incorretas
  - Duração do bloqueio: 30 minutos
  - Ao alcançar limite, registrar tentativa de入侵
- **Severidade**: ERRO

### Permissão
Define permissões de acesso a funcionalidades.

#### Regras a Implementar

##### PERM_001 - PermissaoNomeUnicoRule
- **Nome**: Permissão Nome Único
- **Descrição**: Garante que o nome da permissão seja único
- **Critérios**:
  - Nome não pode ser duplicado
  - Formato: RECURSO_ACAO (ex: USUARIO_READ)
- **Severidade**: ERRO

##### PERM_002 - PermissaoHierarquiaValidaRule
- **Nome**: Permissão Hierarquia Válida
- **Descrição**: Valida relação hierárquica de permissões
- **Critérios**:
  - Permissão filha não pode existir sem permissão pai
  - Ao excluir pai, excluir todas as filhas ou mover para outro pai
- **Severidade**: ERRO

### Papel (Role)
Agrupa permissões para atribuição a usuários.

#### Regras a Implementar

##### ROLE_001 - PapelNomeUnicoRule
- **Nome**: Papel Nome Único
- **Descrição**: Garante que o nome do papel seja único
- **Critérios**:
  - Nome não pode ser duplicado
  - Não pode usar nomes reservados (ADMIN, USER, GUEST)
- **Severidade**: ERRO

##### ROLE_002 - PapelSistemaProtegidoRule
- **Nome**: Papel Sistema Protegido
- **Descrição**: Protege papéis do sistema de modificações indevidas
- **Critérios**:
  - Papéis ADMIN e USER não podem ser excluídos
  - Papéis sistema não podem ter permissões removidas
- **Severidade**: ERRO

##### ROLE_003 - PapelMinimoPermissaoRule
- **Nome**: Papel Mínimo Permissão
- **Descrição**: Garante que papéis tenham ao menos uma permissão
- **Critérios**:
  - Papel deve ter pelo menos 1 permissão associada
  - Mostrar aviso se papel não tiver permissões
- **Severidade**: AVISO

### Sessão
Representa uma sessão de usuário autenticado.

#### Regras a Implementar

##### SESS_001 - SessaoExpiracaoRule
- **Nome**: Sessão Expiração
- **Descrição**: Controla tempo de expiração de sessões
- **Critérios**:
  - Sessão expira após 30 minutos de inatividade
  - Sessão máxima de 8 horas
  - Renovar sessão a cada ação do usuário
- **Severidade**: ERRO

##### SESS_002 - SessaoUnicaRule
- **Nome**: Sessão Única
- **Descrição**: Controla quantidade de sessões simultâneas
- **Critérios**:
  - Usuário pode ter no máximo 3 sessões simultâneas
  - Ao criar 4ª, encerrar a mais antiga
  - Configurável por papel (admin pode ter mais)
- **Severidade**: AVISO

## Referências

- ADR 004 - Use ServiceConfig for DI
- ADR 011 - Exception Handling Patterns
- Module: `ia-core-security-service`