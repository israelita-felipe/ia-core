# TipoCanal - Model Layer Test Cases

## Overview
Test cases for the `TipoCanal` enum, verifying available communication channel types.

## ADR-012 Compliance
- **Base Test Class**: BaseUnitTest
- **CDU References**: N/A (communication domain)
- **Test Type**: Unit tests
- **Testing Patterns**: AssertJ, AAA pattern

## Test Scenarios

### 1. Enum Values
**Given**: The TipoCanal enum
**When**: Values are accessed
**Then**: Should return correct channel type values

#### 1.1 Tipo EMAIL
- **Given**: TipoCanal.EMAIL
- **When**: Value is accessed
- **Then**: Should return EMAIL
- **Edge Cases**: None

#### 1.2 Tipo SMS
- **Given**: TipoCanal.SMS
- **When**: Value is accessed
- **Then**: Should return SMS
- **Edge Cases**: None

#### 1.3 Tipo WHATSAPP
- **Given**: TipoCanal.WHATSAPP
- **When**: Value is accessed
- **Then**: Should return WHATSAPP
- **Edge Cases**: None

#### 1.4 Tipo TELEGRAM
- **Given**: TipoCanal.TELEGRAM
- **When**: Value is accessed
- **Then**: Should return TELEGRAM
- **Edge Cases**: None

### 2. Enum Conversion
**Given**: A string value
**When**: Converted to TipoCanal
**Then**: Should return corresponding enum value

#### 2.1 Valid string conversion
- **Given**: A valid channel type string
- **When**: Converted to enum
- **Then**: Should return corresponding TipoCanal value
- **Edge Cases**: Case sensitivity

#### 2.2 Invalid string conversion
- **Given**: An invalid channel type string
- **When**: Converted to enum
- **Then**: Should throw exception or return null
- **Edge Cases**: Null string, empty string

### 3. Enum Validation
**Given**: A TipoCanal value
**When**: Validation is performed
**Then**: Should validate correctly

#### 3.1 Valid enum value
- **Given**: A valid TipoCanal value
- **When**: Validation is performed
- **Then**: Should pass validation
- **Edge Cases**: None

#### 3.2 Null enum value
- **Given**: A null TipoCanal value
- **When**: Validation is performed
- **Then**: Should fail validation
- **Edge Cases**: None

## Test Flow
1. Access enum values (EMAIL, SMS, WHATSAPP, TELEGRAM)
2. Test string to enum conversion (valid, invalid)
3. Test enum validation (valid, null)
4. Verify edge cases (case sensitivity, null values)
