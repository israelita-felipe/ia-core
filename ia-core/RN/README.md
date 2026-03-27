# Regras de Negócio - ia-core-apps

## Módulos com RN

### Communication
- **Local**: `ia-core/ia-core-communication-service/.../rules/`
- **Arquivos**:
  - `MensagemCanalValidoRule.java`
  - `ContatoTelefoneValidoRule.java`

### Scheduler
- **Local**: `ia-core/ia-core-quartz-service/`
- **Módulo**: Quartz

### Security
- **Local**: Múltiplos módulos
- **Status**: A implementar RN

## Padrão

```java
@ValidatorScope
public class XxxRule implements BusinessRule<DTO> { }
```

## Referência
- Código: `ia-core/ia-core-communication-service/`