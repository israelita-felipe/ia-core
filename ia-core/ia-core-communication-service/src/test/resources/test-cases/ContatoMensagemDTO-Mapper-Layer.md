# ContatoMensagemTranslator - Mapper Layer Test Cases

## Overview
Test cases for the `ContatoMensagemTranslator` class, verifying conversion between entity and DTO.

## ADR-012 Compliance
- **Base Test Class**: BaseUnitTest
- **CDU References**: N/A (communication domain)
- **Test Type**: Unit tests
- **Testing Patterns**: AssertJ, AAA pattern, Instancio

## Test Scenarios

### 1. Entity to DTO Conversion
**Given**: A ContatoMensagem entity
**When**: Converted to DTO
**Then**: Should maintain all fields correctly

#### 1.1 Successful entity to DTO conversion
- **Given**: A valid ContatoMensagem entity
- **When**: toDTO(entity) is called
- **Then**: Should return DTO with all fields mapped
- **Edge Cases**: None

#### 1.2 Entity with null fields to DTO
- **Given**: A ContatoMensagem entity with null fields
- **When**: toDTO(entity) is called
- **Then**: Should return DTO with null fields
- **Edge Cases**: None

### 2. DTO to Entity Conversion
**Given**: A ContatoMensagemDTO
**When**: Converted to entity
**Then**: Should maintain data integrity

#### 2.1 Successful DTO to entity conversion
- **Given**: A valid ContatoMensagemDTO
- **When**: toEntity(dto) is called
- **Then**: Should return entity with all fields mapped
- **Edge Cases**: None

#### 2.2 DTO with null fields to entity
- **Given**: A ContatoMensagemDTO with null fields
- **When**: toEntity(dto) is called
- **Then**: Should return entity with null fields
- **Edge Cases**: None

### 3. Bidirectional Conversion
**Given**: A ContatoMensagem entity
**When**: Converted to DTO and back
**Then**: Should maintain data integrity

#### 3.1 Entity to DTO to entity round-trip
- **Given**: A valid ContatoMensagem entity
- **When**: entity -> DTO -> entity conversion is performed
- **Then**: Should return entity with same data
- **Edge Cases**: None

#### 3.2 DTO to entity to DTO round-trip
- **Given**: A valid ContatoMensagemDTO
- **When**: DTO -> entity -> DTO conversion is performed
- **Then**: Should return DTO with same data
- **Edge Cases**: None

### 4. Relationship Mapping
**Given**: A ContatoMensagem with relationships
**When**: Converted to DTO
**Then**: Should map relationships correctly

#### 4.1 Contact relationship mapping
- **Given**: A ContatoMensagem with Contact
- **When**: toDTO(entity) is called
- **Then**: Should map Contact relationship
- **Edge Cases**: Null Contact

#### 4.2 Message relationship mapping
- **Given**: A ContatoMensagem with Message
- **When**: toDTO(entity) is called
- **Then**: Should map Message relationship
- **Edge Cases**: Null Message

## Test Flow
1. Create ContatoMensagem entity and DTO instances
2. Test entity to DTO conversion (success, null fields)
3. Test DTO to entity conversion (success, null fields)
4. Test bidirectional conversion (round-trip)
5. Test relationship mapping (Contact, Message)
6. Verify edge cases (null relationships)
