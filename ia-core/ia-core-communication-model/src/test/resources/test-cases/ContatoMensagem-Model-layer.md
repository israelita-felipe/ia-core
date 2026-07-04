# Casos de Teste - ContatoMensagem (Model Layer)

## Objetivo
Testar a entidade ContatoMensagem que representa a associação entre um grupo de contatos e um número de telefone.

## CDU Referenciado
CDU003-Manter-Communication

## Classes Testáveis
- ContatoMensagem (entidade JPA)

## Casos de Teste

### CT001 - Deve criar contato com campos obrigatórios
**Descrição**: Verificar se é possível criar um contato com todos os campos obrigatórios preenchidos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ContatoMensagem com grupoContato e telefone preenchidos
- When: O contato é criado
- Then: O contato é criado com sucesso e todos os campos obrigatórios estão preenchidos

### CT002 - Deve validar campo grupoContato obrigatório
**Descrição**: Verificar se o campo grupoContato é obrigatório.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ContatoMensagem sem grupoContato
- When: O contato é persistido
- Then: Uma exceção de validação é lançada

### CT003 - Deve validar campo telefone obrigatório
**Descrição**: Verificar se o campo telefone é obrigatório.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ContatoMensagem sem telefone
- When: O contato é persistido
- Then: Uma exceção de validação é lançada

### CT004 - Deve criar contato com campo nome opcional
**Descrição**: Verificar se é possível criar um contato com o campo nome opcional.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ContatoMensagem com nome preenchido
- When: O contato é criado
- Then: O contato é criado com sucesso e o campo nome está preenchido

### CT005 - Deve criar contato usando builder
**Descrição**: Verificar se é possível criar um contato usando o SuperBuilder.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O builder de ContatoMensagem
- When: O contato é construído usando o builder
- Then: O contato é criado com sucesso com todos os campos especificados

### CT006 - Deve clonar contato usando toBuilder
**Descrição**: Verificar se é possível clonar um contato usando o método toBuilder.
**Pré-condições**: Uma instância de ContatoMensagem existente
**Fluxo Principal**:
- Given: Uma instância de ContatoMensagem com campos preenchidos
- When: O contato é clonado usando toBuilder()
- Then: Um novo contato é criado com os mesmos valores

### CT007 - Deve herdar de BaseEntity
**Descrição**: Verificar se ContatoMensagem herda corretamente de BaseEntity.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ContatoMensagem
- When: Verifica-se a herança
- Then: ContatoMensagem é instância de BaseEntity

### CT008 - Deve ter nome de tabela correto
**Descrição**: Verificar se o nome da tabela está correto.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A constante TABLE_NAME de ContatoMensagem
- When: Verifica-se o valor
- Then: O nome da tabela é "COM_CONTATO_MENSAGEM"

### CT009 - Deve ter nome de schema correto
**Descrição**: Verificar se o nome do schema está correto.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A constante SCHEMA_NAME de ContatoMensagem
- When: Verifica-se o valor
- Then: O nome do schema é "COMMUNICATION"
