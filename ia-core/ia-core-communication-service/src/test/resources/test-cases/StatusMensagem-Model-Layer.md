# StatusMensagem - Model Layer Test Cases

## Overview
Test cases for the `StatusMensagem` enum, verifying possible message status values.

## ADR-012 Compliance
- **Base Test Class**: BaseUnitTest
- **CDU References**: N/A (communication domain)
- **Test Type**: Unit tests
- **Testing Patterns**: AssertJ, AAA pattern

## Test Scenarios

### 1. Enum Values
**Given**: The StatusMensagem enum
**When**: Values are accessed
**Then**: Should return correct status values

#### 1.1 Status PENDENTE
- **Given**: StatusMensagem.PENDENTE
- **When**: Value is accessed
- **Then**: Should return PENDENTE
- **Edge Cases**: None

#### 1.2 Status ENVIADA
- **Given**: StatusMensagem.ENVIADA
- **When**: Value is accessed
- **Then**: Should return ENVIADA
- **Edge Cases**: None

#### 1.3 Status FALHA
- **Given**: StatusMensagem.FALHA
- **When**: Value is accessed
- **Then**: Should return FALHA
- **Edge Cases**: None

### 2. Enum Conversion
**Given**: A string value
**When**: Converted to StatusMensagem
**Then**: Should return corresponding enum value

#### 2.1 Valid string conversion
- **Given**: A valid status string
- **When**: Converted to enum
- **Then**: Should return corresponding StatusMensagem value
- **Edge Cases**: Case sensitivity

#### 2.2 Invalid string conversion
- **Given**: An invalid status string
- **When**: Converted to enum
- **Then**: Should throw exception or return null
- **Edge Cases**: Null string, empty string

### 3. Enum Validation
**Given**: A StatusMensagem value
**When**: Validation is performed
**Then**: Should validate correctly

#### 3.1 Valid enum value
- **Given**: A valid StatusMensagem value
- **When**: Validation is performed
- **Then**: Should pass validation
- **Edge Cases**: None

#### 3.2 Null enum value
- **Given**: A null StatusMensagem value
- **When**: Validation is performed
- **Then**: Should fail validation
- **Edge Cases**: None

## Test Flow
1. Access enum values (PENDENTE, ENVIADA, FALHA)
2. Test string to enum conversion (valid, invalid)
3. Test enum validation (valid, null)
4. Verify edge cases (case sensitivity, null values)
