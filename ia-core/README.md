# IA Core

Sistema de inteligência artificial modular para processamento de linguagem natural, integração com modelos de linguagem (LLM) e agendamento de tarefas.

## 🏗️ Arquitetura

O projeto segue os princípios de **Clean Architecture** com separação em camadas:

```
ia-core/
├── ia-core-model/           # Entidades e modelos de domínio
├── ia-core-service/         # Lógica de negócio
├── ia-core-rest/           # Controllers REST
├── ia-core-view/           # Interface MVVM
├── ia-core-llm-model/      # Modelos específicos de LLM
├── ia-core-llm-service/    # Serviços de LLM
├── ia-core-llm-view/       # View de LLM
├── ia-core-quartz/         # Modelo de agendamento
├── ia-core-quartz-service/ # Serviços de agendamento
├── ia-core-quartz-view/    # View de agendamento
├── ia-core-nlp/           # Processamento de linguagem natural
├── ia-core-grammar/       # Gramáticas ANTLR
├── ia-core-report/        # Relatórios Jasper
└── ia-core-flyway/        # Migrações de banco
```

## 🔧 Tecnologias

- **Java 17+** com Spring Boot 3.x
- **Jakarta EE** (Validação, Persistence)
- **Spring Data JPA** com Flyway
- **Spring AI** para integração com LLMs
- **Quartz** para agendamento
- **Lombok** para redução de boilerplate
- **MVVM** para interface

## 📦 Módulos

### ia-core-model
Entidades base e utilitários compartilhados:
- `BaseEntity` - Entidade base com auditoria
- `TSID` - Identificadores distribuídos
- `FilterRequest` - Filtros dinâmicos

### ia-core-llm-service
Serviços de integração com modelos de linguagem:
- `ChatService` - Comunicação com LLMs
- `TemplateService` - Gerenciamento de templates
- `PromptService` - Prompts de catálogo LLM

### ia-core-quartz-service
Gerenciamento de agendamento:
- `SchedulerConfigService` - Configuração de tarefas
- `JobSchedulerChecker` - Verificação de jobs

## 🚀 Começando

### Pré-requisitos
- Java 17+
- Maven 3.8+
- Banco de dados HSQLDB, PostgreSQL ou MySQL

### Build
```bash
# Compilar todos os módulos
mvn clean install

# Compilar módulo específico
mvn clean install -pl ia-core/ia-core-llm-service
```

### Configuração
Arquivos de configuração estão em `src/main/resources`:
- `application.yml` - Configuração principal
- `application-flyway.yml` - Migrações

## 📖 Documentação

- [Plano de Refatoração](PLANO_REFATORACAO_COMPLETO.md)
- [FASE 4 - Performance](PERFORMANCE_OPTIMIZATION_PLAN.md)
- [Status do Projeto](REFACTORING_STATUS.md)

## 🏛️ Padrões Aplicados

### SOLID
- **S**ingle Responsibility: Serviços com responsabilidade única
- **O**pen/Closed: Extensível via novos módulos
- **L**iskov Substitution: Interfaces consistentes
- **I**nterface Segregation: DTOs específicos por operação
- **D**ependency Inversion: Spring DI

### Clean Code
- Nomes descritivos
- Métodos pequenos
- Documentação javadoc
- Classes coesas

### MVVM (View)
- `FormView` - Interface Swing/JavaFX
- `FormViewModel` - Lógica de apresentação
- `FormViewModelConfig` - Configuração injetável

## 🧪 Testes

```bash
# Executar testes
mvn test

# Executar testes com cobertura
mvn test jacoco:report
```

## 📝 Convenções de Commit

```
feat: Nova funcionalidade
fix: Correção de bug
docs: Documentação
refactor: Refatoração (sem mudança de comportamento)
perf: Melhoria de performance
test: Adição de testes
chore: Tarefas de manutenção
```

## 📄 Licença

Este projeto está sob licença interna.

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas mudanças (`git commit -m 'feat: adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request

## 📞 Suporte

Para dúvidas, consulte a [documentação](PLANO_REFATORACAO_COMPLETO.md) ou abra uma issue.
