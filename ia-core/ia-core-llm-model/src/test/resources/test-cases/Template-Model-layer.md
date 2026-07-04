# Casos de Teste - Template (Model Layer)

## Contexto
A classe `Template` é uma entidade JPA que representa um template de prompt reutilizável no sistema LLM.

## Tipo de Teste
- **Camada**: Model (Entity)
- **Tipo**: Unitário
- **ADR Referenciado**: ADR-012 (Testing Patterns)

## Casos de Teste

### 1. Criação de Template com Builder
**Descrição**: Verificar se é possível criar uma instância de Template usando o builder padrão.

**Pré-condições**:
- Nenhuma

**Passos**:
1. Criar Template usando Template.builder()
2. Definir campos obrigatórios (nome, conteudo)
3. Chamar build()

**Resultado Esperado**:
- Template criado com sucesso
- Campos definidos corretamente

### 2. Valores Padrão
**Descrição**: Verificar se os campos com valores padrão são inicializados corretamente.

**Pré-condições**:
- Template criado com builder sem definir valores padrão

**Passos**:
1. Criar Template sem definir valores padrão
2. Verificar valores padrão

**Resultado Esperado**:
- Valores padrão inicializados corretamente

### 3. Geração de ID
**Descrição**: Verificar se o ID é gerado automaticamente antes da persistência.

**Pré-condições**:
- Template criado sem ID

**Passos**:
1. Criar Template sem definir ID
2. Verificar se @PrePersist gera o ID

**Resultado Esperado**:
- ID gerado automaticamente

### 4. Equals e HashCode
**Descrição**: Verificar se equals e hashCode funcionam corretamente considerando a herança.

**Pré-condições**:
- Dois templates com mesmo ID
- Dois templates com IDs diferentes

**Passos**:
1. Comparar templates com mesmo ID
2. Comparar templates com IDs diferentes
3. Verificar hashCode

**Resultado Esperado**:
- Templates com mesmo ID são iguais
- Templates com IDs diferentes são diferentes
- HashCode consistente com equals
