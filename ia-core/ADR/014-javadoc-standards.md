# ADR-014: Padrões de Documentação Javadoc

## Status

✅ Aceito e Revisto

## Contexto

A documentação Javadoc é essencial para a manutenibilidade e compreensão do código. O projeto precisa de padrões consistentes de documentação para:

- Facilitar onboarding de novos desenvolvedores
- Documentar APIs de forma padronizada
- Melhorar manutenibilidade do código
- Fornecer referência rápida para uso de classes e métodos
- Gerar documentação API automática de qualidade
- Suportar completude de código nas IDEs

## Decisão

Estabelecer padrões Javadoc consistentes e rigorosos para todo o projeto, seguindo as melhores práticas consolidadas da indústria, sempre em pt-BR.

## Detalhes

### 1. Princípios Fundamentais

1. **Completude**: Documentar **todas** as classes públicas, interfaces, enums, métodos públicos e construtores
2. **Clareza**: Usar linguagem clara e concisa, evitando jargão desnecessário
3. **Concisão**: Primeira linha (até 80 caracteres) resume completamente o elemento
4. **Precisão**: Informações precisas e atualizadas, sincronizadas com o código
5. **Coerência**: Usar padrões consistentes em todo o projeto

### 2. Escopo de Documentação Obrigatória

| Elemento | Obrigatório | Notas |
|----------|-------------|-------|
| Classes públicas | ✅ Sim | Sempre documentar |
| Interfaces públicas | ✅ Sim | Incluir padrão de uso |
| Enums públicos | ✅ Sim | Documentar cada constante |
| Construtores públicos | ✅ Sim | Especialmente não-padrão |
| Métodos públicos | ✅ Sim | Incluir @param, @return, @throws |
| Campos públicos | ✅ Sim | Especialmente constantes |
| Métodos privados | ❌ Não | Opcional (comentários inline podem ser suficientes) |
| Getters/Setters triviais | ⚠️ Opcional | Diferenciados em ADR-001 |

### 3. Estrutura de Documentação Obrigatória

| Elemento | Tag | Exemplo |
|----------|-----|---------|
| Classe | `@author`, `@since` | `@author Israel Araújo`, `@since 1.0.0` |
| Método | `@param`, `@return`, `@throws` | Conforme aplicável |
| Campo | Descrição clara | Sem tag obrigatória |
| Enum | Valor + descrição | Para cada constante |

### 4. Estrutura de Documentação

#### Formato Padrão

```java
/**
 * [Descrição de uma linha clara e completa].
 *
 * [Parágrafo adicional com detalhes, contexto, comportamento especial ou notas importantes].
 * [Segunda linha do parágrafo adicional, se necessário].
 *
 * [Parágrafo com exemplos de uso ou implementação, se aplicável].
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see ClasseRelacionada
 */
public class Exemplo { }
```

#### Regras de Formatação

1. **Primeira linha**: Descrição completa, terminada com ponto
   - Máximo 80 caracteres
   - Responde: "o que é isso?" ou "o que faz?"
   - Deve ser possível copiar como resumo único

2. **Linhas vazias**: Separam parágrafos logicamente distintos
   - Entre descrição geral e detalhes
   - Entre detalhes e exemplos
   - Entre conteúdo e tags

3. **Parágrafo adicional**: Detalhes, notas importantes ou comportamento especial
   - Pode ter múltiplas linhas
   - Use HTML para formatação se necessário: `<p>`, `<ul>`, `<b>`, `<code>`

4. **Exemplo de código**: Use `<pre>{@code ...}</pre>` para code inline ou blocos
   - Preserva espaçamento
   - Evita escape de caracteres especiais

5. **Ordem de tags**: @param, @return, @throws, @author, @since, @see, @deprecated
   - Tags estruturais antes de tags informativas
   - @see sempre por último

### 5. Tags Padronizadas

#### Tags Principais

```java
/**
 * Descrição do método.
 *
 * @param paramName descrição do parâmetro
 * @param otherParam descrição do outro parâmetro
 * @return descrição do retorno
 * @throws Exception descrição de quando ocorre
 * @author Israel Araújo
 * @since 1.0.0
 */
public void metodoExemplo(String paramName, int otherParam) { }
```

