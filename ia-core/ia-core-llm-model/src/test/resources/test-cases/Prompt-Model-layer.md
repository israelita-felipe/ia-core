# Casos de Teste - Prompt (Model Layer)

## Contexto
A classe `Prompt` é uma entidade JPA que representa um prompt template no sistema LLM.

## Tipo de Teste
- **Camada**: Model (Entity)
- **Tipo**: Unitário
- **ADR Referenciado**: ADR-012 (Testing Patterns)

## Casos de Teste

### 1. Criação de Prompt com Builder
**Descrição**: Verificar se é possível criar uma instância de Prompt usando o builder padrão.

**Pré-condições**:
- Nenhuma

**Passos**:
1. Criar Prompt usando Prompt.builder()
2. Definir campos obrigatórios
3. Chamar build()

**Resultado Esperado**:
- Prompt criado com sucesso
- Campos definidos corretamente

### 2. Valores Padrão
**Descrição**: Verificar se os campos com valores padrão são inicializados corretamente.

**Pré-condições**:
- Prompt criado com builder sem definir valores padrão

**Passos**:
1. Criar Prompt sem definir valores padrão
2. Verificar valores padrão

**Resultado Esperado**:
- Valores padrão inicializados corretamente

### 3. Geração de ID
**Descrição**: Verificar se o ID é gerado automaticamente antes da persistência.

**Pré-condições**:
- Prompt criado sem ID

**Passos**:
1. Criar Prompt sem definir ID
2. Verificar se @PrePersist gera o ID

**Resultado Esperado**:
- ID gerado automaticamente

### 4. Equals e HashCode
**Descrição**: Verificar se equals e hashCode funcionam corretamente considerando a herança.

**Pré-condições**:
- Dois prompts com mesmo ID
- Dois prompts com IDs diferentes

**Passos**:
1. Comparar prompts com mesmo ID
2. Comparar prompts com IDs diferentes
3. Verificar hashCode

**Resultado Esperado**:
- Prompts com mesmo ID são iguais
- Prompts com IDs diferentes são diferentes
- HashCode consistente com equals
