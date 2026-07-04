# Casos de Teste: PromptDTO

## Descrição
Casos de teste para a classe PromptDTO do módulo ia-core-llm-service-model.

## Cenários

### Cenário 1: Criar PromptDTO com builder
**Given**: Um builder de PromptDTO
**When**: O PromptDTO é criado com todos os campos
**Then**: O PromptDTO deve ser criado com sucesso e todos os campos devem ter os valores corretos

### Cenário 2: Inicializar valores padrão
**Given**: Um builder de PromptDTO
**When**: O PromptDTO é criado sem especificar valores padrão
**Then**: Os valores padrão devem ser inicializados corretamente

### Cenário 3: Verificar inner class CAMPOS
**Given**: A classe PromptDTO
**When**: A inner class CAMPOS é acessada
**Then**: Todos os campos do DTO devem ter constantes correspondentes em CAMPOS