#### Tags Complementares

```java
/**
 * Método descontinuado.
 *
 * @deprecated Desde versão 2.0.0, use {@link #novoMetodo(String)} em seu lugar
 * @see #novoMetodo(String)
 */
@Deprecated(since = "2.0.0", forRemoval = true)
public void metodoAntigo() { }
```

### 6. Convenções de Descrição Detalhadas

| Tipo | Padrão | Exemplo | Notas |
|------|--------|---------|-------|
| Método | Verbo infinitivo | "Busca uma pessoa pelo ID" | Não use "Este método..." |
| Parâmetro | Artigo + substantivo | `@param id o identificador único da pessoa` | Comece com minúscula |
| Retorno | "o/a [resultado]" ou substantivo | `@return a lista de pessoas encontradas` | Indique se pode ser null |
| Exceção | "se [condição]" | `@throws IllegalArgumentException se id for null` | Seja específico sobre a condição |
| Classe | Substantivo | "Repositório de Pessoa" | Não use "A classe..." |
| Tipo genérico | Com parâmetro | `@return Optional&lt;Pessoa&gt; contendo a pessoa` | Indique o tipo genérico |

### 7. Indicação de Null-Safety

```java
/**
 * Busca uma pessoa pelo ID.
 *
 * @param id o identificador da pessoa (não pode ser null)
 * @return a pessoa encapsulada em Optional, sendo empty se não encontrada
 * @throws IllegalArgumentException se id for null
 */
Optional<Pessoa> findByIdWithAll(Long id);

/**
 * Obtém a descrição da pessoa.
 *
 * @return a descrição, nunca null
 */
String getDescriptao();

/**
 * Define observações.
 *
 * @param observacoes observações adicionais (pode ser null)
 */
void setObservacoes(String observacoes);
```

### 8. Documentação de Retornos Complexos

```java
/**
 * Busca pessoas com filtros dinâmicos.
 *
 * @param nome nome da pessoa (opcional)
 * @param pageable informações de paginação (não pode ser null)
 * @return página de {@link PessoaDTO} contendo pessoas que correspondem aos critérios,
 *         nunca null. Página vazia se nenhuma pessoa encontrada.
 * @throws IllegalArgumentException se pageable for null
 */
Page<PessoaDTO> buscarComFiltros(String nome, Pageable pageable);
```

## Implementação Detalhada

### 9. Documentação de Classes

#### Interface de Repositório

```java
/**
 * Repositório para operações de persistência de Pessoa.
 *
 * Fornece métodos CRUD completos e consultas específicas para a entidade Pessoa,
 * incluindo suporte a paginação, filtros dinâmicos e otimizações de performance
 * via EntityGraph.
 *
 * <p>Exemplo de uso:
 * <pre>{@code
 * Optional<Pessoa> pessoa = pessoaRepository.findByIdWithAll(1L);
 * Page<PessoaDTO> pagina = pessoaRepository.buscar(filtro, PageRequest.of(0, 10));
 * }</pre></p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see BaseEntityRepository
 * @see Pessoa
 */
public interface PessoaRepository extends BaseEntityRepository<Pessoa, Long> {
    // ...
}
```

#### Classe de Entidade

```java
/**
 * Representa uma pessoa no sistema.
 *
 * Esta entidade encapsula informações pessoais de um indivíduo,
 * incluindo identificação, contato e relacionamentos com outras entidades.
 *
 * <p>Principais responsabilidades:
 * <ul>
 *   <li>Armazenar dados demográficos e de contato</li>
 *   <li>Manter relacionamentos com Endereço e Telefone</li>
 *   <li>Validar regras de negócio da entidade</li>
 * </ul></p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see Endereco
 * @see Telefone
 */
@Entity
@Table(name = "pessoa")
public class Pessoa extends BaseEntity {
    // ...
}
```

#### Enum

