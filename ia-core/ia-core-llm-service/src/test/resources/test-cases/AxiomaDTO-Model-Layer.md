# Caso de Teste: AxiomaDTO - Modelo/DTO

## Descrição
Caso de teste para a camada modelo/dto do DTO `AxiomaDTO`. Representação de axioma OWL com prefixo, versão URI, expressão e consistência.

## Classe Testada
`com.ia.core.owl.model.axiom.Axioma`

## Stack do DTO
| Camada | Componente | Status |
|--------|------------|--------|
| Model | `com.ia.core.owl.model.axiom.Axioma` | Implementado |
| Repository | `com.ia.core.owl.service.axioma.AxiomaRepository` | Implementado |
| Mapper | `com.ia.core.owl.service.axioma.AxiomaMapper` | Implementado |
| ServiceModel | [`com.ia.core.llm.service.model...AxiomaDTO`](../../../../ia-core-llm-service-model/src/main/java/com/ia/core/llm/service/model/axioma/AxiomaDTO.java) | Implementado |
| Service | `com.ia.core.owl.service.DefaultOwlService` | Implementado |
| API/REST | `com.ia.core.llm.rest.web.OntologiasController` | Implementado |
| View/Client | `Não implementado` | Não implementado |

## Objetivo
Documentar e validar a camada modelo/dto de `AxiomaDTO` dentro da stack completa do `ia-core-llm-*`.

## Fluxo do Teste
1. Dado o contexto do DTO `AxiomaDTO` no domínio Axioma OWL.
2. Quando a camada modelo/dto recebe ou produz dados compatíveis com `AxiomaDTO`.
3. Então o comportamento deve preservar os campos `uri`, `prefix`, `uriVersion`, `expressao`, `consistente`, `inferido`, `ativo`.
4. E deve manter rastreabilidade com [CDU/Manter-OWL: Manter OWL](../../../../CDU/Manter-OWL/README.md).
5. E deve registrar falhas, exceções ou lacunas de implementação sem expor dados sensíveis.

## Cenários

### Cenário 1: Cenário feliz da camada modelo/dto
**Given**: Dados válidos para `AxiomaDTO` no contexto `Axioma OWL`.
**When**: A camada modelo/dto processa o DTO.
**Then**: Deve preservar os campos obrigatórios, valores padrão e regras de validação aplicáveis.

### Cenário 2: Dados inválidos ou incompletos
**Given**: Dados ausentes, nulos, vazios ou fora dos limites definidos pelo DTO.
**When**: A camada modelo/dto valida ou transforma `AxiomaDTO`.
**Then**: Deve rejeitar a operação ou retornar erro estruturado quando aplicável.

### Cenário 3: Exceções e falhas esperadas
**Given**: Dependência indisponível, dado inexistente ou violação de regra de negócio.
**When**: A camada modelo/dto executa o fluxo.
**Then**: Deve propagar exceção esperada ou mapear para resposta segura conforme ADR-011.

### Cenário 4: Evidência de cobertura da stack
**Given**: Caso de teste documentado para `AxiomaDTO`.
**When**: A stack é comparada com os módulos `ia-core-llm-*`.
**Then**: Cada camada existente deve ter caso de teste correspondente; camadas não implementadas devem permanecer como lacuna explícita.

## Dependências
- JUnit 5
- AssertJ
- Mockito quando houver mocks/stubs
- Spring Boot Test para API/REST quando aplicável
- MockMvc ou WebTestClient quando aplicável
- Dados de teste sem informação sensível

## Referências
- [CDU/Manter-OWL: Manter OWL](../../../../CDU/Manter-OWL/README.md)
- ADR 010: Padrões de Nomenclatura
- ADR 012: Padrões de Teste Automatizado
- ADR 050: Diretrizes Gerais de Padronização
- ADR 052: MADR e Linguagem Normativa

## Aderência a ADRs

Este caso de teste foi gerado como documento vivo de rastreabilidade entre teste, CDU, domínio e decisões arquiteturais do `ia-core`.

### Metadados de contexto

| Campo | Valor |
|-------|-------|
| Componente | `AxiomaDTO - Modelo/DTO` |
| Camada | Modelo/DTO |
| Tipo de teste | Unitário/Integração por camada |
| Domínio | Axioma OWL |
| CDU relacionada | [CDU/Manter-OWL: Manter OWL](../../../../CDU/Manter-OWL/README.md) |
| Status da camada | Implementado |

### Matriz de conformidade

| ADR | Tema | Aplicabilidade | Critério de conformidade |
|-----|------|----------------|--------------------------|
| ADR-010 | Padrões de Nomenclatura | Obrigatório | Nomes de arquivos, classes, métodos e campos seguem ADR-010. |
| ADR-012 | Padrões de Teste Automatizado | Obrigatório | Cenário feliz, negativo, dependências, mocks e rastreabilidade documentados. |
| ADR-050 | Diretrizes Gerais de Padronização | Obrigatório | Documento UTF-8, claro e alinhado à padronização do ia-core. |
| ADR-052 | MADR e Linguagem Normativa | Obrigatório | Critérios objetivos e termos normativos sem ambiguidade. |


### Critérios de aceitação obrigatórios

- [ ] O caso informa objetivo, classe/componente testado, tipo de teste, domínio e CDU relacionado.
- [ ] O fluxo cobre cenário feliz, entradas inválidas, exceções e dependências relevantes.
- [ ] Os nomes de classes, métodos, arquivos e mensagens seguem ADR-010.
- [ ] Os asserts são explícitos, legíveis e preferencialmente usam AssertJ/JUnit 5 conforme ADR-012.
- [ ] Mocks, stubs e verificações de interação são documentados sem expor dados sensíveis.
- [ ] O documento está em UTF-8 e usa linguagem clara e consistente com ADR-050/ADR-052.
- [ ] Validar campos obrigatórios, tamanhos, valores padrão, imutabilidade/cópia e serialização quando aplicável.
- [ ] Validar que o DTO não contém lógica de negócio complexa fora do escopo de transferência e validação.


### Evidências esperadas

- Cenário feliz documentado com Given/When/Then ou fluxo equivalente.
- Cenários negativos e exceções documentados quando aplicáveis.
- Dependências, mocks, stubs e pré-condições explicitados.
- Resultados esperados verificáveis por teste automatizado.
- Rastreabilidade com CDU, domínio, classe/componente e ADRs aplicáveis.
- Lacunas de implementação explicitadas quando repository, mapper, API ou view não existirem.

### Referências ADR

- [ADR-010 - Padrões de Nomenclatura](../../../../ADR/010-nomenclature-standards.md)
- [ADR-012 - Padrões de Teste Automatizado](../../../../ADR/012-testing-patterns.md)
- [ADR-050 - Diretrizes Gerais de Padronização](../../../../ADR/050-standardization-guidelines.md)
- [ADR-052 - MADR e Linguagem Normativa](../../../../ADR/052-adopt-madr-and-rfc-normative-language-for-adrs.md)

### Referências do projeto

- CDU: [CDU/Manter-OWL: Manter OWL](../../../../CDU/Manter-OWL/README.md)
- DTO: [`AxiomaDTO`](../../../../ia-core-llm-service-model/src/main/java/com/ia/core/owl/service/model/axioma/AxiomaDTO.java)
