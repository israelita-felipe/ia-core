# Plano de Refatoração: Periodicidade → Periodicidade2

## Visão Geral

Este documento define o plano de refatoração para substituir `Periodicidade` por `Periodicidade2` e `PeriodicidadeDTO` por `Periodicidade2DTO` em todo o projeto.

## Estado Atual

### Entidades
- `Periodicidade` (antiga) - [`ia-core/ia-core-quartz/src/main/java/com/ia/core/quartz/model/periodicidade/Periodicidade.java`](ia-core/ia-core-quartz/src/main/java/com/ia/core/quartz/model/periodicidade/Periodicidade.java)
- `Periodicidade2` (nova) - [`ia-core/ia-core-quartz/src/main/java/com/ia/core/quartz/model/periodicidade/Periodicidade2.java`](ia-core/ia-core-quartz/src/main/java/com/ia/core/quartz/model/periodicidade/Periodicidade2.java)

### DTOs Antigos (periodicidade/)
- [`PeriodicidadeDTO.java`](ia-core/ia-core-quartz-service-model/src/main/java/com/ia/core/quartz/service/periodicidade/dto/PeriodicidadeDTO.java)
- [`PeriodicidadeTranslator.java`](ia-core/ia-core-quartz-service-model/src/main/java/com/ia/core/quartz/service/periodicidade/dto/PeriodicidadeTranslator.java)
- [`PeriodicidadeSearchRequest.java`](ia-core/ia-core-quartz-service-model/src/main/java/com/ia/core/quartz/service/periodicidade/dto/PeriodicidadeSearchRequest.java)
- [`PeriodicidadeFormatter.java`](ia-core/ia-core-quartz-service-model/src/main/java/com/ia/core/quartz/service/periodicidade/dto/PeriodicidadeFormatter.java)

### DTOs Novos (periodicidade2/)
- [`Periodicidade2DTO.java`](ia-core/ia-core-quartz-service-model/src/main/java/com/ia/core/quartz/service/periodicidade2/dto/Periodicidade2DTO.java)
- [`Periodicidade2Translator.java`](ia-core/ia-core-quartz-service-model/src/main/java/com/ia/core/quartz/service/periodicidade2/dto/Periodicidade2Translator.java)
- [`Periodicidade2SearchRequest.java`](ia-core/ia-core-quartz-service-model/src/main/java/com/ia/core/quartz/service/periodicidade2/dto/Periodicidade2SearchRequest.java)
- [`IntervaloTemporalDTO.java`](ia-core/ia-core-quartz-service-model/src/main/java/com/ia/core/quartz/service/periodicidade2/dto/IntervaloTemporalDTO.java)
- [`RecorrenciaDTO.java`](ia-core/ia-core-quartz-service-model/src/main/java/com/ia/core/quartz/service/periodicidade2/dto/RecorrenciaDTO.java)

---

## Arquivos a Modificar

### Fase 1: Service Layer

#### 1.1 PeriodicidadeMapper → Periodicidade2Mapper
| Arquivo | Ação | Descrição |
|---------|------|-----------|
| [`ia-core/ia-core-quartz-service/src/main/java/com/ia/core/quartz/service/PeriodicidadeMapper.java`](ia-core/ia-core-quartz-service/src/main/java/com/ia/core/quartz/service/PeriodicidadeMapper.java) | **DEPRECIAR** | Mapper antigo para Periodicidade. Já existe `Periodicidade2Mapper` |
| [`ia-core/ia-core-quartz-service/src/main/java/com/ia/core/quartz/service/Periodicidade2Mapper.java`](ia-core/ia-core-quartz-service/src/main/java/com/ia/core/quartz/service/Periodicidade2Mapper.java) | **MANTER** | Mapper para Periodicidade2 (já existe) |

#### 1.2 SchedulerConfigService
| Arquivo | Ação | Modificações |
|---------|------|--------------|
| [`ia-core/ia-core-quartz-service/src/main/java/com/ia/core/quartz/service/SchedulerConfigService.java`](ia-core/ia-core-quartz-service/src/main/java/com/ia/core/quartz/service/SchedulerConfigService.java) | **ANALISAR** | Verificar uso de `PeriodicidadeFormatter.asCronExpression()` e substituir por lógica equivalente com `Periodicidade2DTO` |

---

