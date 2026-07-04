# ContatoMensagemController - API Layer Test Cases

## Overview
Test cases for the `ContatoMensagemController` class, verifying REST endpoints for contact message operations.

## ADR-012 Compliance
- **Base Test Class**: BaseAPITest
- **CDU References**: N/A (communication domain)
- **Test Type**: Unit tests
- **Testing Patterns**: AssertJ, AAA pattern, Mockito, MockMvc

## Test Scenarios

### 1. Constructor Initialization
**Given**: A ContatoMensagemController instance
**When**: The controller is instantiated
**Then**: Should initialize with service

#### 1.1 Constructor with service
- **Given**: A ContatoMensagemService instance
- **When**: new ContatoMensagemController(service) is called
- **Then**: Should initialize with provided service
- **Edge Cases**: Null service

### 2. GET /api/contato-mensagem/{id}
**Given**: A ContatoMensagemController
**When**: GET request is made with ID
**Then**: Should return contact message

#### 2.1 Successful retrieval
- **Given**: A valid contact message ID
- **When**: GET /api/contato-mensagem/{id} is called
- **Then**: Should return 200 OK with contact message
- **Edge Cases**: None

#### 2.2 Not found
- **Given**: An invalid contact message ID
- **When**: GET /api/contato-mensagem/{id} is called
- **Then**: Should return 404 Not Found
- **Edge Cases**: Null ID, negative ID

### 3. GET /api/contato-mensagem
**Given**: A ContatoMensagemController
**When**: GET request is made
**Then**: Should return list of contact messages

#### 3.1 Successful listing
- **Given**: Contact messages exist
- **When**: GET /api/contato-mensagem is called
- **Then**: Should return 200 OK with list
- **Edge Cases**: Empty list

#### 3.2 With pagination
- **Given**: Contact messages with pagination parameters
- **When**: GET /api/contato-mensagem?page=0&size=10 is called
- **Then**: Should return paginated results
- **Edge Cases**: Invalid page parameters

### 4. POST /api/contato-mensagem
**Given**: A ContatoMensagemController
**When**: POST request is made
**Then**: Should create new contact message

#### 4.1 Successful creation
- **Given**: A valid contact message DTO
- **When**: POST /api/contato-mensagem is called
- **Then**: Should return 201 Created with created entity
- **Edge Cases**: None

#### 4.2 Validation failure
- **Given**: An invalid contact message DTO
- **When**: POST /api/contato-mensagem is called
- **Then**: Should return 400 Bad Request with validation errors
- **Edge Cases**: Null DTO, missing required fields

### 5. PUT /api/contato-mensagem/{id}
**Given**: A ContatoMensagemController
**When**: PUT request is made with ID
**Then**: Should update contact message

#### 5.1 Successful update
- **Given**: A valid contact message ID and DTO
- **When**: PUT /api/contato-mensagem/{id} is called
- **Then**: Should return 200 OK with updated entity
- **Edge Cases**: None

#### 5.2 Not found
- **Given**: An invalid contact message ID
- **When**: PUT /api/contato-mensagem/{id} is called
- **Then**: Should return 404 Not Found
- **Edge Cases**: None

### 6. DELETE /api/contato-mensagem/{id}
**Given**: A ContatoMensagemController
**When**: DELETE request is made with ID
**Then**: Should delete contact message

#### 6.1 Successful deletion
- **Given**: A valid contact message ID
- **When**: DELETE /api/contato-mensagem/{id} is called
- **Then**: Should return 204 No Content
- **Edge Cases**: None

#### 6.2 Not found
- **Given**: An invalid contact message ID
- **When**: DELETE /api/contato-mensagem/{id} is called
- **Then**: Should return 404 Not Found
- **Edge Cases**: None

## Test Flow
1. Create ContatoMensagemController instance
2. Test GET by ID (success, not found)
3. Test GET list (success, pagination)
4. Test POST create (success, validation failure)
5. Test PUT update (success, not found)
6. Test DELETE (success, not found)
7. Verify edge cases (null values, invalid parameters)
