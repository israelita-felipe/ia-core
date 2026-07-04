# Casos de Teste - HasVariavel (Service Model Layer)

## Objetivo
Testar a interface HasVariavel que define métodos para obter variáveis de um DTO.

## CDU Referenciado
CDU003-Manter-Communication

## Classes Testáveis
- HasVariavel (interface)

## Casos de Teste

### CT001 - Deve ter método getContext
**Descrição**: Verificar se a interface HasVariavel tem o método getContext.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A interface HasVariavel
- When: Verifica-se o método
- Then: O método getContext existe e retorna Map<Variavel, Object>

### CT002 - Deve ter método getVariaveis
**Descrição**: Verificar se a interface HasVariavel tem o método getVariaveis.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A interface HasVariavel
- When: Verifica-se o método
- Then: O método getVariaveis existe e retorna List<Variavel>

### CT003 - Deve ter método contemVariavel
**Descrição**: Verificar se a interface HasVariavel tem o método contemVariavel.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: A interface HasVariavel
- When: Verifica-se o método
- Then: O metodo contemVariavel existe e retorna boolean

### CT004 - Deve implementar getVariaveis com default
**Descrição**: Verificar se o método getVariaveis tem implementação default que retorna lista de chaves do contexto.
**Pré-condições**: Uma implementação de HasVariavel com contexto
**Fluxo Principal**:
- Given: Uma implementação de HasVariavel com contexto preenchido
- When: O método getVariaveis é chamado
- Then: Uma lista com as chaves do contexto é retornada

### CT005 - Deve implementar contemVariavel com default
**Descrição**: Verificar se o método contemVariavel tem implementação default que verifica se a variável existe no contexto.
**Pré-condições**: Uma implementação de HasVariavel com contexto
**Fluxo Principal**:
- Given: Uma implementação de HasVariavel com contexto preenchido
- When: O método contemVariavel é chamado com uma variável existente
- Then: O método retorna true
