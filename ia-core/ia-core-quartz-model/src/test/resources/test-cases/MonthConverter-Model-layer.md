# Casos de Teste - MonthConverter

## Descrição
Testes para a classe MonthConverter que converte Month para Integer e vice-versa.

## Classe Testada
`com.ia.core.quartz.model.periodicidade.converter.MonthConverter`

## Tipo de Teste
Unitários

## Casos de Teste

### CT001 - Verificar conversão para banco de dados
**Descrição**: Verificar se a conversão de Month para Integer funciona corretamente
**Pré-condições**: Nenhuma
**Passos**:
1. Criar uma instância do conversor
2. Converter Month.JANUARY para Integer
3. Verificar se retorna 1
4. Converter Month.FEBRUARY para Integer
5. Verificar se retorna 2
6. Converter Month.MARCH para Integer
7. Verificar se retorna 3
8. Converter Month.APRIL para Integer
9. Verificar se retorna 4
10. Converter Month.MAY para Integer
11. Verificar se retorna 5
12. Converter Month.JUNE para Integer
13. Verificar se retorna 6
14. Converter Month.JULY para Integer
15. Verificar se retorna 7
16. Converter Month.AUGUST para Integer
17. Verificar se retorna 8
18. Converter Month.SEPTEMBER para Integer
19. Verificar se retorna 9
20. Converter Month.OCTOBER para Integer
21. Verificar se retorna 10
22. Converter Month.NOVEMBER para Integer
23. Verificar se retorna 11
24. Converter Month.DECEMBER para Integer
25. Verificar se retorna 12
26. Converter null para Integer
27. Verificar se retorna null
**Resultado Esperado**: A conversão deve retornar o valor correto para cada mês e null para null

### CT002 - Verificar conversão para entidade
**Descrição**: Verificar se a conversão de Integer para Month funciona corretamente
**Pré-condições**: Nenhuma
**Passos**:
1. Criar uma instância do conversor
2. Converter Integer 1 para Month
3. Verificar se retorna Month.JANUARY
4. Converter Integer 2 para Month
5. Verificar se retorna Month.FEBRUARY
6. Converter Integer 3 para Month
7. Verificar se retorna Month.MARCH
8. Converter Integer 4 para Month
9. Verificar se retorna Month.APRIL
10. Converter Integer 5 para Month
11. Verificar se retorna Month.MAY
12. Converter Integer 6 para Month
13. Verificar se retorna Month.JUNE
14. Converter Integer 7 para Month
15. Verificar se retorna Month.JULY
16. Converter Integer 8 para Month
17. Verificar se retorna Month.AUGUST
18. Converter Integer 9 para Month
19. Verificar se retorna Month.SEPTEMBER
20. Converter Integer 10 para Month
21. Verificar se retorna Month.OCTOBER
22. Converter Integer 11 para Month
23. Verificar se retorna Month.NOVEMBER
24. Converter Integer 12 para Month
25. Verificar se retorna Month.DECEMBER
26. Converter null para Month
27. Verificar se retorna null
**Resultado Esperado**: A conversão deve retornar o mês correto para cada valor e null para null
