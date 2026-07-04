# Casos de Teste: SkillDTO

## Descrição
Casos de teste para a classe SkillDTO do módulo ia-core-llm-service-model.

## Cenários

### Cenário 1: Criar SkillDTO com builder
**Given**: Um builder de SkillDTO
**When**: O SkillDTO é criado com todos os campos
**Then**: O SkillDTO deve ser criado com sucesso e todos os campos devem ter os valores corretos

### Cenário 2: Inicializar valores padrão
**Given**: Um builder de SkillDTO
**When**: O SkillDTO é criado sem especificar valores padrão
**Then**: Os valores padrão devem ser inicializados corretamente

### Cenário 3: Verificar inner class CAMPOS
**Given**: A classe SkillDTO
**When**: A inner class CAMPOS é acessada
**Then**: Todos os campos do DTO devem ter constantes correspondentes em CAMPOS
