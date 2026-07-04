# ContatoMensagemUseCase - Service Layer Test Cases

## Overview
Test cases for the `ContatoMensagemUseCase` class, verifying CRUD operations and business validations.

## ADR-012 Compliance
- **Base Test Class**: BaseServiceTest
- **CDU References**: N/A (communication domain)
- **Test Type**: Unit tests
- **Testing Patterns**: AssertJ, AAA pattern, Mockito, Instancio

## Test Scenarios

### 1. Constructor Initialization
**Given**: A ContatoMensagemUseCase instance
**When**: The use case is instantiated
**Then**: Should initialize with repository

#### 1.1 Constructor with repository
- **Given**: A ContatoMensagemRepository instance
- **When**: new ContatoMensagemUseCase(repository) is called
- **Then**: Should initialize with provided repository
- **Edge Cases**: Null repository

### 2. Find by ID
**Given**: A ContatoMensagemUseCase
**When**: Find by ID is called
**Then**: Should return contact message

#### 2.1 Successful find by ID
- **Given**: A valid contact message ID
- **When**: findById(id) is called
- **Then**: Should return contact message
- **Edge Cases**: None

#### 2.2 Not found
- **Given**: An invalid contact message ID
- **When**: findById(id) is called
- **Then**: Should return null or throw exception
- **Edge Cases**: Null ID, negative ID

### 3. List All
**Given**: A ContatoMensagemUseCase
**When**: List all is called
**Then**: Should return list of contact messages

#### 3.1 Successful list all
- **Given**: Contact messages exist
- **When**: findAll() is called
- **Then**: Should return list of contact messages
- **Edge Cases**: Empty list

#### 3.2 With pagination
- **Given**: Contact messages with pagination parameters
- **When**: findAll(pageable) is called
- **Then**: Should return paginated results
- **Edge Cases**: Invalid page parameters

### 4. Create
**Given**: A ContatoMensagemUseCase
**When**: Create is called
**Then**: Should create new contact message

#### 4.1 Successful create
- **Given**: A valid contact message
- **When**: create(message) is called
- **Then**: Should save and return created entity
- **Edge Cases**: None

#### 4.2 Validation failure
- **Given**: An invalid contact message
- **When**: create(message) is called
- **Then**: Should throw validation exception
- **Edge Cases**: Null message, missing required fields

### 5. Update
**Given**: A ContatoMensagemUseCase
**When**: Update is called
**Then**: Should update contact message

#### 5.1 Successful update
- **Given**: A valid contact message
- **When**: update(message) is called
- **Then**: Should save and return updated entity
- **Edge Cases**: None

#### 5.2 Not found
- **Given**: A contact message with invalid ID
- **When**: update(message) is called
- **Then**: Should throw not found exception
- **Edge Cases**: None

### 6. Delete
**Given**: A ContatoMensagemUseCase
**When**: Delete is called
**Then**: Should delete contact message

#### 6.1 Successful delete
- **Given**: A valid contact message ID
- **When**: delete(id) is called
- **Then**: Should delete contact message
- **Edge Cases**: None

#### 6.2 Not found
- **Given**: An invalid contact message ID
- **When**: delete(id) is called
- **Then**: Should throw not found exception
- **Edge Cases**: None

### 7. Business Validation
**Given**: A ContatoMensagemUseCase
**When**: Business validation is performed
**Then**: Should validate business rules

#### 7.1 Valid data
- **Given**: Valid contact message data
- **When**: Validation is performed
- **Then**: Should pass validation
- **Edge Cases**: None

#### 7.2 Invalid data
- **Given**: Invalid contact message data
- **When**: Validation is performed
- **Then**: Should fail validation
- **Edge Cases**: Duplicate contact, invalid message

## Test Flow
1. Create ContatoMensagemUseCase instance
2. Test constructor initialization
3. Test find by ID (success, not found)
4. Test list all (success, pagination)
5. Test create (success, validation failure)
6. Test update (success, not found)
7. Test delete (success, not found)
8. Test business validation (valid, invalid)
9. Verify edge cases (null values, invalid parameters)
