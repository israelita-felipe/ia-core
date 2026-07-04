# Caso de Teste: ResourceNotFoundException

## Descrição
Testa a classe ResourceNotFoundException que representa recursos não encontrados no sistema.

## Classe Testada
`com.ia.core.model.exception.ResourceNotFoundException`

## Fluxo do Teste
1. Criar exceção com tipo e ID do recurso
2. Criar exceção com mensagem personalizada
3. Verificar propriedades da exceção
4. Lançar exceção e validar comportamento

## Cenários

### Cenário 1: Criar exceção com tipo e ID do recurso
- **Dado**: Um tipo de recurso "Usuario" e um ID "123"
- **Quando**: Criar ResourceNotFoundException com tipo e ID
- **Então**: Deve usar código de erro padrão "RESOURCE_NOT_FOUND"
- **E**: Deve gerar mensagem formatada automaticamente
- **E**: Deve armazenar o tipo do recurso
- **E**: Deve armazenar o ID do recurso

### Cenário 2: Criar exceção com mensagem personalizada
- **Dado**: Uma mensagem personalizada
- **Quando**: Criar ResourceNotFoundException com mensagem
- **Então**: Deve usar código de erro padrão "RESOURCE_NOT_FOUND"
- **E**: Deve usar a mensagem fornecida
- **E**: Deve ter tipo e ID como null

### Cenário 3: Verificar getters de tipo e ID
- **Dado**: Uma exceção criada com tipo e ID
- **Quando**: Chamar getResourceType() e getResourceId()
- **Então**: Deve retornar o tipo do recurso
- **E**: Deve retornar o ID do recurso

### Cenário 4: Lançar exceção e validar comportamento
- **Dado**: Um tipo de recurso "Pedido" e um ID "456"
- **Quando**: Lançar ResourceNotFoundException
- **Então**: Deve ser instância de ResourceNotFoundException
- **E**: Deve ter mensagem formatada corretamente
- **E**: Deve ter código de erro padrão
