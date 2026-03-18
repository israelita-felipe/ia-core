# Instruções de Uso dos Novos Testes

## Compilação e Execução

### 1. Compilar o módulo de testes
```bash
cd /home/israel/git/ia-core-apps/ia-core/ia-core-security-test

# Limpar e compilar
mvn clean compile -DskipTests

# Resultado esperado:
# [INFO] BUILD SUCCESS
```

### 2. Executar testes específicos

#### Executar testes de autenticação e autorização
```bash
mvn test -Dtest=AuthenticationAuthorizationIntegrationTest
```

#### Executar testes de edge cases do UserService
```bash
mvn test -Dtest=UserServiceEdgeCasesAdvancedTest
```

#### Executar todos os testes
```bash
mvn test
```

### 3. Gerar relatório de cobertura
```bash
# Gerar relatório JaCoCo
mvn clean test jacoco:report

# Visualizar relatório
# Abrir: target/site/jacoco/index.html
```

## Estrutura dos Testes Criados

### AuthenticationAuthorizationIntegrationTest
**Localização**: `src/test/java/com/ia/test/security/integration/AuthenticationAuthorizationIntegrationTest.java`

Teste de integração que valida:
- Autenticação de usuários válidos e inválidos
- Autorização com roles
- Autorização com privilégios
- Fluxos completos de usuario -> role -> privilégios
- Tratamento de erros
- Operações em lote

**Exemplo de execução**:
```bash
mvn test -Dtest=AuthenticationAuthorizationIntegrationTest#BasicAuthenticationFlowTests
```

### UserServiceEdgeCasesAdvancedTest
**Localização**: `src/test/java/com/ia/test/security/service/UserServiceEdgeCasesAdvancedTest.java`

Teste de casos extremos que valida:
- Validações de string (vazio, espaços, caracteres especiais)
- Estados inconsistentes
- Relacionamentos vazios
- Operações concorrentes
- Valores extremos
- Recuperação de erros

**Exemplo de execução**:
```bash
mvn test -Dtest=UserServiceEdgeCasesAdvancedTest#StringValidationsTests
```

## Builders de Teste

### PrivilegeTestDataBuilder
Criar privilégio para testes:
```java
Privilege privilege = PrivilegeTestDataBuilder.privilege()
    .withName("READ_USERS")
    .build();
```

### RoleTestDataBuilder
Criar role para testes:
```java
Role role = RoleTestDataBuilder.role()
    .withName("ROLE_ADMIN")
    .build();
```

### UserTestDataBuilder
Criar usuário para testes:
```java
User user = UserTestDataBuilder.user()
    .withUserName("testuser")
    .withUserCode("USR_001")
    .withEnabled(true)
    .build();
```

## Padrões Utilizados

### Anotações JUnit 5
- `@Test`: Define método de teste
- `@DisplayName("descrição")`: Descrição legível do teste
- `@Nested`: Agrupa testes relacionados
- `@BeforeEach`: Setup executado antes de cada teste
- `@ParameterizedTest`: Executa teste com múltiplos parâmetros
- `@ValueSource`: Fornece valores para testes parametrizados

### Mockito
- `@Mock`: Cria mock de dependência
- `@ExtendWith(MockitoExtension.class)`: Ativa Mockito no teste
- `when().thenReturn()`: Define comportamento esperado do mock
- `verify()`: Valida se mock foi chamado

### AssertJ
- `assertThat().isTrue()`: Verifica se é verdadeiro
- `assertThat().isFalse()`: Verifica se é falso
- `assertThat().isNull()`: Verifica se é nulo
- `assertThat().isNotNull()`: Verifica se não é nulo
- `assertThat().hasSize()`: Verifica tamanho
- `assertThat().isEmpty()`: Verifica se está vazio
- `assertThat().contains()`: Verifica se contém elemento

## Documentação

### Javadoc
Todos os métodos têm documentação JavaDoc:
```java
/**
 * Descrição do método.
 *
 * @param parametro descrição do parâmetro
 * @return descrição do retorno
 */
public void metodo(String parametro) {
    // implementação
}
```

### Nomes Descritivos
Os testes usam nomes descritivos em português:
```java
@DisplayName("Deve validar usuário com roles diferentes")
void shouldValidateUserWithDifferentRoles() {
```

## Checklist de Validação

- [ ] Compilação bem-sucedida: `mvn clean compile`
- [ ] AuthenticationAuthorizationIntegrationTest compilado
- [ ] UserServiceEdgeCasesAdvancedTest compilado
- [ ] PrivilegeTestDataBuilder disponível
- [ ] RoleTestDataBuilder corrigido
- [ ] Documentação TESTES_CRIADOS.md presente
- [ ] Relatório de cobertura gerado

## Troubleshooting

### Erro: "cannot find symbol"
**Solução**: Executar `mvn clean compile` novamente

### Erro de teste falhando
**Solução**: Verificar implementação do builder ou do método testado

### Covergura baixa
**Solução**: Adicionar mais testes seguindo o padrão estabelecido

## Próximos Passos

1. ✅ Executar testes: `mvn test -Dtest=AuthenticationAuthorizationIntegrationTest`
2. ✅ Gerar relatório: `mvn clean test jacoco:report`
3. 🔲 Corrigir testes existentes com dependências incompletas
4. 🔲 Adicionar testes para PrivilegeService
5. 🔲 Adicionar testes para LogOperationService
6. 🔲 Adicionar testes para AuthorizationManager
7. 🔲 Adicionar testes para Quartz Jobs

## Suporte

Para questões sobre os testes, consulte:
- TESTES_CRIADOS.md - Documentação detalhada
- Javadoc dos arquivos de teste
- Comentários dentro dos testes