### Fase 2: View Layer (periodicidade/)

#### 2.1 Arquivos de View Antigos (periodicidade/)
| Arquivo | Ação | Substituição |
|---------|------|--------------|
| [`ia-core/ia-core-quartz-view/src/main/java/com/ia/core/quartz/view/periodicidade/form/PeriodicidadeFormView.java`](ia-core/ia-core-quartz-view/src/main/java/com/ia/core/quartz/view/periodicidade/form/PeriodicidadeFormView.java) | **DEPRECIAR** | Criar `Periodicidade2FormView.java` |
| [`ia-core/ia-core-quartz-view/src/main/java/com/ia/core/quartz/view/periodicidade/form/PeriodicidadeFormViewModel.java`](ia-core/ia-core-quartz-view/src/main/java/com/ia/core/quartz/view/periodicidade/form/PeriodicidadeFormViewModel.java) | **DEPRECIAR** | Criar `Periodicidade2FormViewModel.java` |
| [`ia-core/ia-core-quartz-view/src/main/java/com/ia/core/quartz/view/periodicidade/form/PeriodicidadeFormViewModelConfig.java`](ia-core/ia-core-quartz-view/src/main/java/com/ia/core/quartz/view/periodicidade/form/PeriodicidadeFormViewModelConfig.java) | **DEPRECIAR** | Criar `Periodicidade2FormViewModelConfig.java` |

#### 2.2 Arquivos de SchedulerConfig que Referenciam PeriodicidadeDTO
| Arquivo | Ação | Modificações |
|---------|------|--------------|
| [`ia-core/ia-core-quartz-view/src/main/java/com/ia/core/quartz/view/quartz/form/SchedulerConfigFormView.java`](ia-core/ia-core-quartz-view/src/main/java/com/ia/core/quartz/view/quartz/form/SchedulerConfigFormView.java) | **MODIFICAR** | Trocar import `PeriodicidadeDTO` → `Periodicidade2DTO`; Trocar `PeriodicidadeFormView` → `Periodicidade2FormView` |
| [`ia-core/ia-core-quartz-view/src/main/java/com/ia/core/quartz/view/quartz/form/SchedulerConfigFormViewModel.java`](ia-core/ia-core-quartz-view/src/main/java/com/ia/core/quartz/view/quartz/form/SchedulerConfigFormViewModel.java) | **MODIFICAR** | Trocar import `PeriodicidadeFormViewModel` → `Periodicidade2FormViewModel`; Trocar `PeriodicidadeFormViewModelConfig` → `Periodicidade2FormViewModelConfig` |
| [`ia-core/ia-core-quartz-view/src/main/java/com/ia/core/quartz/view/quartz/list/SchedulerConfigListView.java`](ia-core/ia-core-quartz-view/src/main/java/com/ia/core/quartz/view/quartz/list/SchedulerConfigListView.java) | **MODIFICAR** | Trocar `PeriodicidadeFormatter.format()` → lógica equivalente com `Periodicidade2DTO`; Trocar import `PeriodicidadeFormatter` |

---

### Fase 3: Criar Novos Arquivos de View (periodicidade2/)

#### 3.1 Novos Arquivos a Criar
| Novo Arquivo | Baseado Em |
|--------------|------------|
| `ia-core/ia-core-quartz-view/src/main/java/com/ia/core/quartz/view/periodicidade2/form/Periodicidade2FormView.java` | `PeriodicidadeFormView.java` (adaptado para Periodicidade2DTO) |
| `ia-core/ia-core-quartz-view/src/main/java/com/ia/core/quartz/view/periodicidade2/form/Periodicidade2FormViewModel.java` | `PeriodicidadeFormViewModel.java` (adaptado para Periodicidade2DTO) |
| `ia-core/ia-core-quartz-view/src/main/java/com/ia/core/quartz/view/periodicidade2/form/Periodicidade2FormViewModelConfig.java` | `PeriodicidadeFormViewModelConfig.java` (adaptado para Periodicidade2DTO) |

#### 3.2 Diretório a Criar
```
ia-core/ia-core-quartz-view/src/main/java/com/ia/core/quartz/view/periodicidade2/
└── form/
    ├── Periodicidade2FormView.java
    ├── Periodicidade2FormViewModel.java
    └── Periodicidade2FormViewModelConfig.java
```

