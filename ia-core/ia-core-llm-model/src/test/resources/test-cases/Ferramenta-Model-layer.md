# Casos de Teste - Ferramenta (Model Layer)

## Contexto
A classe `Ferramenta` é uma entidade JPA que representa uma ferramenta disponível para uso pelos agentes no sistema LLM.

## Tipo de Teste
- **Camada**: Model (Entity)
- **Tipo**: Unitário
- **ADR Referenciado**: ADR-012 (Testing Patterns)

## Casos de Teste

### 1. Criação de Ferramenta com Builder
**Descrição**: Verificar se é possível criar uma instância de Ferramenta usando o builder padrão.

**Pré-condições**:
- Nenhuma

**Passos**:
1. Criar Ferramenta usando Ferramenta.builder()
2. Definir campos obrigatórios (identificador, titulo)
3. Chamar build()

**Resultado Esperado**:
- Ferramenta criada com sucesso
- Campos definidos corretamente

### 2. Valores Padrão
**Descrição**: Verificar se os campos com valores padrão são inicializados corretamente.

**Pré-condições**:
- Ferramenta criada com builder sem definir valores padrão

**Passos**:
1. Criar Ferramenta sem definir valores padrão
2. Verificar valores padrão

**Resultado Esperado**:
- Valores padrão inicializados corretamente

### 3. Geração de ID
**Descrição**: Verificar se o ID é gerado automaticamente antes da persistência.

**Pré-condições**:
- Ferramenta criada sem ID

**Passos**:
1. Criar Ferramenta sem definir ID
2. Verificar se @PrePersist gera o ID

**Resultado Esperado**:
- ID gerado automaticamente

### 4. Equals e HashCode
**Descrição**: Verificar se equals e hashCode funcionam corretamente considerando a herança.

**Pré-condições**:
- Duas ferramentas com mesmo ID
- Duas ferramentas com IDs diferentes

**Passos**:
1. Comparar ferramentas com mesmo ID
2. Comparar ferramentas com IDs diferentes
3. Verificar hashCode

**Resultado Esperado**:
- Ferramentas com mesmo ID são iguais
- Ferramentas com IDs diferentes são diferentes
- HashCode consistente com equals
