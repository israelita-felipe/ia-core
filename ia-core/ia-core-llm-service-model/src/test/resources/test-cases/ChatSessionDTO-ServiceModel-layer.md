# Casos de Teste: ChatSessionDTO

## Descrição
Casos de teste para a classe ChatSessionDTO do módulo ia-core-llm-service-model.

## Cenários

### Cenário 1: Criar ChatSessionDTO com builder
**Given**: Um builder de ChatSessionDTO
**When**: O ChatSessionDTO é criado com todos os campos
**Then**: O ChatSessionDTO deve ser criado com sucesso e todos os campos devem ter os valores corretos

### Cenário 2: Inicializar valores padrão
**Given**: Um builder de ChatSessionDTO
**When**: O ChatSessionDTO é criado sem especificar valores padrão
**Then**: Os valores padrão devem ser inicializados corretamente

### Cenário 3: Verificar inner class CAMPOS
**Given**: A classe ChatSessionDTO
**When**: A inner class CAMPOS é acessada
**Then**: Todos os campos do DTO devem ter constantes correspondentes em CAMPOS
