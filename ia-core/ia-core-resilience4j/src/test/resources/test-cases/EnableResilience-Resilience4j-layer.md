# Caso de Teste: EnableResilience

## Descrição
Testa a anotação EnableResilience que habilita o Resilience4j com aspecto AOP.

## Classe Testada
`com.ia.core.resilience4j.annotation.EnableResilience`

## Fluxo do Teste
1. Testar configuração da anotação
2. Testar import da configuração automática

## Cenários

### Cenário 1: Verificar target da anotação
- **Dado**: A anotação EnableResilience
- **Quando**: Verificar o target
- **Então**: Deve ser ElementType.TYPE

### Cenário 2: Verificar retention da anotação
- **Dado**: A anotação EnableResilience
- **Quando**: Verificar a retention
- **Então**: Deve ser RetentionPolicy.RUNTIME

### Cenário 3: Verificar import da configuração
- **Dado**: A anotação EnableResilience
- **Quando**: Verificar o @Import
- **Então**: Deve importar ResilienceAutoConfiguration
