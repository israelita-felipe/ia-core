# Caso de Teste: CoreTokenizerService

## Descrição
Testa a interface CoreTokenizerService que define o contrato para serviços de tokenização.

## Interface Testada
`com.ia.core.nlp.model.services.CoreTokenizerService`

## Fluxo do Teste
1. Verificar método de tokenização
2. Testar validação de entrada

## Cenários

### Cenário 1: Verificar método tokenize
- **Dado**: A interface CoreTokenizerService
- **Quando**: Verificar método tokenize
- **Então**: Deve ter método tokenize(String text)
- **E**: Deve retornar array de strings
- **E**: Deve lançar IllegalArgumentException se texto for null
