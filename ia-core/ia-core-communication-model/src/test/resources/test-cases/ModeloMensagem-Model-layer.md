# Casos de Teste - ModeloMensagem (Model Layer)

## Objetivo
Testar a entidade ModeloMensagem que representa um modelo de mensagem reutilizável.

## CDU Referenciado
CDU003-Manter-Communication

## Classes Testáveis
- ModeloMensagem (entidade JPA)

## Casos de Teste

### CT001 - Deve criar modelo com campos obrigatórios
**Descrição**: Verificar se é possível criar um modelo com todos os campos obrigatórios preenchidos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagem com nome, corpoModelo e tipoCanal preenchidos
- When: O modelo é criado
- Then: O modelo é criado com sucesso e todos os campos obrigatórios estão preenchidos

### CT002 - Deve validar campo nome obrigatório
**Descrição**: Verificar se o campo nome é obrigatório.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagem sem nome
- When: O modelo é persistido
- Then: Uma exceção de validação é lançada

### CT003 - Deve validar campo corpoModelo obrigatório
**Descrição**: Verificar se o campo corpoModelo é obrigatório.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagem sem corpoModelo
- When: O modelo é persistido
- Then: Uma exceção de validação é lançada

### CT004 - Deve validar campo tipoCanal obrigatório
**Descrição**: Verificar se o campo tipoCanal é obrigatório.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagem sem tipoCanal
- When: O modelo é persistido
- Then: Uma exceção de validação é lançada

### CT005 - Deve criar modelo com campo descricao opcional
**Descrição**: Verificar se é possível criar um modelo com o campo descricao opcional.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagem com descricao preenchida
- When: O modelo é criado
- Then: O modelo é criado com sucesso e o campo descricao está preenchido

### CT006 - Deve inicializar campo ativo com valor padrão true
**Descrição**: Verificar se o campo ativo é inicializado com valor padrão true.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagem sem especificar ativo
- When: O modelo é criado
- Then: O campo ativo é true

### CT007 - Deve criar modelo usando builder
**Descrição**: Verificar se é possível criar um modelo usando o SuperBuilder.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O builder de ModeloMensagem
- When: O modelo é construído usando o builder
- Then: O modelo é criado com sucesso com todos os campos especificados

### CT008 - Deve clonar modelo usando toBuilder
**Descrição**: Verificar se é possível clonar um modelo usando o método toBuilder.
**Pré-condições**: Uma instância de ModeloMensagem existente
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagem com campos preenchidos
- When: O modelo é clonado usando toBuilder()
- Then: Um novo modelo é criado com os mesmos valores

### CT009 - Deve herdar de BaseEntity
**Descrição**: Verificar se ModeloMensagem herda corretamente de BaseEntity.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de ModeloMensagem
- When: Verifica-se a herança
- Then: ModeloMensagem é instância de BaseEntity

### CT010 - Deve ter nome de tabela correto
**Descrição**: Verificar se o nome da tabela está correto.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A constante TABLE_NAME de ModeloMensagem
- When: Verifica-se o valor
- Then: O nome da tabela é "COM_MODELO_MENSAGEM"

### CT011 - Deve ter nome de schema correto
**Descrição**: Verificar se o nome do schema está correto.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A constante SCHEMA_NAME de ModeloMensagem
- When: Verifica-se o valor
- Then: O nome do schema é "COMMUNICATION"
