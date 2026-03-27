# ADR-037: Usar Semantic Versioning para Controle de Versão

## Status

✅ Aceito

## Contexto

O projeto precisa de um sistema de versionamento claro que:
- Indique claramente o tipo de mudança
- Permita automação de releases
- Comunique compatibilidade
- Facilite gerenciamento de dependências

## Decisão

Adotar **Semantic Versioning (SemVer)** 2.0.0 como padrão de versionamento.

## Detalhes

### Formato de Versão

```
MAJOR.MINOR.PATCH[-PRERELEASE][+BUILD]
Exemplo: 1.2.3, 2.0.0-beta.1, 1.0.0+20240115
```

| Número | Significado | Incremento quando |
|--------|-------------|-------------------|
| MAJOR | Incompatibilidade | Quebra de API |
| MINOR | Nova funcionalidade | Funcionalidade nova compat |
| PATCH | Bug fix | Correção compat |

### Regras de Incremento

```
1.0.0 → 1.1.0 (MINOR) → 1.1.1 (PATCH) → 2.0.0 (MAJOR)
```

### O que constitui uma mudança:

| Tipo | MAJOR | MINOR | PATCH |
|------|-------|-------|-------|
| API pública | ❌ Compat | ✅ Compat | ✅ Compat |
| Funcionalidade | Nova | Adicionada | - |
| Bug fix | - | - | Corrigido |
| Deprecação | Adicionada | - | - |

### Breaking Changes (MAJOR)

```java
// Antes
public interface PessoaService {
    List<Pessoa> findAll();  // retorna List
}

// Depois - BREAKING CHANGE
public interface PessoaService {
    Page<Pessoa> findAll(Pageable page);  // requer Pageable
}
```

### Exemplos de Versionamento

```bash
# 1.0.0 - Release inicial
# Primeira versão pública

# 1.1.0 - Nova funcionalidade
# Adicionado endpoint de busca por CPF

# 1.1.1 - Bug fix
# Corrigido bug de paginação

# 2.0.0 - Breaking changes
# Removido endpoint legado
# Nova estrutura de resposta

# 2.1.0 - Nova funcionalidade
# Adicionado filtro por período

# 2.1.0-beta.1 - Pre-release
# Versão beta com nova feature

# 2.1.0 - Release estável
# Versão final da 2.1.0
```

### Tags no Git

```bash
# Criar tag de versão
git tag -a v1.2.3 -m "Release versão 1.2.3"

# Push de tag
git push origin v1.2.3

# Listar tags
git tag -l
```

### Maven Version

```xml
<groupId>com.ia</groupId>
<artifactId>ia-core-service</artifactId>
<version>1.2.3</version>
```

### Integração com Conventional Commits

| Commit Type | Release Type |
|-------------|--------------|
| feat | MINOR |
| fix | PATCH |
| feat!: | MAJOR |
| fix!: | MAJOR |
| BREAKING CHANGE | MAJOR |

### Ferramentas de Release

```bash
# semantic-release (npm)
npx semantic-release

# Maven Release Plugin
mvn release:prepare release:perform

# GitHub Releases
# Automático via GitHub Actions
```

## Consequências

### Positivas

- ✅ Comunicação clara de mudanças
- ✅ Automação de releases
- ✅ Gerenciamento de dependências
- ✅ Previsibilidade de breaking changes

### Negativas

- ❌ Requer disciplina
- ❌ Curva de aprendizado
- ❌ Pode ser restritivo para projetos ágeis

## Status de Implementação

✅ **COMPLETO**

- Tags configuradas
- GitHub Actions para releases
- Maven central准备好了

## Data

2024-03-10

## Revisores

- Team Lead
- Architect

## Referências

1. **Semantic Versioning 2.0.0**
   - URL: https://semver.org/
   - Especificação oficial

2. **SemVer GitHub**
   - URL: https://github.com/semver/semver
   - Repositório

3. **Maven Versioning**
   - URL: https://maven.apache.org/maven-releasing.html
   - Release plugin

4. **GitHub Releases**
   - URL: https://docs.github.com/en/repositories/releasing-projects
   - Criação de releases

5. **Conventional Commits + SemVer**
   - URL: https://www.conventionalcommits.org/
   - Integração recomendada