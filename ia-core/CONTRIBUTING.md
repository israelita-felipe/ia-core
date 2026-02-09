# Guia de Contribuição

Obrigado por considerar contribuir para o projeto IA Core! Este documento fornece diretrizes e instruções para contribuir.

## 📋 Índice

- [Código de Conduta](#código-de-conduta)
- [Começando](#começando)
- [Processo de Contribuição](#processo-de-contribuição)
- [Padrões de Código](#padrões-de-código)
- [Convenções de Commit](#convenções-de-commit)
- [Testes](#testes)
- [Documentação](#documentação)

## Código de Conduta

Este projeto segue o Código de Conduta padrão. Ao participar, você deve apoiar este código.

## Começando

### Pré-requisitos

- Java 17 ou superior
- Maven 3.8+
- Git

### Configuração do Ambiente

1. **Fork o repositório**
   ```
   https://github.com/seu-usuario/ia-core-apps/fork
   ```

2. **Clone localmente**
   ```bash
   git clone https://github.com/seu-usuario/ia-core-apps.git
   cd ia-core-apps/ia-core
   ```

3. **Configure o JDK**
   ```bash
   export JAVA_HOME=/path/to/java-17
   java -version
   ```

4. **Compile o projeto**
   ```bash
   mvn clean install -DskipTests
   ```

## Processo de Contribuição

### 1. Crie uma Branch

```bash
# Para novas funcionalidades
git checkout -b feature/descricao-funcionalidade

# Para correções de bugs
git checkout -b fix/descricao-bug

# Para documentação
git checkout -b docs/melhoria-documentacao
```

### 2. Faça suas Mudanças

- Siga os padrões de código (abaixo)
- Adicione testes para novas funcionalidades
- Atualize a documentação conforme necessário

### 3. Commit suas Mudanças

```bash
# Verifique as mudanças
git status

# Adicione arquivos
git add .

# Commit com mensagem descritiva
git commit -m "feat: adiciona nova funcionalidade de processamento"
```

### 4. Push e Pull Request

```bash
# Push para seu fork
git push origin feature/descricao-funcionalidade

# Abra Pull Request no repositório original
```

## Padrões de Código

### Style Guide

#### Nomenclatura

**Classes:**
- PascalCase: `ComandoSistema`, `TemplateService`
- Sufixo para Services: `*Service`
- Sufixo para Repositories: `*Repository`
- Sufixo para DTOs: `*DTO`
- Sufixo para Mappers: `*Mapper`, `*Translator`

**Métodos:**
- camelCase: `findById()`, `saveEntity()`, `processImage()`
- Verbos para ações: `create`, `update`, `delete`, `find`, `process`
- Prefixos booleanos: `is`, `has`, `can`

**Variáveis:**
- camelCase: `comandoSistema`, `templateList`, `userId`
- Constantes: `SCREAMING_SNAKE_CASE`

#### Estrutura de Classes

```java
// 1. Package
package com.ia.core.llm.service.comando;

// 2. Imports (agrupados: java, jakarta, spring, outros)
// 3. Comentário Javadoc
// 4. Anotações
// 5. Declaração de classe

@Entity
@Table(name = "TB_COMANDO_SISTEMA")
@Getter
@Setter
public class ComandoSistema extends BaseEntity {
  // 1. Constantes estáticas
  // 2. Atributos (públicos, protegidos, privados)
  // 3. Construtores
  // 4. Métodos de negócio
  // 5. Métodos de ciclo de vida
  // 6. Métodos equals/hashCode/toString
}
```

#### Documentação Javadoc

```java
/**
 * Classe que representa um comando de sistema para o modelo de linguagem.
 *
 * <p>Esta classe é responsável por:
 * <ul>
 *   <li>Armazenar comandos do sistema</li>
 *   <li>Associar templates aos comandos</li>
 *   <li>Gerenciar finalidades dos comandos</li>
 * </ul>
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Service
public class ComandoSistemaService {

  /**
   * Busca um comando pelo ID com seu template carregado.
   *
   * <p>Este método utiliza {@link EntityGraph} para evitar N+1 queries
   * ao carregar o relacionamento com {@link Template}.</p>
   *
   * @param id identificador do comando
   * @return {@link Optional} contendo o comando ou vazio se não encontrado
   * @throws EntityNotFoundException se o comando não existir
   */
  public Optional<ComandoSistema> findById(Long id) {
    // implementação
  }
}
```

### Convenções de Arquivo

- **Indentação:** 2 espaços
- **Linha máxima:** 120 caracteres
- **Encoding:** UTF-8
- **Quebras de linha:** LF (Unix)

### Imports

```java
// Java standard library
import java.util.List;
import java.util.Optional;

// Jakarta EE
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

// Spring
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.repository.JpaRepository;

// Projeto
import com.ia.core.llm.model.comando.ComandoSistema;
import com.ia.core.service.repository.BaseEntityRepository;
```

## Convenções de Commit

### Formato

```
<tipo>(<escopo>): <descrição>

[corpo opcional]

[rodapé opcional]
```

### Tipos

| Tipo | Descrição |
|------|-----------|
| `feat` | Nova funcionalidade |
| `fix` | Correção de bug |
| `docs` | Mudanças na documentação |
| `style` | Formatação, pontos e vírgulas, etc (sem mudança de código) |
| `refactor` | Refatoração (sem mudança de comportamento) |
| `perf` | Melhorias de performance |
| `test` | Adição ou correção de testes |
| `chore` | Tarefas de manutenção |

### Escopos

| Escopo | Descrição |
|--------|-----------|
| `llm` | Módulo de LLM |
| `quartz` | Módulo de agendamento |
| `nlp` | Módulo de NLP |
| `model` | Entidades e modelos |
| `service` | Serviços |
| `rest` | Controllers REST |
| `view` | Interface MVVM |
| `config` | Configurações |

### Exemplos

```
feat(llm): adiciona serviço de extração de texto

- Implementa TextExtractionService
- Adiciona ImageProcessingService
- Refatora LLMTransformationService para delegação

Closes #123
```

```
fix(quartz): corrige memory leak em SchedulerConfigService

- Remove referência circular
- Adiciona cleanup em destroy

Fixes #456
```

```
docs(readme): atualiza instruções de build

- Adiciona pré-requisitos
- Corrige comandos Maven
```

## Testes

### Estrutura de Testes

```
src/test/java/com/ia/core/
├── service/
│   └── ComandoSistemaServiceTest.java
├── repository/
│   └── ComandoSistemaRepositoryTest.java
└── integration/
    └── LLMServiceIntegrationTest.java
```

### Padrões de Testes

```java
@ExtendWith(MockitoExtension.class)
class ComandoSistemaServiceTest {

  @Mock
  private ComandoSistemaRepository repository;

  @InjectMocks
  private ComandoSistemaService service;

  @Test
  @DisplayName("Deve retornar comando quando encontrado por ID")
  void deveRetornarComandoQuandoEncontrado() {
    // Given
    Long id = 1L;
    ComandoSistema comando = new ComandoSistema();
    comando.setId(id);
    when(repository.findById(id)).thenReturn(Optional.of(comando));

    // When
    Optional<ComandoSistema> result = service.findById(id);

    // Then
    assertThat(result).isPresent();
    assertThat(result.get().getId()).isEqualTo(id);
  }
}
```

### Cobertura de Código

- Mínimo de **70%** de cobertura
- Testes obrigatórios para Services
- Testes de integração para Repositories

## Documentação

### Atualização de Documentação

1. **README.md**: Atualize para novas funcionalidades
2. **CHANGELOG.md**: Registre mudanças
3. **Javadoc**: Documente novos métodos e classes
4. **Wiki**: Mantenha informações atualizadas

### Style Guide de Documentação

- Use voz ativa
- Mantenha簡潔 e clara
- Forneça exemplos
- Mantenha atualizada

## 📞 Obtenção de Ajuda

- **Issues**: Abra para bugs ou funcionalidades
- **Discussões**: Use para perguntas
- **Wiki**: Consulte documentação

## 🙏 Agradecimentos

Obrigado por contribuir!
