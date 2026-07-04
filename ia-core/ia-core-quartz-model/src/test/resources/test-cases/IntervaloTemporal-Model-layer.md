# Casos de Teste - IntervaloTemporal

## Descrição
Testes para a classe IntervaloTemporal que representa um intervalo temporal para eventos.

## Classe Testada
`com.ia.core.quartz.model.periodicidade.IntervaloTemporal`

## Tipo de Teste
Unitários

## Casos de Teste

### CT001 - Verificar construtor com data e hora
**Descrição**: Verificar se o construtor com data e hora inicializa corretamente os campos
**Pré-condições**: Nenhuma
**Passos**:
1. Criar uma instância com startDate, startTime, endDate e endTime
2. Verificar se startDate foi definido corretamente
3. Verificar se startTime foi definido corretamente
4. Verificar se endDate foi definido corretamente
5. Verificar se endTime foi definido corretamente
**Resultado Esperado**: Todos os campos devem ser inicializados corretamente

### CT002 - Verificar construtor para evento no mesmo dia
**Descrição**: Verificar se o construtor para evento no mesmo dia inicializa corretamente os campos
**Pré-condições**: Nenhuma
**Passos**:
1. Criar uma instância com date, startTime e endTime
2. Verificar se startDate foi definido como date
3. Verificar se startTime foi definido corretamente
4. Verificar se endDate foi definido como date
5. Verificar se endTime foi definido corretamente
**Resultado Esperado**: startDate e endDate devem ser iguais a date

### CT003 - Verificar construtor padrão
**Descrição**: Verificar se o construtor padrão inicializa todos os campos como null
**Pré-condições**: Nenhuma
**Passos**:
1. Criar uma instância usando o construtor padrão
2. Verificar se startDate é null
3. Verificar se startTime é null
4. Verificar se endDate é null
5. Verificar se endTime é null
**Resultado Esperado**: Todos os campos devem ser null
