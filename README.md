[![Build Status](https://travis-ci.org/burakahmetyoruk/transfer_service.svg?branch=master)](https://travis-ci.org/burakahmetyoruk/transfer_service)

# transfer_service
Example Spring Boot Application for Transfer Money service.
```
This service provides:
    1. Create Account with initial balance.
    2. Transfer Money from one account to another.
        1. The account could not be overdrawn
        2. System handles the simultaneous transfer request 
           with using Java Concurrent Lock feature
```
