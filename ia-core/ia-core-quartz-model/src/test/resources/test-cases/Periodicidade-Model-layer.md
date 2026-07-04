# Casos de Teste - Periodicidade

## Classe: Periodicidade
**Pacote:** com.ia.core.quartz.model.periodicidade
**Tipo:** Entidade JPA
**Camada:** Model

## Descrição
Entidade que representa a periodicidade de um evento baseada na RFC 5545 (iCalendar). Contém intervalo base, regra de recorrência, exclusão de recorrência, fuso horário, datas de exceção/inclusão e status ativo.

## Casos de Teste

### CT001 - Construtor Padrão
**Descrição:** Verificar se o construtor padrão inicializa os campos com valores padrão corretos.
**Pré-condições:** Nenhuma
**Passos:**
1. Criar instância usando construtor padrão
2. Verificar intervaloBase não é nulo
3. Verificar regra não é nulo
4. Verificar exclusaoRecorrencia não é nulo
5. Verificar zoneId não é nulo
6. Verificar exceptionDates não é nulo
7. Verificar includeDates não é nulo
8. Verificar ativo é true
**Resultado Esperado:** Todos os campos devem ser inicializados com valores padrão não nulos, ativo deve ser true.

### CT002 - Construtor Completo com Builder
**Descrição:** Verificar se o construtor com @SuperBuilder inicializa todos os campos corretamente.
**Pré-condições:** Nenhuma
**Passos:**
1. Criar instância usando Periodicidade.builder() com todos os campos
2. Definir intervaloBase, regra, exclusaoRecorrencia, zoneId, exceptionDates, includeDates, ativo
3. Verificar todos os campos foram definidos corretamente
**Resultado Esperado:** Todos os campos devem ser inicializados com os valores fornecidos.

### CT003 - Getters e Setters
**Descrição:** Verificar se os getters e setters funcionam corretamente para todos os campos.
**Pré-condições:** Instância criada
**Passos:**
1. Criar instância
2. Definir valor para cada campo usando setters
3. Recuperar valor usando getters
4. Verificar valores correspondem aos definidos
**Resultado Esperado:** Getters devem retornar os valores definidos pelos setters.

### CT004 - Constantes TABLE_NAME e SCHEMA_NAME
**Descrição:** Verificar se as constantes de tabela e schema estão corretas.
**Pré-condições:** Nenhuma
**Passos:**
1. Acessar Periodicidade.TABLE_NAME
2. Acessar Periodicidade.SCHEMA_NAME
3. Verificar valores correspondem ao esperado
**Resultado Esperado:** TABLE_NAME deve ser "QRTZ_PERIODICIDADE", SCHEMA_NAME deve ser "QUARTZ".

### CT005 - Herança de BaseEntity
**Descrição:** Verificar se a classe herda corretamente de BaseEntity.
**Pré-condições:** Nenhuma
**Passos:**
1. Criar instância de Periodicidade
2. Verificar se é instância de BaseEntity
3. Verificar se possui campos de BaseEntity (id, version, etc.)
**Resultado Esperado:** Periodicidade deve ser instância de BaseEntity.

### CT006 - Anotações JPA
**Descrição:** Verificar se as anotações JPA estão presentes e corretas.
**Pré-condições:** Nenhuma
**Passos:**
1. Verificar presença de @Entity
2. Verificar presença de @Table com nome e schema corretos
3. Verificar presença de @Embedded em intervaloBase, regra, exclusaoRecorrencia
4. Verificar presença de @ElementCollection em exceptionDates, includeDates
**Resultado Esperado:** Todas as anotações JPA devem estar presentes e configuradas corretamente.
