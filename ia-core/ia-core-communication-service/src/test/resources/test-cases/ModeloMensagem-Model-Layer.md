# ModeloMensagem - Model Layer Test Cases

## Overview
Test cases for the `ModeloMensagem` entity, verifying message template and variables.

## ADR-012 Compliance
- **Base Test Class**: BaseUnitTest
- **CDU References**: N/A (communication domain)
- **Test Type**: Unit tests
- **Testing Patterns**: AssertJ, AAA pattern, Mockito

## Test Scenarios

### 1. Constructor Initialization
**Given**: A ModeloMensagem instance
**When**: The entity is instantiated
**Then**: Should initialize with default values

#### 1.1 Default constructor
- **Given**: No parameters
- **When**: new ModeloMensagem() is called
- **Then**: Should create instance with default field values
- **Edge Cases**: None

#### 1.2 Constructor with template
- **Given**: A template string
- **When**: new ModeloMensagem(template) is called
- **Then**: Should initialize with provided template
- **Edge Cases**: Null template, empty template

### 2. Template Management
**Given**: A ModeloMensagem instance
**When**: Template is set
**Then**: Should maintain template correctly

#### 2.1 Valid template
- **Given**: A ModeloMensagem with valid template
- **When**: Template is accessed
- **Then**: Should return the template
- **Edge Cases**: None

#### 2.2 Template with variables
- **Given**: A ModeloMensagem with template containing variables
- **When**: Template is accessed
- **Then**: Should return template with variable placeholders
- **Edge Cases**: None

#### 2.3 Empty template
- **Given**: A ModeloMensagem with empty template
- **When**: Validation is performed
- **Then**: Should fail validation
- **Edge Cases**: Blank template

### 3. Variable Management
**Given**: A ModeloMensagem instance
**When**: Variables are defined
**Then**: Should maintain variable list correctly

#### 3.1 Adding variables
- **Given**: A ModeloMensagem and variable definitions
- **When**: Variables are added
- **Then**: Should maintain the variable list
- **Edge Cases**: Duplicate variables, null variables

#### 3.2 Variable substitution
- **Given**: A ModeloMensagem with template and variables
- **When**: Variables are substituted in template
- **Then**: Should replace placeholders with actual values
- **Edge Cases**: Missing variable values

#### 3.3 Empty variable list
- **Given**: A ModeloMensagem without variables
- **When**: Variable list is accessed
- **Then**: Should return empty list
- **Edge Cases**: None

### 4. Field Validation
**Given**: A ModeloMensagem instance
**When**: Required fields are set
**Then**: Should validate field constraints

#### 4.1 Required fields
- **Given**: A ModeloMensagem without required fields
- **When**: Validation is performed
- **Then**: Should fail validation
- **Edge Cases**: None

#### 4.2 Optional fields
- **Given**: A ModeloMensagem without optional fields
- **When**: Optional fields are accessed
- **Then**: Should return null or default values
- **Edge Cases**: None

## Test Flow
1. Create ModeloMensagem instance
2. Test constructor initialization
3. Test template management (valid, with variables, empty)
4. Test variable management (add, substitute, empty)
5. Test field validation
6. Verify edge cases (null values, missing variables)
