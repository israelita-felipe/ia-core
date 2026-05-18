# ia-core-grammar

## 📋 Descrição

Módulo que fornece suporte a gramáticas customizadas usando ANTLR 4 (ANother Tool for Language Recognition). Permite definir, gerar parsers e processar linguagens específicas do domínio (DSL - Domain Specific Language).

## 🏗️ Estrutura

```
ia-core-grammar/
├── src/main/java/
│   └── com/ia/core/grammar/
│       ├── parser/                 # Parsers gerados
│       ├── visitor/                # Visitors para AST
│       ├── listener/               # Listeners para AST
│       └── util/                   # Utilitários
├── src/main/antlr/
│   └── com/ia/core/grammar/
│       ├── ExpressionLexer.g4      # Definição de tokens
│       ├── ExpressionParser.g4     # Regras de gramática
│       └── CustomLanguage.g4       # Linguagem customizada
└── pom.xml
```

## 🔑 Responsabilidades

- **Gramática ANTLR**: Define sintaxe via `.g4`
- **Parser Generation**: Gera parser automaticamente
- **Lexer/Tokenizer**: Reconhecimento de tokens
- **AST (Abstract Syntax Tree)**: Árvore sintática abstrata
- **Visitors**: Percorrer e processar AST
- **Listeners**: Reagir a eventos de parsing
- **Error Handling**: Tratamento customizado de erros

## 🛠️ Tecnologias Utilizadas

- **ANTLR 4**: Framework de geração de parser
- **ANTLR4 Maven Plugin**: Compilação de `.g4`
- **Java**: Implementação de visitors/listeners

## 📦 Dependências

- `org.antlr:antlr4-runtime` - Runtime ANTLR
- `org.antlr:antlr4-maven-plugin` - Compilação

## 🔗 Relacionamentos

Depende de:
- ANTLR runtime

Utilizado por:
- `ia-core-nlp` - Processamento linguístico
- `ia-core-service` - Análise de texto customizado
- Qualquer módulo que needed parsear DSL

## 💡 Padrões Implementados

- **Visitor Pattern**: AST traversal customizado
- **Listener Pattern**: Reação a eventos de parsing
- **Grammar-Driven Design**: Sintaxe define comportamento

## 🚀 Como Usar

### Exemplo: Criar Gramática para Expressões Matemáticas

**ExpressionLexer.g4:**
```antlr
lexer grammar ExpressionLexer;

// Tokens
NUMBER: [0-9]+('.'[0-9]+)?;
PLUS: '+';
MINUS: '-';
MULT: '*';
DIV: '/';
LPAREN: '(';
RPAREN: ')';
WS: [ \t\n\r]+ -> skip;
```

**ExpressionParser.g4:**
```antlr
parser grammar ExpressionParser;

options { tokenVocab=ExpressionLexer; }

// Regras
expression: term ((PLUS | MINUS) term)*;
term: factor ((MULT | DIV) factor)*;
factor: NUMBER | LPAREN expression RPAREN;
```

### Implementar Visitor para Executar Expressão

```java
public class ExpressionEvaluator extends ExpressionParserBaseVisitor<Double> {

    @Override
    public Double visitExpression(ExpressionParser.ExpressionContext ctx) {
        double result = visit(ctx.term(0));

        for (int i = 0; i < ctx.getChildCount(); i += 2) {
            if (ctx.getChild(i) instanceof ExpressionParser.TermContext) {
                continue;
            }

            String operator = ctx.getChild(i).getText();
            double rightValue = visit(ctx.term((i / 2)));

            if (operator.equals("+")) {
                result += rightValue;
            } else if (operator.equals("-")) {
                result -= rightValue;
            }
        }

        return result;
    }

    @Override
    public Double visitTerm(ExpressionParser.TermContext ctx) {
        double result = visit(ctx.factor(0));

        for (int i = 0; i < ctx.getChildCount(); i += 2) {
            if (ctx.getChild(i) instanceof ExpressionParser.FactorContext) {
                continue;
            }

            String operator = ctx.getChild(i).getText();
            double rightValue = visit(ctx.factor((i / 2)));

            if (operator.equals("*")) {
                result *= rightValue;
            } else if (operator.equals("/")) {
                result /= rightValue;
            }
        }

        return result;
    }

    @Override
    public Double visitFactor(ExpressionParser.FactorContext ctx) {
        if (ctx.NUMBER() != null) {
            return Double.parseDouble(ctx.NUMBER().getText());
        }
        return visit(ctx.expression());
    }
}
```

