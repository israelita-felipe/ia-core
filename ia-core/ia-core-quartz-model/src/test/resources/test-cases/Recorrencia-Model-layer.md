# Casos de Teste - Recorrencia

## Descrição
Testes para a classe Recorrencia que representa a regra de recorrência de uma Periodicidade.

## Classe Testada
`com.ia.core.quartz.model.periodicidade.Recorrencia`

## Tipo de Teste
Unitários

## Casos de Teste

### CT001 - Verificar construtor padrão
**Descrição**: Verificar se o construtor padrão inicializa todos os campos como null ou vazios
**Pré-condições**: Nenhuma
**Passos**:
1. Criar uma instância usando o construtor padrão
2. Verificar se frequency é null
3. Verificar se intervalValue é null
4. Verificar se byDay está vazio
5. Verificar se byMonthDay está vazio
6. Verificar se byMonth está vazio
7. Verificar se bySetPosition está vazio
8. Verificar se untilDate é null
9. Verificar se countLimit é null
10. Verificar se weekStartDay é null
11. Verificar se byYearDay está vazio
12. Verificar se byWeekNo está vazio
13. Verificar se byHour está vazio
14. Verificar se byMinute está vazio
15. Verificar se bySecond está vazio
**Resultado Esperado**: Todos os campos devem ser null ou vazios

### CT002 - Verificar construtor com todos os parâmetros
**Descrição**: Verificar se o construtor com todos os parâmetros inicializa corretamente os campos
**Pré-condições**: Nenhuma
**Passos**:
1. Criar uma instância com todos os parâmetros
2. Verificar se frequency foi definido corretamente
3. Verificar se intervalValue foi definido corretamente
4. Verificar se byDay foi definido corretamente
5. Verificar se byMonthDay foi definido corretamente
6. Verificar se byMonth foi definido corretamente
7. Verificar se bySetPosition foi definido corretamente
8. Verificar se untilDate foi definido corretamente
9. Verificar se countLimit foi definido corretamente
10. Verificar se weekStartDay foi definido corretamente
11. Verificar se byYearDay foi definido corretamente
12. Verificar se byWeekNo foi definido corretamente
13. Verificar se byHour foi definido corretamente
14. Verificar se byMinute foi definido corretamente
15. Verificar se bySecond foi definido corretamente
**Resultado Esperado**: Todos os campos devem ser inicializados corretamente
