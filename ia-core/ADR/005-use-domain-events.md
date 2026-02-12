# ADR-005: Usar Domain Events para Comunicação Desacoplada

## Status

✅ Aceito

## Contexto

O projeto precisa de uma forma de notificar outras partes do sistema sobre operações CRUD sem criar dependências diretas entre serviços (evitando dependências circulares).

## Decisão

Usar **Domain Events** com Spring `ApplicationEventPublisher` e publicação automática via callbacks.

## Detalhes

### Enum de Tipos de Evento

```java
public enum CrudOperationType implements EventType {
    CREATED,   // Entidade criada
    UPDATED,   // Entidade atualizada
    DELETED;   // Entidade deletada
}
```

### Classe de Evento Base

```java
public class BaseServiceEvent<T> extends ApplicationEvent {
    private final T dto;
    private final EventType eventType;
    private final Object additionalData;
    
    public BaseServiceEvent(Object source, T dto, EventType eventType) {
        super(source);
        this.dto = dto;
        this.eventType = eventType;
    }
    
    public BaseServiceEvent(Object source, T dto, EventType eventType, Object data) {
        this(source, dto, eventType);
        this.additionalData = data;
    }
}
```

### Callbacks em Interfaces de Serviço

```java
public interface SaveBaseService<T extends BaseEntity, D extends DTO<?>> {
    default void beforeSave(D toSave) throws ServiceException {
        // Hook para lógica antes do save
    }
    
    default void afterSave(D original, D saved, CrudOperationType type) 
        throws ServiceException {
        // Publish event here in implementations
    }
}
```

### Publicação Automática em DefaultSecuredBaseService

```java
public abstract class DefaultSecuredBaseService<T extends BaseEntity, D extends DTO<?>>
    extends AbstractSecuredBaseService<T, D>
    implements SaveBaseService<T, D>, DeleteBaseService<T, D> {

    @Override
    public void afterSave(D original, D saved, CrudOperationType type)
        throws ServiceException {
        if (saved != null && config.getEventPublisher() != null) {
            config.getEventPublisher().publishEvent(
                new BaseServiceEvent<>(this, saved, type));
            log.debug("Evento de {} publicado para {}", type,
                saved.getClass().getSimpleName());
        }
    }

    @Override
    public void afterDelete(Long id, D dto) throws ServiceException {
        if (dto != null && config.getEventPublisher() != null) {
            config.getEventPublisher().publishEvent(
                new BaseServiceEvent<>(this, dto, CrudOperationType.DELETED));
            log.debug("Evento de DELETED publicado para {} com id {}",
                dto.getClass().getSimpleName(), id);
        }
    }
}
```

## Exemplo de Uso

### publicador de Evento (Automático)

```java
@Service
public class FamiliaService 
    extends DefaultSecuredBaseService<Familia, FamiliaDTO> {
    // Herda publicação automática de afterSave/afterDelete
}
```

### Consumidor de Evento

```java
@Component
public class FamiliaEventListener {
    
    @EventListener
    public void handleFamiliaEvent(BaseServiceEvent<FamiliaDTO> event) {
        if (event.getEventType() == CrudOperationType.CREATED) {
            log.info("Nova família criada: {}", event.getDto().getId());
            // Notificar responsáveis, etc.
        } else if (event.getEventType() == CrudOperationType.DELETED) {
            log.info("Família {} foi removida", event.getDto().getId());
        }
    }
}
```

## Benefícios

### Desacoplamento

1. **Sem Dependências Diretas** - Serviços não se conhecem
2. **Extensibilidade** - Novos consumidores podem ser adicionados
3. **Auditoria** - Todos os eventos podem ser logados
4. **Integrações** - Fácil integrar com sistemas externos

### Manutenção

1. **Single Responsibility** - Cada serviço faz uma coisa
2. **Testabilidade** - Eventos podem ser mockados
3. **Debugging** - Logs de eventos publicados

## Consequências

### Positivas

- ✅ Desacoplamento entre serviços
- ✅ Elimina dependências circulares
- ✅ Auditoria automática
- ✅ Facilidade de integração
- ✅ Publicação automática via herança

### Negativas

- ❌ Curva de aprendizado com eventos
- ❌ Debugging mais complexo (assíncrono)
- ❌需要 Event Listeners para reagir

## Status de Implementação

✅ **COMPLETO**

- [`CrudOperationType.java`](../../ia-core-service/src/main/java/com/ia/core/service/event/CrudOperationType.java) implementado
- [`BaseServiceEvent.java`](../../ia-core-service/src/main/java/com/ia/core/service/event/BaseServiceEvent.java) implementado
- [`SaveBaseService.afterSave()`](../../ia-core-service/src/main/java/com/ia/core/service/SaveBaseService.java) com callback
- [`DeleteBaseService.afterDelete()`](../../ia-core-service/src/main/java/com/ia/core/service/DeleteBaseService.java) com callback
- [`DefaultSecuredBaseService.afterSave/afterDelete()`](../../security-core-service/src/main/java/com/ia/core/security/service/DefaultSecuredBaseService.java) publicação automática

## Data

2024-03-01

## Revisores

- Team Lead
- Architect
