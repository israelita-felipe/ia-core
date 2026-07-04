# EmailService - Service Layer Test Cases

## Overview
Test cases for the `EmailService` class, verifying email sending with SMTP configurations.

## ADR-012 Compliance
- **Base Test Class**: BaseServiceTest
- **CDU References**: N/A (communication domain)
- **Test Type**: Unit tests
- **Testing Patterns**: AssertJ, AAA pattern, Mockito

## Test Scenarios

### 1. Constructor Initialization
**Given**: An EmailService instance
**When**: The service is instantiated
**Then**: Should initialize with JavaMailSender

#### 1.1 Constructor with JavaMailSender
- **Given**: A JavaMailSender instance
- **When**: new EmailService(javaMailSender) is called
- **Then**: Should initialize with provided JavaMailSender
- **Edge Cases**: Null JavaMailSender

#### 1.2 Constructor with SMTP configuration
- **Given**: SMTP configuration properties
- **When**: new EmailService(config) is called
- **Then**: Should initialize with SMTP configuration
- **Edge Cases**: Invalid configuration

### 2. Email Sending
**Given**: An EmailService instance
**When**: An email is sent
**Then**: Should use correct SMTP configuration

#### 2.1 Successful email sending
- **Given**: A valid email message
- **When**: sendEmail(message) is called
- **Then**: Should send email successfully
- **Edge Cases**: None

#### 2.2 Failed email sending
- **Given**: An invalid email message or SMTP error
- **When**: sendEmail(message) is called
- **Then**: Should handle failure appropriately
- **Edge Cases**: SMTP connection failure, authentication failure

### 3. Recipient Validation
**Given**: An EmailService instance
**When**: Recipient is validated
**Then**: Should validate recipient correctly

#### 3.1 Valid recipient
- **Given**: A valid email address
- **When**: Validation is performed
- **Then**: Should pass validation
- **Edge Cases**: None

#### 3.2 Invalid recipient
- **Given**: An invalid email address
- **When**: Validation is performed
- **Then**: Should fail validation
- **Edge Cases**: Null recipient, malformed email, empty recipient

### 4. Content Validation
**Given**: An EmailService instance
**When**: Content is validated
**Then**: Should validate content correctly

#### 4.1 Valid content
- **Given**: Valid email subject and body
- **When**: Validation is performed
- **Then**: Should pass validation
- **Edge Cases**: None

#### 4.2 Invalid content
- **Given**: Invalid email subject or body
- **When**: Validation is performed
- **Then**: Should fail validation
- **Edge Cases**: Null subject, empty body, oversized content

### 5. SMTP Configuration
**Given**: An EmailService instance
**When**: SMTP configuration is used
**Then**: Should use correct configuration

#### 5.1 Default SMTP configuration
- **Given**: No custom configuration provided
- **When**: Email is sent
- **Then**: Should use default SMTP settings
- **Edge Cases**: None

#### 5.2 Custom SMTP configuration
- **Given**: Custom SMTP configuration provided
- **When**: Email is sent
- **Then**: Should use custom SMTP settings
- **Edge Cases**: Invalid custom configuration

## Test Flow
1. Create EmailService instance
2. Test constructor initialization
3. Test email sending (success, failure)
4. Test recipient validation (valid, invalid)
5. Test content validation (valid, invalid)
6. Test SMTP configuration (default, custom)
7. Verify edge cases (null values, malformed emails)
