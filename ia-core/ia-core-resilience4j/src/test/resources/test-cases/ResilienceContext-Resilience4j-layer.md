# Caso de Teste: ResilienceContext

## Descrição
Testa o DTO ResilienceContext que contém informações de contexto para operações resilientes.

## Classe Testada
`com.ia.core.resilience4j.dto.ResilienceContext`

## Fluxo do Teste
1. Testar criação com Builder
2. Testar getters e setters
3. Testar campos do contexto

## Cenários

### Cenário 1: Criar contexto com Builder
- **Dado**: Um ResilienceProfile, Method, ProceedingJoinPoint, Resilient annotation, args e ResilienceRegistry
- **Quando**: Criar ResilienceContext usando Builder
- **Então**: Deve criar instância com todos os campos preenchidos

### Cenário 2: Verificar campo profile
- **Dado**: Um ResilienceContext criado
- **Quando**: Obter o profile
- **Então**: Deve retornar o ResilienceProfile configurado

### Cenário 3: Verificar campo method
- **Dado**: Um ResilienceContext criado
- **Quando**: Obter o method
- **Então**: Deve retornar o Method configurado

### Cenário 4: Verificar campo joinPoint
- **Dado**: Um ResilienceContext criado
- **Quando**: Obter o joinPoint
- **Então**: Deve retornar o ProceedingJoinPoint configurado

### Cenário 5: Verificar campo annotation
- **Dado**: Um ResilienceContext criado
- **Quando**: Obter a annotation
- **Então**: Deve retornar a Resilient annotation configurada

### Cenário 6: Verificar campo args
- **Dado**: Um ResilienceContext criado
- **Quando**: Obter os args
- **Então**: Deve retornar o array de args configurado

### Cenário 7: Verificar campo resilienceRegistry
- **Dado**: Um ResilienceContext criado
- **Quando**: Obter o resilienceRegistry
- **Então**: Deve retornar o ResilienceRegistry configurado
