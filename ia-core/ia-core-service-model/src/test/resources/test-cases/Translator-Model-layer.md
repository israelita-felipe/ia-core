# Casos de Teste - Translator (Model Layer)

## CDU Relacionado
CDU029-Internacionalizacao

## Descrição
Casos de teste básicos para validar o padrão Translator no módulo ia-core-service-model.

## Cenários de Teste

### CT001 - Obter Locale Padrão
**Descrição**: Validar método getDefaultLocale()
**Entrada**: Nenhuma
**Resultado Esperado**: Locale pt_BR retornado
**Tipo**: Unitário
**Prioridade**: Alta

### CT002 - Listar Locales Suportados
**Descrição**: Validar método getSupportedLocales()
**Entrada**: Nenhuma
**Resultado Esperado**: Lista de locales configurados retornada
**Tipo**: Unitário
**Prioridade**: Alta

### CT003 - Obter Tradução com Locale Específico
**Descrição**: Validar getTranslation(key, locale)
**Entrada**: Chave e locale válidos
**Resultado Esperado**: Tradução no locale especificado retornada
**Tipo**: Unitário
**Prioridade**: Alta

### CT004 - Obter Tradução com Locale Padrão
**Descrição**: Validar getTranslation(key)
**Entrada**: Chave válida
**Resultado Esperado**: Tradução em pt_BR retornada
**Tipo**: Unitário
**Prioridade**: Alta

### CT005 - Tradução com Parâmetros
**Descrição**: Validar getTranslation(key, locale, params)
**Entrada**: Chave, locale e parâmetros
**Resultado Esperado**: Tradução com parâmetros substituídos
**Tipo**: Unitário
**Prioridade**: Média

### CT006 - Tradução com Chave Inexistente
**Descrição**: Validar comportamento com chave não encontrada
**Entrada**: Chave inexistente
**Resultado Esperado**: Chave original retornada ou erro
**Tipo**: Unitário
**Prioridade**: Alta

### CT007 - Tradução com Locale Não Suportado
**Descrição**: Validar comportamento com locale não suportado
**Entrada**: Locale não configurado
**Resultado Esperado**: Fallback para locale padrão ou erro
**Tipo**: Unitário
**Prioridade**: Média

## Gaps de Cobertura
- Testes de integração com ResourceBundles
- Testes de performance de cache
- Testes de hot-reload de traduções

## Referências
- CDU029-Internacionalizacao
- ADR-012: Testing Patterns
