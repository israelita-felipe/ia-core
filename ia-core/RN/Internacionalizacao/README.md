# Regras de Negócio - Módulo Internacionalização

## Visão Geral
Este documento define as regras de negócio implementadas no módulo de Internacionalização (i18n) do ia-core-apps.

## Referência
- **CDU**: CDU029-Internacionalizacao
- **Service**: ia-core-service
- **Módulo**: ia-core-service

## Entidades

### Translator
Interface principal para internacionalização de mensagens.

## Regras Implementadas

##### INT_001 - LocalePadraoRegrasRule
- **Nome**: Locale Padrão É pt_BR
- **Descrição**: O locale padrão da aplicação é Português do Brasil
- **Critérios**:
  - getDefaultLocale() retorna sempre pt_BR
  - Traduções sem locale especificado usam pt_BR
  - pt_BR é o primeiro locale na lista de suportados
- **Severidade**: INFO
- **Referência CDU**: CDU029-Internacionalizacao

##### INT_002 - ChaveObrigatoriaTradusaoRule
- **Nome**: Chave Obrigatória para Tradução
- **Descrição**: Toda tradução requer uma chave única
- **Critérios**:
  - key é obrigatório em todas as chamadas getTranslation()
  - key não pode ser nula ou vazia
  - chaves são únicas por locale
- **Severidade**: ERRO
- **Referência CDU**: CDU029-Internacionalizacao

##### INT_003 - FallbackChaveOriginalRule
- **Nome**: Fallback para Chave Original
- **Descrição**: Se tradução não encontrada, retornar a chave original
- **Critérios**:
  - Sistema tenta locale especificado
  - Se falhar, tenta locale padrão
  - Se falhar, retorna a própria chave como fallback
- **Severidade**: INFO
- **Referência CDU**: CDU029-Internacionalizacao

##### INT_004 - CaseSensitiveChavesRule
- **Nome**: Chaves de Tradução são Case-Sensitive
- **Descrição**: Chaves de tradução diferenciam maiúsculas de minúsculas
- **Critérios**:
  - "error.notfound" ≠ "error.NotFound"
  - Buscas por chave são case-sensitive
  - Erros de digitação são reportados
- **Severidade**: INFO
- **Referência CDU**: CDU029-Internacionalizacao

##### INT_005 - ThreadSafeTradutorRule
- **Nome**: Implementação deve Ser Thread-Safe
- **Descrição**: A implementação de Translator deve ser thread-safe
- **Critérios**:
  - ResourceBundle é thread-safe por padrão
  - Cache deve usar ConcurrentMap
  - Métodos não devem modificar estado
- **Severidade**: INFO
- **Referência CDU**: CDU029-Internacionalizacao

##### INT_006 - CacheTraducaoRule
- **Nome**: Traduções Podem Ser Cacheadas
- **Descrição**: Traduções podem ser cacheadas para performance
- **Critérios**:
  - Cache é opcional mas recomendado
  - Cache deve ter TTL configurável
  - Cache deve ser invalidado em atualizações
- **Severidade**: INFO
- **Referência CDU**: CDU029-Internacionalizacao

## Validadores

- `TranslatorValidator` - Validações para implementações de Translator

## Padrão de Implementação

As regras de negócio seguem o padrão `BusinessRule<T>` do módulo ia-core-service:

```java
public class MinhaRegra implements BusinessRule<Translator> {
    @Override
    public String getCode() {
        return "INT_001";
    }

    @Override
    public String getName() {
        return "Minha Regra";
    }

    @Override
    public String getDescription() {
        return "Descrição da regra";
    }

    @Override
    public void validate(Translator entity, ValidationResult result) {
        // Lógica de validação
    }
}
```

## Referências

- ADR-053: Usar CDU para Documentação de Casos de Uso
- [ResourceBundle Documentation](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html)
- [Locale Documentation](https://docs.oracle.com/javase/8/docs/api/java/util/Locale.html)
- Service Base: `com.ia.core.service.rules.BusinessRule`