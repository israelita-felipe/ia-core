# ia-core-nlp

## 📋 Descrição

Módulo de Processamento de Linguagem Natural (NLP). Fornece funcionalidades para análise e processamento de texto, incluindo tokenização, análise sintática, reconhecimento de entidades nomeadas e análise de sentimentos.

## 🏗️ Estrutura

```
ia-core-nlp/
├── src/main/java/
│   └── com/ia/core/nlp/
│       ├── service/                 # Serviços NLP
│       ├── analyzer/                # Analisadores de texto
│       ├── tokenizer/               # Tokenização
│       ├── ner/                     # Named Entity Recognition
│       ├── sentiment/               # Análise de sentimentos
│       └── util/                    # Utilitários NLP
├── src/main/resources/models/       # Modelos treinados
└── pom.xml
```

## 🔑 Responsabilidades

- **Tokenização**: Quebrar texto em palavras/sentenças
- **Análise Sintática**: Identificar estrutura gramatical
- **NER (Named Entity Recognition)**: Identificar entidades (pessoas, lugares, organizações)
- **Análise de Sentimentos**: Classificar sentimento (positivo, negativo, neutro)
- **POS Tagging**: Marcar partes da fala (noun, verb, adj, etc.)
- **Stopword Removal**: Remover palavras comuns
- **Lematização**: Reduzir palavras à forma base

## 🛠️ Tecnologias Utilizadas

- **Apache OpenNLP**: Processamento NLP em Java
- **Stanford CoreNLP**: Análise profunda de texto
- **NLTK Models**: Modelos pré-treinados
- Spring Boot
- Lombok

## 📦 Dependências

- `ia-core-service` - Serviços base
- `org.apache.opennlp:opennlp-tools`
- `edu.stanford.nlp:stanford-corenlp`

## 🔗 Relacionamentos

Depende de:
- `ia-core-service` - Herda serviços base
- `ia-core-model` - Modelos compartilhados

Utilizado por:
- `ia-core-rest` - Endpoints para análise de texto
- `ia-core-llm-service` - Preprocessamento para LLM
- Aplicações que precisam processar texto

## 💡 Padrões Implementados

- **Strategy Pattern**: Diferentes estratégias de análise
- **Pipeline Pattern**: Processamento sequencial de texto
- **Cache Pattern**: Cache de modelos carregados

## 🚀 Como Usar

### Usar Tokenizer

```java
@Service
public class TextTokenizationService {

    @Autowired
    private OpenNLPTokenizer tokenizer;

    /**
     * Tokeniza um texto em sentencer
     */
    public String[] tokenizeSentences(String text) {
        return tokenizer.tokenizeSentences(text);
    }

    /**
     * Tokeniza uma sentença em palavras
     */
    public String[] tokenizeWords(String sentence) {
        return tokenizer.tokenizeWords(sentence);
    }

    /**
     * Exemplo de uso
     */
    public void exemplo() {
        String texto = "Java é incrível. Vou aprender NLP.";

        String[] sentencas = tokenizeSentences(texto);
        // ["Java é incrível.", "Vou aprender NLP."]

        String[] palavras = tokenizeWords(sentencas[0]);
        // ["Java", "é", "incrível", "."]
    }
}
```

### Usar Named Entity Recognition (NER)

```java
@Service
public class EntityRecognitionService {

    @Autowired
    private OpenNLPNER ner;

    /**
     * Extrai entidades nomeadas do texto
     */
    public List<Entity> extractEntities(String text) {
        return ner.extractEntities(text);
    }

    /**
     * Extrai apenas pessoas
     */
    public List<String> extractPersonNames(String text) {
        return ner.extractPersons(text);
    }

    /**
     * Extrai apenas organizações
     */
    public List<String> extractOrganizations(String text) {
        return ner.extractOrganizations(text);
    }

    /**
     * Extrai apenas locais
     */
    public List<String> extractLocations(String text) {
        return ner.extractLocations(text);
    }

    /**
     * Exemplo de uso
     */
    public void exemplo() {
        String texto = "João trabalha na Google em São Paulo.";

        List<Entity> entities = extractEntities(texto);
        // [
        //   { text: "João", type: "PERSON" },
        //   { text: "Google", type: "ORGANIZATION" },
        //   { text: "São Paulo", type: "LOCATION" }
        // ]
    }
}
```

### Usar Análise de Sentimento

```java
@Service
public class SentimentAnalysisService {

    @Autowired
    private SentimentAnalyzer sentimentAnalyzer;

    /**
     * Analisa sentimento de um texto
     */
    public SentimentResult analyzeSentiment(String text) {
        return sentimentAnalyzer.analyze(text);
    }

    /**
     * Classifica em categorias
     */
    public SentimentClass classifySentiment(String text) {
        SentimentResult result = analyzeSentiment(text);

        if (result.getScore() > 0.6) {
            return SentimentClass.POSITIVE;
        } else if (result.getScore() < 0.4) {
            return SentimentClass.NEGATIVE;
        } else {
            return SentimentClass.NEUTRAL;
        }
    }

    /**
     * Exemplo de uso
     */
    public void exemplo() {
        String texto = "Adorei este produto! Muito bom mesmo!";

        SentimentResult resultado = analyzeSentiment(texto);
        // score: 0.92 (muito positivo)

        SentimentClass classe = classifySentiment(texto);
        // POSITIVE
    }
}

public enum SentimentClass {
    POSITIVE("Positivo"),
    NEGATIVE("Negativo"),
    NEUTRAL("Neutro");

    private String label;

    SentimentClass(String label) {
        this.label = label;
    }
}
```