```java
/**
 * Estados possíveis de uma Pessoa no sistema.
 *
 * Define o ciclo de vida de uma pessoa:
 * <ul>
 *   <li>ATIVO: pessoa registrada e ativa no sistema</li>
 *   <li>INATIVO: pessoa desativada mas com dados históricos preservados</li>
 *   <li>DELETADO: pessoa marcada como deletada (soft delete)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public enum EstadoPessoa {
    /**
     * Pessoa ativa no sistema e apta a ser usada.
     */
    ATIVO("Ativo"),

    /**
     * Pessoa inativa mas com histórico mantido.
     */
    INATIVO("Inativo"),

    /**
     * Pessoa deletada logicamente (soft delete).
     */
    DELETADO("Deletado");

    // ...
}
```

### 10. Documentação de Métodos

#### Método Simples

```java
/**
 * Busca uma pessoa pelo identificador único.
 *
 * Utiliza EntityGraph para já carregar relacionamentos em uma única query,
 * otimizando performance e evitando N+1 queries.
 *
 * @param id o identificador único da pessoa (não pode ser null)
 * @return Optional contendo a pessoa se encontrada, empty caso contrário
 * @throws IllegalArgumentException se id for null
 * @see Pessoa
 */
Optional<Pessoa> findByIdWithAll(Long id);
```

#### Método com Múltiplos Parâmetros

```java
/**
 * Busca pessoas com critérios dinâmicos e paginação.
 *
 * Executa busca flexível usando Specification pattern para filtros dinâmicos.
 * Retorna resultado paginado de acordo com os parâmetros.
 *
 * @param nome nome ou parte do nome para buscar (opcional, ignora case)
 * @param email email da pessoa para buscar (opcional, ignora case)
 * @param estado estado da pessoa para filtrar (obrigatório, não pode ser null)
 * @param pageable informações de paginação (obrigatório, não pode ser null)
 * @return página contendo DTOs das pessoas encontradas. Página vazia se nenhuma
 *         pessoa corresponder aos critérios
 * @throws IllegalArgumentException se estado ou pageable forem null
 * @see PessoaDTO
 * @see Specification
 */
Page<PessoaDTO> buscarComFiltros(String nome, String email,
                                   EstadoPessoa estado, Pageable pageable);
```

#### Método com Comportamento Especial

```java
/**
 * Salva uma nova pessoa ou atualiza uma existente.
 *
 * <p>Comportamento:
 * <ul>
 *   <li>Se a pessoa não possui ID, cria nova pessoa</li>
 *   <li>Se a pessoa possui ID, atualiza dados existentes</li>
 *   <li>Antes de salvar, executa validações de regra de negócio</li>
 *   <li>Dispara evento de domínio PessoaSalvaEvent após sucesso</li>
 * </ul></p>
 *
 * @param dto dados da pessoa para salvar
 * @return a pessoa salva com ID gerado
 * @throws ValidationException se os dados forem inválidos
 * @throws ResourceNotFoundException se pessoa com ID não existir (ao atualizar)
 * @throws DataIntegrityViolationException se violar constraints do banco
 * @see PessoaSalvaEvent
 * @see PessoaValidator
 */
Pessoa salvar(PessoaDTO dto);
```

#### Método com Tipo Genérico

```java
/**
 * Converte uma lista de entidades para DTOs.
 *
 * Utiliza o MapStruct configurado para realizar mapeamento eficiente
 * de uma coleção de entidades para seus DTOs correspondentes.
 *
 * @param <E> o tipo da entidade fonte
 * @param <D> o tipo do DTO destino
 * @param entities lista de entidades para converter (não pode ser null)
 * @param mapper mapeador responsável pela conversão (não pode ser null)
 * @return lista contendo os DTOs convertidos, nunca null.
 *         Lista vazia se entities estiver vazia
 * @throws IllegalArgumentException se entities ou mapper forem null
 */
<E extends BaseEntity, D extends BaseDTO> List<D> converterParaDtos(
    List<E> entities, Mapper<E, D> mapper);
```

#### Método Descontinuado

