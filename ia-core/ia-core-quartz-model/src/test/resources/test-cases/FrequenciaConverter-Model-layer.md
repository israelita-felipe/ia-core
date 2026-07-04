# Casos de Teste - FrequenciaConverter

## Descrição
Testes para a classe FrequenciaConverter que converte Frequencia para Integer e vice-versa.

## Classe Testada
`com.ia.core.quartz.model.periodicidade.converter.FrequenciaConverter`

## Tipo de Teste
Unitários

## Casos de Teste

### CT001 - Verificar conversão para banco de dados
**Descrição**: Verificar se a conversão de Frequencia para Integer funciona corretamente
**Pré-condições**: Nenhuma
**Passos**:
1. Criar uma instância do conversor
2. Converter Frequencia.DIARIAMENTE para Integer
3. Verificar se retorna 1
4. Converter Frequencia.SEMANALMENTE para Integer
5. Verificar se retorna 2
6. Converter Frequencia.MENSALMENTE para Integer
7. Verificar se retorna 3
8. Converter Frequencia.ANUALMENTE para Integer
9. Verificar se retorna 4
10. Converter null para Integer
11. Verificar se retorna null
**Resultado Esperado**: A conversão deve retornar o código correto para cada frequência e null para null

### CT002 - Verificar conversão para entidade
**Descrição**: Verificar se a conversão de Integer para Frequencia funciona corretamente
**Pré-condições**: Nenhuma
**Passos**:
1. Criar uma instância do conversor
2. Converter Integer 1 para Frequencia
3. Verificar se retorna Frequencia.DIARIAMENTE
4. Converter Integer 2 para Frequencia
5. Verificar se retorna Frequencia.SEMANALMENTE
6. Converter Integer 3 para Frequencia
7. Verificar se retorna Frequencia.MENSALMENTE
8. Converter Integer 4 para Frequencia
9. Verificar se retorna Frequencia.ANUALMENTE
10. Converter null para Frequencia
11. Verificar se retorna null
12. Converter Integer 99 para Frequencia
13. Verificar se retorna null
**Resultado Esperado**: A conversão deve retornar a frequência correta para cada código e null para códigos inválidos ou null
