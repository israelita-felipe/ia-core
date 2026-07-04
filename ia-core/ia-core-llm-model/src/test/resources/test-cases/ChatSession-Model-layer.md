# Casos de Teste - ChatSession (Model Layer)

## Contexto
A classe `ChatSession` é uma entidade JPA que representa uma sessão de chat no sistema LLM.

## Tipo de Teste
- **Camada**: Model (Entity)
- **Tipo**: Unitário
- **ADR Referenciado**: ADR-012 (Testing Patterns)

## Casos de Teste

### 1. Criação de ChatSession com Builder
**Descrição**: Verificar se é possível criar uma instância de ChatSession usando o builder padrão.

**Pré-condições**:
- Nenhuma

**Passos**:
1. Criar ChatSession usando ChatSession.builder()
2. Definir campos obrigatórios
3. Chamar build()

**Resultado Esperado**:
- ChatSession criado com sucesso
- Campos definidos corretamente

### 2. Valores Padrão
**Descrição**: Verificar se os campos com valores padrão são inicializados corretamente.

**Pré-condições**:
- ChatSession criado com builder sem definir valores padrão

**Passos**:
1. Criar ChatSession sem definir valores padrão
2. Verificar valores padrão

**Resultado Esperado**:
- Valores padrão inicializados corretamente

### 3. Geração de ID
**Descrição**: Verificar se o ID é gerado automaticamente antes da persistência.

**Pré-condições**:
- ChatSession criado sem ID

**Passos**:
1. Criar ChatSession sem definir ID
2. Verificar se @PrePersist gera o ID

**Resultado Esperado**:
- ID gerado automaticamente

### 4. Equals e HashCode
**Descrição**: Verificar se equals e hashCode funcionam corretamente considerando a herança.

**Pré-condições**:
- Duas sessões com mesmo ID
- Duas sessões com IDs diferentes

**Passos**:
1. Comparar sessões com mesmo ID
2. Comparar sessões com IDs diferentes
3. Verificar hashCode

**Resultado Esperado**:
- Sessões com mesmo ID são iguais
- Sessões com IDs diferentes são diferentes
- HashCode consistente com equals
