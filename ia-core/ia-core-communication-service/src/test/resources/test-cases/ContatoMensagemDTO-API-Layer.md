# ContatoMensagemDTO - API Layer Test Cases

## Overview
Test cases for the `ContatoMensagemDTO` class, verifying data transfer fields between contact and message.

## ADR-012 Compliance
- **Base Test Class**: BaseUnitTest
- **CDU References**: N/A (communication domain)
- **Test Type**: Unit tests
- **Testing Patterns**: AssertJ, AAA pattern, Instancio

## Test Scenarios

### 1. Constructor Initialization
**Given**: A ContatoMensagemDTO instance
**When**: The DTO is instantiated
**Then**: Should initialize with default values

#### 1.1 Default constructor
- **Given**: No parameters
- **When**: new ContatoMensagemDTO() is called
- **Then**: Should create instance with default field values
- **Edge Cases**: None

#### 1.2 Builder pattern construction
- **Given**: Builder pattern usage
- **When**: ContatoMensagemDTO.builder() is used
- **Then**: Should create instance with provided values
- **Edge Cases**: Null values in builder

### 2. Field Validation
**Given**: A ContatoMensagemDTO instance
**When**: Fields are set
**Then**: Should validate field constraints

#### 2.1 Required fields
- **Given**: A ContatoMensagemDTO without required fields
- **When**: Validation is performed
- **Then**: Should fail validation
- **Edge Cases**: None

#### 2.2 Optional fields
- **Given**: A ContatoMensagemDTO without optional fields
- **When**: Optional fields are accessed
- **Then**: Should return null or default values
- **Edge Cases**: None

### 3. CAMPOS Constant Verification
**Given**: The ContatoMensagemDTO class
**When**: CAMPOS constant is accessed
**Then**: Should contain all field names

#### 3.1 CAMPOS constant exists
- **Given**: ContatoMensagemDTO class
- **When**: CAMPOS is accessed
- **Then**: Should return non-null constant
- **Edge Cases**: None

#### 3.2 CAMPOS contains all fields
- **Given**: ContatoMensagemDTO class
- **When**: CAMPOS is verified
- **Then**: Should contain all field names
- **Edge Cases**: None

#### 3.3 CAMPOS reflection test
- **Given**: ContatoMensagemDTO class
- **When**: CAMPOS is verified via reflection
- **Then**: Should match actual field names
- **Edge Cases**: None

### 4. Field Access Methods
**Given**: A ContatoMensagemDTO instance
**When**: Field access methods are called
**Then**: Should return correct values

#### 4.1 Get field by name
- **Given**: A ContatoMensagemDTO with field values
- **When**: getField(fieldName) is called
- **Then**: Should return field value
- **Edge Cases**: Invalid field name

#### 4.2 Set field by name
- **Given**: A ContatoMensagemDTO instance
- **When**: setField(fieldName, value) is called
- **Then**: Should set field value
- **Edge Cases**: Invalid field name, null value

## Test Flow
1. Create ContatoMensagemDTO instance
2. Test constructor initialization (default, builder)
3. Test field validation (required, optional)
4. Test CAMPOS constant verification (exists, contains fields, reflection)
5. Test field access methods (get, set)
6. Verify edge cases (null values, invalid field names)