```java
/**
 * Busca pessoa pelo email.
 *
 * @deprecated Desde versão 1.5.0, use {@link #buscarComFiltros(String, String, EstadoPessoa, Pageable)}
 *             que oferece filtros mais flexíveis. Este método será removido em versão 2.0.0
 * @param email email da pessoa para buscar
 * @return Optional contendo a pessoa se encontrada
 * @see #buscarComFiltros(String, String, EstadoPessoa, Pageable)
 */
@Deprecated(since = "1.5.0", forRemoval = true)
Optional<Pessoa> findByEmail(String email);
```

### 11. Documentação de Construtores

```java
/**
 * Cria nova instância de Pessoa com dados básicos.
 *
 * Inicializa estado como ATIVO e datas de auditoria com timestamp atual.
 *
 * @param nome nome completo da pessoa (obrigatório)
 * @param email email único da pessoa (obrigatório)
 * @throws IllegalArgumentException se nome ou email forem null/vazio
 */
public Pessoa(String nome, String email) {
    this.nome = nome;
    this.email = email;
    this.estado = EstadoPessoa.ATIVO;
}

/**
 * Cria nova instância de Pessoa com todos os dados.
 *
 * Construtor completo para inicialização com todos os campos obrigatórios.
 *
 * @param nome nome completo
 * @param email email único
 * @param estado estado inicial
 * @see #Pessoa(String, String)
 */
public Pessoa(String nome, String email, EstadoPessoa estado) {
    this(nome, email);
    this.estado = estado;
}
```

### 12. Documentação de Campos

```java
/**
 * Identificador único da pessoa.
 * Gerado automaticamente ao persistir a entidade.
 */
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

/**
 * Nome completo da pessoa.
 * Campo obrigatório, máximo 255 caracteres.
 */
@Column(name = "nome", nullable = false, length = 255)
private String nome;

/**
 * Email único da pessoa.
 * Deve ser válido e único no banco de dados.
 * Campo obrigatório.
 */
@Column(name = "email", nullable = false, unique = true)
private String email;

/**
 * Estado atual da pessoa no sistema.
 * Padrão: ATIVO. Determina se pessoa está ativa, inativa ou deletada.
 *
 * @see EstadoPessoa
 */
@Column(name = "estado")
@Enumerated(EnumType.STRING)
private EstadoPessoa estado = EstadoPessoa.ATIVO;
```

## Boas Práticas

### 13. O Que Fazer ✅

- ✅ Documente a **interface pública**, não a implementação
- ✅ Use **voz ativa**: "Busca uma pessoa" em vez de "Uma pessoa é buscada"
- ✅ Seja **específico**: "email da pessoa" em vez de "o email"
- ✅ Documente **comportamento especial** e casos extremos
- ✅ Use **exemplos de código** para APIs complexas
- ✅ Mantenha documentação **sincronizada com o código**
- ✅ Use HTML para **estruturar** informações complexas (`<ul>`, `<p>`, `<code>`)
- ✅ Indique claramente quando retorno **pode ser null**
- ✅ Documente **exceções específicas** que o método lança
- ✅ Use `{@link }` para **referências cruzadas** para outras classes/métodos
- ✅ Verifique **links** em {@link} - IDE alertarão se estiverem quebrados
- ✅ Documente apenas **exceções verificadas** (checked exceptions) em @throws
- ✅ Para getters/setters triviais, siga **ADR-001** (documentação opcional)

### 14. O Que Evitar ❌

- ❌ Não repita informação óbvia: "setNome - define o nome" é redundante
- ❌ Não use jargão sem explicar: "deslazifica DERFobics" sem contexto
- ❌ Não deixe exemplos no Javadoc fora de sincronização
- ❌ Não documente **parâmetros não-usados** no método
- ❌ Não use **caracteres especiais** sem escape: `<`, `>`, `&` devem ser `&lt;`, `&gt;`, `&amp;`
- ❌ Não misture **HTML inline** com markdown no Javadoc
- ❌ Não assuma que leitor conhece **contexto da classe**: seja auto-descritivo
- ❌ Não ignore **dados genéricos**: explique os tipos de `Optional<?>`, `List<?>`, etc.
- ❌ Não documente getters/setters triviais desnecessariamente (veja ADR-001)
- ❌ Não use "Este método..." ou "A classe..." nas descrições
- ❌ Não gere @throws para RuntimeException a menos que seja parte do contrato
- ❌ Não gere @see para classes que não existem no projeto

