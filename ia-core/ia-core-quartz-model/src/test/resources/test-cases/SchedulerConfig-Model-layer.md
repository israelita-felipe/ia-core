# Casos de Teste - SchedulerConfig

## Classe: SchedulerConfig
**Pacote:** com.ia.core.quartz.model.scheduler
**Tipo:** Entidade JPA
**Camada:** Model

## Descrição
Entidade que armazena a configuração de agendamento de jobs. Contém nome da classe do job e periodicidade associada.

## Casos de Teste

### CT001 - Construtor Padrão
**Descrição:** Verificar se o construtor padrão inicializa periodicidade com valor padrão.
**Pré-condições:** Nenhuma
**Passos:**
1. Criar instância usando construtor padrão
2. Verificar periodicidade não é nulo
**Resultado Esperado:** periodicidade deve ser inicializada com nova instância de Periodicidade.

### CT002 - Construtor Completo com Builder
**Descrição:** Verificar se o construtor com @SuperBuilder inicializa todos os campos corretamente.
**Pré-condições:** Nenhuma
**Passos:**
1. Criar instância usando SchedulerConfig.builder() com todos os campos
2. Definir jobClassName e periodicidade
3. Verificar todos os campos foram definidos corretamente
**Resultado Esperado:** Todos os campos devem ser inicializados com os valores fornecidos.

### CT003 - Getters e Setters
**Descrição:** Verificar se os getters e setters funcionam corretamente para todos os campos.
**Pré-condições:** Instância criada
**Passos:**
1. Criar instância
2. Definir jobClassName usando setter
3. Definir periodicidade usando setter
4. Recuperar valores usando getters
5. Verificar valores correspondem aos definidos
**Resultado Esperado:** Getters devem retornar os valores definidos pelos setters.

### CT004 - Constantes TABLE_NAME e SCHEMA_NAME
**Descrição:** Verificar se as constantes de tabela e schema estão corretas.
**Pré-condições:** Nenhuma
**Passos:**
1. Acessar SchedulerConfig.TABLE_NAME
2. Acessar SchedulerConfig.SCHEMA_NAME
3. Verificar valores correspondem ao esperado
**Resultado Esperado:** TABLE_NAME deve ser "QRTZ_SCHEDULER_CONFIG", SCHEMA_NAME deve ser "QUARTZ".

### CT005 - Herança de BaseEntity
**Descrição:** Verificar se a classe herda corretamente de BaseEntity.
**Pré-condições:** Nenhuma
**Passos:**
1. Criar instância de SchedulerConfig
2. Verificar se é instância de BaseEntity
3. Verificar se possui campos de BaseEntity (id, version, etc.)
**Resultado Esperado:** SchedulerConfig deve ser instância de BaseEntity.

### CT006 - Anotações JPA
**Descrição:** Verificar se as anotações JPA estão presentes e corretas.
**Pré-condições:** Nenhuma
**Passos:**
1. Verificar presença de @Entity
2. Verificar presença de @Table com nome e schema corretos
3. Verificar presença de @NamedEntityGraph
4. Verificar presença de @OneToOne em periodicidade
5. Verificar jobClassName possui @Column com nullable=false
**Resultado Esperado:** Todas as anotações JPA devem estar presentes e configuradas corretamente.
