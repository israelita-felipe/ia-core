---
title: Use HTTP Semantics for RESTful API Design
author: Israel
status: proposed
date: 2026-06-02
tags: [http, rfc7231, rest, api]
---

## Context
The project uses Spring Boot for REST APIs. Need to ensure proper use of HTTP methods, status codes, headers, etc.

## Decision
Follow RFC 7231 guidelines for HTTP method semantics, status codes, and header usage. Use GET for safe operations, POST for create, PUT for update, DELETE for delete. Use appropriate status codes (200 OK, 201 Created, 204 No Content, 400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 500 Internal Server Error). Use Accept and Content-Type headers appropriately. Use HATEOAS principles where applicable.

## Consequences
Improves consistency and interoperability of the API. May require updates to existing controllers to align with RFC 7231 semantics.