### 15. Uso de Links e Referências Cruzadas

```java
/**
 * Busca pessoas por filtros complexos.
 *
 * Para critérios simples, use {@link #findByNome(String)}.
 * Para paginação, veja {@link org.springframework.data.domain.Pageable}.
 *
 * @param specification a especificação de filtro (veja {@link Specification})
 * @return página contendo resultados (veja {@link Page})
 * @see #findByNome(String)
 * @see PessoaDTO
 */
Page<PessoaDTO> buscar(Specification<Pessoa> specification, Pageable pageable);
```

Regras:
- Use `{@link ClassName}` ou `{@link ClassName#method}` para links
- Use `@see` para referências relacionadas no final
- Links para classes externas: `{@link java.util.List}`

### 16. HTML Permitido no Javadoc

```java
/**
 * Processo de validação com múltiplas etapas.
 *
 * <p>Este método aplica validações em ordem:
 * <ol>
 *   <li>Validação de nullidade</li>
 *   <li>Validação de regras de negócio</li>
 *   <li>Validação de integridade referencial</li>
 * </ol></p>
 *
 * <p><b>Nota importante:</b> Este método é <code>thread-safe</code>.</p>
 *
 * <p>Exemplo:
 * <pre>{@code
 * Pessoa pessoa = new Pessoa("João", "joao@email.com");
 * validador.validar(pessoa); // não lança exceção
 * }</pre></p>
 *
 * @param pessoa a pessoa para validar
 * @throws ValidationException se validação falhar
 */
void validar(Pessoa pessoa);
```

Tags HTML aceitas:
- Estrutura: `<p>`, `<ul>`, `<ol>`, `<li>`, `<table>`, `<tr>`, <td>`, `<th>`
- Ênfase: `<b>`, `<i>`, `<strong>`, `<em>`
- Código: `<code>`, `<pre>`
- Quebra: `<br>`

### 17. Checklist de Validação

Antes de fazer commit, verifique:

```
CLASSE PÚBLICA:
☐ Tem Javadoc?
☐ Descreve responsabilidade claramente?
☐ Tem @author e @since?
☐ Tem @see para classes relacionadas?

MÉTODO PÚBLICO:
☐ Tem Javadoc?
☐ Primeira linha é uma frase completa?
☐ Tem @param para cada parâmetro?
☐ Tem @return (se retorna algo)?
☐ Lista @throws para exceções verificadas?
☐ Indica nullable/non-null?
☐ Tem exemplo de código se complexo?

CAMPO PÚBLICO:
☐ Tem Javadoc?
☐ Descreve propósito e uso?
☐ Documenta constraints (obrigatório, tamanho, etc)?

QUALIDADE GERAL:
☐ Sem referências mortas ({@link} apontam para classes válidas)?
☐ Sem caracteres especiais sem escape (< > &)?
☐ Sem markdown, apenas HTML ou texto simples?
☐ Sincronizado com código (parâmetros, retorno, exceções)?
```

## Ferramentas e Integração

### 18. Validação em Build

Adicione ao `pom.xml` para validação do Javadoc:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-javadoc-plugin</artifactId>
    <version>3.5.0</version>
    <configuration>
        <source>11</source>
        <detectJavaApiLink>false</detectJavaApiLink>
        <detectLinks>true</detectLinks>
        <failOnWarnings>true</failOnWarnings>
        <failOnError>true</failOnError>
        <docencoding>UTF-8</docencoding>
        <charset>UTF-8</charset>
        <encoding>UTF-8</encoding>
        <excludePackageNames>*.internal:*.config</excludePackageNames>
    </configuration>
    <executions>
        <execution>
            <id>attach-javadocs</id>
            <phase>package</phase>
            <goals>
                <goal>jar</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### 19. Geração de Documentação HTML

```bash
# Gerar documentação
mvn javadoc:javadoc

