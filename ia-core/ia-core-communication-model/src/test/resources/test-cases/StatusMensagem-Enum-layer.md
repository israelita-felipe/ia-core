# Casos de Teste - StatusMensagem (Enum Layer)

## Objetivo
Testar o enum StatusMensagem que representa o status de uma mensagem enviada.

## CDU Referenciado
CDU003-Manter-Communication

## Classes Testáveis
- StatusMensagem (enum)

## Casos de Teste

### CT001 - Deve ter constante PENDENTE
**Descrição**: Verificar se o enum tem a constante PENDENTE.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O enum StatusMensagem
- When: Verifica-se a constante PENDENTE
- Then: A constante existe com descrição "Pendente"

### CT002 - Deve ter constante ENVIADA
**Descrição**: Verificar se o enum tem a constante ENVIADA.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O enum StatusMensagem
- When: Verifica-se a constante ENVIADA
- Then: A constante existe com descrição "Enviada"

### CT003 - Deve ter constante ENTREGUE
**Descrição**: Verificar se o enum tem a constante ENTREGUE.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O enum StatusMensagem
- When: Verifica-se a constante ENTREGUE
- Then: A constante existe com descrição "Entregue"

### CT004 - Deve ter constante LIDA
**Descrição**: Verificar se o enum tem a constante LIDA.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O enum StatusMensagem
- When: Verifica-se a constante LIDA
- Then: A constante existe com descrição "Lida"

### CT005 - Deve ter constante FALHA
**Descrição**: Verificar se o enum tem a constante FALHA.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O enum StatusMensagem
- When: Verifica-se a constante FALHA
- Then: A constante existe com descrição "Falha"

### CT006 - Deve ter constante CANCELADA
**Descrição**: Verificar se o enum tem a constante CANCELADA.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O enum StatusMensagem
- When: Verifica-se a constante CANCELADA
- Then: A constante existe com descrição "Cancelada"

### CT007 - Deve ter 6 constantes
**Descrição**: Verificar se o enum tem exatamente 6 constantes.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: O enum StatusMensagem
- When: Conta-se as constantes
- Then: Existem exatamente 6 constantes

### CT008 - Deve retornar descrição correta para cada constante
**Descrição**: Verificar se cada constante retorna a descrição correta.
**Pré-condições**: Nenhuma
**Fluxo Principal**:
- Given: Cada constante do enum
- When: Obtém-se a descrição
- Then: A descrição corresponde ao valor esperado
