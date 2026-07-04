# Casos de Teste - SchedulerConfigTrigger

## Classe: SchedulerConfigTrigger
**Pacote:** com.ia.core.quartz.model.scheduler
**Tipo:** Entidade JPA
**Camada:** Model

## Descrição
Entidade que representa um trigger do Quartz. Armazena informações de execução de um job agendado, incluindo nome, scheduler, grupo, tempos de execução, estado e prioridade. Implementa Comparable para ordenação.

## Casos de Teste

### CT001 - Construtor Padrão
**Descrição:** Verificar se o construtor padrão cria instância válida.
**Pré-condições:** Nenhuma
**Passos:**
1. Criar instância usando construtor padrão
2. Verificar instância não é nula
**Resultado Esperado:** Instância deve ser criada com sucesso.

### CT002 - Construtor Completo com Builder
**Descrição:** Verificar se o construtor com @SuperBuilder inicializa todos os campos corretamente.
**Pré-condições:** Nenhuma
**Passos:**
1. Criar instância usando SchedulerConfigTrigger.builder() com todos os campos
2. Definir todos os campos (triggerName, schedulerName, triggerGroup, jobName, jobGroup, description, nextFireTime, prevFireTime, priority, triggerState, triggerType, triggerStartTime, endTime, calendarName, misFireInstr, jobData)
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
1. Acessar SchedulerConfigTrigger.TABLE_NAME
2. Acessar SchedulerConfigTrigger.SCHEMA_NAME
3. Verificar valores correspondem ao esperado
**Resultado Esperado:** TABLE_NAME deve ser "QRTZ_TRIGGERS", SCHEMA_NAME deve ser "QUARTZ".

### CT005 - Método compareTo
**Descrição:** Verificar se o método compareTo ordena corretamente por triggerName.
**Pré-condições:** Duas instâncias com triggerNames diferentes
**Passos:**
1. Criar trigger1 com triggerName="A"
2. Criar trigger2 com triggerName="B"
3. Chamar trigger1.compareTo(trigger2)
4. Verificar resultado < 0
5. Chamar trigger2.compareTo(trigger1)
6. Verificar resultado > 0
7. Criar trigger3 com triggerName="A"
8. Chamar trigger1.compareTo(trigger3)
9. Verificar resultado = 0
**Resultado Esperado:** compareTo deve retornar valor negativo se triggerName < outro, positivo se >, zero se igual.

### CT006 - Método compareTo com null
**Descrição:** Verificar se o método compareTo trata null corretamente.
**Pré-condições:** Instâncias com triggerName null
**Passos:**
1. Criar trigger1 com triggerName=null
2. Criar trigger2 com triggerName="A"
3. Chamar trigger1.compareTo(trigger2)
4. Verificar resultado < 0
5. Chamar trigger2.compareTo(trigger1)
6. Verificar resultado > 0
7. Criar trigger3 com triggerName=null
8. Chamar trigger1.compareTo(trigger3)
9. Verificar resultado = 0
**Resultado Esperado:** compareTo deve tratar null como menor que qualquer valor não nulo.

### CT007 - Método equals
**Descrição:** Verificar se o método equals compara corretamente por triggerName.
**Pré-condições:** Duas instâncias
**Passos:**
1. Criar trigger1 com triggerName=UUID.randomUUID()
2. Criar trigger2 com mesmo triggerName
3. Verificar trigger1.equals(trigger2) é true
4. Criar trigger3 com triggerName diferente
5. Verificar trigger1.equals(trigger3) é false
6. Verificar trigger1.equals(null) é false
7. Verificar trigger1.equals("string") é false
**Resultado Esperado:** equals deve retornar true apenas para instâncias com mesmo triggerName.

### CT008 - Método equals com null
**Descrição:** Verificar se o método equals trata triggerName null corretamente.
**Pré-condições:** Instância com triggerName null
**Passos:**
1. Criar trigger1 com triggerName=null
2. Criar trigger2 com triggerName=null
3. Verificar trigger1.equals(trigger2) é false (referências diferentes)
4. Verificar trigger1.equals(trigger1) é true (mesma referência)
**Resultado Esperado:** equals com triggerName null deve usar comparação de referência.

### CT009 - Método hashCode
**Descrição:** Verificar se o método hashCode gera valor consistente.
**Pré-condições:** Instância com triggerName
**Passos:**
1. Criar trigger com triggerName=UUID.randomUUID()
2. Chamar hashCode() duas vezes
3. Verificar valores são iguais
4. Criar outro trigger com mesmo triggerName
5. Verificar hashCodes são iguais
**Resultado Esperado:** hashCode deve retornar mesmo valor para mesma triggerName.

### CT010 - Método hashCode com null
**Descrição:** Verificar se o método hashCode trata triggerName null corretamente.
**Pré-condições:** Instância com triggerName null
**Passos:**
1. Criar trigger com triggerName=null
2. Chamar hashCode()
3. Verificar não lança exceção
**Resultado Esperado:** hashCode com triggerName null deve usar super.hashCode().

### CT011 - Anotações JPA
**Descrição:** Verificar se as anotações JPA estão presentes e corretas.
**Pré-condições:** Nenhuma
**Passos:**
1. Verificar presença de @Entity
2. Verificar presença de @Table com nome e schema corretos
3. Verificar presença de @Id em triggerName
4. Verificar presença de @Column em todos os campos
**Resultado Esperado:** Todas as anotações JPA devem estar presentes e configuradas corretamente.
