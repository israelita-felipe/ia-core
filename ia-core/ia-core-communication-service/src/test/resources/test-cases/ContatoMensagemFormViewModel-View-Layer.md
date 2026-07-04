# ContatoMensagemFormViewModel - View Layer Test Cases

## Overview
Test cases for the `ContatoMensagemFormViewModel` class, verifying data binding and validations in the form.

## ADR-012 Compliance
- **Base Test Class**: BaseVaadinManagerTest
- **CDU References**: N/A (communication domain)
- **Test Type**: Unit tests
- **Testing Patterns**: AssertJ, AAA pattern, Mockito

## Test Scenarios

### 1. Constructor Initialization
**Given**: A ContatoMensagemFormViewModel instance
**When**: The view model is instantiated
**Then**: Should initialize with default values

#### 1.1 Default constructor
- **Given**: No parameters
- **When**: new ContatoMensagemFormViewModel() is called
- **Then**: Should create instance with default field values
- **Edge Cases**: None

#### 1.2 Constructor with data
- **Given**: Contact message data
- **When**: new ContatoMensagemFormViewModel(data) is called
- **Then**: Should initialize with provided data
- **Edge Cases**: Null data

### 2. Field Binding
**Given**: A ContatoMensagemFormViewModel
**When**: Fields are bound
**Then**: Should maintain correct binding

#### 2.1 Required field binding
- **Given**: Required fields in the form
- **When**: Fields are bound to view model
- **Then**: Should bind correctly
- **Edge Cases**: None

#### 2.2 Optional field binding
- **Given**: Optional fields in the form
- **When**: Fields are bound to view model
- **Then**: Should bind correctly
- **Edge Cases**: None

### 3. Email Validation
**Given**: A ContatoMensagemFormViewModel
**When**: Email is validated
**Then**: Should validate correctly

#### 3.1 Valid email
- **Given**: A valid email address
- **When**: Validation is performed
- **Then**: Should pass validation
- **Edge Cases**: None

#### 3.2 Invalid email
- **Given**: An invalid email address
- **When**: Validation is performed
- **Then**: Should fail validation
- **Edge Cases**: Null email, malformed email, empty email

### 4. Phone Validation
**Given**: A ContatoMensagemFormViewModel
**When**: Phone is validated
**Then**: Should validate correctly

#### 4.1 Valid phone
- **Given**: A valid phone number
- **When**: Validation is performed
- **Then**: Should pass validation
- **Edge Cases**: None

#### 4.2 Invalid phone
- **Given**: An invalid phone number
- **When**: Validation is performed
- **Then**: Should fail validation
- **Edge Cases**: Null phone, malformed phone, empty phone

### 5. Form Clear
**Given**: A ContatoMensagemFormViewModel
**When**: Form is cleared
**Then**: Should reset all fields

#### 5.1 Successful clear
- **Given**: A form with data
- **When**: clear() is called
- **Then**: Should reset all fields to default values
- **Edge Cases**: None

### 6. Load Existing Data
**Given**: A ContatoMensagemFormViewModel
**When**: Existing data is loaded
**Then**: Should populate form correctly

#### 6.1 Successful load
- **Given**: Existing contact message data
- **When**: loadData(data) is called
- **Then**: Should populate form fields
- **Edge Cases**: None

#### 6.2 Load with null data
- **Given**: Null contact message data
- **When**: loadData(data) is called
- **Then**: Should handle gracefully
- **Edge Cases**: None

## Test Flow
1. Create ContatoMensagemFormViewModel instance
2. Test constructor initialization (default, with data)
3. Test field binding (required, optional)
4. Test email validation (valid, invalid)
5. Test phone validation (valid, invalid)
6. Test form clear
7. Test load existing data (success, null)
8. Verify edge cases (null values, malformed data)
