# ia-core-security-view

## 📋 Descrição

Módulo de UI (User Interface) com Vaadin para o subsistema de segurança. Fornece componentes e telas para gerenciamento de usuários, roles e permissões.

## 🏗️ Estrutura

```
ia-core-security-view/
├── src/main/java/
│   └── com/ia/core/security/view/
│       ├── user/                  # Telas de usuário
│       ├── role/                  # Telas de role
│       ├── permission/            # Telas de permissão
│       ├── component/             # Componentes compartilhados
│       └── config/                # Configuração
└── pom.xml
```

## 🔑 Responsabilidades

- **User Management UI**: CRUD de usuários
- **Role Management UI**: Gerenciar papéis
- **Permission Management UI**: Controle de permissões
- **Login Form**: Autenticação
- **Security Components**: Wrappers seguros de componentes

## 🛠️ Tecnologias

- Vaadin 25.x
- Spring Security Integration
- Spring Boot Web

## 💡 Componentes Principais

- `LoginView`: Tela de login
- `UserListView`: Lista de usuários com filtros
- `UserFormView`: Criar/editar usuário
- `RoleManagementView`: Gerenciar roles
- `PermissionSelector`: Seletor de permissões