---

### Fase 4: Arquivos i18n

#### 4.1 Arquivos a Modificar
| Arquivo | Ação | Modificações |
|---------|------|--------------|
| [`ia-core/ia-core-quartz-service-model/src/main/resources/i18n/translations_quartz_service_model_pt_BR.properties`](ia-core/ia-core-quartz-service-model/src/main/resources/i18n/translations_quartz_service_model_pt_BR.properties) | **MODIFICAR** | Adicionar traduções para `periodicidade2.*` (equivalente a `periodicidade.*`) |
| [`gestor-igreja/Biblia/biblia-service-model/src/main/resources/i18n/translations_biblia_pt_BR.properties`](gestor-igreja/Biblia/biblia-service-model/src/main/resources/i18n/translations_biblia_pt_BR.properties) | **ANALISAR** | Verificar referências a `evento.validation.periodicidade.*` e `intencao.validation.periodicidade.*` |

#### 4.2 Novas Chaves i18n a Criar
```properties
# periodicidade2
periodicidade2=Periodicidade
periodicidade2.ativo=Ativo
periodicidade2.intervaloBase=Intervalo Base
periodicidade2.regra=Regra
periodicidade2.zoneId=Zone ID
# ... outras chaves conforme Periodicidade2Translator
```

---

### Fase 5: Arquivos a Remover (após migração completa)

#### 5.1 Arquivos de periodicidade/ (após validação)
| Arquivo | Observação |
|---------|------------|
| `ia-core/ia-core-quartz-service-model/src/main/java/com/ia/core/quartz/service/periodicidade/dto/PeriodicidadeDTO.java` | Remover após migração |
| `ia-core/ia-core-quartz-service-model/src/main/java/com/ia/core/quartz/service/periodicidade/dto/PeriodicidadeTranslator.java` | Remover após migração |
| `ia-core/ia-core-quartz-service-model/src/main/java/com/ia/core/quartz/service/periodicidade/dto/PeriodicidadeSearchRequest.java` | Remover após migração |
| `ia-core/ia-core-quartz-service-model/src/main/java/com/ia/core/quartz/service/periodicidade/dto/PeriodicidadeFormatter.java` | Remover após migração (criar equivalente para Periodicidade2) |

#### 5.2 Arquivos de View (periodicidade/)
| Arquivo | Observação |
|---------|------------|
| `ia-core/ia-core-quartz-view/src/main/java/com/ia/core/quartz/view/periodicidade/` | Remover diretório após migração |

---

## Ordem de Execução Sugerida

### Passo 1: Atualizar SchedulerConfigFormView e ViewModel
1. Modificar imports em `SchedulerConfigFormView.java`
2. Modificar imports em `SchedulerConfigFormViewModel.java`
3. Testar compilação

### Passo 2: Criar Periodicidade2FormView e dependências
1. Criar diretório `periodicidade2/form/`
2. Criar `Periodicidade2FormViewModelConfig.java`
3. Criar `Periodicidade2FormViewModel.java`
4. Criar `Periodicidade2FormView.java`
5. Testar compilação

### Passo 3: Atualizar SchedulerConfigListView
1. Substituir uso de `PeriodicidadeFormatter` por lógica equivalente
2. Atualizar imports

### Passo 4: Atualizar traduções i18n
1. Adicionar chaves para `periodicidade2` em `translations_quartz_service_model_pt_BR.properties`
2. Testar traduções

### Passo 5: Depreciar/Remover código antigo
1. Marcar classes antigas como `@Deprecated`
2. Compilar e verificar avisos
3. Remover código antigo após validação

---

## Verificações de Compilação

Após cada fase, executar:
```bash
cd ia-core && ./mvnw compile -DskipTests
```

---

## Casos de Uso Afetados

### SchedulerConfig
- Criação de jobs agendados
- Edição de configuração de jobs
- Visualização de lista de jobs

### Periodicidade (antiga)
- Formulário de periodicidade (será substituído por Periodicidade2)

---

## Métricas de Sucesso

- [ ] 100% das referências a `PeriodicidadeDTO` substituídas por `Periodicidade2DTO`
- [ ] 100% das referências a `PeriodicidadeFormatter` removidas ou substituídas
- [ ] Todos os testes de integração passando
- [ ] Compilação sem warnings de depreciação