### Usar o Parser

```java
@Service
public class ExpressionParsingService {

    /**
     * Parsea e avalia uma expressão matemática
     */
    public Double evaluateExpression(String expression) {
        // 1. Tokenizar
        CharStream input = CharStreams.fromString(expression);
        ExpressionLexer lexer = new ExpressionLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // 2. Fazer parse
        ExpressionParser parser = new ExpressionParser(tokens);
        parser.setErrorHandler(new BailErrorStrategy()); // Falhar rápido

        ExpressionParser.ExpressionContext tree = parser.expression();

        // 3. Visitar árvore
        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        return evaluator.visit(tree);
    }
}
```

### Implementar Listener para Log

```java
public class ExpressionLoggingListener extends ExpressionParserBaseListener {

    @Override
    public void enterExpression(ExpressionParser.ExpressionContext ctx) {
        System.out.println("Começando a processar expressão...");
    }

    @Override
    public void exitExpression(ExpressionParser.ExpressionContext ctx) {
        System.out.println("Expressão processada com sucesso");
    }

    @Override
    public void enterFactor(ExpressionParser.FactorContext ctx) {
        if (ctx.NUMBER() != null) {
            System.out.println("Encontrado número: " + ctx.NUMBER().getText());
        }
    }
}
```

### Tratamento de Erros Customizado

```java
public class CustomErrorListener extends BaseErrorListener {

    private List<String> errors = new ArrayList<>();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                           int line, int charPositionInLine, String msg,
                           RecognitionException e) {
        String error = String.format("Erro na linha %d, coluna %d: %s",
            line, charPositionInLine, msg);
        errors.add(error);
    }

    public List<String> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
```

## 🏗️ Configuração Maven

### pom.xml

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.antlr</groupId>
            <artifactId>antlr4-maven-plugin</artifactId>
            <version>${antlr.version}</version>
            <executions>
                <execution>
                    <goals>
                        <goal>antlr4</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

## 🧪 Testes

```java
@SpringBootTest
public class ExpressionParsingTest {

    @Autowired
    private ExpressionParsingService parsingService;

    @Test
    public void testSimpleExpression() {
        Double result = parsingService.evaluateExpression("2 + 3");
        assertEquals(5.0, result);
    }

    @Test
    public void testComplexExpression() {
        Double result = parsingService.evaluateExpression("(2 + 3) * 4");
        assertEquals(20.0, result);
    }

    @Test
    public void testDivision() {
        Double result = parsingService.evaluateExpression("10 / 2");
        assertEquals(5.0, result);
    }
}
```

## 📖 Pontos-Chave ANTLR

| Conceito | Descrição |
|----------|-----------|
| **Lexer** | Tokenização (palavras-chave, identifiers, números) |
| **Parser** | Sintaxe (regras, estrutura, relacionamentos) |
| **AST** | Árvore representando estrutura |
| **Visitor** | Traversar AST com retorno de valor |
| **Listener** | Reagir a eventos de parsing (sem retorno) |

## 🤝 Contribuição

Ao adicionar nova gramática:
1. Defina regras `.g4` claras
2. Implemente visitor ou listener
3. Adicione tratamento de erro
4. Documente a sintaxe
5. Adicione testes

## 📝 Notas

- ANTLR gera muitos arquivos (cache em `.gitignore`)
- Grammar Lexer deve vir antes de Parser
- Listeners tendem a ser mais simples que Visitors
- Erros de parsing devem ser tratados graciosamente

## 🔍 Referências

- [ANTLR 4 Documentation](https://www.antlr.org/)
- [ANTLR 4 Maven Plugin](https://www.antlr.org/wiki/display/ANTLR4/Using+ANTLR+4+with+Maven)
- [The Definitive ANTLR 4 Reference](https://pragprog.com/titles/tpantlr2/the-definitive-antlr-4-reference/)


