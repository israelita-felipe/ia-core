# Casos de Teste - Ontologia (Model Layer)

## Contexto
A classe `Ontologia` é uma entidade JPA que representa uma ontologia no sistema LLM.

## Tipo de Teste
- **Camada**: Model (Entity)
- **Tipo**: Unitário
- **ADR Referenciado**: ADR-012 (Testing Patterns)

## Casos de Teste

### 1. Criação de Ontologia com Builder
**Descrição**: Verificar se é possível criar uma instância de Ontologia usando o builder padrão.

**Pré-condições**:
- Nenhuma

**Passos**:
1. Criar Ontologia usando Ontologia.builder()
2. Definir campos obrigatórios (nome, formato)
3. Chamar build()

**Resultado Esperado**:
- Ontologia criada com sucesso
- Campos definidos corretamente

### 2. Valores Padrão
**Descrição**: Verificar se os campos com valores padrão são inicializados corretamente.

**Pré-condições**:
- Ontologia criada com builder sem definir valores padrão

**Passos**:
1. Criar Ontologia sem definir valores padrão
2. Verificar valores padrão

**Resultado Esperado**:
- Valores padrão inicializados corretamente

### 3. Geração de ID
**Descrição**: Verificar se o ID é gerado automaticamente antes da persistência.

**Pré-condições**:
- Ontologia criada sem ID

**Passos**:
1. Criar Ontologia sem definir ID
2. Verificar se @PrePersist gera o ID

**Resultado Esperado**:
- ID gerado automaticamente

### 4. Equals e HashCode
**Descrição**: Verificar se equals e hashCode funcionam corretamente considerando a herança.

**Pré-condições**:
- Duas ontologias com mesmo ID
- Duas ontologias com IDs diferentes

**Passos**:
1. Comparar ontologias com mesmo ID
2. Comparar ontologias com IDs diferentes
3. Verificar hashCode

**Resultado Esperado**:
- Ontologias com mesmo ID são iguais
- Ontologias com IDs diferentes são diferentes
- HashCode consistente com equals
