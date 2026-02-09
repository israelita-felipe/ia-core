# ADR-004: Usar ServiceConfig para Injeção de Dependências

## Status

✅ Aceito

## Contexto

O projeto precisa de uma forma consistente de gerenciar dependências complexas em serviços, especialmente aqueles que precisam de múltiplas dependências opcionais ou específicas.

## Decisão

Usar **ServiceConfig Pattern** com classes de configuração injetáveis via construtor.

## Detalhes

### Estrutura do ServiceConfig

```java
@Component
public class PessoaServiceConfig 
    extends DefaultSecuredBaseServiceConfig<Pessoa, PessoaDTO> {

    @Getter
    private final EnderecoRepository enderecoRepository;
    
    @Getter
    private final ContatoRepository contatoRepository;

    public PessoaServiceConfig(
            PlatformTransactionManager transactionManager,
            BaseEntityRepository<Pessoa> repository,
            BaseEntityMapper<Pessoa, PessoaDTO> mapper,
            SearchRequestMapper searchRequestMapper,
            Translator translator,
            CoreSecurityAuthorizationManager authorizationManager,
            SecurityContextService securityContextService,
            LogOperationService logOperationService,
            List<IServiceValidator<PessoaDTO>> validators,
            EnderecoRepository enderecoRepository,
            ContatoRepository contatoRepository) {
        super(transactionManager, repository, mapper, searchRequestMapper,
              translator, authorizationManager, securityContextService,
              logOperationService, validators);
        this.enderecoRepository = enderecoRepository;
        this.contatoRepository = contatoRepository;
    }
}
```

### Serviço Usando Config

```java
@Service
public class PessoaService 
    extends DefaultSecuredBaseService<Pessoa, PessoaDTO> {

    public PessoaService(PessoaServiceConfig config) {
        super(config);
    }

    public List<Endereco> buscarEnderecos(Long pessoaId) {
        return getConfig().getEnderecoRepository()
            .findByPessoaId(pessoaId);
    }
}
```

### ManagerConfig (View Layer)

```java
@Component
public class PessoaManagerConfig 
    extends DefaultSecuredViewBaseMangerConfig<PessoaDTO> {

    public PessoaManagerConfig(
            BaseClient<PessoaDTO> client,
            CoreSecurityAuthorizationManager authorizationManager) {
        super(client, authorizationManager);
    }
}
```

## Benefícios

### Para Serviços

1. **Dependências Explícitas** - Todas as dependências visíveis no construtor
2. **Testabilidade** - Easy mock de configurações
3. **Reusabilidade** - Configurações podem ser compartilhadas
4. **Extensibilidade** - Novos serviços podem estender configurações base

### Para Testes

```java
@Test
void testSave() {
    // Criar config com mocks
    PessoaServiceConfig config = new PessoaServiceConfig(
        transactionManager, repository, mapper, 
        searchRequestMapper, translator, authManager,
        securityContext, logService, validators,
        enderecoRepository, contatoRepository
    );
    
    PessoaService service = new PessoaService(config);
    // ...
}
```

## Consequências

### Positivas

- ✅ Dependências explícitas
- ✅ Injeção via construtor (imutável)
- ✅ Testabilidade
- ✅ Herança de configurações base
- ✅ 32+ ServiceConfigs implementados

### Negativas

- ❌ Verbose para serviços simples
- ❌ Muitos arquivos de configuração

## Status de Implementação

✅ **COMPLETO**

- [`DefaultSecuredBaseServiceConfig`](../../security-core-service/src/main/java/com/ia/core/security/service/DefaultSecuredBaseService.java) implementado
- 32+ ServiceConfigs em `biblia-service`
- 21+ ManagerConfigs em `biblia-view`

## Data

2024-02-15

## Revisores

- Team Lead
- Architect
