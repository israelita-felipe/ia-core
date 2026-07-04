# Caso de Teste: EIIPEnum

## Descrição
Testa o enum EIIPEnum que define valores EIIP para bases nitrogenadas.

## Enum Testado
`com.ia.core.nlp.model.EIIPEnum`

## Fluxo do Teste
1. Testar valores do enum
2. Testar valores numéricos EIIP
3. Verificar valores corretos para cada base

## Cenários

### Cenário 1: Verificar valores do enum
- **Dado**: O enum EIIPEnum
- **Quando**: Listar todos os valores
- **Então**: Deve incluir G, A, T, C

### Cenário 2: Verificar valor EIIP de G (Guanina)
- **Dado**: O valor EIIPEnum.G
- **Quando**: Obter o valor
- **Então**: Deve ser 0.0806

### Cenário 3: Verificar valor EIIP de A (Adenina)
- **Dado**: O valor EIIPEnum.A
- **Quando**: Obter o valor
- **Então**: Deve ser 0.1260

### Cenário 4: Verificar valor EIIP de T (Timina)
- **Dado**: O valor EIIPEnum.T
- **Quando**: Obter o valor
- **Então**: Deve ser 0.1335

### Cenário 5: Verificar valor EIIP de C (Citosina)
- **Dado**: O valor EIIPEnum.C
- **Quando**: Obter o valor
- **Então**: Deve ser 0.1340
