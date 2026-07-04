# Casos de Teste - DayOfWeekConverter

## Descrição
Testes para a classe DayOfWeekConverter que converte DayOfWeek para Integer e vice-versa.

## Classe Testada
`com.ia.core.quartz.model.periodicidade.converter.DayOfWeekConverter`

## Tipo de Teste
Unitários

## Casos de Teste

### CT001 - Verificar conversão para banco de dados
**Descrição**: Verificar se a conversão de DayOfWeek para Integer funciona corretamente
**Pré-condições**: Nenhuma
**Passos**:
1. Criar uma instância do conversor
2. Converter DayOfWeek.MONDAY para Integer
3. Verificar se retorna 1
4. Converter DayOfWeek.TUESDAY para Integer
5. Verificar se retorna 2
6. Converter DayOfWeek.WEDNESDAY para Integer
7. Verificar se retorna 3
8. Converter DayOfWeek.THURSDAY para Integer
9. Verificar se retorna 4
10. Converter DayOfWeek.FRIDAY para Integer
11. Verificar se retorna 5
12. Converter DayOfWeek.SATURDAY para Integer
13. Verificar se retorna 6
14. Converter DayOfWeek.SUNDAY para Integer
15. Verificar se retorna 7
16. Converter null para Integer
17. Verificar se retorna null
**Resultado Esperado**: A conversão deve retornar o valor correto para cada dia da semana e null para null

### CT002 - Verificar conversão para entidade
**Descrição**: Verificar se a conversão de Integer para DayOfWeek funciona corretamente
**Pré-condições**: Nenhuma
**Passos**:
1. Criar uma instância do conversor
2. Converter Integer 1 para DayOfWeek
3. Verificar se retorna DayOfWeek.MONDAY
4. Converter Integer 2 para DayOfWeek
5. Verificar se retorna DayOfWeek.TUESDAY
6. Converter Integer 3 para DayOfWeek
7. Verificar se retorna DayOfWeek.WEDNESDAY
8. Converter Integer 4 para DayOfWeek
9. Verificar se retorna DayOfWeek.THURSDAY
10. Converter Integer 5 para DayOfWeek
11. Verificar se retorna DayOfWeek.FRIDAY
12. Converter Integer 6 para DayOfWeek
13. Verificar se retorna DayOfWeek.SATURDAY
14. Converter Integer 7 para DayOfWeek
15. Verificar se retorna DayOfWeek.SUNDAY
16. Converter null para DayOfWeek
17. Verificar se retorna null
**Resultado Esperado**: A conversão deve retornar o dia correto para cada valor e null para null
