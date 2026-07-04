# Casos de Teste - Mensagem (Model Layer)

## Objetivo
Testar a entidade Mensagem que representa uma mensagem de comunicação.

## CDU Referenciado
CDU003-Manter-Communication

## Classes Testáveis
- Mensagem (entidade JPA)

## Casos de Teste

### CT001 - Deve criar mensagem com campos obrigatórios
**Descrição**: Verificar se é possível criar uma mensagem com todos os campos obrigatórios preenchidos.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de Mensagem com telefoneDestinatario, corpoMensagem, tipoCanal e statusMensagem preenchidos
- When: A mensagem é criada
- Then: A mensagem é criada com sucesso e todos os campos obrigatórios estão preenchidos

### CT002 - Deve validar campo telefoneDestinatario obrigatório
**Descrição**: Verificar se o campo telefoneDestinatario é obrigatório.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de Mensagem sem telefoneDestinatario
- When: A mensagem é persistida
- Then: Uma exceção de validação é lançada

### CT003 - Deve validar campo corpoMensagem obrigatório
**Descrição**: Verificar se o campo corpoMensagem é obrigatório.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de Mensagem sem corpoMensagem
- When: A mensagem é persistida
- Then: Uma exceção de validação é lançada

### CT004 - Deve validar campo tipoCanal obrigatório
**Descrição**: Verificar se o campo tipoCanal é obrigatório.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de Mensagem sem tipoCanal
- When: A mensagem é persistida
- Then: Uma exceção de validação é lançada

### CT005 - Deve validar campo statusMensagem obrigatório
**Descrição**: Verificar se o campo statusMensagem é obrigatório.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de Mensagem sem statusMensagem
- When: A mensagem é persistida
- Then: Uma exceção de validação é lançada

### CT006 - Deve criar mensagem com campos opcionais
**Descrição**: Verificar se é possível criar uma mensagem com campos opcionais.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de Mensagem com nomeDestinatario, idExterno, dataEnvio, dataEntrega, dataLeitura e motivoFalha preenchidos
- When: A mensagem é criada
- Then: A mensagem é criada com sucesso e todos os campos opcionais estão preenchidos

### CT007 - Deve criar mensagem usando builder
**Descrição**: Verificar se é possível criar uma mensagem usando o SuperBuilder.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O builder de Mensagem
- When: A mensagem é construída usando o builder
- Then: A mensagem é criada com sucesso com todos os campos especificados

### CT008 - Deve clonar mensagem usando toBuilder
**Descrição**: Verificar se é possível clonar uma mensagem usando o método toBuilder.
**Pré-condições**: Uma instância de Mensagem existente
**Fluxo Principal**:
- Given: Uma instância de Mensagem com campos preenchidos
- When: A mensagem é clonada usando toBuilder()
- Then: Uma nova mensagem é criada com os mesmos valores

### CT009 - Deve herdar de BaseEntity
**Descrição**: Verificar se Mensagem herda corretamente de BaseEntity.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Uma instância de Mensagem
- When: Verifica-se a herança
- Then: Mensagem é instância de BaseEntity

### CT010 - Deve ter nome de tabela correto
**Descrição**: Verificar se o nome da tabela está correto.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A constante TABLE_NAME de Mensagem
- When: Verifica-se o valor
- Then: O nome da tabela é "COM_MENSAGEM"

### CT011 - Deve ter nome de schema correto
**Descrição**: Verificar se o nome do schema está correto.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A constante SCHEMA_NAME de Mensagem
- When: Verifica-se o valor
- Then: O nome do schema é "COMMUNICATION"
