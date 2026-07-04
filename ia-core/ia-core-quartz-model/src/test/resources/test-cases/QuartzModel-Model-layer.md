# Casos de Teste - QuartzModel

## Classe: QuartzModel
**Pacote:** com.ia.core.quartz.model
**Tipo:** Classe de Constantes
**Camada:** Model

## Descrição
Classe de constantes para o módulo Quartz. Define prefixos de tabelas e nomes de schema utilizados pelo Quartz.

## Casos de Teste

### CT001 - Constante TABLE_PREFIX
**Descrição:** Verificar se a constante TABLE_PREFIX tem o valor correto.
**Pré-condições:** Nenhuma
**Passos:**
1. Acessar QuartzModel.TABLE_PREFIX
2. Verificar valor corresponde ao esperado
**Resultado Esperado:** TABLE_PREFIX deve ser "QRTZ_".

### CT002 - Constante SCHEMA
**Descrição:** Verificar se a constante SCHEMA tem o valor correto.
**Pré-condições:** Nenhuma
**Passos:**
1. Acessar QuartzModel.SCHEMA
2. Verificar valor corresponde ao esperado
**Resultado Esperado:** SCHEMA deve ser "QUARTZ".

### CT003 - Classe não é instanciável
**Descrição:** Verificar se a classe não possui construtor público (classe utilitária).
**Pré-condições:** Nenhuma
**Passos:**
1. Tentar criar instância usando new QuartzModel()
2. Verificar se não há construtor público
**Resultado Esperado:** Classe deve ter apenas construtor padrão (se houver) ou ser não instanciável.
