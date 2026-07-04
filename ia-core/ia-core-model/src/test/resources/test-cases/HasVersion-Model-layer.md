# Caso de Teste: HasVersion

## Descrição
Testa a interface HasVersion que fornece contrato padronizado para controle de versão em entidades JPA.

## Interface Testada
`com.ia.core.model.HasVersion<T>`

## Fluxo do Teste
1. Criar implementação de HasVersion
2. Testar métodos de versão
3. Testar compatibilidade entre diferentes tipos de versão

## Cenários

### Cenário 1: Implementar HasVersion com Long
- **Dado**: Uma classe que implementa HasVersion<Long>
- **Quando**: Definir getVersion() e setVersion()
- **Então**: Deve funcionar corretamente com tipo Long
- **E**: Deve usar valor padrão 0L

### Cenário 2: Implementar HasVersion com Integer
- **Dado**: Uma classe que implementa HasVersion<Integer>
- **Quando**: Definir getVersion() e setVersion()
- **Então**: Deve funcionar corretamente com tipo Integer
- **E**: Deve usar valor padrão 0

### Cenário 3: Testar valor padrão de versão
- **Dado**: Uma implementação de HasVersion
- **Quando**: Não definir versão explicitamente
- **Então**: Deve usar valor padrão (0 para Integer, 0L para Long)

### Cenário 4: Testar incremento de versão
- **Dado**: Uma implementação de HasVersion com versão atual
- **Quando**: Incrementar versão
- **Então**: Deve atualizar valor da versão
- **E**: Deve manter tipo correto
