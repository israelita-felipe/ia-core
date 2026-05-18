# ia-core-security-test

## 📋 Descrição

Módulo especializado de testes integrados para o subsistema de segurança. Fornece fixtures de usuários, roles e utilitários para testes de autenticação.

## 🏗️ Estrutura

```
ia-core-security-test/
├── src/main/java/
│   └── com/ia/core/security/test/
│       ├── fixture/               # Fixtures de usuario/role
│       ├── base/                  # Base para testes de segurança
│       ├── annotation/            # @WithMockUser customizado
│       └── util/                  # Helpers de segurança
└── pom.xml
```

## 🔑 Responsabilidades

- **UserFixture**: Dados de teste de usuário
- **SecurityTestBase**: Base para testes autenticados
- **AuthAssertions**: Assertions de autenticação
- **MockUserGenerator**: Gerador de usuários mockados

## 🛠️ Tecnologias

- JUnit 5
- Spring Test
- Spring Security Test
- Mockito

## 💡 Recursos

- Fixtures de usuários/roles pré-definidos
- Helpers para testar endpoints protegidos
- Geração de tokens JWT para testes
- Assertions customizadas de segurança


