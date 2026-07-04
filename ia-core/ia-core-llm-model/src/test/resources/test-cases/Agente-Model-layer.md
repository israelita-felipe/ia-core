# Casos de Teste - Agente (Model Layer)

## Contexto
A classe `Agente` é uma entidade JPA que representa um agente para orquestração de ferramentas e skills no sistema LLM.

## Tipo de Teste
- **Camada**: Model (Entity)
- **Tipo**: Unitário
- **ADR Referenciado**: ADR-012 (Testing Patterns)

## Casos de Teste

### 1. Criação de Agente com Builder
**Descrição**: Verificar se é possível criar uma instância de Agente usando o builder padrão.

**Pré-condições**:
- Nenhuma

**Passos**:
1. Criar Agente usando Agente.builder()
2. Definir campos obrigatórios (identificador, titulo)
3. Chamar build()

**Resultado Esperado**:
- Agente criado com sucesso
- Campos definidos corretamente

### 2. Validação de Campos Obrigatórios
**Descrição**: Verificar se os campos obrigatórios são validados corretamente.

**Pré-condições**:
- Agente criado sem campos obrigatórios

**Passos**:
1. Tentar criar Agente sem identificador
2. Tentar criar Agente sem titulo

**Resultado Esperado**:
- Validação falha quando campos obrigatórios não estão presentes

### 3. Valores Padrão
**Descrição**: Verificar se os campos com valores padrão são inicializados corretamente.

**Pré-condições**:
- Agente criado com builder sem definir valores padrão

**Passos**:
1. Criar Agente sem definir ativo
2. Criar Agente sem definir temperature
3. Criar Agente sem definir maxTokens

**Resultado Esperado**:
- ativo = true
- temperature = 0.7
- maxTokens = 2048

### 4. Relacionamento com Ferramentas
**Descrição**: Verificar se é possível adicionar e remover ferramentas do agente.

**Pré-condições**:
- Agente criado
- Ferramenta criada

**Passos**:
1. Adicionar ferramenta usando adicionarFerramenta()
2. Verificar se ferramenta está no conjunto
3. Remover ferramenta usando removerFerramenta()
4. Verificar se ferramenta não está mais no conjunto

**Resultado Esperado**:
- Ferramenta adicionada com sucesso
- Ferramenta removida com sucesso

### 5. Relacionamento com Skills
**Descrição**: Verificar se é possível adicionar e remover skills do agente.

**Pré-condições**:
- Agente criado
- Skill criada

**Passos**:
1. Adicionar skill usando adicionarSkill()
2. Verificar se skill está no conjunto
3. Remover skill usando removerSkill()
4. Verificar se skill não está mais no conjunto

**Resultado Esperado**:
- Skill adicionada com sucesso
- Skill removida com sucesso

### 6. Geração de ID
**Descrição**: Verificar se o ID é gerado automaticamente antes da persistência.

**Pré-condições**:
- Agente criado sem ID

**Passos**:
1. Criar Agente sem definir ID
2. Verificar se @PrePersist gera o ID

**Resultado Esperado**:
- ID gerado automaticamente

### 7. Equals e HashCode
**Descrição**: Verificar se equals e hashCode funcionam corretamente considerando a herança.

**Pré-condições**:
- Dois agentes com mesmo ID
- Dois agentes com IDs diferentes

**Passos**:
1. Comparar agentes com mesmo ID
2. Comparar agentes com IDs diferentes
3. Verificar hashCode

**Resultado Esperado**:
- Agentes com mesmo ID são iguais
- Agentes com IDs diferentes são diferentes
- HashCode consistente com equals