### Usar POS Tagging

```java
@Service
public class POSTaggingService {

    @Autowired
    private OpenNLPPosTagger posTagger;

    /**
     * Marca as partes da fala em uma sentença
     */
    public Map<String, String> tagPOSWords(String sentence) {
        return posTagger.tag(sentence);
    }

    /**
     * Exemplo de uso
     */
    public void exemplo() {
        String sentenca = "Java é uma linguagem de programação.";

        Map<String, String> tags = tagPOSWords(sentenca);
        // "Java" -> "NN" (noun)
        // "é" -> "VBZ" (verb)
        // "uma" -> "DT" (determiner)
        // "linguagem" -> "NN"
        // "de" -> "IN" (preposition)
        // "programação" -> "NN"
    }
}
```

### Pipeline Completo de Análise

```java
@Service
public class CompleteTextAnalysisService {

    @Autowired
    private TextTokenizationService tokenizer;

    @Autowired
    private EntityRecognitionService ner;

    @Autowired
    private SentimentAnalysisService sentiment;

    /**
     * Análise completa de um texto
     */
    public TextAnalysisResult analyzeText(String texto) {
        // 1. Tokenizar
        String[] sentencas = tokenizer.tokenizeSentences(texto);

        // 2. Extrair entidades
        List<Entity> entities = new ArrayList<>();
        for (String sentenca : sentencas) {
            entities.addAll(ner.extractEntities(sentenca));
        }

        // 3. Analisar sentimento
        SentimentResult sentimentResult = sentiment.analyzeSentiment(texto);

        return TextAnalysisResult.builder()
            .originalText(texto)
            .sentenceCount(sentencas.length)
            .entities(entities)
            .sentiment(sentimentResult)
            .build();
    }
}

@Data
@Builder
public class TextAnalysisResult {
    private String originalText;
    private int sentenceCount;
    private List<Entity> entities;
    private SentimentResult sentiment;
}
```

### REST Controller para NLP

```java
@RestController
@RequestMapping("/api/v1/nlp")
public class NLPController {

    @Autowired
    private CompleteTextAnalysisService analysisService;

    @PostMapping("/analyze")
    public ResponseEntity<TextAnalysisResult> analyzeText(
            @RequestBody TextAnalysisRequest request) {
        TextAnalysisResult result = analysisService.analyzeText(request.getText());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/sentiment")
    public ResponseEntity<SentimentResult> analyzeSentiment(
            @RequestBody SentimentRequest request) {
        SentimentResult result = analysisService.sentiment(request.getText());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/entities")
    public ResponseEntity<List<Entity>> extractEntities(
            @RequestBody EntityRequest request) {
        List<Entity> entities = analysisService.ner(request.getText());
        return ResponseEntity.ok(entities);
    }
}
```

## 📊 Tipos de Análise

| Tipode Análise | Descrição | Exemplo |
|----------------|-----------|---------|
| **Tokenization** | Quebrar em palavras/sentenças | "Olá mundo" → ["Olá", "mundo"] |
| **POS Tagging** | Identificar partes da fala | "livro" → NOUN |
| **NER** | Encontrar entidades | "João vive em SP" → PERSON, LOCATION |
| **Sentiment** | Classificar sentimento | "Adorei!" → POSITIVE |
| **Lemmatization** | Reduzir à forma base | "correndo", "corre" → "correr" |

## 🧪 Testes

```java
@SpringBootTest
public class NLPServiceTest {

    @Autowired
    private SentimentAnalysisService sentimentService;

    @Test
    public void testPositiveSentiment() {
        SentimentResult result = sentimentService.analyzeSentiment("Adorei!");
        assertTrue(result.getScore() > 0.6);
    }

    @Test
    public void testNegativeSentiment() {
        SentimentResult result = sentimentService.analyzeSentiment("Odiei!");
        assertTrue(result.getScore() < 0.4);
    }
}
```

## 📥 Download de Modelos

Os modelos OpenNLP devem ser baixados e colocados em `src/main/resources/models/`:
- `en-sent.bin` - Sentence detector
- `en-token.bin` - Tokenizer
- `en-pos-maxent.bin` - POS tagger
- `en-ner-person.bin` - NER para pessoas

## ⚙️ Configuração

### application.yml

```yaml
nlp:
  models:
    path: classpath:/models/
    cache-size: 10
  language: pt
  enable-all-analyzers: true
```

## 🤝 Contribuição

Ao adicionar novos analisadores:
1. Implemente a interface `TextAnalyzer`
2. Carregue modelos adequadamente
3. Adicione cache para performance
4. Documente com exemplos
5. Adicione testes

## 📝 Notas

- Modelos ocupam espaço em disco
- Primeira carga é lenta (cache reduz isso)
- Desempenho depende do tamanho do texto
- Português pode usar diferentes tokenizers

## 🔍 Referências

- [Apache OpenNLP](https://opennlp.apache.org/)
- [Stanford CoreNLP](https://stanfordnlp.github.io/CorenLP/)
- [NLP with Java](https://www.baeldung.com/java-nlp)


