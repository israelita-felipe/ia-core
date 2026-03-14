# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.2.0] - 2026-03-14

### Added
- ADR-015: Spring Data Projections para otimização de queries
- EntityProjection interface base para projeções
- SchedulerConfigSummary - Exemplo de projection implementada
- Métodos de projection em SchedulerConfigRepository

### Changed
- Fase 4: Performance - Otimização de queries com projection

## [1.1.0] - 2026-02-17

### Added
- Domain Events para notificação de mudanças
- Implementação de Escala com INVALID status quando Evento alterado
- Unified SchedulerConfigService com métodos QuartzJobUseCase

### Changed
- Fase 3: SRP em Services - Concluída
- Fase A: ApplicationEventPublisher GENERALIZADO

### Removed
- QuartzJobController (unificado no QuartzController)
- QuartzJobService (unificado no SchedulerConfigService)
- QuartzJobServiceConfig (unificado no SchedulerConfigServiceConfig)
- QuartzJobClient (unificado no QuartzClient)

## [1.0.0] - 2024-01-01

### Added
- ia-core-model: Entidades base e modelos de domínio
- ia-core-service: Lógica de negócio com padrão Service
- ia-core-rest: Controllers REST
- ia-core-view: Interface MVVM
- ia-core-llm-*: Módulos de integração com LLMs
- ia-core-quartz-*: Módulos de agendamento
- ia-core-security-*: Módulos de segurança
- Jakarta Validation em todos os DTOs
- i18n completo em todos os módulos

### Changed
- Arquitetura Clean Architecture
- Padrões SOLID implementados
- MVVM pattern para views

### Fixed
- Nomenclatura padronizada (typos corrigidos)
- Dependências circulares eliminadas
