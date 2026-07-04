# GrupoContato - Model Layer Test Cases

## Overview
Test cases for the `GrupoContato` entity, verifying contact grouping and configuration fields.

## ADR-012 Compliance
- **Base Test Class**: BaseUnitTest
- **CDU References**: N/A (communication domain)
- **Test Type**: Unit tests
- **Testing Patterns**: AssertJ, AAA pattern, Mockito

## Test Scenarios

### 1. Constructor Initialization
**Given**: A GrupoContato instance
**When**: The entity is instantiated
**Then**: Should initialize with default values

#### 1.1 Default constructor
- **Given**: No parameters
- **When**: new GrupoContato() is called
- **Then**: Should create instance with default field values
- **Edge Cases**: None

#### 1.2 Constructor with name
- **Given**: A group name
- **When**: new GrupoContato(name) is called
- **Then**: Should initialize with provided name
- **Edge Cases**: Null name, empty name

### 2. Contact Management
**Given**: A GrupoContato instance
**When**: Contacts are added or removed
**Then**: Should maintain contact list correctly

#### 2.1 Adding contacts to group
- **Given**: A GrupoContato and Contact entities
- **When**: Contacts are added to the group
- **Then**: Should maintain the contact list
- **Edge Cases**: Duplicate contacts, null contacts

#### 2.2 Removing contacts from group
- **Given**: A GrupoContato with contacts
- **When**: Contacts are removed from the group
- **Then**: Should update the contact list
- **Edge Cases**: Removing non-existent contact

#### 2.3 Empty group
- **Given**: A GrupoContato without contacts
- **When**: Contact list is accessed
- **Then**: Should return empty list
- **Edge Cases**: None

### 3. Field Validation
**Given**: A GrupoContato instance
**When**: Required fields are set
**Then**: Should validate field constraints

#### 3.1 Name validation
- **Given**: A GrupoContato with invalid name
- **When**: Validation is performed
- **Then**: Should fail validation
- **Edge Cases**: Null name, empty name, blank name

#### 3.2 Optional fields
- **Given**: A GrupoContato without optional fields
- **When**: Optional fields are accessed
- **Then**: Should return null or default values
- **Edge Cases**: None

## Test Flow
1. Create GrupoContato instance
2. Test constructor initialization
3. Test contact management (add, remove, empty)
4. Test field validation
5. Verify edge cases (null values, duplicates)