# Gerar e verificar completude
mvn clean compile javadoc:javadoc

# Visualizar no navegador
open target/site/apidocs/index.html  # macOS
xdg-open target/site/apidocs/index.html  # Linux
start target\site\apidocs\index.html  # Windows
```

### 20. Integração com IDE

#### IntelliJ IDEA

1. **Gerar Javadoc automático**: Alt+/ (Windows/Linux) ou Cmd+/ (macOS)
2. **Validar**: Code > Inspect Code > Javadoc problems
3. **Template customizado**:
   - Build, Execution, Deployment > File and Code Templates
   - Abas: Class, Method, Field templates

#### Eclipse

1. **Gerar Javadoc**: Source > Generate Element Comment
2. **Template**: Preferences > Java > Code Style > Code Templates
3. **Validação**: Project > Properties > Java Compiler > Javadoc

#### VS Code / Spring Tools

Use extensões:
- **Javadoc Generator** (Al Azif)
- **Code Documentation Generator** (Vmaxim)

## Problemas Comuns e Soluções

### 21. Problemas e Soluções

| Problema | Causa | Solução |
|----------|-------|---------|
| "** should be written as &lt;b&gt;**" | Caracteres especiais não escapados | Use `&lt;`, `&gt;`, `&amp;` |
| Links com {@link} aparecem como quebrados | Classe não existe ou import errado | Verifique FQDN da classe (ex: `java.util.List`) |
| Documentação não gerada em build | ignoreMissingParams em maven | Configure `<failOnWarnings>true</failOnWarnings>` |
| Método @deprecated mas sem @since | Versão de descontinuação não informada | Adicione `@deprecated Desde versão X.Y.Z` |
| Exceção não documentada | Esqueceu @throws | Liste **todas** as exceções checked |
| Primeiro parágrafo muito longo | Descrição em múltiplas linhas | Coloque primeira frase completa na primeira linha |

## Integração com Padrões do Projeto

### 22. Alinhamento com ADRs Relacionadas

Este padrão integra-se com:

1. **ADR-001 (MapStruct)**: Documente tipos genéricos em mappers
   ```java
   /**
    * @return {@link PessoaDTO} contendo dados da pessoa
    */
   PessoaDTO toDto(Pessoa pessoa);
   ```

2. **ADR-002 (Specification)**: Documente filtros dinâmicos
   ```java
   /**
    * @param specification a especificação de filtros (veja {@link Specification})
    */
   Page<PessoaDTO> buscar(Specification<Pessoa> spec, Pageable pageable);
   ```

3. **ADR-003 (Translator i18n)**: Documente métodos relacionados
   ```java
   /**
    * @return a descrição traduzida para o locale atual
    * @see Translator
    */
   String getDescricaoTraduzida();
   ```

4. **ADR-005 (Domain Events)**: Liste eventos disparados
   ```java
   /**
    * Dispara {@link PessoaSalvaEvent} após sucessos salvo.
    * Dispara {@link PessoaDeltadaEvent} se deletado.
    */
   void salvar(PessoaDTO dto);
   ```


## Consequências

### Positivas ✅

- ✅ Documentação **consistente** em todo o código
- ✅ **Facilita onboarding** de novos desenvolvedores
- ✅ **API auto-documentável** gerada automaticamente
- ✅ **Referência rápida** disponível nas IDEs (IntelliJ, Eclipse, VS Code)
- ✅ Documentação integrada ao **build Maven com validação**
- ✅ Reduz **gaps de conhecimento** sobre interfaces públicas
- ✅ Facilita **refatoração segura** com informações claras
- ✅ Melhora **code review** com APIs bem documentadas

### Negativas ❌

- ❌ Overhead inicial de **escrita** e manutenção
- ❌ Documentação pode ficar **desatualizada** se código mudar
- ❌ Requer **disciplina** para manter sincronizado
- ❌ Aumenta **tamanho do código fonte** (mas não afeta binário)

### Mitigação

- Validação automática em build (fail on missing Javadoc)
- Checklist de validação antes de commit
- Revisão em code review focada em Javadoc
- Documentação de Javadoc como critério de Definition of Done

## Data

2024-03-01 (Original)
2026-04-30 (Revisão Completa com Melhores Práticas)

## Revisores

- Israel Araújo (Architect)
- Tim Lead
- Dev Team

## Histórico de Versões

| Versão | Data | Mudanças |
|--------|------|----------|
| 1.0 | 2024-03-01 | Versão inicial com padrões básicos |
| 1.1 | 2026-04-30 | Adição de melhores práticas, checklist, ferramentas e integração |

## Referências

### Documentação Oficial

1. **Oracle - Javadoc Tool Documentation**
   - URL: https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html
   - Documentação oficial completa da ferramenta Javadoc
   - Tags suportadas e sintaxe

2. **Oracle - How to Write Doc Comments for the Javadoc Tool**
   - URL: https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html
   - Guia oficial "How to Write Doc Comments for the Javadoc Tool"

3. **Java SE API Specification**
   - URL: https://docs.oracle.com/javase/11/docs/api/
   - Exemplos de Javadoc em APIs oficiais

### Guias de Estilo

4. **Google Java Style Guide - Javadoc**
   - URL: https://google.github.io/styleguide/javaguide.html#s7-javadoc
   - Padrões de Javadoc da Google
   - Boas práticas da indústria

5. **Code Conventions for the Java Programming Language**
   - URL: https://www.oracle.com/java/technologies/javase/codeconventions-136091.html
   - Convenções Java oficiais
   - Documentação de código

### Tutoriais e Guias

6. **Baeldung - Javadoc Guide**
   - URL: https://www.baeldung.com/javadoc
   - Guia prático e completo
   - Exemplos de uso

7. **Oracle - How to Write Doc Comments for the Javadoc Tool**
   - URL: https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html
   - Guia oficial de como escrever comentários de documentação para Javadoc

8. **Effective Java - Item 56: Document all Exposed API Elements**
   - URL: https://books.google.com/books/about/Effective_Java.html
   - Livro "Effective Java" de Joshua Bloch
   - Discussão profunda sobre documentação

### Referências de Padrões

9. **Spring Framework - JavaDoc**
   - URL: https://spring.io/projects/spring-framework
   - Exemplo de Javadoc em framework maduro
   - Padrões de documentação Spring

10. **Apache Commons - Javadoc Guidelines**
    - URL: https://commons.apache.org/guides/index.html
    - Diretrizes de Javadoc de projetos open source maduros
    - Maven Javadoc Plugin

11. **Maven Javadoc Plugin**
    - URL: https://maven.apache.org/plugins/maven-javadoc-plugin/
    - Configuração e otimizações do plugin
    - Geração de HTML automática

### Ferramentas e Plugins

12. **Checkstyle - Javadoc Validation**
    - URL: https://checkstyle.sourceforge.io/config_javadoc.html
    - Validação automática de Javadoc em build

13. **SpotBugs - Documentation Warnings**
    - URL: https://spotbugs.readthedocs.io/
    - Detecta problemas comuns de documentação

14. **IntelliJ IDEA - Javadoc Support**
    - URL: https://www.jetbrains.com/help/idea/javadocs.html
    - Suporte nativo em IDE

15. **Eclipse - Javadoc Support**
    - URL: https://www.eclipse.org/eclipse/news/4.26/eclipse-4.26/
    - Integração com ferramentas Eclipse

### Padrões Relacionados (Este Projeto)

16. **ADR-001: Use MapStruct for Mapping**
    - Documentação de tipos genéricos em mappers

17. **ADR-002: Use Specification for Filtering**
    - Documentação de padrão Specification

18. **ADR-003: Use Translator for i18n**
    - Documentação de internacionalização

19. **ADR-005: Use Domain Events**
    - Documentação de eventos de domínio

## Notas Implementação

- Este padrão deve ser validado em build com `maven-javadoc-plugin`
- Configurar IDE para enforçar padrões via templates
- Incluir validação de Javadoc em criteria de code review
- Gerar documentação HTML automaticamente em cada release
- Revisar e manter este ADR anualmente
