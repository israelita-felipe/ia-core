# Caso de Teste: EntityProjection

## Descrição
Testa a classe EntityProjection que representa projeções de entidades para consultas.

## Classe Testada
`com.ia.core.model.projection.EntityProjection`

## Fluxo do Teste
1. Criar EntityProjection com campos
2. Testar getters e setters
3. Testar validação de campos
4. Testar conversão para especificação

## Cenários

### Cenário 1: Criar EntityProjection com campos
- **Dado**: Uma lista de campos ["id", "nome", "email"]
- **Quando**: Criar EntityProjection
- **Então**: Deve armazenar os campos
- **E**: Deve manter a ordem dos campos

### Cenário 2: Testar getter de campos
- **Dado**: Um EntityProjection criado com campos
- **Quando**: Chamar getFields()
- **Então**: Deve retornar lista de campos

### Cenário 3: Testar setter de campos
- **Dado**: Um EntityProjection criado
- **Quando**: Chamar setFields(novaLista)
- **Então**: Deve atualizar os campos

### Cenário 4: Testar validação de campos vazios
- **Dado**: Uma lista vazia de campos
- **Quando**: Criar EntityProjection
- **Então**: Deve aceitar lista vazia

### Cenário 5: Testar validação de campos nulos
- **Dado**: Uma lista com campos nulos
- **Quando**: Criar EntityProjection
- **Então**: Deve filtrar campos nulos ou lançar exceção
