# Casos de Teste: AgentSessionRequestDTO

## Descrição
Casos de teste para a classe AgentSessionRequestDTO do módulo ia-core-llm-service-model.

## Cenários

### Cenário 1: Criar AgentSessionRequestDTO com builder
**Given**: Um builder de AgentSessionRequestDTO
**When**: O AgentSessionRequestDTO é criado com todos os campos
**Then**: O AgentSessionRequestDTO deve ser criado com sucesso e todos os campos devem ter os valores corretos

### Cenário 2: Inicializar valores padrão
**Given**: Um builder de AgentSessionRequestDTO
**When**: O AgentSessionRequestDTO é criado sem especificar valores padrão
**Then**: Os valores padrão devem ser inicializados corretamente

### Cenário 3: Verificar inner class CAMPOS
**Given**: A classe AgentSessionRequestDTO
**When**: A inner class CAMPOS é acessada
**Then**: Todos os campos do DTO devem ter constantes correspondentes em CAMPOS
