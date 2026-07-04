# Casos de Teste - Skill (Model Layer)

## Contexto
A classe `Skill` é uma entidade JPA que representa uma habilidade especializada que pode ser atribuída aos agentes no sistema LLM.

## Tipo de Teste
- **Camada**: Model (Entity)
- **Tipo**: Unitário
- **ADR Referenciado**: ADR-012 (Testing Patterns)

## Casos de Teste

### 1. Criação de Skill com Builder
**Descrição**: Verificar se é possível criar uma instância de Skill usando o builder padrão.

**Pré-condições**:
- Nenhuma

**Passos**:
1. Criar Skill usando Skill.builder()
2. Definir campos obrigatórios (identificador, titulo)
3. Chamar build()

**Resultado Esperado**:
- Skill criada com sucesso
- Campos definidos corretamente

### 2. Valores Padrão
**Descrição**: Verificar se os campos com valores padrão são inicializados corretamente.

**Pré-condições**:
- Skill criada com builder sem definir valores padrão

**Passos**:
1. Criar Skill sem definir valores padrão
2. Verificar valores padrão

**Resultado Esperado**:
- Valores padrão inicializados corretamente

### 3. Geração de ID
**Descrição**: Verificar se o ID é gerado automaticamente antes da persistência.

**Pré-condições**:
- Skill criada sem ID

**Passos**:
1. Criar Skill sem definir ID
2. Verificar se @PrePersist gera o ID

**Resultado Esperado**:
- ID gerado automaticamente

### 4. Equals e HashCode
**Descrição**: Verificar se equals e hashCode funcionam corretamente considerando a herança.

**Pré-condições**:
- Duas skills com mesmo ID
- Duas skills com IDs diferentes

**Passos**:
1. Comparar skills com mesmo ID
2. Comparar skills com IDs diferentes
3. Verificar hashCode

**Resultado Esperado**:
- Skills com mesmo ID são iguais
- Skills com IDs diferentes são diferentes
- HashCode consistente com equals
