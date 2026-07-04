# Casos de Teste - Frequencia (Enum)

## Descrição
Testes para o enum Frequencia que representa as frequências de recorrência conforme RFC 5545.

## Classe Testada
`com.ia.core.quartz.model.periodicidade.Frequencia`

## Tipo de Teste
Unitários

## Casos de Teste

### CT001 - Verificar valores do enum
**Descrição**: Verificar se os valores do enum estão corretamente configurados
**Pré-condições**: Nenhuma
**Passos**:
1. Obter o valor DIARIAMENTE
2. Verificar se rfcName é "DAILY"
3. Verificar se código é 1
4. Obter o valor SEMANALMENTE
5. Verificar se rfcName é "WEEKLY"
6. Verificar se código é 2
7. Obter o valor MENSALMENTE
8. Verificar se rfcName é "MONTHLY"
9. Verificar se código é 3
10. Obter o valor ANUALMENTE
11. Verificar se rfcName é "YEARLY"
12. Verificar se código é 4
**Resultado Esperado**: Todos os valores do enum devem estar corretos

### CT002 - Verificar método of(int)
**Descrição**: Verificar se o método of(int) retorna a frequência correta para um código válido
**Pré-condições**: Nenhuma
**Passos**:
1. Chamar Frequencia.of(1)
2. Verificar se retorna DIARIAMENTE
3. Chamar Frequencia.of(2)
4. Verificar se retorna SEMANALMENTE
5. Chamar Frequencia.of(3)
6. Verificar se retorna MENSALMENTE
7. Chamar Frequencia.of(4)
8. Verificar se retorna ANUALMENTE
9. Chamar Frequencia.of(99)
10. Verificar se retorna null
**Resultado Esperado**: O método deve retornar a frequência correta para códigos válidos e null para códigos inválidos

### CT003 - Verificar método fromRfcName(String)
**Descrição**: Verificar se o método fromRfcName(String) retorna a frequência correta para um nome RFC válido
**Pré-condições**: Nenhuma
**Passos**:
1. Chamar Frequencia.fromRfcName("DAILY")
2. Verificar se retorna DIARIAMENTE
3. Chamar Frequencia.fromRfcName("WEEKLY")
4. Verificar se retorna SEMANALMENTE
5. Chamar Frequencia.fromRfcName("MONTHLY")
6. Verificar se retorna MENSALMENTE
7. Chamar Frequencia.fromRfcName("YEARLY")
8. Verificar se retorna ANUALMENTE
9. Chamar Frequencia.fromRfcName("daily")
10. Verificar se retorna DIARIAMENTE (case-insensitive)
11. Chamar Frequencia.fromRfcName(null)
12. Verificar se retorna null
13. Chamar Frequencia.fromRfcName("INVALID")
14. Verificar se retorna null
**Resultado Esperado**: O método deve retornar a frequência correta para nomes RFC válidos (case-insensitive) e null para nomes inválidos ou null
