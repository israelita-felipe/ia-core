# Mensagem - Model Layer Test Cases

## Overview
Test cases for the `Mensagem` entity, verifying content fields, status, and channel type.

## ADR-012 Compliance
- **Base Test Class**: BaseUnitTest
- **CDU References**: N/A (communication domain)
- **Test Type**: Unit tests
- **Testing Patterns**: AssertJ, AAA pattern, Mockito

## Test Scenarios

### 1. Constructor Initialization
**Given**: A Mensagem instance
**When**: The entity is instantiated
**Then**: Should initialize with default values

#### 1.1 Default constructor
- **Given**: No parameters
- **When**: new Mensagem() is called
- **Then**: Should create instance with default field values
- **Edge Cases**: None

#### 1.2 Constructor with content
- **Given**: Content and channel type
- **When**: new Mensagem(content, channel) is called
- **Then**: Should initialize with provided values
- **Edge Cases**: Null content, null channel

### 2. Status Management
**Given**: A Mensagem instance
**When**: Status is set
**Then**: Should maintain status correctly

#### 2.1 Status PENDENTE
- **Given**: A Mensagem with status PENDENTE
- **When**: Status is accessed
- **Then**: Should return PENDENTE
- **Edge Cases**: None

#### 2.2 Status ENVIADA
- **Given**: A Mensagem with status ENVIADA
- **When**: Status is accessed
- **Then**: Should return ENVIADA
- **Edge Cases**: None

#### 2.3 Status FALHA
- **Given**: A Mensagem with status FALHA
- **When**: Status is accessed
- **Then**: Should return FALHA
- **Edge Cases**: None

### 3. Channel Type Validation
**Given**: A Mensagem instance
**When**: Channel type is set
**Then**: Should validate channel type

#### 3.1 Valid channel types
- **Given**: A Mensagem with valid channel type (EMAIL, WHATSAPP, TELEGRAM, SMS)
- **When**: Channel type is accessed
- **Then**: Should return the valid channel type
- **Edge Cases**: None

#### 3.2 Invalid channel type
- **Given**: A Mensagem with invalid channel type
- **When**: Validation is performed
- **Then**: Should fail validation
- **Edge Cases**: Null channel type

### 4. Content Validation
**Given**: A Mensagem instance
**When**: Content is set
**Then**: Should validate content constraints

#### 4.1 Valid content
- **Given**: A Mensagem with valid content
- **When**: Content is accessed
- **Then**: Should return the content
- **Edge Cases**: None

#### 4.2 Empty content
- **Given**: A Mensagem with empty content
- **When**: Validation is performed
- **Then**: Should fail validation
- **Edge Cases**: Blank content

## Test Flow
1. Create Mensagem instance
2. Test constructor initialization
3. Test status management (PENDENTE, ENVIADA, FALHA)
4. Test channel type validation
5. Test content validation
6. Verify edge cases (null values, empty content)
