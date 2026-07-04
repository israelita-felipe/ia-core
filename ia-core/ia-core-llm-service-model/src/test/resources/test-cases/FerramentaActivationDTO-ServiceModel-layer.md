# Casos de Teste: FerramentaActivationDTO

## Descrição
Casos de teste para a classe FerramentaActivationDTO do módulo ia-core-llm-service-model.

## Cenários

### Cenário 1: Criar FerramentaActivationDTO com builder
**Given**: Um builder de FerramentaActivationDTO
**When**: O FerramentaActivationDTO é criado com todos os campos
**Then**: O FerramentaActivationDTO deve ser criado com sucesso e todos os campos devem ter os valores corretos

### Cenário 2: Inicializar valores padrão
**Given**: Um builder de FerramentaActivationDTO
**When**: O FerramentaActivationDTO é criado sem especificar valores padrão
**Then**: Os valores padrão devem ser inicializados corretamente

### Cenário 3: Verificar inner class CAMPOS
**Given**: A classe FerramentaActivationDTO
**When**: A inner class CAMPOS é acessada
**Then**: Todos os campos do DTO devem ter constantes correspondentes em CAMPOS
