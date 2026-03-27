# ADR-036: Usar Conventional Commits para Mensagens de Commit

## Status

✅ Aceito

## Contexto

O projeto precisa de um padrão claro para mensagens de commit que:
- Permita geração automática de changelogs
- Facilite leitura do histórico
- permita filtragem de commits por tipo
- Integre com versionamento semântico

## Decisão

Adotar **Conventional Commits** como padrão de mensagens de commit.

## Detalhes

### Estrutura da Mensagem

```
<tipo>(<escopo>): <descrição>

[corpo opcional]

[footer opcional]
```

### Tipos de Commit

| Tipo | Descrição |
|------|-----------|
| feat | Nova funcionalidade |
| fix | Bug fix |
| docs | Documentação |
| style | Formatação (não muda lógica) |
| refactor | Refatoração (não Adds functionality) |
| test | Adição/modificação de testes |
| chore | Tarefas de build, tooling |
| perf | Performance improvement |
| ci | Changes to CI configuration |
| revert | Reverte commit anterior |

### Exemplos de Commits

```bash
# Feature simples
git commit -m "feat(pessoa): adiciona endpoint para buscar pessoa por CPF"

# Feature com escopo
git commit -m "feat(evento): adiciona validação de data futura"

# Bug fix
git commit -m "fix(pagina): corrige erro de renderização em evento sem local"

# Com corpo e footer
git commit -m "fix(auth): corrige token JWT expirando prematuramente

O token estava com tempo de vida inferior ao configurado devido a
um erro de conversão de milissegundos.

Closes #123
Refs #456"
```

### Escopos Comuns

| Escopo | Descrição |
|--------|-----------|
| api | Controllers, endpoints |
| service | Lógica de negócio |
| repository | Acesso a dados |
| model | Entidades, DTOs |
| view | UI, ViewModels |
| security | Autenticação, autorização |
| config | Configurações |
| build | Build, dependências |
| ci | Pipelines, actions |
| docs | Documentação |

### Regular Commits

```
feat: adiciona suporte a paginação
fix: corrige bug na busca
docs: atualiza readme
style: formata código
refactor: simplifica lógica
test: adiciona testes unitários
chore: atualiza dependência
```

### Regras de Nomenclatura

| Regra | Exemplo |
|-------|---------|
| Usar imperativo | "add" não "added" |
| Minúsculo | "feat" não "FEAT" |
| Sem ponto final | "adiciona" não "adiciona." |
| 72 caracteres max (linha 1) | - |
| Corpo em 100 caracteres | - |

### Integração com Versionamento

Commits com `feat` → Release minor (1.1.0)
Commits com `fix` → Release patch (1.0.1)
Commits com `BREAKING CHANGE` → Release major (2.0.0)

### Ferramentas Recomendadas

```bash
# Commitlint - Validação de commits
npm install --save-dev @commitlint/cli @commitlint/config-conventional

# Commitizen - CLI interativo
npm install --save-dev commitizen cz-conventional-changelog
```

### Git Hooks

```json
// .husky/commit-msg
{
  "hooks": {
    "commit-msg": "commitlint -E HUSKY_GIT_PARAMS"
  }
}
```

## Consequências

### Positivas

- ✅ Changelog automático
- ✅ Histórico legível
- ✅ Facilita debugging
- ✅ Integração com CI/CD
- ✅ Versionamento automático

### Negativas

- ❌ Curva de aprendizado
- ❌ Requer disciplina
- ❌ Pode parecer restritivo

## Status de Implementação

✅ **COMPLETO**

- Husky configurado
- Commitlint integrado
- Conventional changelog ativo

## Data

2024-03-05

## Revisores

- Team Lead
- Architect

## Referências

1. **Conventional Commits**
   - URL: https://www.conventionalcommits.org/
   - Site oficial

2. **Conventional Commits GitHub**
   - URL: https://github.com/conventional-commits/conventional-commits
   - Repositório

3. **Commitlint Documentation**
   - URL: https://commitlint.js.org/
   - Ferramenta de validação

4. **Semantic Versioning**
   - URL: https://semver.org/
   - Versionamento

5. **Keep a Changelog**
   - URL: https://keepachangelog.com/
   - Padrão de changelog