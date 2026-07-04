# Casos de Teste - GrupoContato (Model Layer)

## Objetivo
Testar a entidade GrupoContato que representa um grupo de contatos para envio de mensagens em massa.

## CDU Referenciado
CDU003-Manter-Communication

## Classes Testáveis
- GrupoContato (entidade JPA)

## Casos de Teste

### CT001 - Deve criar grupo com campos obrigatórios
**Descrição**: Verificar se é possível criar um grupo com todos os campos obrigatórios preenchidos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de GrupoContato com nome preenchido
- When: O grupo é criado
- Then: O grupo é criado com sucesso e todos os campos obrigatórios estão preenchidos

### CT002 - Deve validar campo nome obrigatório
**Descrição**: Verificar se o campo nome é obrigatório.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de GrupoContato sem nome
- When: O grupo é persistido
- Then: Uma exceção de validação é lançada

### CT003 - Deve criar grupo com campo descricao opcional
**Descrição**: Verificar se é possível criar um grupo com o campo descricao opcional.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de GrupoContato com descricao preenchida
- When: O grupo é criado
- Then: O grupo é criado com sucesso e o campo descricao está preenchido

### CT004 - Deve inicializar campo ativo com valor padrão true
**Descrição**: Verificar se o campo ativo é inicializado com valor padrão true.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de GrupoContato sem especificar ativo
- When: O grupo é criado
- Then: O campo ativo é true

### CT005 - Deve criar grupo com lista de contatos vazia
**Descrição**: Verificar se a lista de contatos é inicializada como vazia.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de GrupoContato
- When: O grupo é criado
- Then: A lista de contatos é inicializada como ArrayList vazia

### CT006 - Deve criar grupo usando builder
**Descrição**: Verificar se é possível criar um grupo usando o SuperBuilder.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O builder de GrupoContato
- When: O grupo é construído usando o builder
- Then: O grupo é criado com sucesso com todos os campos especificados

### CT007 - Deve clonar grupo usando toBuilder
**Descrição**: Verificar se é possível clonar um grupo usando o método toBuilder.
**Pré-condições**: Uma instância de GrupoContato existente
**Fluxo Principal**:
- Given: Uma instância de GrupoContato com campos preenchidos
- When: O grupo é clonado usando toBuilder()
- Then: Um novo grupo é criado com os mesmos valores

### CT008 - Deve herdar de BaseEntity
**Descrição**: Verificar se GrupoContato herda corretamente de BaseEntity.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de GrupoContato
- When: Verifica-se a herança
- Then: GrupoContato é instância de BaseEntity

### CT009 - Deve ter nome de tabela correto
**Descrição**: Verificar se o nome da tabela está correto.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A constante TABLE_NAME de GrupoContato
- When: Verifica-se o valor
- Then: O nome da tabela é "COM_GRUPO_CONTATO"

### CT010 - Deve ter nome de schema correto
**Descrição**: Verificar se o nome do schema está correto.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A constante SCHEMA_NAME de GrupoContato
- When: Verifica-se o valor
- Then: O nome do schema é "COMMUNICATION"

### CT011 - Deve adicionar contato à lista de contatos
**Descrição**: Verificar se é possível adicionar um contato à lista de contatos.
**Pré-condições**: Uma instância de GrupoContato e um ContatoMensagem
**Fluxo Principal**:
- Given: Uma instância de GrupoContato e um ContatoMensagem
- When: O contato é adicionado à lista de contatos
- Then: A lista de contatos contém o contato adicionado
