# CDU - Internacionalização

## 1. Metadados
- **Nome do CDU**: Internacionalização
- **Versão**: 1.0
- **Data**: 2026-06-18
- **Autor**: IA Core
- **Status**: Em Revisão

## 2. Descrição do Caso de Uso

### 2.1. Descrição Breve
O caso de uso "Internacionalização" define o padrão para suporte a múltiplos idiomas em aplicações, fornecendo uma interface para tradução de mensagens e gerenciamento de locales. O padrão permite que aplicações se adaptem a diferentes idiomas e regiões de forma consistente.

### 2.2. Objetivos
- Definir interface padrão para internacionalização
- Suportar múltiplos idiomas simultaneamente
- Fornecer locale padrão (pt_BR)
- Permitir recuperação de traduções por chave
- Suportar formatação de números, datas e moedas
- Fornecer lista de locales suportados

### 2.3. Escopo
**Incluído**:
- Translator: Interface principal de internacionalização
- getDefaultLocale(): Retorna locale padrão
- getSupportedLocales(): Retorna lista de locales suportados
- getTranslation(key, locale): Retorna tradução para chave e locale
- getTranslation(key): Retorna tradução para locale padrão
- Suporte a pt_BR como locale padrão

**Excluído**:
- Armazenamento de traduções (delegado para ResourceBundles ou banco de dados)
- Detecção automática de locale do usuário (delegado para camada de apresentação)
- Formatação de números/datas/moedas (delegado para DateFormat, NumberFormat)
- Validação de chaves de tradução (delegado para implementação)

## 3. Atores

| Ator | Descrição | Tipo |
|------|------------|------|
| Desenvolvedor | Desenvolvedor que usa Translator para internacionalizar mensagens | Primário |
| Usuário Final | Usuário que interage com aplicação em seu idioma preferido | Secundário |
| Sistema | Sistema que aplica traduções automaticamente | Sistema |

## 4. Pré-condições

### 4.1. Para Obter Tradução
- Chave de tradução deve estar disponível
- Locale deve estar especificado ou usar padrão
- Recursos de tradução devem estar carregados

### 4.2. Para Listar Locales
- Implementação de Translator deve estar disponível
- Locales suportados devem estar configurados

### 4.3. Para Usar Locale Padrão
- Locale padrão deve estar configurado
- Recursos para locale padrão devem estar disponíveis

## 5. Pós-condições

### 5.1. Após Obter Tradução Bem-Sucedida
- Mensagem traduzida é retornada
- Placeholder são substituídos se aplicável
- Locale correto é usado

### 5.2. Após Listar Locales
- Lista de locales suportados é retornada
- Locales são válidos e configurados

### 5.3. Após Tradução Não Encontrada
- Chave original pode ser retornada
- Ou mensagem de erro padrão pode ser retornada
- Log de aviso pode ser gerado

## 6. Fluxo Principal

### 6.1. Obter Tradução com Locale Específico
**Given**: Chave de tradução e locale disponíveis
**When**: Desenvolvedor chama translator.getTranslation(key, locale)
**Then**: Tradução no locale especificado é retornada

### 6.2. Obter Tradução com Locale Padrão
**Given**: Apenas chave de tradução disponível
**When**: Desenvolvedor chama translator.getTranslation(key)
**Then**: Tradução no locale padrão (pt_BR) é retornada

### 6.3. Obter Locale Padrão
**Given**: Sistema precisa saber locale padrão
**When**: Desenvolvedor chama translator.getDefaultLocale()
**Then**: Locale pt_BR é retornado

### 6.4. Listar Locales Suportados
**Given**: Sistema precisa saber quais locales são suportados
**When**: Desenvolvedor chama translator.getSupportedLocales()
**Then**: Lista de locales configurados é retornada

### 6.5. Verificar Suporte a Locale
**Given**: Sistema precisa verificar se locale é suportado
**When**: Desenvolvedor verifica se locale está na lista de suportados
**Then**: Boolean indicando suporte é retornado

## 7. Fluxos Alternativos

### 7.1. Tradução com Placeholder
**Given**: Chave de tradução contém placeholder {0}
**When**: Desenvolvedor fornece valores para substituição
**Then**: Placeholder são substituídos nos valores fornecidos

### 7.2. Tradução com Parâmetros Nomeados
**Given**: Chave de tradução contém placeholder {nome}
**When**: Desenvolvedor fornece mapa de parâmetros
**Then**: Placeholder são substituídos pelos valores do mapa

### 7.3. Fallback para Locale Padrão
**Given**: Tradução não encontrada no locale especificado
**When**: Sistema tenta locale padrão
**Then**: Tradução do locale padrão é retornada se disponível

## 8. Fluxos de Exceção

