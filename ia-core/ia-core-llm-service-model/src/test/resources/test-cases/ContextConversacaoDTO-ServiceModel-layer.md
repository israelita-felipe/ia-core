# Casos de Teste: ContextConversacaoDTO

## Descrição
Casos de teste para a classe ContextConversacaoDTO do módulo ia-core-llm-service-model.

## Cenários

### Cenário 1: Criar ContextConversacaoDTO com builder
**Given**: Um builder de ContextConversacaoDTO
**When**: O ContextConversacaoDTO é criado com todos os campos
**Then**: O ContextConversacaoDTO deve ser criado com sucesso e todos os campos devem ter os valores corretos

### Cenário 2: Inicializar valores padrão
**Given**: Um builder de ContextConversacaoDTO
**When**: O ContextConversacaoDTO é criado sem especificar valores padrão
**Then**: Os valores padrão devem ser inicializados corretamente

### Cenário 3: Verificar inner class CAMPOS
**Given**: A classe ContextConversacaoDTO
**When**: A inner class CAMPOS é acessada
**Then**: Todos os campos do DTO devem ter constantes correspondentes em CAMPOS
