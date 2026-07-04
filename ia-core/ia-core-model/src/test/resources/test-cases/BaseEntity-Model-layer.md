# Caso de Teste: BaseEntity

## Descrição
Testa a classe BaseEntity que fornece funcionalidades comuns para entidades JPA.

## Classe Testada
`com.ia.core.model.BaseEntity`

## Fluxo do Teste
1. Criar instância de BaseEntity
2. Testar herança de funcionalidades
3. Testar comportamento com JPA

## Cenários

### Cenário 1: Criar instância de BaseEntity
- **Dado**: Uma classe que estende BaseEntity
- **Quando**: Criar nova instância
- **Então**: Deve herdar funcionalidades de BaseEntity
- **E**: Deve ter ID gerado automaticamente

### Cenário 2: Testar herança de HasVersion
- **Dado**: Uma classe que estende BaseEntity
- **Quando**: Verificar implementação de HasVersion
- **Então**: Deve implementar interface HasVersion
- **E**: Deve ter métodos de controle de versão

### Cenário 3: Testar geração de ID com TSID
- **Dado**: Uma classe que estende BaseEntity
- **Quando**: Criar nova instância
- **Então**: ID deve ser gerado com TSID
- **E**: ID deve ser único
