# Casos de Teste - PeriodicidadeRepository

## Interface: PeriodicidadeRepository
**Pacote:** com.ia.core.quartz.model.periodicidade.repository
**Tipo:** Spring Data JPA Repository
**Camada:** Model

## Descrição
Repositório para acesso a dados de Periodicidade. Fornece métodos de consulta especializados incluindo busca por status ativo, por período de datas e por existência de regras de recorrência.

## Casos de Teste

### CT001 - findByAtivoTrue
**Descrição:** Verificar se o método retorna todas as periodicidades ativas.
**Pré-condições:** Banco de dados com periodicidades ativas e inativas
**Passos:**
1. Criar periodicidades com ativo=true e ativo=false
2. Salvar no banco
3. Chamar findByAtivoTrue()
4. Verificar se retorna apenas periodicidades com ativo=true
**Resultado Esperado:** Lista contendo apenas periodicidades com ativo=true.

### CT002 - findAtivasAteData
**Descrição:** Verificar se o método retorna periodicidades ativas que iniciaram até uma determinada data.
**Pré-condições:** Banco de dados com periodicidades com diferentes datas de início
**Passos:**
1. Criar periodicidades com startDate antes, igual e depois da data de referência
2. Definir ativo=true para todas
3. Salvar no banco
4. Chamar findAtivasAteData(data)
5. Verificar se retorna apenas periodicidades com startDate <= data
**Resultado Esperado:** Lista contendo apenas periodicidades ativas com startDate <= data.

### CT003 - findAllWithRecurrence
**Descrição:** Verificar se o método retorna periodicidades que possuem regra de recorrência configurada.
**Pré-condições:** Banco de dados com periodicidades com e sem regra
**Passos:**
1. Criar periodicidades com regra configurada e sem regra
2. Salvar no banco
3. Chamar findAllWithRecurrence()
4. Verificar se retorna apenas periodicidades com regra não nula
**Resultado Esperado:** Lista contendo apenas periodicidades com regra configurada.

### CT004 - findAtivasBetweenDates
**Descrição:** Verificar se o método retorna periodicidades ativas que podem ocorrer em um período específico.
**Pré-condições:** Banco de dados com periodicidades com diferentes intervalos
**Passos:**
1. Criar periodicidades com diferentes intervalos de datas
2. Definir ativo=true para todas
3. Salvar no banco
4. Chamar findAtivasBetweenDates(startDate, endDate)
5. Verificar se retorna periodicidades que podem ocorrer no período
**Resultado Esperado:** Lista contendo periodicidades ativas que podem ocorrer no período especificado.

### CT005 - Herança de JpaRepository
**Descrição:** Verificar se a interface herda de JpaRepository.
**Pré-condições:** Nenhuma
**Passos:**
1. Verificar se PeriodicidadeRepository extends JpaRepository<Periodicidade, Long>
2. Verificar se possui métodos padrão do JpaRepository (save, findById, findAll, delete)
**Resultado Esperado:** Interface deve herdar de JpaRepository com tipos corretos.

### CT006 - Anotação @Repository
**Descrição:** Verificar se a interface possui anotação @Repository.
**Pré-condições:** Nenhuma
**Passos:**
1. Verificar presença de @Repository na interface
**Resultado Esperado:** Interface deve possuir anotação @Repository.