### 8.1. Chave Não Encontrada
**Given**: Chave de tradução não existe
**When**: Sistema tenta obter tradução
**Then**: Chave original é retornada ou mensagem de erro

### 8.2. Locale Não Suportado
**Given**: Locale especificado não é suportado
**When**: Sistema tenta obter tradução
**Then**: Locale padrão é usado ou erro é reportado

### 8.3. Recursos Não Carregados
**Given**: Arquivos de tradução não estão disponíveis
**When**: Sistema tenta obter tradução
**Then**: Erro é reportado ou fallback é aplicado

## 9. Regras de Negócio

| ID | Regra | Descrição |
|----|-------|-----------|
| RN001 | LocalePadrao | Locale padrão é pt_BR (Português do Brasil) |
| RN002 | ChaveObrigatoria | Toda tradução requer uma chave única |
| RN003 | Fallback | Se tradução não encontrada, usar chave original |
| RN004 | CaseSensitive | Chaves de tradução são case-sensitive |
| RN005 | ThreadSafe | Implementação deve ser thread-safe |
| RN006 | Cache | Traduções podem ser cacheadas para performance |

## 10. Estrutura de Dados

### 10.1. Translator
```java
public interface Translator {
    /**
     * Retorna o locale padrão da aplicação
     */
    Locale getDefaultLocale();

    /**
     * Retorna lista de locales suportados
     */
    List<Locale> getSupportedLocales();

    /**
     * Retorna tradução para chave no locale especificado
     */
    String getTranslation(String key, Locale locale);

    /**
     * Retorna tradução para chave no locale padrão
     */
    String getTranslation(String key);

    /**
     * Retorna tradução com parâmetros
     */
    String getTranslation(String key, Locale locale, Object... params);
}
```

### 10.2. Exemplo de Implementação
```java
@Service
public class ResourceBundleTranslator implements Translator {
    private static final Locale DEFAULT_LOCALE = new Locale("pt", "BR");
    private static final List<Locale> SUPPORTED_LOCALES = List.of(
        DEFAULT_LOCALE,
        Locale.ENGLISH,
        Locale.FRENCH
    );

    @Override
    public Locale getDefaultLocale() {
        return DEFAULT_LOCALE;
    }

    @Override
    public List<Locale> getSupportedLocales() {
        return SUPPORTED_LOCALES;
    }

    @Override
    public String getTranslation(String key, Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        return bundle.getString(key);
    }

    @Override
    public String getTranslation(String key) {
        return getTranslation(key, DEFAULT_LOCALE);
    }
}
```

## 11. Contratos de Interface

### 11.1. Translator.getDefaultLocale()
- **Parâmetros**: Nenhum
- **Retorno**: Locale
- **Comportamento**: Retorna locale padrão da aplicação (pt_BR)

### 11.2. Translator.getSupportedLocales()
- **Parâmetros**: Nenhum
- **Retorno**: List<Locale>
- **Comportamento**: Retorna lista de locales configurados

### 11.3. Translator.getTranslation(key, locale)
- **Parâmetros**: key (String), locale (Locale)
- **Retorno**: String
- **Comportamento**: Retorna tradução para chave no locale especificado

### 11.4. Translator.getTranslation(key)
- **Parâmetros**: key (String)
- **Retorno**: String
- **Comportamento**: Retorna tradução para chave no locale padrão

## 12. Requisitos Especiais

### 12.1. Performance
- Traduções devem ser cacheadas quando possível
- ResourceBundles devem ser carregados uma vez
- Acesso a traduções deve ser rápido

### 12.2. Thread-Safety
- Implementação deve ser thread-safe
- ResourceBundles são thread-safe por padrão
- Cache deve ser thread-safe

### 12.3. Manutenção
- Arquivos de tradução devem seguir convenção de nomenclatura
- Chaves devem ser organizadas por domínio
- Documentação de chaves deve ser mantida

## 13. Pontos de Extensão

### 13.1. Implementações Customizadas
- Módulos podem implementar Translator com diferentes estratégias
- Implementações podem usar banco de dados em vez de ResourceBundles
- Implementações podem suportar hot-reload de traduções

### 13.2. Validação de Chaves
- Módulos podem adicionar validação de chaves em tempo de compilação
- Ferramentas podem verificar se todas as chaves estão traduzidas

### 13.3. Integração com Jakarta Validation
- Traduções podem ser usadas em mensagens de validação
- Interface Translator pode ser integrada com @Valid

## 14. Referências

- [ResourceBundle Documentation](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html)
- [Locale Documentation](https://docs.oracle.com/javase/8/docs/api/java/util/Locale.html)
- [ADR-012: Testing Patterns](/home/israel/git/ia-core-apps/ia-core/ADR/012-testing-patterns.md)
