# ADR-014: Padrões de Documentação Javadoc

## Status

✅ Aceito

## Contexto

A documentação Javadoc é essencial para a manutenibilidade e compreensão do código. O projeto ia-core-apps precisa de padrões consistentes de documentação para:

- Facilitar onboarding de novos desenvolvedores
- Documentar APIs de forma padronizada
- Melhorar manutenibilidade do código
- Fornecer referência rápida para uso de classes e métodos

## Decisão

Estabelecer padrões Javadoc consistentes para todo o projeto.

## Detalhes

### Elementos Obrigatórios

| Elemento | Tag | Exemplo |
|----------|-----|---------|
| Classe | `@author`, `@since` | `@author Israel Araújo`, `@since 1.0.0` |
| Método | `@param`, `@return`, `@throws` | Conforme aplicável |

### Estrutura de Documentação

```java
/**
 * Descrição clara e concisa da classe/método.
 * 
 * Detalhes adicionais quando necessários para compreensão.
 *
 * @author Nome do Autor
 * @since 1.0.0
 * @see ClasseRelacionada
 */
public class Exemplo { }
```

### Tags Padronizadas

```java
/**
 * @param paramName descrição do parâmetro
 * @return descrição do retorno
 * @throws ExceptionType mensagem de quando ocorre
 * @see #metodoRelacionado
 */
public void metodoExemplo() { }
```

### Convenções de Descrição

| Tipo | Padrão | Exemplo |
|------|--------|---------|
| Parâmetro | "o/da [entidade]" | `@param id o identificador da pessoa` |
| Retorno | substantivo + "(nunca null)" | `@return a lista de pessoas` |
| Exceção | "se [condição]" | `@throws Exception se id for null` |

## Implementação

### Classe

```java
/**
 * Repositório para operações de persistência de Pessoa.
 * 
 * Fornece métodos CRUD e consultas específicas para a entidade
 * Pessoa, incluindo suporte a paginação e filtros dinâmicos.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see BaseEntityRepository
 * @see Pessoa
 */
public interface PessoaRepository { }
```

### Método

```java
/**
 * Busca uma pessoa pelo ID.
 * 
 * Utiliza EntityGraph para carregar relacionamentos em uma
 * única query, otimizando performance.
 *
 * @param id identificador único da pessoa (não pode ser null)
 * @return Optional contendo a pessoa se encontrada
 * @throws IllegalArgumentException se id for null
 * @see Pessoa
 */
Optional<Pessoa> findByIdWithAll(Long id);
```

### Exceções

```java
/**
 * @throws ResourceNotFoundException se a pessoa não existir
 * @throws ValidationException se os dados forem inválidos
 * @throws ServiceException se ocorrer erro no processamento
 */
void salvar(PessoaDTO dto);
```

## Consequências

### Positivas

- ✅ Documentação consistente em todo o código
- ✅ Facilita onboarding de novos devs
- ✅ API auto-documentável
- ✅ Referência rápida disponível no código

### Negativas

- ❌ Overhead inicial de escrita
- ❌ Documentação pode ficar desatualizada

## Status de Implementação

✅ **COMPLETO**

- [`JAVADOC_STANDARDS.md`](JAVADOC_STANDARDS.md) criado
- Padrões documentados e disponíveis

## Data

2024-03-01

## Revisores

- Team Lead
- Architect

## Referências

1. **Oracle - Javadoc Tool Documentation**
   - URL: https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html
   - Documentação oficial da ferramenta Javadoc

2. **Google Java Style Guide - Javadoc**
   - URL: https://google.github.io/styleguide/javaguide.html#s7-javadoc
   - Seção de Javadoc do guia de estilo do Google

3. **Baeldung - Javadoc Guide**
   - URL: https://www.baeldung.com/javadoc
   - Guia completo sobre como escrever Javadoc

4. **Spring Framework - JavaDoc**
   - URL: https://spring.io/projects/spring-framework
   - Exemplo de Javadoc em frameworks maduros

5. **Apache Commons - Javadoc Guidelines**
   - URL: https://commons.apache.org/guides/index.html
   - Diretrizes de Javadoc de projetos open source maduros
