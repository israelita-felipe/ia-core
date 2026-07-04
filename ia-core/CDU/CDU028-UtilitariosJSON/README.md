# CDU - Utilitários JSON

## 1. Metadados
- **Nome do CDU**: Utilitários JSON
- **Versão**: 1.0
- **Data**: 2026-06-18
- **Autor**: IA Core
- **Status**: Em Revisão

## 2. Descrição do Caso de Uso

### 2.1. Descrição Breve
O caso de uso "Utilitários JSON" define o padrão para manipulação de dados JSON usando Gson, fornecendo métodos para serialização, deserialização e extração de propriedades de objetos JSON. O utilitário suporta tipos temporais (LocalDateTime, LocalDate, LocalTime) com adaptadores customizados.

### 2.2. Objetivos
- Fornecer métodos utilitários para manipulação de JSON
- Suportar serialização de objetos para JSON
- Suportar deserialização de JSON para objetos
- Permitir extração de propriedades aninhadas de JSON
- Tratar tipos temporais com adaptadores customizados
- Fornecer interface fluente para operações complexas

### 2.3. Escopo
**Incluído**:
- JsonUtil: Classe utilitária principal
- Serialização de objetos para JSON string
- Deserialização de JSON string para objetos
- Extração de propriedades de objetos JSON
- Suporte a propriedades aninhadas (dot notation)
- Adaptadores customizados para tipos temporais
- Tratamento de valores nulos

**Excluído**:
- Validação de esquema JSON (delegado para bibliotecas especializadas)
- Transformação complexa de JSON (delegado para bibliotecas como JsonPath)
- Geração de JSON Schema (delegado para bibliotecas especializadas)

## 3. Atores

| Ator | Descrição | Tipo |
|------|------------|------|
| Desenvolvedor | Desenvolvedor que usa JsonUtil para manipular JSON | Primário |
| Sistema | Sistema que processa dados JSON | Sistema |

## 4. Pré-condições

### 4.1. Para Serializar Objeto
- Objeto a ser serializado deve estar disponível
- Gson deve estar configurado com adaptadores necessários
- Objeto deve ser serializável (POJO com getters/setters ou anotações)

### 4.2. Para Deserializar JSON
- String JSON válida deve estar disponível
- Classe de destino deve estar disponível
- Gson deve estar configurado com adaptadores necessários

### 4.3. Para Extrair Propriedade
- String JSON válida deve estar disponível
- Caminho da propriedade deve estar especificado
- Gson deve estar configurado

## 5. Pós-condições

### 5.1. Após Serialização Bem-Sucedida
- String JSON é gerada
- Tipos temporais são formatados corretamente
- Valores nulos são tratados conforme configuração

### 5.2. Após Deserialização Bem-Sucedida
- Objeto é instanciado
- Propriedades são populadas corretamente
- Tipos temporais são convertidos corretamente

### 5.3. Após Extração Bem-Sucedida
- Valor da propriedade é retornado
- Propriedades aninhadas são acessadas corretamente
- Tipo de retorno é apropriado

## 6. Fluxo Principal

### 6.1. Serialização de Objeto Simples
**Given**: Objeto POJO disponível
**When**: Desenvolvedor chama JsonUtil.toJson(objeto)
**Then**: String JSON é gerada com todas as propriedades

### 6.2. Deserialização de JSON para Objeto
**Given**: String JSON válida disponível
**When**: Desenvolvedor chama JsonUtil.fromJson(json, Classe.class)
**Then**: Objeto é instanciado com propriedades populadas

### 6.3. Extração de Propriedade Simples
**Given**: String JSON com propriedade "nome"
**When**: Desenvolvedor chama JsonUtil.extractProperty(json, "nome")
**Then**: Valor da propriedade "nome" é retornado

### 6.4. Extração de Propriedade Aninhada
**Given**: String JSON com propriedade aninhada "usuario.endereco.rua"
**When**: Desenvolvedor chama JsonUtil.extractProperty(json, "usuario.endereco.rua")
**Then**: Valor da propriedade aninhada é retornado

### 6.5. Serialização com Tipo Temporal
**Given**: Objeto com campo LocalDateTime
**When**: Desenvolvedor chama JsonUtil.toJson(objeto)
**Then**: Campo temporal é serializado com formato ISO-8601

### 6.6. Deserialização com Tipo Temporal
**Given**: String JSON com data em formato ISO-8601
**When**: Desenvolvedor chama JsonUtil.fromJson(json, Classe.class)
**Then**: Campo temporal é convertido para LocalDateTime corretamente

