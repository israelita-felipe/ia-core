# Casos de Teste: AgenteDTO

## Descrição
Casos de teste para a classe AgenteDTO do módulo ia-core-llm-service-model.

## Cenários

### Cenário 1: Criar AgenteDTO com builder
**Given**: Um builder de AgenteDTO
**When**: O AgenteDTO é criado com todos os campos
**Then**: O AgenteDTO deve ser criado com sucesso e todos os campos devem ter os valores corretos

### Cenário 2: Inicializar valores padrão
**Given**: Um builder de AgenteDTO
**When**: O AgenteDTO é criado sem especificar valores padrão
**Then**: Os valores padrão devem ser inicializados corretamente (ativo=true, temperature=0.7, maxTokens=2048)

### Cenário 3: Clonar AgenteDTO
**Given**: Um AgenteDTO existente com dados
**When**: O método cloneObject() é chamado
**Then**: Um novo AgenteDTO deve ser criado com os mesmos dados, mas com id=null e version=DEFAULT_VERSION

### Cenário 4: Copiar AgenteDTO
**Given**: Um AgenteDTO existente com dados
**When**: O método copyObject() é chamado
**Then**: Um novo AgenteDTO deve ser criado com id=null e version=DEFAULT_VERSION

### Cenário 5: Verificar inner class CAMPOS
**Given**: A classe AgenteDTO
**When**: A inner class CAMPOS é acessada
**Then**: Todos os campos do DTO devem ter constantes correspondentes em CAMPOS