## 7. Fluxos Alternativos

### 7.1. Serialização com Pretty Print
**Given**: Desenvolvedor deseja JSON formatado
**When**: Desenvolvedor configura Gson com setPrettyPrinting()
**Then**: JSON é gerado com indentação

### 7.2. Deserialização com Tipo Genérico
**Given**: Desenvolvedor deseja deserializar para tipo genérico
**When**: Desenvolvedor usa TypeToken para especificar tipo
**Then**: Objeto genérico é instanciado corretamente

### 7.3. Extração com Valor Padrão
**Given**: Propriedade não existe no JSON
**When**: Desenvolvedor especifica valor padrão
**Then**: Valor padrão é retornado em vez de null

## 8. Fluxos de Exceção

### 8.1. JSON Inválido
**Given**: String JSON malformada
**When**: Sistema tenta deserializar
**Then**: JsonSyntaxException é lançada

### 8.2. Propriedade Inexistente
**Given**: Caminho de propriedade não existe no JSON
**When**: Sistema tenta extrair propriedade
**Then**: Null é retornado ou exceção é lançada

### 8.3. Tipo Incompatível
**Given**: Tipo de destino incompatível com JSON
**When**: Sistema tenta deserializar
**Then**: JsonParseException é lançada

## 9. Regras de Negócio

| ID | Regra | Descrição |
|----|-------|-----------|
| RN001 | FormatoTemporal | Tipos temporais são serializados/deserializados em formato ISO-8601 |
| RN002 | TratamentoNulo | Valores nulos são incluídos por padrão |
| RN003 | NotacaoPonto | Propriedades aninhadas são acessadas via dot notation |
| RN004 | CaseSensitive | Nomes de propriedades são case-sensitive |
| RN005 | Singleton | Gson é compartilhado entre chamadas (thread-safe) |
| RN006 | Adaptadores | Adaptadores customizados são registrados uma vez |

## 10. Estrutura de Dados

### 10.1. JsonUtil
```java
public class JsonUtil {
    private static final Gson gson;

    static {
        gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
            .create();
    }

    public static String toJson(Object obj);
    public static <T> T fromJson(String json, Class<T> classOfT);
    public static <T> T fromJson(String json, Type typeOfT);
    public static Object extractProperty(String json, String propertyPath);
    public static <T> T extractProperty(String json, String propertyPath, Class<T> type);
}
```

### 10.2. Adaptadores Temporais
```java
public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    @Override
    public JsonElement serialize(LocalDateTime src, Type type, JsonSerializationContext context);

    @Override
    public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext context);
}
```

## 11. Contratos de Interface

### 11.1. JsonUtil.toJson()
- **Parâmetros**: obj (Object)
- **Retorno**: String
- **Comportamento**: Serializa objeto para JSON string

### 11.2. JsonUtil.fromJson()
- **Parâmetros**: json (String), classOfT (Class<T>) ou typeOfT (Type)
- **Retorno**: T
- **Comportamento**: Deserializa JSON string para objeto do tipo especificado

### 11.3. JsonUtil.extractProperty()
- **Parâmetros**: json (String), propertyPath (String), type (Class<T>) opcional
- **Retorno**: Object ou T
- **Comportamento**: Extrai valor de propriedade usando dot notation

## 12. Requisitos Especiais

### 12.1. Performance
- Gson deve ser configurado como singleton
- Adaptadores devem ser reutilizáveis
- Cache de reflexão deve ser utilizado pelo Gson

### 12.2. Thread-Safety
- Gson é thread-safe
- JsonUtil deve ser thread-safe
- Adaptadores customizados devem ser thread-safe

### 12.3. Internacionalização
- Formatos de data/hora devem seguir ISO-8601
- Timezones devem ser tratados consistentemente

## 13. Pontos de Extensão

### 13.1. Adaptadores Customizados
- Módulos podem registrar adaptadores para tipos específicos
- Adaptadores devem seguir padrão JsonSerializer/JsonDeserializer

### 13.2. Serialização Condicional
- Módulos podem adicionar lógica de exclusão condicional
- ExclusionStrategy pode ser configurada no Gson

### 13.3. Pós-Processamento
- Módulos podem adicionar hooks de pós-processamento
- TypeAdapterFactory pode ser usado para customização

## 14. Referências

- [Gson Documentation](https://github.com/google/gson)
- [ISO-8601](https://en.wikipedia.org/wiki/ISO_8601)
- [ADR-012: Testing Patterns](/home/israel/git/ia-core-apps/ia-core/ADR/012-testing-patterns.md)
